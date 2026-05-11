package eu.autopay.pay.demo.ui.status

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.autopay.pay.demo.R
import eu.autopay.pay.demo.ui.common.ErrorCompose
import eu.autopay.pay.demo.ui.common.PrimaryButton
import eu.autopay.pay.demo.ui.nav.NavigationDispatcher
import eu.autopay.pay.demo.ui.theme.DemoTheme
import eu.autopay.pay.demo.ui.utils.Sizes
import eu.autopay.pay.sdk.model.APErrorType
import eu.autopay.pay.sdk.model.APResult
import eu.autopay.pay.sdk.model.APTransactionStatus
import eu.autopay.pay.sdk.utils.errorString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TransactionStatusCompose(viewModel: TransactionStatusViewModel = viewModel()) {
    val error by viewModel.error.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val status by viewModel.success.collectAsStateWithLifecycle()
    Content(
        isLoading = isLoading,
        error = error,
        onError = viewModel::checkTransactionStatus,
        status = status,
        reason = viewModel.reason,
    )
}

@Composable
private fun Content(
    isLoading: Boolean,
    error: Throwable?,
    onError: (Boolean) -> Unit,
    status: APTransactionStatus?,
    reason: APErrorType?,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    // Just as an example, recalling checking status if there are some pending transactions
    LaunchedEffect(status) {
        if (status?.transactions?.any { it.paymentStatus == APResult.PENDING } == true) {
            lifecycleOwner.lifecycleScope.launch {
                delay(CHECK_INTERVAL)
                onError(false)
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(Modifier.fillMaxSize().padding(paddingValues)) {
            if (isLoading)
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                )
            else if (reason != null) {
                StatusContent(status = APResult.FAILURE, reason = reason)
            } else {
                error?.let {
                    ErrorCompose(modifier = Modifier.align(Alignment.Center), throwable = it) {
                        onError(true)
                    }
                }
                    ?: status?.let { status ->
                        StatusContent(status = status.transactions.firstOrNull()?.paymentStatus)
                    }
            }
        }
    }
}

@Composable
private fun StatusContent(status: APResult?, reason: APErrorType? = null) {
    Column(
        modifier =
            Modifier.fillMaxSize()
                .padding(horizontal = Sizes.DoublePadding, vertical = Sizes.SingleHalfPadding)
    ) {
        Column(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter =
                    painterResource(
                        when (status) {
                            APResult.SUCCESS,
                            APResult.SUCCESS_MANY -> R.drawable.ic_demo_status_success

                            APResult.PENDING -> R.drawable.ic_demo_status_pending

                            APResult.FAILURE,
                            APResult.TRANSACTION_CANCELED,
                            null -> R.drawable.ic_demo_status_error
                        }
                    ),
                contentDescription = null,
                tint =
                    when (status) {
                        APResult.SUCCESS,
                        APResult.SUCCESS_MANY -> MaterialTheme.colorScheme.primary

                        APResult.PENDING -> MaterialTheme.colorScheme.outline
                        APResult.FAILURE,
                        APResult.TRANSACTION_CANCELED,
                        null -> MaterialTheme.colorScheme.error
                    },
            )
            Spacer(Modifier.height(Sizes.QuadruplePadding))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text =
                    reason?.errorString()?.let { stringResource(it) }
                        ?: stringResource(
                            when (status) {
                                APResult.SUCCESS,
                                APResult.SUCCESS_MANY -> R.string.demo_status_success

                                APResult.PENDING -> R.string.demo_status_pending
                                APResult.FAILURE,
                                APResult.TRANSACTION_CANCELED,
                                null -> R.string.demo_status_error
                            }
                        ),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        PrimaryButton(text = stringResource(R.string.demo_common_ok)) {
            NavigationDispatcher.popBack()
        }
    }
}

private const val CHECK_INTERVAL = 5000L

@Preview(showBackground = true)
@Composable
private fun StatusSuccessPreview() =
    DemoTheme.AutopaySDKTheme {
        Content(
            false,
            null,
            {},
            APTransactionStatus(
                "",
                "",
                "",
                "",
                "",
                listOf(
                    APTransactionStatus.Transaction("", "", "", "", "", "", APResult.SUCCESS, "")
                ),
            ),
            null,
        )
    }

@Preview(showBackground = true)
@Composable
private fun StatusPendingPreview() =
    DemoTheme.AutopaySDKTheme {
        Content(
            false,
            null,
            {},
            APTransactionStatus(
                "",
                "",
                "",
                "",
                "",
                listOf(
                    APTransactionStatus.Transaction("", "", "", "", "", "", APResult.PENDING, "")
                ),
            ),
            null,
        )
    }

@Preview(showBackground = true)
@Composable
private fun StatusErrorPreview() =
    DemoTheme.AutopaySDKTheme {
        Content(
            false,
            null,
            {},
            APTransactionStatus(
                "",
                "",
                "",
                "",
                "",
                listOf(
                    APTransactionStatus.Transaction("", "", "", "", "", "", APResult.FAILURE, "")
                ),
            ),
            null,
        )
    }

@Preview(showBackground = true)
@Composable
private fun StatusErrorWithReasonPreview() =
    DemoTheme.AutopaySDKTheme {
        Content(
            false,
            null,
            {},
            APTransactionStatus(
                "",
                "",
                "",
                "",
                "",
                listOf(
                    APTransactionStatus.Transaction("", "", "", "", "", "", APResult.FAILURE, "")
                ),
            ),
            APErrorType.entries.toTypedArray().random(),
        )
    }
