package eu.autopay.pay.demo.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import eu.autopay.pay.demo.R
import eu.autopay.pay.demo.ui.theme.DemoTheme
import eu.autopay.pay.demo.ui.utils.Sizes
import eu.autopay.pay.sdk.model.APError
import eu.autopay.pay.sdk.utils.errorString

@Composable
fun ErrorCompose(modifier: Modifier = Modifier, throwable: Throwable, onRetry: () -> Unit) {
    Box(modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.align(Alignment.Center).padding(Sizes.DoublePadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_demo_status_error),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
            )
            Spacer(Modifier.height(Sizes.QuadruplePadding))
            Text(
                text = stringResource(R.string.demo_error_title),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(Sizes.SinglePadding))
            Text(
                text =
                    ((throwable as? APError?)?.type?.errorString()?.let { stringResource(it) }
                            ?: throwable.message)
                        ?.ifEmpty { stringResource(eu.autopay.pay.sdk.R.string.error_general) }
                        ?: stringResource(eu.autopay.pay.sdk.R.string.error_general),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(Sizes.DoublePadding))
            PrimaryButton(text = stringResource(R.string.demo_common_retry), onClick = onRetry)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorPreview() =
    DemoTheme.AutopaySDKTheme {
        ErrorCompose(modifier = Modifier.fillMaxSize(), Throwable("Error text")) {}
    }
