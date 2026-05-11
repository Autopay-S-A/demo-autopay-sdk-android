package eu.autopay.pay.demo.ui.nav

import kotlinx.serialization.Serializable

sealed class NavigationRoute {
    @Serializable data object Home : NavigationRoute()

    @Serializable data object Native : NavigationRoute()

    @Serializable data class WebView(val orderId: String, val url: String) : NavigationRoute()

    @Serializable
    data class DemoType(val type: eu.autopay.pay.demo.ui.type.DemoType) : NavigationRoute()

    @Serializable data class Status(val orderId: String, val reason: String?) : NavigationRoute()

    @Serializable data object Theme : NavigationRoute()

    @Serializable data object Environment : NavigationRoute()

    @Serializable data object CardActivation : NavigationRoute()
}
