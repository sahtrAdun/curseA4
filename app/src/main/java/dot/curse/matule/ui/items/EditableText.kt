package dot.curse.matule.ui.items

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun EditableText(
    modifier: Modifier = Modifier,
    initialText: String = "",
    placeholder: String,
    onTextChanged: (String) -> Unit,
    enabled: Boolean = true
) {
    var text by remember { mutableStateOf(initialText) }

    BasicTextField(
        value = text,
        onValueChange = {
            if (enabled) {
                text = it
                onTextChanged(it)
            }
        },
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onBackground, // Цвет текста
            fontSize = 16.sp // Размер шрифта
        ),
        modifier = modifier,
        decorationBox = { innerTextField ->
            // Декорация для поля ввода
            if (text.isEmpty()) {
                Text(
                    text = placeholder, // Placeholder
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                )
            } else {
                innerTextField() // Отображаем введенный текст
            }
        },
        cursorBrush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary) // Цвет курсора
    )
}