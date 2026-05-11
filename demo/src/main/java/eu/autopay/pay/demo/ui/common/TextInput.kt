package eu.autopay.pay.demo.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import eu.autopay.pay.demo.ui.theme.DemoTheme
import eu.autopay.pay.demo.ui.utils.Sizes

@Composable
fun DemoTextInput(
    textField: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    validInput: Boolean = true,
    placeholder: String? = null,
    prefix: String? = null,
    label: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(Sizes.SinglePadding)) {
        label?.let { Text(text = it, style = MaterialTheme.typography.labelLarge) }
        BasicTextField(
            modifier = Modifier.fillMaxWidth().onFocusChanged { isFocused = it.isFocused },
            value = textField,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true,
            minLines = 1,
            maxLines = 1,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            textStyle =
                MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
            decorationBox = { innerTextField ->
                Decoration(
                    value = textField.text,
                    isFocused = isFocused,
                    placeholder = placeholder,
                    prefix = prefix,
                    validInput = validInput,
                    innerTextField = innerTextField,
                )
            },
        )
    }
}

@Composable
private fun Decoration(
    value: String?,
    validInput: Boolean,
    isFocused: Boolean,
    placeholder: String?,
    prefix: String?,
    innerTextField: @Composable () -> Unit,
) {
    val borderColor =
        when {
            isFocused -> MaterialTheme.colorScheme.primary
            !validInput && !isFocused -> MaterialTheme.colorScheme.error
            else -> MaterialTheme.colorScheme.outline.copy(0.2f)
        }

    Box(
        modifier =
            Modifier.background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(Sizes.TextInputRadius),
                )
                .defaultMinSize(minHeight = Sizes.MinButtonSize)
                .border(
                    width = Sizes.BorderSmall,
                    color = borderColor,
                    shape = RoundedCornerShape(Sizes.TextInputRadius),
                )
                .padding(Sizes.DoublePadding)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Sizes.SinglePadding),
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Row {
                    prefix?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    Box {
                        if (value.isNullOrEmpty() && !placeholder.isNullOrEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.outline,
                            )
                        }
                        innerTextField()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
private fun TextInputEmptyPreview() =
    DemoTheme.AutopaySDKTheme {
        DemoTextInput(
            textField = TextFieldValue(),
            onValueChange = {},
            placeholder = "Placeholder",
            label = "Label",
            prefix = "Prefix",
        )
    }

@Preview(showBackground = true, apiLevel = 34)
@Composable
private fun TextInputFilledPreview() =
    DemoTheme.AutopaySDKTheme {
        DemoTextInput(
            textField = TextFieldValue("InsertedText"),
            onValueChange = {},
            placeholder = "Placeholder",
            label = "Label",
            prefix = "Prefix",
        )
    }

@Preview(showBackground = true, apiLevel = 34)
@Composable
private fun TextInputInvalidPreview() =
    DemoTheme.AutopaySDKTheme {
        DemoTextInput(
            textField = TextFieldValue("InsertedText"),
            onValueChange = {},
            placeholder = "Placeholder",
            label = "Label",
            prefix = "Prefix",
            validInput = false,
        )
    }
