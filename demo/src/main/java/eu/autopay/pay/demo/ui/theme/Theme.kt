package eu.autopay.pay.demo.ui.theme

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.ColorUtils
import androidx.core.view.WindowCompat
import eu.autopay.pay.demo.ui.style.StyleHolder
import eu.autopay.pay.sdk.Autopay
import eu.autopay.pay.sdk.ui.style.APThemeColor
import eu.autopay.pay.sdk.ui.style.AutopayUIStyle

object DemoTheme {
    private val LightColorScheme = mutableStateOf(StyleHolder.getLightColorPalette().lightScheme())
    private val DarkColorScheme = mutableStateOf(StyleHolder.getDarkColorPalette().darkScheme())

    fun setColorPalette(light: StyleHolder.Palette, dark: StyleHolder.Palette) {
        LightColorScheme.value = light.lightScheme()
        DarkColorScheme.value = dark.darkScheme()
        setAutopayUiStyle(light, dark)
    }

    @Composable
    fun AutopaySDKTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit,
    ) {
        val colorScheme = if (darkTheme) DarkColorScheme.value else LightColorScheme.value
        setAutopayUiStyle(StyleHolder.getLightColorPalette(), StyleHolder.getDarkColorPalette())
        val view = LocalView.current
        val activity = LocalActivity.current
        SideEffect {
            activity?.window?.run {
                WindowCompat.setDecorFitsSystemWindows(this, false)
                @Suppress("DEPRECATION")
                statusBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(this, view).isAppearanceLightStatusBars =
                    ColorUtils.calculateLuminance(colorScheme.background.toArgb()) > 0.5
            }
        }
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = Shapes(),
            typography = Typography,
            content = content,
        )
    }

    private fun setAutopayUiStyle(light: StyleHolder.Palette, dark: StyleHolder.Palette) {
        val currentStyle = Autopay.getCurrentUiStyle()
        Autopay.setUiStyle(
            AutopayUIStyle(
                typography =
                    currentStyle.typography.copy(
                        defaultTextColor =
                            APThemeColor(Color(light.textColor), Color(dark.textColor))
                    ),
                primaryButtonStyle =
                    currentStyle.primaryButtonStyle.copy(
                        containerColor =
                            APThemeColor(Color(light.brandColor), Color(dark.brandColor)),
                        inactiveContainerColor =
                            APThemeColor(
                                Color(light.brandColor).copy(alpha = 0.4f),
                                Color(dark.brandColor).copy(alpha = 0.4f),
                            ),
                        contentColor =
                            APThemeColor(
                                Color(light.brandContentColor),
                                Color(dark.brandContentColor),
                            ),
                        inactiveContentColor =
                            APThemeColor(
                                Color(light.brandContentColor).copy(alpha = 0.7f),
                                Color(dark.brandContentColor).copy(alpha = 0.7f),
                            ),
                    ),
                secondaryButtonStyle =
                    currentStyle.secondaryButtonStyle.copy(
                        contentColor =
                            APThemeColor(Color(light.brandColor), Color(dark.brandColor)),
                        inactiveContentColor =
                            APThemeColor(
                                Color(light.brandColor).copy(alpha = 0.7f),
                                Color(dark.brandColor).copy(alpha = 0.7f),
                            ),
                        borderColor = APThemeColor(Color(light.brandColor), Color(dark.brandColor)),
                        inactiveBorderColor =
                            APThemeColor(
                                Color(light.brandColor).copy(alpha = 0.4f),
                                Color(dark.brandColor).copy(alpha = 0.4f),
                            ),
                    ),
                tertiaryButtonStyle =
                    currentStyle.tertiaryButtonStyle.copy(
                        contentColor =
                            APThemeColor(Color(light.brandColor), Color(dark.brandColor)),
                        inactiveContentColor =
                            APThemeColor(
                                Color(light.brandColor).copy(alpha = 0.7f),
                                Color(dark.brandColor).copy(alpha = 0.7f),
                            ),
                        borderColor = APThemeColor(Color(light.brandColor), Color(dark.brandColor)),
                        inactiveBorderColor =
                            APThemeColor(
                                Color(light.brandColor).copy(alpha = 0.4f),
                                Color(dark.brandColor).copy(alpha = 0.4f),
                            ),
                    ),
                inputStyle =
                    currentStyle.inputStyle.copy(
                        inputTextColor =
                            APThemeColor(Color(light.textColor), Color(dark.textColor)),
                        errorTextColor =
                            APThemeColor(Color(light.errorColor), Color(dark.errorColor)),
                        labelTextColor =
                            APThemeColor(Color(light.textColor), Color(dark.textColor)),
                        borderActiveColor =
                            APThemeColor(Color(light.brandColor), Color(dark.brandColor)),
                        borderErrorColor =
                            APThemeColor(Color(light.errorColor), Color(dark.errorColor)),
                        borderInactiveColor =
                            APThemeColor(
                                Color(light.neutralDarkColor).copy(alpha = 0.4f),
                                Color(dark.neutralDarkColor).copy(alpha = 0.4f),
                            ),
                        backgroundColor =
                            APThemeColor(
                                Color(light.boxBackgroundColor),
                                Color(dark.boxBackgroundColor),
                            ),
                        trailingIconsColor =
                            APThemeColor(Color(light.textColor), Color(dark.textColor)),
                        placeholderTextColor =
                            APThemeColor(
                                Color(light.neutralDarkColor),
                                Color(dark.neutralDarkColor),
                            ),
                    ),
                gatewayButtonStyle =
                    currentStyle.gatewayButtonStyle.copy(
                        backgroundColor =
                            APThemeColor(
                                Color(light.boxBackgroundColor),
                                Color(dark.boxBackgroundColor),
                            ),
                        borderColor =
                            APThemeColor(
                                Color(light.neutralDarkColor).copy(alpha = 0.2f),
                                Color(dark.neutralDarkColor).copy(alpha = 0.2f),
                            ),
                        textColor = APThemeColor(Color(light.textColor), Color(dark.textColor)),
                    ),
                gatewayTitleStyle =
                    currentStyle.gatewayTitleStyle.copy(
                        backgroundColor =
                            APThemeColor(
                                Color(light.boxBackgroundColor),
                                Color(dark.boxBackgroundColor),
                            ),
                        textColor = APThemeColor(Color(light.textColor), Color(dark.textColor)),
                    ),
                checkboxStyle =
                    currentStyle.checkboxStyle.copy(
                        checkedColor =
                            APThemeColor(Color(light.brandColor), Color(dark.brandColor)),
                        uncheckedColor =
                            APThemeColor(
                                Color(light.neutralDarkColor),
                                Color(dark.neutralDarkColor),
                            ),
                        errorColor = APThemeColor(Color(light.errorColor), Color(dark.errorColor)),
                    ),
                switchStyle =
                    currentStyle.switchStyle.copy(
                        checkedThumbColor =
                            APThemeColor(
                                Color(light.boxBackgroundColor),
                                Color(dark.boxBackgroundColor),
                            ),
                        checkedTrackColor =
                            APThemeColor(Color(light.brandColor), Color(dark.brandColor)),
                        checkedBorderColor =
                            APThemeColor(Color(light.brandColor), Color(dark.brandColor)),
                        uncheckedThumbColor =
                            APThemeColor(
                                Color(light.neutralDarkColor),
                                Color(dark.neutralDarkColor),
                            ),
                        uncheckedTrackColor =
                            APThemeColor(
                                Color(light.boxBackgroundColor),
                                Color(dark.boxBackgroundColor),
                            ),
                        uncheckedBorderColor =
                            APThemeColor(
                                Color(light.neutralDarkColor),
                                Color(dark.neutralDarkColor),
                            ),
                    ),
                radioButtonStyle =
                    currentStyle.radioButtonStyle.copy(
                        checkedColor =
                            APThemeColor(Color(light.brandColor), Color(dark.brandColor)),
                        uncheckedColor =
                            APThemeColor(
                                Color(light.neutralDarkColor),
                                Color(dark.neutralDarkColor),
                            ),
                    ),
                dialogStyle =
                    currentStyle.dialogStyle.copy(
                        dialogBackgroundColor =
                            APThemeColor(
                                Color(light.neutralLightColor),
                                Color(dark.neutralLightColor),
                            )
                    ),
                loaderStyle =
                    currentStyle.loaderStyle.copy(
                        color = APThemeColor(Color(light.brandColor), Color(dark.brandColor))
                    ),
                bankGridStyle =
                    currentStyle.bankGridStyle.copy(
                        backgroundColor =
                            APThemeColor(
                                Color(light.boxBackgroundColor),
                                Color(dark.boxBackgroundColor),
                            ),
                        checkedBorderColor =
                            APThemeColor(Color(light.brandColor), Color(dark.brandColor)),
                        uncheckedBorderColor =
                            APThemeColor(
                                Color(light.neutralDarkColor).copy(alpha = 0.2f),
                                Color(dark.neutralDarkColor).copy(alpha = 0.2f),
                            ),
                    ),
                paymentSummaryStyle =
                    currentStyle.paymentSummaryStyle.copy(
                        backgroundColor =
                            APThemeColor(
                                Color(light.boxBackgroundColor),
                                Color(dark.boxBackgroundColor),
                            ),
                        dividerColor =
                            APThemeColor(
                                Color(light.neutralDarkColor).copy(alpha = 0.2f),
                                Color(dark.neutralDarkColor).copy(alpha = 0.2f),
                            ),
                    ),
                dccPaymentFormStyle =
                    currentStyle.dccPaymentFormStyle.copy(
                        cellBackgroundColor =
                            APThemeColor(
                                Color(light.boxBackgroundColor),
                                Color(dark.boxBackgroundColor),
                            ),
                        selectedBorderColor =
                            APThemeColor(Color(light.brandColor), Color(dark.brandColor)),
                        unselectedBorderColor =
                            APThemeColor(
                                Color(light.neutralDarkColor).copy(alpha = 0.2f),
                                Color(dark.neutralDarkColor).copy(alpha = 0.2f),
                            ),
                    ),
                errorColor = APThemeColor(Color(light.errorColor), Color(dark.errorColor)),
                footerIconsColor =
                    APThemeColor(Color(light.neutralDarkColor), Color(dark.neutralDarkColor)),
            )
        )
    }

    private fun StyleHolder.Palette.lightScheme() =
        lightColorScheme(
            primary = Color(brandColor),
            onPrimary = Color(brandContentColor),
            secondary = Color(brandContentColor),
            onSecondary = Color(textColor),
            surface = Color(boxBackgroundColor),
            onSurface = Color(textColor),
            outline = Color(neutralDarkColor),
            onSurfaceVariant = Color(neutralDarkColor),
            background = Color(neutralLightColor),
            onBackground = Color(textColor),
            error = Color(errorColor),
        )

    private fun StyleHolder.Palette.darkScheme() =
        darkColorScheme(
            primary = Color(brandColor),
            onPrimary = Color(brandContentColor),
            secondary = Color(brandContentColor),
            onSecondary = Color(textColor),
            surface = Color(boxBackgroundColor),
            onSurface = Color(textColor),
            outline = Color(neutralDarkColor),
            onSurfaceVariant = Color(neutralDarkColor),
            background = Color(neutralLightColor),
            onBackground = Color(textColor),
            error = Color(errorColor),
        )
}
