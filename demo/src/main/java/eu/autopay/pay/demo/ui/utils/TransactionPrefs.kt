package eu.autopay.pay.demo.ui.utils

import android.content.SharedPreferences
import eu.autopay.pay.demo.ui.utils.DemoConfigHolder.currentConfig
import eu.autopay.pay.demo.ui.utils.DemoConfigHolder.getConfig

object TransactionPrefs {

    private const val KEY_TRANSACTION_DONE = "transaction_done"

    var prefs: SharedPreferences? = null
        set(value) {
            field = value
            currentConfig.value = getConfig()
        }

    fun setTransactionDone(done: Boolean) {
        prefs?.edit()?.putBoolean(KEY_TRANSACTION_DONE, done)?.commit()
    }

    fun isTransactionDone(): Boolean {
        return prefs?.getBoolean(KEY_TRANSACTION_DONE, false) ?: false
    }
}
