package eu.autopay.pay.demo.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import eu.autopay.pay.demo.ui.theme.DemoTheme
import eu.autopay.pay.demo.ui.utils.Sizes

@Composable
fun PrimaryButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth().heightIn(min = Sizes.MinButtonSize),
        onClick = onClick,
        enabled = enabled,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 66f / 256),
            ),
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp))
    }
}

@Composable
fun SecondaryButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth().heightIn(min = Sizes.MinButtonSize),
        onClick = onClick,
        border = BorderStroke(width = Sizes.BorderSmall, color = MaterialTheme.colorScheme.primary),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
            ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonEnabledPreview() =
    DemoTheme.AutopaySDKTheme { PrimaryButton(text = "Text") {} }

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonDisabledPreview() =
    DemoTheme.AutopaySDKTheme { PrimaryButton(text = "Text", enabled = false) {} }

@Preview(showBackground = true)
@Composable
private fun SecondaryButtonPreview() =
    DemoTheme.AutopaySDKTheme { SecondaryButton(text = "Text") {} }
