package eu.autopay.pay.demo.ui.type

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.autopay.pay.demo.R
import eu.autopay.pay.demo.ui.common.Footer
import eu.autopay.pay.demo.ui.common.PrimaryButton
import eu.autopay.pay.demo.ui.common.Toolbar
import eu.autopay.pay.demo.ui.nav.NavigationDispatcher
import eu.autopay.pay.demo.ui.theme.DemoTheme
import eu.autopay.pay.demo.ui.utils.Sizes

@Composable
fun DemoTypeCompose(viewModel: DemoTypeViewModel = viewModel()) {
    Content(viewModel.currentType)
}

@Composable
private fun Content(current: DemoType) {
    var selectedType by rememberSaveable { mutableStateOf(current) }
    var isClicked by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Toolbar(stringResource(R.string.demo_list_title)) },
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Column(
                modifier =
                    Modifier.fillMaxWidth()
                        .weight(1f)
                        .padding(
                            vertical = Sizes.SingleHalfPadding,
                            horizontal = Sizes.DoublePadding,
                        )
                        .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.demo_type_title),
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(Sizes.SingleHalfPadding))
                Text(
                    text = stringResource(R.string.demo_type_content),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(Sizes.TriplePadding))
                TypeBox(DemoType.Native, selectedType) { selectedType = DemoType.Native }
                Spacer(Modifier.height(Sizes.SingleHalfPadding))
                TypeBox(DemoType.WebView, selectedType) { selectedType = DemoType.WebView }
            }
            Column(Modifier.padding(horizontal = Sizes.DoublePadding)) {
                PrimaryButton(
                    text = stringResource(R.string.demo_common_save),
                    enabled = !isClicked,
                ) {
                    isClicked = true
                    NavigationDispatcher.popBack("type" to selectedType)
                }
                Footer(
                    Modifier.padding(top = Sizes.DoublePadding, bottom = Sizes.SingleHalfPadding)
                )
            }
        }
    }
}

@Composable
private fun TypeBox(type: DemoType, current: DemoType, onClick: () -> Unit) {
    val borderColor by
        animateColorAsState(
            if (type == current) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.outline,
            label = "borderColor",
        )
    val borderWidth by
        animateDpAsState(
            if (type == current) Sizes.BorderMedium else Sizes.BorderSmall,
            label = "borderWidth",
        )
    Box(
        modifier =
            Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(Sizes.DoublePadding))
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(Sizes.DoublePadding),
                )
                .border(borderWidth, borderColor, RoundedCornerShape(Sizes.DoublePadding))
                .clickable { onClick() }
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(Sizes.TriplePadding)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Sizes.SingleHalfPadding),
            ) {
                Icon(
                    painter = painterResource(type.icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = stringResource(type.title),
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
                )
            }
            Spacer(Modifier.height(Sizes.SingleHalfPadding))
            Text(
                text = stringResource(type.explanation),
                style = MaterialTheme.typography.bodySmall,
            )
        }
        AnimatedContent(
            modifier = Modifier.padding(Sizes.DoublePadding).align(Alignment.TopEnd),
            targetState = type,
            label = "",
        ) {
            Box {
                if (it == current)
                    Icon(
                        painter = painterResource(R.drawable.ic_demo_selected_round),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
            }
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun DemoTypeNativePreview() = DemoTheme.AutopaySDKTheme { Content(DemoType.Native) }

@Preview(apiLevel = 34)
@Composable
private fun DemoTypeWebViewPreview() = DemoTheme.AutopaySDKTheme { Content(DemoType.WebView) }
