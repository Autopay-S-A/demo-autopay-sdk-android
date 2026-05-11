@file:OptIn(ExperimentalStdlibApi::class)

package eu.autopay.pay.demo.ui.style

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.graphics.toColorInt
import eu.autopay.pay.demo.R
import eu.autopay.pay.demo.ui.common.DemoTextInput
import eu.autopay.pay.demo.ui.common.Footer
import eu.autopay.pay.demo.ui.common.PrimaryButton
import eu.autopay.pay.demo.ui.common.Toolbar
import eu.autopay.pay.demo.ui.nav.NavigationDispatcher
import eu.autopay.pay.demo.ui.theme.DemoTheme
import eu.autopay.pay.demo.ui.utils.Sizes
import eu.autopay.pay.demo.ui.utils.rememberTextFieldValue

@Composable
fun StyleCompose() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Toolbar(stringResource(R.string.demo_theme_title)) },
    ) { paddingValues ->
        val isDarkMode = isSystemInDarkTheme()
        var lightModeSelected by rememberSaveable { mutableStateOf(!isDarkMode) }

        var brandColorLight: Color by remember {
            mutableStateOf(Color(StyleHolder.lightPalette.value.brandColor))
        }
        var brandContentColorLight: Color by remember {
            mutableStateOf(Color(StyleHolder.lightPalette.value.brandContentColor))
        }
        var neutralLightColorLight: Color by remember {
            mutableStateOf(Color(StyleHolder.lightPalette.value.neutralLightColor))
        }
        var neutralDarkColorLight: Color by remember {
            mutableStateOf(Color(StyleHolder.lightPalette.value.neutralDarkColor))
        }
        var errorColorLight: Color by remember {
            mutableStateOf(Color(StyleHolder.lightPalette.value.errorColor))
        }
        var boxBackgroundColorLight: Color by remember {
            mutableStateOf(Color(StyleHolder.lightPalette.value.boxBackgroundColor))
        }
        var textColorLight: Color by remember {
            mutableStateOf(Color(StyleHolder.lightPalette.value.textColor))
        }

        var brandColorDark: Color by remember {
            mutableStateOf(Color(StyleHolder.darkPalette.value.brandColor))
        }
        var brandContentColorDark: Color by remember {
            mutableStateOf(Color(StyleHolder.darkPalette.value.brandContentColor))
        }
        var neutralLightColorDark: Color by remember {
            mutableStateOf(Color(StyleHolder.darkPalette.value.neutralLightColor))
        }
        var neutralDarkColorDark: Color by remember {
            mutableStateOf(Color(StyleHolder.darkPalette.value.neutralDarkColor))
        }
        var errorColorDark: Color by remember {
            mutableStateOf(Color(StyleHolder.darkPalette.value.errorColor))
        }
        var boxBackgroundColorDark: Color by remember {
            mutableStateOf(Color(StyleHolder.darkPalette.value.boxBackgroundColor))
        }
        var textColorDark: Color by remember {
            mutableStateOf(Color(StyleHolder.darkPalette.value.textColor))
        }

        var textFieldBrandColorLight by rememberTextFieldValue(brandColorLight.hex())
        var textFieldBrandContentColorLight by rememberTextFieldValue(brandContentColorLight.hex())
        var textFieldNeutralLightColorLight by rememberTextFieldValue(neutralLightColorLight.hex())
        var textFieldNeutralDarkColorLight by rememberTextFieldValue(neutralDarkColorLight.hex())
        var textFieldErrorColorLight by rememberTextFieldValue(errorColorLight.hex())
        var textFieldBoxBackgroundColorLight by
            rememberTextFieldValue(boxBackgroundColorLight.hex())
        var textFieldTextColorLight by rememberTextFieldValue(textColorLight.hex())

        var textFieldBrandColorDark by rememberTextFieldValue(brandColorDark.hex())
        var textFieldBrandContentColorDark by rememberTextFieldValue(brandContentColorDark.hex())
        var textFieldNeutralLightColorDark by rememberTextFieldValue(neutralLightColorDark.hex())
        var textFieldNeutralDarkColorDark by rememberTextFieldValue(neutralDarkColorDark.hex())
        var textFieldErrorColorDark by rememberTextFieldValue(errorColorDark.hex())
        var textFieldBoxBackgroundColorDark by rememberTextFieldValue(boxBackgroundColorDark.hex())
        var textFieldTextColorDark by rememberTextFieldValue(textColorDark.hex())
        var isClicked by rememberSaveable { mutableStateOf(false) }

        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Column(modifier = Modifier.weight(1f)) {
                Column(
                    modifier =
                        Modifier.weight(1f)
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(
                                vertical = Sizes.SingleHalfPadding,
                                horizontal = Sizes.DoublePadding,
                            )
                ) {
                    Tabs(lightModeSelected) { lightModeSelected = it }
                    Spacer(Modifier.height(Sizes.SingleHalfPadding))
                    Text(
                        text = stringResource(R.string.demo_theme_brand_color_title),
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.height(Sizes.DoubleHalfPadding))
                    Text(
                        text = stringResource(R.string.demo_theme_brand_color),
                        style = MaterialTheme.typography.labelLarge,
                    )
                    Spacer(Modifier.height(Sizes.SinglePadding))
                    LazyRow(
                        modifier =
                            Modifier.layout { measurable, constraints ->
                                    val placeable =
                                        measurable.measure(
                                            constraints.copy(
                                                maxWidth =
                                                    constraints.maxWidth +
                                                        Sizes.QuadruplePadding.roundToPx()
                                            )
                                        )
                                    layout(placeable.width, placeable.height) {
                                        placeable.place(0, 0)
                                    }
                                }
                                .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Sizes.SinglePadding),
                    ) {
                        item { Spacer(Modifier.width(Sizes.SinglePadding)) }
                        DEFAULT_MAIN_COLORS.forEach { color ->
                            item {
                                val isColorSelected =
                                    lightModeSelected && color == brandColorLight ||
                                        !lightModeSelected && color == brandColorDark

                                val borderWidth by
                                    animateDpAsState(
                                        if (isColorSelected) Sizes.BorderMedium else Sizes.Zero,
                                        label = "borderWidth",
                                    )
                                val borderColor by
                                    animateColorAsState(
                                        if (isColorSelected) {
                                            if (isSystemInDarkTheme()) Color.White else Color.Black
                                        } else Color.Transparent,
                                        label = "borderColor",
                                    )
                                Box(
                                    modifier =
                                        Modifier.size(Sizes.MinButtonSize + Sizes.SinglePadding)
                                            .border(
                                                border = BorderStroke(borderWidth, borderColor),
                                                shape = CircleShape,
                                            ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Box(Modifier) {}

                                    ColorBox(color) {
                                        if (lightModeSelected) brandColorLight = color
                                        else brandColorDark = color
                                        if (lightModeSelected)
                                            textFieldBrandColorLight = TextFieldValue(color.hex())
                                        else textFieldBrandColorDark = TextFieldValue(color.hex())
                                    }
                                }
                            }
                        }
                        item { Spacer(Modifier.width(Sizes.SinglePadding)) }
                    }
                    Spacer(Modifier.height(Sizes.DoublePadding))
                    Box {
                        ColorInput(
                            textField =
                                if (lightModeSelected) textFieldBrandColorLight
                                else textFieldBrandColorDark,
                            onValueChange = { value ->
                                if (
                                    value.text.all { it in CHARSET } &&
                                        value.text.length <= HEX_LENGTH
                                ) {
                                    if (lightModeSelected) textFieldBrandColorLight = value
                                    else textFieldBrandColorDark = value
                                }
                                (if (lightModeSelected) textFieldBrandColorLight
                                    else textFieldBrandColorDark)
                                    .text
                                    .run {
                                        if (this.length == HEX_LENGTH) {
                                            this.parseColor().let {
                                                if (lightModeSelected) brandColorLight = it
                                                else brandColorDark = it
                                            }
                                        }
                                    }
                            },
                            label = stringResource(R.string.demo_theme_brand_insert_hex),
                        )
                        (if (lightModeSelected) brandColorLight else brandColorDark).run {
                            DEFAULT_MAIN_COLORS.none { it == this }
                                .takeIf { it }
                                ?.let {
                                    Icon(
                                        painterResource(R.drawable.ic_demo_check),
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier =
                                            Modifier.align(Alignment.BottomEnd)
                                                .padding(Sizes.DoublePadding),
                                    )
                                }
                        }
                    }
                    Spacer(Modifier.height(Sizes.TriplePadding))
                    Text(
                        text = stringResource(R.string.demo_theme_extra_color_title),
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
                    )
                    Spacer(Modifier.height(Sizes.DoublePadding))
                    ColorInput(
                        textField =
                            if (lightModeSelected) textFieldBrandContentColorLight
                            else textFieldBrandContentColorDark,
                        onValueChange = { value ->
                            if (
                                value.text.all { it in CHARSET } && value.text.length <= HEX_LENGTH
                            ) {
                                if (lightModeSelected) textFieldBrandContentColorLight = value
                                else textFieldBrandContentColorDark = value
                            }
                            (if (lightModeSelected) textFieldBrandContentColorLight
                                else textFieldBrandContentColorDark)
                                .text
                                .run {
                                    if (this.length == HEX_LENGTH) {
                                        this.parseColor().let {
                                            if (lightModeSelected) brandContentColorLight = it
                                            else brandContentColorDark = it
                                        }
                                    }
                                }
                        },
                        label = stringResource(R.string.demo_theme_content_color),
                        color =
                            if (lightModeSelected) brandContentColorLight else brandContentColorDark,
                    )
                    Spacer(Modifier.height(Sizes.DoublePadding))
                    ColorInput(
                        textField =
                            if (lightModeSelected) textFieldNeutralLightColorLight
                            else textFieldNeutralLightColorDark,
                        onValueChange = { value ->
                            if (
                                value.text.all { it in CHARSET } && value.text.length <= HEX_LENGTH
                            ) {
                                if (lightModeSelected) textFieldNeutralLightColorLight = value
                                else textFieldNeutralLightColorDark = value
                            }
                            (if (lightModeSelected) textFieldNeutralLightColorLight
                                else textFieldNeutralLightColorDark)
                                .text
                                .run {
                                    if (this.length == HEX_LENGTH) {
                                        this.parseColor().let {
                                            if (lightModeSelected) neutralLightColorLight = it
                                            else neutralLightColorDark = it
                                        }
                                    }
                                }
                        },
                        label = stringResource(R.string.demo_theme_neutral_light_color),
                        color =
                            if (lightModeSelected) neutralLightColorLight else neutralLightColorDark,
                    )
                    Spacer(Modifier.height(Sizes.DoublePadding))
                    ColorInput(
                        textField =
                            if (lightModeSelected) textFieldNeutralDarkColorLight
                            else textFieldNeutralDarkColorDark,
                        onValueChange = { value ->
                            if (
                                value.text.all { it in CHARSET } && value.text.length <= HEX_LENGTH
                            ) {
                                if (lightModeSelected) textFieldNeutralDarkColorLight = value
                                else textFieldNeutralDarkColorDark = value
                            }
                            (if (lightModeSelected) textFieldNeutralDarkColorLight
                                else textFieldNeutralDarkColorLight)
                                .text
                                .run {
                                    if (this.length == HEX_LENGTH) {
                                        this.parseColor().let {
                                            if (lightModeSelected) neutralDarkColorLight = it
                                            else neutralDarkColorDark = it
                                        }
                                    }
                                }
                        },
                        label = stringResource(R.string.demo_theme_neutral_dark_color),
                        color =
                            if (lightModeSelected) neutralDarkColorLight else neutralDarkColorDark,
                    )
                    Spacer(Modifier.height(Sizes.DoublePadding))
                    ColorInput(
                        textField =
                            if (lightModeSelected) textFieldErrorColorLight
                            else textFieldErrorColorDark,
                        onValueChange = { value ->
                            if (
                                value.text.all { it in CHARSET } && value.text.length <= HEX_LENGTH
                            ) {
                                if (lightModeSelected) textFieldErrorColorLight = value
                                else textFieldErrorColorDark = value
                            }
                            (if (lightModeSelected) textFieldErrorColorLight
                                else textFieldErrorColorDark)
                                .text
                                .run {
                                    if (this.length == HEX_LENGTH) {
                                        this.parseColor().let {
                                            if (lightModeSelected) errorColorLight = it
                                            else errorColorDark = it
                                        }
                                    }
                                }
                        },
                        label = stringResource(R.string.demo_theme_error_color),
                        color = if (lightModeSelected) errorColorLight else errorColorDark,
                    )
                    Spacer(Modifier.height(Sizes.DoublePadding))
                    ColorInput(
                        textField =
                            if (lightModeSelected) textFieldBoxBackgroundColorLight
                            else textFieldBoxBackgroundColorDark,
                        onValueChange = { value ->
                            if (
                                value.text.all { it in CHARSET } && value.text.length <= HEX_LENGTH
                            ) {
                                if (lightModeSelected) textFieldBoxBackgroundColorLight = value
                                else textFieldBoxBackgroundColorDark = value
                            }
                            (if (lightModeSelected) textFieldBoxBackgroundColorLight
                                else textFieldBoxBackgroundColorDark)
                                .text
                                .run {
                                    if (this.length == HEX_LENGTH) {
                                        this.parseColor().let {
                                            if (lightModeSelected) boxBackgroundColorLight = it
                                            else boxBackgroundColorDark = it
                                        }
                                    }
                                }
                        },
                        label = stringResource(R.string.demo_theme_background_color),
                        color =
                            if (lightModeSelected) boxBackgroundColorLight
                            else boxBackgroundColorDark,
                    )
                    Spacer(Modifier.height(Sizes.DoublePadding))
                    ColorInput(
                        textField =
                            if (lightModeSelected) textFieldTextColorLight
                            else textFieldTextColorDark,
                        imeAction = ImeAction.Done,
                        onValueChange = { value ->
                            if (
                                value.text.all { it in CHARSET } && value.text.length <= HEX_LENGTH
                            ) {
                                if (lightModeSelected) textFieldTextColorLight = value
                                else textFieldTextColorDark = value
                            }
                            (if (lightModeSelected) textFieldTextColorLight
                                else textFieldTextColorDark)
                                .text
                                .run {
                                    if (this.length == HEX_LENGTH) {
                                        this.parseColor().let {
                                            if (lightModeSelected) textColorLight = it
                                            else textColorDark = it
                                        }
                                    }
                                }
                        },
                        label = stringResource(R.string.demo_theme_text_color),
                        color = if (lightModeSelected) textColorLight else textColorDark,
                    )
                    Spacer(Modifier.height(Sizes.SinglePadding))
                }
            }
            Column(Modifier.padding(horizontal = Sizes.DoublePadding)) {
                Spacer(Modifier.height(Sizes.SinglePadding))
                PrimaryButton(
                    text = stringResource(R.string.demo_common_save),
                    enabled = !isClicked,
                ) {
                    isClicked = true
                    val lightPalette =
                        StyleHolder.Palette(
                            brandColor = brandColorLight.toArgb().toLong(),
                            brandContentColor = brandContentColorLight.toArgb().toLong(),
                            neutralLightColor = neutralLightColorLight.toArgb().toLong(),
                            neutralDarkColor = neutralDarkColorLight.toArgb().toLong(),
                            errorColor = errorColorLight.toArgb().toLong(),
                            boxBackgroundColor = boxBackgroundColorLight.toArgb().toLong(),
                            textColor = textColorLight.toArgb().toLong(),
                        )

                    StyleHolder.saveLightColorPalette(lightPalette)
                    val darkPalette =
                        StyleHolder.Palette(
                            brandColor = brandColorDark.toArgb().toLong(),
                            brandContentColor = brandContentColorDark.toArgb().toLong(),
                            neutralLightColor = neutralLightColorDark.toArgb().toLong(),
                            neutralDarkColor = neutralDarkColorDark.toArgb().toLong(),
                            errorColor = errorColorDark.toArgb().toLong(),
                            boxBackgroundColor = boxBackgroundColorDark.toArgb().toLong(),
                            textColor = textColorDark.toArgb().toLong(),
                        )
                    StyleHolder.saveDarkColorPalette(darkPalette)
                    DemoTheme.setColorPalette(lightPalette, darkPalette)
                    NavigationDispatcher.popBack()
                }
                Footer(
                    Modifier.padding(top = Sizes.DoublePadding, bottom = Sizes.SingleHalfPadding)
                )
            }
        }
    }
}

