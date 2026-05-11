package eu.autopay.pay.demo.ui.webview

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.autopay.pay.demo.R
import eu.autopay.pay.demo.ui.common.ErrorCompose
import eu.autopay.pay.demo.ui.common.Toolbar
import eu.autopay.pay.demo.ui.nav.NavigationDispatcher
import eu.autopay.pay.sdk.model.APError
import eu.autopay.pay.sdk.model.APEvent
import eu.autopay.pay.sdk.ui.webview.APWebView
import eu.autopay.pay.sdk.utils.errorString

@Composable
fun WebViewCompose(viewModel: WebViewViewModel = viewModel()) {
    val error by viewModel.error.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val url by viewModel.success.collectAsStateWithLifecycle()
    val failureReason by viewModel.failureReason.collectAsStateWithLifecycle()

    var webView: APWebView? by remember { mutableStateOf(null) }
    val transactionDone by viewModel.transactionDone.collectAsState()

    BackHandler(enabled = webView?.canGoBack() ?: false) { webView?.goBack() }

    // observe if transaction was finished
    LaunchedEffect(transactionDone) {
        if (transactionDone) {
            NavigationDispatcher.popBack()
            NavigationDispatcher.toCheckStatus(viewModel.orderId, "")
            viewModel.resetTransaction()
        }
    }

    // Save WebView state on lifecycle stop so it survives process death
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle, viewModel) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                webView?.let { view ->
                    try {
                        val bundle = Bundle()
                        view.saveState(bundle)
                        viewModel.saveStateToHandle(bundle)
                    } catch (_: Throwable) {}
                }
            }
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Toolbar(stringResource(R.string.demo_home_form_webview)) },
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (isLoading)
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                )
            else if (failureReason != null) {
                NavigationDispatcher.popBack()
                NavigationDispatcher.toCheckStatus(viewModel.orderId, failureReason)
            } else {
                error?.let {
                    ErrorCompose(
                        modifier = Modifier.align(Alignment.Center),
                        throwable = it,
                        onRetry = viewModel::makeTransaction,
                    )
                }
                    ?: url?.let { url ->
                        AndroidView(
                            factory = { context ->
                                // keep WebView in ViewModel to handle config change
                                val existing = viewModel.webView
                                val aPWebView =
                                    if (existing != null) {
                                        (existing.parent as? ViewGroup)?.removeView(existing)
                                        existing
                                    } else {
                                        APWebView(context)
                                    }

                                if (viewModel.savedWebViewState != null && aPWebView !== existing) {
                                    // restore state after process death
                                    try {
                                        aPWebView.restoreState(viewModel.savedWebViewState!!)
                                        attachCallbacks(
                                            context = context,
                                            apWebView = aPWebView,
                                            viewModel = viewModel,
                                        )
                                    } catch (_: Throwable) {
                                        loadUrl(
                                            context = context,
                                            apWebView = aPWebView,
                                            viewModel = viewModel,
                                            url = url,
                                        )
                                    }
                                } else if (existing != null) {
                                    // reattach callbacks after config change
                                    attachCallbacks(
                                        context = context,
                                        apWebView = aPWebView,
                                        viewModel = viewModel,
                                    )
                                } else {
                                    // first load
                                    loadUrl(
                                        context = context,
                                        apWebView = aPWebView,
                                        viewModel = viewModel,
                                        url = url,
                                    )
                                }

                                webView = aPWebView
                                viewModel.webView = aPWebView
                                aPWebView
                            },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
            }
        }
    }
}

private fun loadUrl(
    context: Context,
    apWebView: APWebView,
    viewModel: WebViewViewModel,
    url: String,
) {
    apWebView.loadUrl(
        url = url,
        transactionCallback = { onPaymentDone(viewModel) },
        eventCallback = { onPaymentEvent(context, it) },
        errorCallback = { onPaymentError(context, it) },
    )
}

private fun attachCallbacks(context: Context, apWebView: APWebView, viewModel: WebViewViewModel) {
    apWebView.attachCallbacks(
        transactionCallback = { onPaymentDone(viewModel) },
        eventCallback = { onPaymentEvent(context, it) },
        errorCallback = { onPaymentError(context, it) },
    )
}

private fun onPaymentDone(viewModel: WebViewViewModel) {
    // save transaction state as webview may finished it after app process death
    viewModel.onTransactionDone()
}

private fun onPaymentEvent(context: Context, event: APEvent?) {
    Toast.makeText(context, event.toString(), Toast.LENGTH_SHORT).show()
}

private fun onPaymentError(context: Context, error: APError?) {
    val message = error?.type?.errorString()?.let { res -> context.getString(res) }
    val commonErrorText = context.getString(eu.autopay.pay.sdk.R.string.error_general)
    Toast.makeText(
            context,
            (message ?: error?.message)?.ifEmpty { commonErrorText } ?: commonErrorText,
            Toast.LENGTH_SHORT,
        )
        .show()
}
