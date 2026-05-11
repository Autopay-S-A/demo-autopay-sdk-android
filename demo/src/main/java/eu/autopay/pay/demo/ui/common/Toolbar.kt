package eu.autopay.pay.demo.ui.common

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import eu.autopay.pay.demo.R
import eu.autopay.pay.demo.ui.theme.DemoTheme
import eu.autopay.pay.demo.ui.utils.Sizes

@Composable
fun Toolbar(text: String, backIcon: Painter = painterResource(R.drawable.ic_demo_back)) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    Row(
        modifier = Modifier.fillMaxWidth().height(Sizes.ToolbarSize),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier.size(Sizes.MinButtonSize),
            onClick = { onBackPressedDispatcher?.onBackPressed() },
        ) {
            Icon(
                painter = backIcon,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f).padding(end = Sizes.ToolbarSize),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
private fun ToolbarPreview() = DemoTheme.AutopaySDKTheme { Toolbar("Toolbar title") }
