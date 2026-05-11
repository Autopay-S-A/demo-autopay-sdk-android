package eu.autopay.pay.demo.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import eu.autopay.pay.demo.R
import eu.autopay.pay.demo.ui.theme.DemoTheme
import eu.autopay.pay.sdk.BuildConfig

@Composable
fun Footer(modifier: Modifier) {
    Text(
        text = stringResource(R.string.demo_footer, BuildConfig.VERSION_CODE),
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline),
    )
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
private fun FooterPreview() = DemoTheme.AutopaySDKTheme { Footer(Modifier) }
