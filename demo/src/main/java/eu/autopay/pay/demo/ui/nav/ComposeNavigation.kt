package eu.autopay.pay.demo.ui.nav

import androidx.navigation.NavController
import eu.autopay.pay.demo.ui.type.DemoType

class ComposeNavigation(private val navController: NavController) : Navigation {

    override fun toDemoTypeChange(demoType: DemoType) {
        navController.navigate(NavigationRoute.DemoType(demoType))
    }

    override fun toEnvironmentChange() {
        navController.navigate(NavigationRoute.Environment)
    }

    override fun toThemeChange() {
        navController.navigate(NavigationRoute.Theme)
    }

    override fun toNative() {
        navController.navigate(NavigationRoute.Native)
    }

    override fun toWebView(url: String?, orderId: String?) {
        navController.navigate(NavigationRoute.WebView(orderId = orderId ?: "", url = url ?: ""))
    }

    override fun popBack(result: Pair<String, Any>?) {
        navController.popBackStack()
        result?.let {
            navController.currentBackStackEntry?.savedStateHandle?.set(it.first, it.second)
        }
    }

    override fun toCheckStatus(orderId: String, reason: String?) {
        navController.navigate(NavigationRoute.Status(orderId, reason))
    }

    override fun toCardActivation() {
        navController.navigate(NavigationRoute.CardActivation)
    }
}
