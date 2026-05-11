package eu.autopay.pay.demo.ui.normal

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import eu.autopay.pay.demo.R
import eu.autopay.pay.demo.ui.common.Toolbar
import eu.autopay.pay.demo.ui.nav.NavigationDispatcher
import eu.autopay.pay.demo.ui.utils.DemoConfigHolder
import eu.autopay.pay.demo.ui.utils.Sizes
import eu.autopay.pay.sdk.model.APGatewayPaymentGroup
import eu.autopay.pay.sdk.model.APSdkState
import eu.autopay.pay.sdk.ui.list.APGatewayListCompose
import eu.autopay.pay.sdk.utils.createMessage
import eu.autopay.pay.sdk.utils.isTokenExpired

@Composable
fun NativeCompose() {
    val listText = stringResource(R.string.demo_list_title)
    val googlePayText = stringResource(R.string.demo_google_pay_title)
    val blikText = stringResource(R.string.demo_blik_title)
    val bankText = stringResource(R.string.demo_bank_title)
    val cardText = stringResource(R.string.demo_card_title)
    val visaText = stringResource(R.string.demo_visa_title)
    var toolbarTitle by rememberSaveable { mutableStateOf(listText) }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { Toolbar(toolbarTitle) }) { paddingValues
        ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            val config = DemoConfigHolder.getConfig()
            val context = LocalContext.current
            Column(
                modifier =
                    Modifier.verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(
                            horizontal = Sizes.DoublePadding,
                            vertical = Sizes.SingleHalfPadding,
                        )
            ) {
                APGatewayListCompose(
                    amount = config.amount,
                    paymentSummary = config.paymentSummary,
                    customerEmail = config.email,
                    onPreTransactionDone = {
                        it.redirectUrl?.let { redirectUrl ->
                            NavigationDispatcher.toWebView(redirectUrl, it.orderId)
                        } ?: NavigationDispatcher.toCheckStatus(it.orderId, it.reason)
                    },
                    onPaymentStateChange = {
                        toolbarTitle =
                            when (it) {
                                is APSdkState.APGatewayDetails ->
                                    when (it.gatewayGroup) {
                                        APGatewayPaymentGroup.GOOGLE_PAY -> googlePayText
                                        APGatewayPaymentGroup.BLIK -> blikText
                                        APGatewayPaymentGroup.BANK_TRANSFER -> bankText
                                        APGatewayPaymentGroup.CARD -> cardText
                                        APGatewayPaymentGroup.VISA -> visaText
                                    }

                                is APSdkState.APGatewaysList -> listText
                                APSdkState.APGatewaysLoading -> listText
                                is APSdkState.APPreTransactionInProgress ->
                                    when (it.gatewayGroup) {
                                        APGatewayPaymentGroup.GOOGLE_PAY -> googlePayText
                                        APGatewayPaymentGroup.BLIK -> blikText
                                        APGatewayPaymentGroup.BANK_TRANSFER -> bankText
                                        APGatewayPaymentGroup.CARD -> cardText
                                        APGatewayPaymentGroup.VISA -> visaText
                                    }
                            }
                    },
                    onPreTransactionError = {
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
