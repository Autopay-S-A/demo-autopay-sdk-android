package eu.autopay.pay.demo.ui.nav

import eu.autopay.pay.demo.ui.type.DemoType

object NavigationDispatcher {
    private lateinit var navigation: Navigation

    fun init(navigation: Navigation) {
        this.navigation = navigation
    }

    fun toDemoTypeChange(demoType: DemoType) {
        navigation.toDemoTypeChange(demoType)
    }

    fun toEnvironmentChange() {
        navigation.toEnvironmentChange()
    }

    fun toThemeChange() {
        navigation.toThemeChange()
    }

    fun toNative() {
        navigation.toNative()
    }

    fun toWebView(url: String? = null, orderId: String? = null) {
        navigation.toWebView(url, orderId)
    }

    fun popBack(results: Pair<String, Any>) {
        navigation.popBack(results)
    }

    fun popBack() {
        navigation.popBack(null)
    }

    fun toCheckStatus(orderId: String, reason: String?) {
        navigation.toCheckStatus(orderId, reason)
    }

    fun toCardActivation() {
        navigation.toCardActivation()
    }
}
