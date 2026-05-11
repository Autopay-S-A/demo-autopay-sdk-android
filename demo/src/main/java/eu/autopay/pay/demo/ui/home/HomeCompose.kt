package eu.autopay.pay.demo.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import eu.autopay.pay.demo.R
import eu.autopay.pay.demo.ui.common.Footer
import eu.autopay.pay.demo.ui.common.PrimaryButton
import eu.autopay.pay.demo.ui.common.SecondaryButton
import eu.autopay.pay.demo.ui.nav.NavigationDispatcher
import eu.autopay.pay.demo.ui.style.hex
import eu.autopay.pay.demo.ui.theme.DemoTheme
import eu.autopay.pay.demo.ui.type.DemoType
import eu.autopay.pay.demo.ui.utils.DemoConfigHolder
import eu.autopay.pay.demo.ui.utils.Sizes

@Composable
fun HomeCompose(demoType: DemoType?) {
    val selectedType: DemoType = demoType ?: DemoType.Native

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier =
                    Modifier.fillMaxWidth()
                        .height(Sizes.ToolbarSize)
                        .padding(start = Sizes.DoublePadding, end = Sizes.SinglePadding),
                horizontalArrangement = Arrangement.spacedBy(Sizes.DoublePadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    Image(
                        painter = painterResource(R.drawable.ic_demo_home_toolbar),
                        contentDescription = stringResource(R.string.demo_logo_autopay),
                    )
                }
                IconButton(
                    modifier = Modifier.size(Sizes.ToolbarSize - Sizes.SinglePadding),
                    onClick = { NavigationDispatcher.toCardActivation() },
                ) {
                    Icon(
                        painterResource(R.drawable.ic_demo_card_cativation),
                        contentDescription = stringResource(R.string.demo_card_autopayment_button),
                    )
                }
            }
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            Column(
                modifier =
                    Modifier.fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = Sizes.DoublePadding),
                verticalArrangement = Arrangement.spacedBy(Sizes.SingleHalfPadding),
            ) {
                Spacer(Modifier)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors =
                        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(Sizes.DoublePadding),
                    elevation =
                        CardDefaults.cardElevation(defaultElevation = Sizes.SingleHalfPadding),
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(Sizes.TriplePadding)) {
                        Text(
                            text = stringResource(R.string.demo_home_active_form),
                            style = MaterialTheme.typography.labelLarge,
                        )
                        Spacer(Modifier.height(Sizes.DoublePadding))
                        Box(
                            modifier =
                                Modifier.fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.background,
                                        RoundedCornerShape(Sizes.SingleHalfPadding),
                                    )
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(Sizes.TriplePadding),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(Sizes.SingleHalfPadding),
                            ) {
                                Icon(
                                    modifier = Modifier.size(Sizes.IconSize),
                                    painter = painterResource(selectedType.icon),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                                Text(
                                    text = stringResource(selectedType.title),
                                    style =
                                        MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
                                    textAlign = TextAlign.Center,
                                )
                                Text(
                                    text = stringResource(selectedType.explanation),
                                    style = MaterialTheme.typography.bodySmall,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                        Spacer(Modifier.height(Sizes.DoublePadding))
                        SecondaryButton(text = stringResource(R.string.demo_common_change)) {
                            NavigationDispatcher.toDemoTypeChange(selectedType)
                        }
                    }
                }
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Sizes.DoublePadding),
                    colors =
                        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation =
                        CardDefaults.cardElevation(defaultElevation = Sizes.SingleHalfPadding),
                ) {
                    Column(
                        modifier =
                            Modifier.fillMaxWidth()
                                .padding(
                                    top = Sizes.TriplePadding,
                                    start = Sizes.TriplePadding,
                                    end = Sizes.TriplePadding,
                                )
                    ) {
                        Text(
                            text = stringResource(R.string.demo_home_theme),
                            style = MaterialTheme.typography.labelLarge,
                        )
                        Spacer(Modifier.height(Sizes.SingleHalfPadding))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(Sizes.SingleHalfPadding),
                        ) {
                            Text(
                                text = stringResource(R.string.demo_home_theme_selected_color),
                                style = MaterialTheme.typography.labelMedium,
                            )
                            Box(
                                modifier =
                                    Modifier.background(
                                        color = MaterialTheme.colorScheme.background,
                                        shape = RoundedCornerShape(Sizes.ThreeQuartersPadding),
                                    )
                            ) {
                                Row(
                                    modifier = Modifier.padding(Sizes.ThreeQuartersPadding),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement =
                                        Arrangement.spacedBy(Sizes.ThreeQuartersPadding),
                                ) {
                                    Box(
                                        modifier =
                                            Modifier.background(
                                                    MaterialTheme.colorScheme.primary,
                                                    RoundedCornerShape(Sizes.HalfPadding),
                                                )
                                                .size(Sizes.ColorBoxSize)
                                    )
                                    Text(
                                        text = "#${MaterialTheme.colorScheme.primary.hex()}",
                                        style = MaterialTheme.typography.labelMedium,
                                    )
                                }
                            }
                        }
                        EditBtn { NavigationDispatcher.toThemeChange() }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Sizes.DoublePadding),
                    colors =
                        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation =
                        CardDefaults.cardElevation(defaultElevation = Sizes.SingleHalfPadding),
                ) {
                    Column(
                        modifier =
                            Modifier.fillMaxWidth()
                                .padding(
                                    top = Sizes.TriplePadding,
                                    start = Sizes.TriplePadding,
                                    end = Sizes.TriplePadding,
                                )
                    ) {
                        Text(
                            text = stringResource(R.string.demo_home_service),
                            style = MaterialTheme.typography.labelLarge,
                        )
                        Spacer(Modifier.height(Sizes.SingleHalfPadding))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(Sizes.SingleHalfPadding),
                        ) {
                            Text(
                                text = stringResource(R.string.demo_home_service_selected),
                                style = MaterialTheme.typography.labelMedium,
                            )
                            Box(
                                modifier =
                                    Modifier.background(
                                        color = MaterialTheme.colorScheme.background,
                                        shape = RoundedCornerShape(Sizes.ThreeQuartersPadding),
                                    )
                            ) {
                                Text(
                                    modifier = Modifier.padding(Sizes.ThreeQuartersPadding),
                                    text =
                                        stringResource(
                                            if (!DemoConfigHolder.currentConfig.value.isProd)
                                                R.string.demo_home_service_development
                                            else R.string.demo_home_service_production
                                        ),
                                    style = MaterialTheme.typography.labelMedium,
                                )
                            }
                        }
                        EditBtn { NavigationDispatcher.toEnvironmentChange() }
                    }
                }
                PrimaryButton(text = stringResource(R.string.demo_common_next)) {
                    when (selectedType) {
                        DemoType.Native -> NavigationDispatcher.toNative()
                        DemoType.WebView -> NavigationDispatcher.toWebView()
                    }
                }
                Footer(Modifier.padding(top = Sizes.HalfPadding))
                Spacer(Modifier)
            }
        }
    }
}

