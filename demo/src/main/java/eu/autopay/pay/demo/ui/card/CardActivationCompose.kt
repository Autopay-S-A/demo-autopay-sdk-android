package eu.autopay.pay.demo.ui.card

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import eu.autopay.pay.demo.R
import eu.autopay.pay.demo.ui.common.Toolbar
import eu.autopay.pay.demo.ui.nav.NavigationDispatcher
import eu.autopay.pay.demo.ui.utils.Sizes
import eu.autopay.pay.sdk.ui.card.APCardActivationCompose
import eu.autopay.pay.sdk.utils.createMessage
import eu.autopay.pay.sdk.utils.isTokenExpired

@Composable
fun CardActivationCompose() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Toolbar(stringResource(R.string.demo_card_activation_title)) },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            val context = LocalContext.current
            Column(
                modifier =
                    Modifier.verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .padding(
                            vertical = Sizes.SingleHalfPadding,
                            horizontal = Sizes.DoublePadding,
                        )
            ) {
                APCardActivationCompose(
                    onActivationDone = {
                        NavigationDispatcher.popBack()
                        it.redirectUrl?.let { redirectUrl ->
                            NavigationDispatcher.toWebView(redirectUrl, it.orderId)
                        } ?: NavigationDispatcher.toCheckStatus(it.orderId, it.reason)
                    },
                    onActivationError = {
                        if (it.isTokenExpired()) {
                            // Display progress that blocks UI, refresh token in your app, update it
                            // by using:
                            // Autopay.updateToken("new_token_here")
                            // Hide progress and let user use retry button inside SDK
                        } else {
                            // handle other payment errors
                            val commonErrorMessage =
                                context.getString(eu.autopay.pay.sdk.R.string.error_general)
                            Toast.makeText(
                                    context,
                                    it.createMessage()?.ifEmpty { commonErrorMessage }
                                        ?: commonErrorMessage,
                                    Toast.LENGTH_SHORT,
                                )
                                .show()
                        }
                    },
                )
            }
        }
    }
}
