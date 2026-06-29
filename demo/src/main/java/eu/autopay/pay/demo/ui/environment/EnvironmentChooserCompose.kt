package eu.autopay.pay.demo.ui.environment

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import eu.autopay.pay.demo.R
import eu.autopay.pay.demo.ui.common.DemoSwitch
import eu.autopay.pay.demo.ui.common.DemoTextInput
import eu.autopay.pay.demo.ui.common.Footer
import eu.autopay.pay.demo.ui.common.PrimaryButton
import eu.autopay.pay.demo.ui.common.Toolbar
import eu.autopay.pay.demo.ui.nav.NavigationDispatcher
import eu.autopay.pay.demo.ui.theme.DemoTheme
import eu.autopay.pay.demo.ui.utils.DemoConfig
import eu.autopay.pay.demo.ui.utils.DemoConfigHolder
import eu.autopay.pay.demo.ui.utils.Sizes
import eu.autopay.pay.demo.ui.utils.rememberTextFieldValue

@Composable
fun EnvironmentChooserCompose() {
    val focusManager = LocalFocusManager.current
    val currentConfig = DemoConfigHolder.currentConfig.value
    var isDevSelected by rememberSaveable { mutableStateOf(!currentConfig.isProd) }

    var tokenTextField by rememberTextFieldValue(currentConfig.token)
    var acceptorIdTextField by rememberTextFieldValue(currentConfig.acceptorId)
    var serviceIdTextField by rememberTextFieldValue(currentConfig.serviceId)
    var amountTextField by
        rememberTextFieldValue("%.2f".format(currentConfig.amount).replace(",", "."))
    var paymentSummaryTextField by rememberTextFieldValue(currentConfig.paymentSummary ?: "")
    var isClicked by rememberSaveable { mutableStateOf(false) }
    var currencyTextField by rememberTextFieldValue(currentConfig.currency)
    var contextPathTextField by rememberTextFieldValue(currentConfig.contextPath)
    var useWebBlik by rememberSaveable { mutableStateOf(currentConfig.useWebBlik) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Toolbar(stringResource(R.string.demo_home_service)) },
    ) { paddingValues ->
        Column(Modifier.fillMaxSize().padding(paddingValues)) {
            Column(
                modifier =
                    Modifier.verticalScroll(rememberScrollState())
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(
                            horizontal = Sizes.DoublePadding,
                            vertical = Sizes.SingleHalfPadding,
                        )
            ) {
                Text(
                    text = stringResource(R.string.demo_config_subtitle),
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(Sizes.SingleHalfPadding))
                Text(
                    text = stringResource(R.string.demo_config_content),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(Sizes.TriplePadding))
                SDKTypeBtn(!isDevSelected, stringResource(R.string.demo_home_service_production)) {
                    isDevSelected = false
                }
                Spacer(Modifier.height(Sizes.SingleHalfPadding))
                SDKTypeBtn(isDevSelected, stringResource(R.string.demo_home_service_development)) {
                    isDevSelected = true
                }
                Spacer(Modifier.height(Sizes.TriplePadding))
                Text(
                    text = stringResource(R.string.demo_service_config_section_title),
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(Sizes.DoublePadding))
                DemoTextInput(
                    textField = tokenTextField,
                    onValueChange = { tokenTextField = it },
                    keyboardActions =
                        KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    label = stringResource(R.string.demo_service_config_token),
                    validInput = tokenTextField.text.isNotEmpty(),
                )
                Spacer(Modifier.height(Sizes.DoublePadding))
                DemoTextInput(
                    textField = serviceIdTextField,
                    onValueChange = { serviceIdTextField = it },
                    keyboardActions =
                        KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                    label = stringResource(R.string.demo_service_config_service_id),
                    validInput = serviceIdTextField.text.isNotEmpty(),
                )
                Spacer(Modifier.height(Sizes.DoublePadding))
                DemoTextInput(
                    textField = acceptorIdTextField,
                    onValueChange = { acceptorIdTextField = it },
                    keyboardActions =
                        KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                    label = stringResource(R.string.demo_service_config_acceptor_id),
                    validInput = acceptorIdTextField.text.isNotEmpty(),
                )
                Spacer(Modifier.height(Sizes.DoublePadding))
                DemoTextInput(
                    textField = contextPathTextField,
                    onValueChange = { contextPathTextField = it },
                    keyboardActions =
                        KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    label = stringResource(R.string.demo_service_config_context_path),
                    validInput =
                        contextPathTextField.text.isNotEmpty() &&
                            contextPathTextField.text.startsWith("/"),
                )
                Spacer(Modifier.height(Sizes.DoublePadding))
                DemoTextInput(
                    textField = amountTextField,
                    onValueChange = { amountTextField = it.copy(text = it.text.replace(",", ".")) },
                    keyboardActions =
                        KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                        ),
                    label = stringResource(R.string.demo_service_config_price),
                    validInput =
                        amountTextField.text.isNotEmpty() &&
                            amountTextField.text.toBigDecimalOrNull() != null,
                )
                Spacer(Modifier.height(Sizes.DoublePadding))
                DemoTextInput(
                    textField = currencyTextField,
                    onValueChange = { currencyTextField = it },
                    keyboardActions =
                        KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    label = stringResource(R.string.demo_service_config_currency),
                    validInput = currencyTextField.text.isNotEmpty(),
                )
                Spacer(Modifier.height(Sizes.DoublePadding))
                DemoTextInput(
                    textField = paymentSummaryTextField,
                    onValueChange = { paymentSummaryTextField = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    label = stringResource(R.string.demo_service_config_payment_summary),
                )
                Spacer(Modifier.height(Sizes.DoublePadding))
                DemoSwitch(
                    text = stringResource(R.string.demo_service_config_use_web_blik),
                    checked = useWebBlik,
                    onCheckedChange = { useWebBlik = it },
                )
            }
            Column(
                Modifier.fillMaxWidth()
                    .padding(horizontal = Sizes.DoublePadding, vertical = Sizes.SingleHalfPadding)
            ) {
                val isSaveEnabled =
                    listOf(
                            tokenTextField,
                            currencyTextField,
                            serviceIdTextField,
                            acceptorIdTextField,
                        )
                        .all { it.text.isNotEmpty() } &&
                        listOf(serviceIdTextField, acceptorIdTextField).all {
                            it.text.all { c -> c.isDigit() }
                        } &&
                        amountTextField.text.toBigDecimalOrNull() != null &&
                        contextPathTextField.text.startsWith("/")
                PrimaryButton(
                    text = stringResource(R.string.demo_common_save),
                    enabled = isSaveEnabled && !isClicked,
                ) {
                    isClicked = true
                    DemoConfigHolder.setConfig(
                        DemoConfig(
                            isProd = !isDevSelected,
                            token = tokenTextField.text,
                            serviceId = serviceIdTextField.text,
                            acceptorId = acceptorIdTextField.text,
                            googlePayMerchantId = currentConfig.googlePayMerchantId,
                            amount = amountTextField.text.toBigDecimal(),
                            paymentSummary = paymentSummaryTextField.text,
                            email = currentConfig.email,
                            currency = currencyTextField.text.uppercase(),
                            contextPath = contextPathTextField.text,
                            useWebBlik = useWebBlik,
                        )
                    )
                    NavigationDispatcher.popBack()
                }
                Footer(Modifier.padding(top = Sizes.DoublePadding))
            }
        }
    }
}

@Composable
private fun SDKTypeBtn(selected: Boolean, text: String, onClick: () -> Unit) {
    val borderColor by
        animateColorAsState(
            if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
            label = "borderColor",
        )
    val borderWidth by
        animateDpAsState(
            if (selected) Sizes.BorderMedium else Sizes.BorderSmall,
            label = "borderWidth",
        )
    Box(
        Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(Sizes.DoublePadding))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(Sizes.DoublePadding))
            .border(borderWidth, borderColor, RoundedCornerShape(Sizes.DoublePadding))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Sizes.TriplePadding),
            horizontalArrangement = Arrangement.spacedBy(Sizes.DoublePadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f).padding(vertical = Sizes.QuarterPadding),
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
            )
            AnimatedVisibility(visible = selected) {
                Icon(
                    painter = painterResource(R.drawable.ic_demo_selected_round),
                    contentDescription = text,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Preview(apiLevel = 34, heightDp = 1200)
@Composable
private fun EnvironmentChooserScreenPreview() =
    DemoTheme.AutopaySDKTheme { EnvironmentChooserCompose() }
