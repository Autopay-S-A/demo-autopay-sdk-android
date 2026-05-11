package eu.autopay.pay.demo.ui.webview

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.autopay.pay.demo.ui.utils.DemoConfigHolder
import eu.autopay.pay.demo.ui.utils.TransactionPrefs
import eu.autopay.pay.sdk.Autopay
import eu.autopay.pay.sdk.model.APProduct
import eu.autopay.pay.sdk.model.APTransactionData
import eu.autopay.pay.sdk.ui.webview.APWebView
import java.math.BigDecimal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WebViewViewModel(val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error: MutableStateFlow<Throwable?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    private val _success: MutableStateFlow<String?> = MutableStateFlow(null)
    val success = _success.asStateFlow()

    private val _failureReason: MutableStateFlow<String?> = MutableStateFlow(null)
    val failureReason = _failureReason.asStateFlow()

    private val _transactionDone = MutableStateFlow(TransactionPrefs.isTransactionDone())
    val transactionDone = _transactionDone.asStateFlow()

    var orderId: String = ""

    var webView: APWebView? = null
    var savedWebViewState: Bundle? = savedStateHandle[KEY_WEB_VIEW_STATE]

    init {
        val url: String? = savedStateHandle[KEY_URL]
        orderId = savedStateHandle[KEY_ORDER_ID] ?: ""

        url?.takeIf { it.isNotEmpty() }?.let { viewModelScope.launch { _success.emit(it) } }
            ?: makeTransaction()
    }

    fun makeTransaction() {
        viewModelScope.launch {
            _isLoading.emit(true)
            try {
                val result =
                    Autopay.makeTransaction(
                        APTransactionData(
                            amount = DemoConfigHolder.getConfig().amount,
                            email = DemoConfigHolder.getConfig().email,
                            phone = "111222333",
                            products =
                                listOf(
                                    APProduct(
                                        BigDecimal.valueOf(24L),
                                        mapOf("productID" to "111", "productName" to ""),
                                    ),
                                    APProduct(
                                        BigDecimal.valueOf(13L),
                                        mapOf(
                                            "productID" to "112",
                                            "productName" to "Moja etykieta",
                                        ),
                                    ),
                                ),
                        )
                    )
                if (result == null)
                    return@launch // SDK not initialized! Fix your configuration by calling
                // Autopay.init() first!

                orderId = result.orderId
                savedStateHandle[KEY_ORDER_ID] = orderId

                if (result.redirectUrl == null) {
                    _failureReason.emit(result.reason)
                } else {
                    savedStateHandle[KEY_URL] = result.redirectUrl
                    _success.emit(result.redirectUrl)
                }
                _isLoading.emit(false)
                _error.emit(null)
            } catch (e: Throwable) {
                _isLoading.emit(false)
                _success.emit(null)
                _error.emit(e)
            }
        }
    }

    fun onTransactionDone() {
        // SavedStateHandle will not work here as webview may finished after app process death, so
        // save it to SharedPreferences or DataStore
        TransactionPrefs.setTransactionDone(true)
        _transactionDone.value = true
    }

    fun resetTransaction() {
        TransactionPrefs.setTransactionDone(false)
        _transactionDone.value = false
    }

    fun saveStateToHandle(bundle: Bundle) {
        savedWebViewState = bundle
        savedStateHandle[KEY_WEB_VIEW_STATE] = bundle
    }

    override fun onCleared() {
        webView?.destroy()
        webView = null
        super.onCleared()
    }

    companion object {
        private const val KEY_URL = "url"
        private const val KEY_ORDER_ID = "orderId"
        private const val KEY_WEB_VIEW_STATE = "webview_state"
    }
}
