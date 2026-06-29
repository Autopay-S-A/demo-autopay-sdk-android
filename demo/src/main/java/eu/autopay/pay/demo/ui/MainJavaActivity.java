package eu.autopay.pay.demo.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.fragment.NavHostFragment;

import eu.autopay.pay.demo.R;
import eu.autopay.pay.demo.ui.nav.JavaNavigation;
import eu.autopay.pay.demo.ui.nav.NavigationDispatcher;
import eu.autopay.pay.sdk.Autopay;
import eu.autopay.pay.sdk.AutopayConfig;
import eu.autopay.pay.sdk.model.APEnvironmentType;
import eu.autopay.pay.sdk.ui.style.APTextInputStyle;
import eu.autopay.pay.sdk.ui.style.APTextStyleWrapper;
import eu.autopay.pay.sdk.ui.style.APThemeColor;
import eu.autopay.pay.sdk.ui.style.AutopayUIStyle;

public class MainJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);
        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostMain);
        if (navHost != null) {
            NavigationDispatcher.INSTANCE.init(new JavaNavigation(navHost.getNavController()));
        }
    }

    /**
     * Example method that shows how to set AutopayConfig in Java project
     */
    private void initAutopay() {
        Autopay.init(new AutopayConfig.Builder(
            APEnvironmentType.DEV.INSTANCE,
            "token",
            "serviceId",
            "acceptorId"
        )
            .contextPath("/payment")
            .enableLogging(true) // false on production!
            .googlePayMerchantId("merchantId")
            .useWebBlik(false) // true if blik in webview
            .build());
    }

    /**
     * Example method that shows how to set SDK's style in Java project
     */
    private void setExampleUiStyle() {
        AutopayUIStyle currentStyle = Autopay.getCurrentUiStyle();
        Autopay.setUiStyle(
                new AutopayUIStyle.Builder(currentStyle)
                        .inputStyle(
                                new APTextInputStyle.Builder()
                                        .labelTextStyle(
                                                new APTextStyleWrapper.Builder()
                                                        .font(Typeface.DEFAULT_BOLD)
                                                        .size(20f)
                                                        .weight(800)
                                                        .getCompose()
                                        )
                                        .errorTextStyle(
                                                new APTextStyleWrapper.Builder()
                                                        .size(18f)
                                                        .weight(300)
                                                        .font(Typeface.SANS_SERIF)
                                                        .letterSpacing(1f)
                                                        .lineHeight(1f)
                                                        .getCompose()
                                        )
                                        .labelTextColor(new APThemeColor(Color.BLUE, Color.GREEN))
                                        .radius(4f)
                                        .build()
                        ).build()
        );
    }
}
