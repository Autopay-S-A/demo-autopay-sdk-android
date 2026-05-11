package eu.autopay.pay.demo.ui.card;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import eu.autopay.pay.demo.R;
import eu.autopay.pay.demo.ui.common.ToolbarView;
import eu.autopay.pay.demo.ui.nav.NavigationDispatcher;
import eu.autopay.pay.demo.ui.style.StyleHolder;
import eu.autopay.pay.sdk.ui.card.APCardActivationView;
import eu.autopay.pay.sdk.utils.ErrorUtils;
import kotlin.Unit;

public class CardActivationFragment extends Fragment {
    public CardActivationFragment() {
        super(R.layout.fragment_card_activation);
    }

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		boolean isDarkMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
		view.setBackgroundColor((int) (isDarkMode ? StyleHolder.INSTANCE.getDarkPalette() : StyleHolder.INSTANCE.getLightPalette()).getValue().getNeutralLightColor());

		((ToolbarView) view.findViewById(R.id.toolbar)).setTitle(getString(R.string.demo_card_activation_title));

		APCardActivationView cardPaywall = view.findViewById(R.id.cardPaywall);
		cardPaywall.setOnActivationDone(apPreTransaction -> {
			String url = apPreTransaction.getRedirectUrl();
			NavigationDispatcher.INSTANCE.popBack();
			if (url == null) {
				NavigationDispatcher.INSTANCE.toCheckStatus(apPreTransaction.getOrderId(), apPreTransaction.getReason());
			} else {
				NavigationDispatcher.INSTANCE.toWebView(url, apPreTransaction.getOrderId());
			}
			return Unit.INSTANCE;
		});
		cardPaywall.setOnActivationError(error -> {
			if (ErrorUtils.INSTANCE.isTokenExpired(error)) {
				// Display progress that blocks UI, refresh token in your app, update it by using:
				// Autopay.updateToken("new_token_here");
				// Hide progress and let user use retry button inside SDK
			} else {
				// handle other payment errors
				String message = ErrorUtils.INSTANCE.createErrorMessage(error);
				if (message == null || message.isEmpty()) {
					message = getString(eu.autopay.pay.sdk.R.string.error_general);
				}
				Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
			}

			return Unit.INSTANCE;
		});
	}
}
