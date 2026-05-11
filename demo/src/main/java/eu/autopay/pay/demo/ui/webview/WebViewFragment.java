package eu.autopay.pay.demo.ui.webview;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import eu.autopay.pay.demo.R;
import eu.autopay.pay.demo.ui.common.ErrorView;
import eu.autopay.pay.demo.ui.common.ToolbarView;
import eu.autopay.pay.demo.ui.nav.NavigationDispatcher;
import eu.autopay.pay.demo.ui.style.StyleHolder;
import eu.autopay.pay.demo.ui.utils.SingleEvent;
import eu.autopay.pay.sdk.model.APError;
import eu.autopay.pay.sdk.model.APEvent;
import eu.autopay.pay.sdk.ui.webview.APWebView;
import kotlin.Unit;

public class WebViewFragment extends Fragment {
    private WebViewJavaViewModel viewModel;
    private ErrorView errorView;
    private APWebView apWebView;
	private FrameLayout webViewContainer;
    private ProgressBar progressBar;

    public WebViewFragment() {
        super(R.layout.fragment_web_view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WebViewJavaViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean isDarkMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        view.setBackgroundColor((int) (isDarkMode ? StyleHolder.INSTANCE.getDarkPalette() : StyleHolder.INSTANCE.getLightPalette()).getValue().getNeutralLightColor());

        ((ToolbarView) view.findViewById(R.id.toolbar)).setTitle(getString(R.string.demo_home_form_webview));

		webViewContainer = view.findViewById(R.id.webContainer);
		handleWebViewState(savedInstanceState);

        errorView = view.findViewById(R.id.errorView);
        apWebView = viewModel.webView;
        progressBar = view.findViewById(R.id.progressBar);

        errorView.setOnRetry(() -> {
            viewModel.makeTransaction();
            return Unit.INSTANCE;
        });

        viewModel.getErrorLD().observe(getViewLifecycleOwner(), this::onError);
        viewModel.isLoadingLD().observe(getViewLifecycleOwner(), this::isLoading);
        viewModel.getSuccessLD().observe(getViewLifecycleOwner(), this::loadUrl);
        viewModel.getFailureReasonLD().observe(getViewLifecycleOwner(), this::onTransactionFailure);
        viewModel.getTransactionDoneLD().observe(getViewLifecycleOwner(), this::onTransactionDone);
    }

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		errorView = null;
		apWebView = null;
		progressBar = null;
		webViewContainer.removeAllViews();
		webViewContainer = null;
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);

		// save WebView state for process death restoration
		if (viewModel.webView != null) {
			viewModel.webView.saveState(outState);
		}
	}

	private void handleWebViewState(Bundle savedInstanceState) {
		// WebView in viewmodel to survive configuration changes
		if (viewModel.webView == null) {
			APWebView webView = new APWebView(requireContext());

			if (savedInstanceState == null) {
				viewModel.getUrl();
			} else {
				try {
					// restore state after process death
					webView.restoreState(savedInstanceState);
					webView.attachCallbacks(result -> {
						onPaymentDone();
						return Unit.INSTANCE;
					}, event -> {
						onPaymentEvent(event);
						return Unit.INSTANCE;
					}, error -> {
						onPaymentError(error);
						return Unit.INSTANCE;
					});
				} catch (Exception e) {
					viewModel.getUrl();
				}
			}

			viewModel.webView = webView;
		}

		webViewContainer.addView(viewModel.webView);
	}

    private void onError(@Nullable Throwable throwable) {
        if (errorView != null) {
            errorView.setVisibility(throwable != null ? View.VISIBLE : View.GONE);
            apWebView.setVisibility(throwable == null && Boolean.FALSE.equals(viewModel.isLoadingLD().getValue()) ? View.VISIBLE : View.GONE);
            errorView.setError(throwable);
        }
    }

    private void isLoading(Boolean isLoading) {
        if (progressBar != null) {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            apWebView.setVisibility(viewModel.getErrorLD().getValue() == null && !isLoading ? View.VISIBLE : View.GONE);
        }
    }

    private void onTransactionFailure(String reason) {
        if (reason == null) return;
        NavigationDispatcher.INSTANCE.popBack();
        NavigationDispatcher.INSTANCE.toCheckStatus(viewModel.orderId, reason);
    }

    private void onTransactionDone(Boolean done) {
        if (done != null && done) {
            NavigationDispatcher.INSTANCE.popBack();
            NavigationDispatcher.INSTANCE.toCheckStatus(viewModel.orderId, "");
            viewModel.resetTransaction();
        }
    }

    private void loadUrl(SingleEvent<String> singleEvent) {
		if (singleEvent == null) return;

		String url = singleEvent.getContentIfNotHandled();
		if (url == null)
			return;

        if (apWebView != null) {
            apWebView.setVisibility(View.VISIBLE);
            apWebView.loadUrl(url, result -> {
				onPaymentDone();
                return Unit.INSTANCE;
            }, event -> {
				onPaymentEvent(event);
				return Unit.INSTANCE;
			}, error -> {
				onPaymentError(error);
				return Unit.INSTANCE;
			});
        }
    }

	private void onPaymentDone() {
		viewModel.onTransactionDone();
	}

	private void onPaymentEvent(@org.jetbrains.annotations.Nullable APEvent event) {
		Context context = getContext();
		if (context != null && event != null) {
			Toast.makeText(context, event.toString(), Toast.LENGTH_SHORT).show();
		}
	}

	private void onPaymentError(@org.jetbrains.annotations.Nullable APError error) {
		Context context = getContext();
		if (context != null && error != null) {
			Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
			NavigationDispatcher.INSTANCE.popBack();
		}
	}
}
