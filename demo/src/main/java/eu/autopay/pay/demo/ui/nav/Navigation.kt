package eu.autopay.pay.demo.ui.nav

import eu.autopay.pay.demo.ui.type.DemoType

interface Navigation {
    fun toDemoTypeChange(demoType: DemoType)

    fun toEnvironmentChange()

    fun toThemeChange()

    fun toNative()

    fun toWebView(url: String? = null, orderId: String?)

    fun popBack(result: Pair<String, Any>?)

    fun toCheckStatus(orderId: String, reason: String?)

    fun toCardActivation()
}
