package eu.autopay.pay.demo.ui.webview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.autopay.pay.demo.ui.utils.DemoConfigHolder;
import eu.autopay.pay.demo.ui.utils.SingleEvent;
import eu.autopay.pay.demo.ui.utils.TransactionPrefs;
import eu.autopay.pay.sdk.Autopay;
import eu.autopay.pay.sdk.model.APPreTransaction;
import eu.autopay.pay.sdk.model.APProduct;
import eu.autopay.pay.sdk.model.APTransactionData;
import eu.autopay.pay.sdk.ui.webview.APWebView;

public class WebViewJavaViewModel extends ViewModel {
	private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
	private final MutableLiveData<Throwable> error = new MutableLiveData<>(null);
	private final MutableLiveData<SingleEvent<String>> success = new MutableLiveData<>();
	private final MutableLiveData<String> failureReason = new MutableLiveData<>(null);
	private final MutableLiveData<Boolean> transactionDone = new MutableLiveData<>();
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	private final SavedStateHandle savedStateHandle;

	public APWebView webView = null;
	public String orderId;
	public String url;

	public WebViewJavaViewModel(SavedStateHandle savedStateHandle) {
		this.savedStateHandle = savedStateHandle;

		transactionDone.setValue(TransactionPrefs.INSTANCE.isTransactionDone());

		url = savedStateHandle.get("url");
		orderId = savedStateHandle.get("orderId");
		if (orderId == null) orderId = "";
	}

	public void getUrl() {
		if (url != null && !url.isEmpty()) {
			emitSuccess(url);
		} else {
			makeTransaction();
		}
	}

	public LiveData<Boolean> isLoadingLD() {
		return isLoading;
	}

	public LiveData<Throwable> getErrorLD() {
		return error;
	}

	public LiveData<SingleEvent<String>> getSuccessLD() {
		return success;
	}

	public LiveData<String> getFailureReasonLD() {
		return failureReason;
	}

	public LiveData<Boolean> getTransactionDoneLD() {
		return transactionDone;
	}

	public void makeTransaction() {
		isLoading.setValue(true);

		executor.execute(() -> {
			try {
				APTransactionData data = APTransactionData
						.builder(DemoConfigHolder.INSTANCE.getConfig().getAmount())
						.email(DemoConfigHolder.INSTANCE.getConfig().getEmail())
						.phone("111222333")
						.products(Arrays.asList(
								new APProduct(
										BigDecimal.valueOf(24L),
										map("productID", "111", "productName", "")
								),
								new APProduct(
										BigDecimal.valueOf(13L),
										map("productID", "112", "productName", "Moja etykieta")
								)
						)).build();

				APPreTransaction result = Autopay.makeTransactionBlocking(data);
				if (result == null) return; //SDK not initialized! Fix your configuration by calling Autopay.init() first!

				orderId = result.getOrderId();
				savedStateHandle.set("orderId", orderId);

				if (result.getRedirectUrl() == null) {
					post(failureReason, result.getReason());
				} else {
					savedStateHandle.set("url", result.getRedirectUrl());
					emitSuccess(result.getRedirectUrl());
				}

				post(isLoading, false);
				post(error, null);

			} catch (Throwable e) {
				post(isLoading, false);
				post(success, null);
				post(error, e);
			}
		});
	}

	public void onTransactionDone() {
		TransactionPrefs.INSTANCE.setTransactionDone(true);
		transactionDone.setValue(true);
	}

	public void resetTransaction() {
		TransactionPrefs.INSTANCE.setTransactionDone(false);
		transactionDone.setValue(false);
	}

	@Override
	protected void onCleared() {
		executor.shutdownNow();
		if (webView != null) {
			webView.destroy();
			webView = null;
		}
		super.onCleared();
	}

	private <T> void post(MutableLiveData<T> liveData, T value) {
		liveData.postValue(value);
	}

	private void emitSuccess(String error) {
		success.postValue(new SingleEvent<>(error));
	}

	private Map<String, String> map(String key1, String value1, String key2, String value2) {
		Map<String, String> map = new HashMap<>();
		map.put(key1, value1);
		map.put(key2, value2);
		return map;
	}
}