@Composable
private fun EditBtn(onClick: () -> Unit) {
    Box(
        modifier =
            Modifier.layout { measurable, constraints ->
                    val placeable =
                        measurable.measure(
                            constraints.copy(
                                maxWidth =
                                    constraints.maxWidth + Sizes.TriplePadding.times(2).roundToPx()
                            )
                        )
                    layout(placeable.width, placeable.height) { placeable.place(0, 0) }
                }
                .fillMaxWidth()
    ) {
        Row(
            modifier =
                Modifier.heightIn(Sizes.MinButtonSize)
                    .clip(RoundedCornerShape(Sizes.ThreeQuartersPadding))
                    .clickable { onClick() }
                    .padding(horizontal = Sizes.TriplePadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Sizes.ThreeQuartersPadding),
        ) {
            val text = stringResource(R.string.demo_common_edit)
            Icon(
                painter = painterResource(R.drawable.ic_demo_edit),
                contentDescription = text,
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = text,
                style =
                    MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
            )
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun HomeScreenNativePreview() = DemoTheme.AutopaySDKTheme { HomeCompose(DemoType.Native) }

@Preview(apiLevel = 34)
@Composable
private fun HomeScreenWebViewPreview() = DemoTheme.AutopaySDKTheme { HomeCompose(DemoType.WebView) }
