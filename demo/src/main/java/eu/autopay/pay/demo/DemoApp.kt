package eu.autopay.pay.demo

import android.app.Application
import eu.autopay.pay.demo.ui.style.StyleHolder
import eu.autopay.pay.demo.ui.utils.DemoConfigHolder
import eu.autopay.pay.demo.ui.utils.TransactionPrefs
import eu.autopay.pay.sdk.Autopay
import eu.autopay.pay.sdk.AutopayConfig
import eu.autopay.pay.sdk.model.APEnvironmentType

class DemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        StyleHolder.sharedPreferences = getSharedPreferences(THEME_PREFS, MODE_PRIVATE)
        TransactionPrefs.prefs = getSharedPreferences(TRANSACTION_PREFS, MODE_PRIVATE)
        DemoConfigHolder.prefs = getSharedPreferences(CONFIG_PREFS, MODE_PRIVATE)
        DemoConfigHolder.getConfig().run {
            Autopay.init(
                AutopayConfig.Builder(
                        token = token,
                        serviceId = serviceId,
                        acceptorId = acceptorId,
                        environmentType =
                            if (!isProd) APEnvironmentType.DEV else APEnvironmentType.PROD,
                    )
                    .enableLogging(true) // false on production!
                    .googlePayMerchantId(googlePayMerchantId)
                    .currencies(listOf(currency))
                    .contextPath(contextPath)
                    .useWebBlik(useWebBlik)
                    .build()
            )
        }
    }

    companion object {
        private const val THEME_PREFS = "THEME_PREFS"
        private const val CONFIG_PREFS = "CONFIG_PREFS"
        private const val TRANSACTION_PREFS = "TRANSACTION_PREFS"
    }
}
