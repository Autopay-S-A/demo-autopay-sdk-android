package eu.autopay.pay.demo.ui.nav;

import androidx.annotation.NonNull;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;

import org.jetbrains.annotations.NotNull;

import eu.autopay.pay.demo.R;
import eu.autopay.pay.demo.ui.status.TransactionStatusFragmentArgs;
import eu.autopay.pay.demo.ui.type.DemoType;
import eu.autopay.pay.demo.ui.type.DemoTypeFragmentArgs;
import eu.autopay.pay.demo.ui.webview.WebViewFragmentArgs;
import kotlin.Pair;

public class JavaNavigation implements Navigation {

	private final NavController navController;

	public JavaNavigation(NavController navController) {
		this.navController = navController;
	}

	@Override
	public void toDemoTypeChange(@NonNull DemoType demoType) {
		navController.navigate(
				R.id.demoTypeFragment,
				new DemoTypeFragmentArgs.Builder(demoType)
						.build()
						.toBundle()
		);
	}

	@Override
	public void toEnvironmentChange() {
		navController.navigate(R.id.environmentFragment);
	}

	@Override
	public void toThemeChange() {
		navController.navigate(R.id.styleFragment);
	}

	@Override
	public void toNative() {
		navController.navigate(R.id.nativeFragment);
	}

	@Override
	public void toWebView(String url, String orderId) {
		navController.navigate(
				R.id.webViewFragment,
				new WebViewFragmentArgs.Builder()
						.setUrl(url)
						.setOrderId(orderId)
						.build()
						.toBundle()
		);
	}

	@Override
	public void popBack(Pair<@NotNull String, ?> result) {
		navController.popBackStack();

		if (result != null) {
			NavBackStackEntry entry = navController.getCurrentBackStackEntry();
			if (entry != null) {
				entry.getSavedStateHandle().set(result.getFirst(), result.getSecond());
			}
		}
	}

	@Override
	public void toCheckStatus(@NonNull String orderId, String reason) {
		navController.navigate(
				R.id.transactionStatusFragment,
				new TransactionStatusFragmentArgs.Builder(orderId)
						.setReason(reason != null ? reason : "")
						.build()
						.toBundle()
		);
	}

	@Override
	public void toCardActivation() {
		navController.navigate(R.id.cardActivationFragment);
	}
}
