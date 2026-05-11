package eu.autopay.pay.demo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.autopay.pay.demo.ui.card.CardActivationCompose
import eu.autopay.pay.demo.ui.environment.EnvironmentChooserCompose
import eu.autopay.pay.demo.ui.home.HomeCompose
import eu.autopay.pay.demo.ui.nav.ComposeNavigation
import eu.autopay.pay.demo.ui.nav.NavigationDispatcher
import eu.autopay.pay.demo.ui.nav.NavigationRoute
import eu.autopay.pay.demo.ui.normal.NativeCompose
import eu.autopay.pay.demo.ui.status.TransactionStatusCompose
import eu.autopay.pay.demo.ui.style.StyleCompose
import eu.autopay.pay.demo.ui.style.StyleHolder
import eu.autopay.pay.demo.ui.theme.DemoTheme
import eu.autopay.pay.demo.ui.type.DemoType
import eu.autopay.pay.demo.ui.type.DemoTypeCompose
import eu.autopay.pay.demo.ui.webview.WebViewCompose

class MainKotlinActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle =
                SystemBarStyle.auto(
                    StyleHolder.lightPalette.value.neutralLightColor.toInt(),
                    StyleHolder.darkPalette.value.neutralLightColor.toInt(),
                )
        )
        super.onCreate(savedInstanceState)
        setContent {
            DemoTheme.AutopaySDKTheme {
                val navController = rememberNavController()
                NavigationDispatcher.init(ComposeNavigation(navController))
                Scaffold(modifier = Modifier.fillMaxSize().safeDrawingPadding().imePadding()) {
                    innerPadding ->
                    Box(modifier = Modifier.consumeWindowInsets(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = NavigationRoute.Home,
                        ) {
                            composable<NavigationRoute.Home> { navBackResult ->
                                HomeCompose(navBackResult.savedStateHandle.get<DemoType>("type"))
                            }
                            composable<NavigationRoute.Native> { NativeCompose() }
                            composable<NavigationRoute.WebView> { WebViewCompose() }
                            composable<NavigationRoute.Theme> { StyleCompose() }
                            composable<NavigationRoute.Environment> { EnvironmentChooserCompose() }
                            composable<NavigationRoute.CardActivation> { CardActivationCompose() }
                            composable<NavigationRoute.DemoType> { DemoTypeCompose() }
                            composable<NavigationRoute.Status> { TransactionStatusCompose() }
                        }
                    }
                }
            }
        }
    }
}
