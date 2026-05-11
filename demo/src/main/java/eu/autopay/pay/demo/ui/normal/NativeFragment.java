package eu.autopay.pay.demo.ui.normal;

import android.content.Context;
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
import eu.autopay.pay.demo.ui.utils.DemoConfig;
import eu.autopay.pay.demo.ui.utils.DemoConfigHolder;
import eu.autopay.pay.sdk.model.APSdkState;
import eu.autopay.pay.sdk.ui.list.APGatewayListView;
import eu.autopay.pay.sdk.utils.ErrorUtils;
import kotlin.Unit;

public class NativeFragment extends Fragment {
    public NativeFragment() {
        super(R.layout.fragment_native);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean isDarkMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        view.setBackgroundColor((int) (isDarkMode ? StyleHolder.INSTANCE.getDarkPalette() : StyleHolder.INSTANCE.getLightPalette()).getValue().getNeutralLightColor());

        ((ToolbarView) view.findViewById(R.id.toolbar)).setTitle(getString(R.string.demo_list_title));

        DemoConfig config = DemoConfigHolder.INSTANCE.getConfig();

        APGatewayListView gatewayView = view.findViewById(R.id.gatewayList);
        gatewayView.setAmount(config.getAmount());
        gatewayView.setCustomerEmail(config.getEmail());
        gatewayView.setPaymentSummary(config.getPaymentSummary());

        gatewayView.setOnPreTransactionDone(apPreTransaction -> {
            String url = apPreTransaction.getRedirectUrl();
            if (url == null) {
                NavigationDispatcher.INSTANCE.toCheckStatus(apPreTransaction.getOrderId(), apPreTransaction.getReason());
            } else {
                NavigationDispatcher.INSTANCE.toWebView(url, apPreTransaction.getOrderId());
            }
            return Unit.INSTANCE;
        });

        gatewayView.setOnPaymentStateChange(apSdkState -> {
            String title = getTitleBySdkState(apSdkState);
            ((ToolbarView) view.findViewById(R.id.toolbar)).setTitle(title);
            return Unit.INSTANCE;
        });

        gatewayView.setOnPreTransactionError(error -> {
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
                Context context = getContext();
                if (context != null) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }
            return Unit.INSTANCE;
        });
    }

    private String getTitleBySdkState(APSdkState apSdkState) {
        if (apSdkState instanceof APSdkState.APGatewayDetails) {
            switch (((APSdkState.APGatewayDetails) apSdkState).getGatewayGroup()) {
                case GOOGLE_PAY:
                    return getString(R.string.demo_google_pay_title);
                case BLIK:
                    return getString(R.string.demo_blik_title);
                case BANK_TRANSFER:
                    return getString(R.string.demo_bank_title);
                case CARD:
                    return getString(R.string.demo_card_title);
                case VISA:
                    return getString(R.string.demo_visa_title);
            }
        } else if (apSdkState instanceof APSdkState.APGatewaysList) {
            return getString(R.string.demo_list_title);
        } else if (apSdkState instanceof APSdkState.APGatewaysLoading) {
            return getString(R.string.demo_list_title);
        } else if (apSdkState instanceof APSdkState.APPreTransactionInProgress) {
            switch (((APSdkState.APPreTransactionInProgress) apSdkState).getGatewayGroup()) {
                case GOOGLE_PAY:
                    return getString(R.string.demo_google_pay_title);
                case BLIK:
                    return getString(R.string.demo_blik_title);
                case BANK_TRANSFER:
                    return getString(R.string.demo_bank_title);
                case CARD:
                    return getString(R.string.demo_card_title);
                case VISA:
                    return getString(R.string.demo_visa_title);
            }
        }
        return "";
    }
}