@Composable
private fun ColorInput(
    textField: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    label: String? = null,
    color: Color? = null,
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Sizes.SinglePadding),
        verticalAlignment = Alignment.Bottom,
    ) {
        DemoTextInput(
            modifier = Modifier.weight(1f),
            textField = textField,
            onValueChange = onValueChange,
            label = label,
            prefix = "#",
            placeholder = stringResource(R.string.demo_theme_input_placeholder),
            validInput = textField.text.length == HEX_LENGTH,
            keyboardActions =
                KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            keyboardOptions =
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text,
                    imeAction = imeAction,
                ),
        )
        color?.let { ColorBox(color) }
    }
}

@Composable
private fun ColorBox(color: Color, onClick: (() -> Unit)? = null) {
    Card(
        modifier = Modifier.size(Sizes.MinButtonSize),
        colors = CardDefaults.cardColors().copy(containerColor = color),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = Sizes.SinglePadding),
    ) {
        onClick?.let {
            Box(modifier = Modifier.fillMaxSize().clip(CircleShape).clickable { onClick() })
        }
    }
}

@Composable
private fun Tabs(lightModeSelected: Boolean, onTabClick: (Boolean) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(Modifier.height(Sizes.SingleHalfPadding))
        Text(
            text = stringResource(R.string.demo_theme_type_title),
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
        )
        Spacer(Modifier.height(Sizes.DoubleHalfPadding))
        val tabShape = RoundedCornerShape(Sizes.TabRadius)
        Box(Modifier.background(MaterialTheme.colorScheme.surface, tabShape)) {
            TabRow(
                modifier = Modifier.padding(Sizes.HalfPadding).clip(tabShape),
                containerColor = MaterialTheme.colorScheme.surface,
                selectedTabIndex = if (lightModeSelected) 0 else 1,
                indicator = { TabIndicator(it, if (lightModeSelected) 0 else 1) },
                divider = {},
            ) {
                Tab(
                    modifier = Modifier.height(Sizes.MinButtonSize).clip(tabShape),
                    selected = lightModeSelected,
                    onClick = { onTabClick(true) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                ) {
                    Box(modifier = Modifier.height(Sizes.MinButtonSize).clip(tabShape)) {
                        Text(
                            text = stringResource(R.string.demo_theme_light),
                            modifier = Modifier.align(Alignment.Center),
                            style =
                                MaterialTheme.typography.bodyLarge
                                    .copy(fontSize = 18.sp)
                                    .copy(
                                        color =
                                            if (lightModeSelected) MaterialTheme.colorScheme.primary
                                            else MaterialTheme.colorScheme.onSurface
                                    ),
                        )
                    }
                }
                Tab(
                    modifier = Modifier.height(Sizes.MinButtonSize).clip(tabShape),
                    selected = !lightModeSelected,
                    onClick = { onTabClick(false) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                ) {
                    Box(modifier = Modifier.height(Sizes.MinButtonSize).clip(tabShape)) {
                        Text(
                            text = stringResource(R.string.demo_theme_dark),
                            modifier = Modifier.align(Alignment.Center),
                            style =
                                MaterialTheme.typography.bodyLarge
                                    .copy(fontSize = 18.sp)
                                    .copy(
                                        color =
                                            if (!lightModeSelected)
                                                MaterialTheme.colorScheme.primary
                                            else MaterialTheme.colorScheme.onSurface
                                    ),
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
private fun TabIndicator(tabPositions: List<TabPosition>, currentPosition: Int) {
    val transition = updateTransition(currentPosition, label = "tabIndicator")
    val indicatorStart by
        transition.animateDp(
            transitionSpec = {
                if (initialState < targetState) {
                    spring(dampingRatio = 1f, stiffness = 50f)
                } else {
                    spring(dampingRatio = 1f, stiffness = 1000f)
                }
            },
            label = "",
        ) {
            tabPositions[it].left
        }

    val indicatorEnd by
        transition.animateDp(
            transitionSpec = {
                spring(
                    dampingRatio = 1f,
                    stiffness = if (initialState < targetState) 1000f else 50f,
                )
            },
            label = "",
        ) {
            tabPositions[it].right
        }

    Box(
        Modifier.offset(x = indicatorStart)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEnd - indicatorStart)
            .fillMaxSize()
            .border(
                Sizes.BorderMedium,
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(Sizes.TabBorderRadius),
            )
            .zIndex(1f)
    )
}

private val DEFAULT_MAIN_COLORS =
    listOf(
        Color(0xFF2E72BF),
        Color(0xFFEF4444),
        Color(0xFFF97316),
        Color(0xFFFACC15),
        Color(0xFF4ADE80),
        Color(0xFF3B82F6),
    )

private const val HEX_LENGTH = 6
private val CHARSET = "0123456789ABCDEF".toList()

private fun String.parseColor(): Color = Color("#$this".toColorInt())

fun Color.hex() = this.toArgb().toHexString(HexFormat.UpperCase).substring(2)

@Preview(apiLevel = 34)
@Composable
private fun StyleScreenPreview() =
    DemoTheme.AutopaySDKTheme {
        StyleHolder.sharedPreferences =
            LocalContext.current.getSharedPreferences("", Context.MODE_PRIVATE)
        StyleCompose()
    }
