package eu.autopay.pay.demo.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import eu.autopay.pay.demo.ui.theme.DemoTheme
import eu.autopay.pay.demo.ui.utils.Sizes

@Composable
fun DemoSwitch(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Sizes.SinglePadding),
        modifier =
            modifier
                .fillMaxWidth()
                .heightIn(Sizes.MinButtonSize)
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val thumbTrackColor = if (isSystemInDarkTheme()) Color.Black else Color.White
        Switch(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) },
            colors =
                SwitchDefaults.colors()
                    .copy(
                        checkedThumbColor = thumbTrackColor,
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        uncheckedTrackColor = thumbTrackColor,
                    ),
        )
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
private fun DemoSwitchUncheckedPreview() =
    DemoTheme.AutopaySDKTheme { DemoSwitch(text = "test1", checked = false, onCheckedChange = {}) }

@Preview(showBackground = true, apiLevel = 34)
@Composable
private fun DemoSwitchCheckedPreview() =
    DemoTheme.AutopaySDKTheme { DemoSwitch(text = "test2", checked = true, onCheckedChange = {}) }
