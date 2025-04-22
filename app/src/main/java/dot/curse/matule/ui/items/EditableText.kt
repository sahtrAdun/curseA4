package dot.curse.matule.ui.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun EditableText(
    modifier: Modifier = Modifier,
    text: String,
    maxLength: Int = Int.MAX_VALUE,
    height: Int = 30,
    onTextChanged: (String) -> Unit,
    placeholder: String,
    enabled: Boolean = true,
    type: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    options: KeyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = type),
    action: KeyboardActions = KeyboardActions()
) {
    val style = MaterialTheme.typography.bodySmall.copy(
        color = MaterialTheme.colorScheme.onBackground,
    )
    BasicTextField(
        modifier = modifier.height(height.dp),
        value = text,
        onValueChange = { value ->
            if (value.length <= maxLength) {
                onTextChanged(value)
            }
        },
        textStyle = style,
        enabled = enabled,
        readOnly = !enabled,
        keyboardOptions = options,
        keyboardActions = action,
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 3.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                innerTextField()
                if (text.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = style.copy(color = MaterialTheme.colorScheme.outline),
                        modifier = Modifier
                            .padding(start = 2.dp)
                    )
                }
            }
        },
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
    )
}