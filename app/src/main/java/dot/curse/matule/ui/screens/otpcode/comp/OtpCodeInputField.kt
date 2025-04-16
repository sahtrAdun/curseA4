package dot.curse.matule.ui.screens.otpcode.comp

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly

@Composable
fun OtpCodeInputField(
    modifier: Modifier = Modifier,
    number: Int?,
    error: Boolean = false,
    focusRequester: FocusRequester,
    onFocusChange: (Boolean) -> Unit,
    onNumberChange: (Int?) -> Unit,
    onKeyboardBack: () -> Unit
) {
    var text by remember(number) {
        mutableStateOf(
            TextFieldValue(
                text = number?.toString().orEmpty(),
                selection = TextRange(
                    index = if(number != null) 1 else 0
                )
            )
        )
    }
    var isFocused by remember { mutableStateOf(false) }
    val textStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = if (isFocused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
    )
    Box(modifier = modifier
        .size(width = 45.dp, height = 100.dp)
        .then(if (error || isFocused) Modifier.border(
            color = if (error) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(12.dp),
            width = 1.dp
        ) else Modifier)
        .background(
            color = Color.Transparent,
            shape = RoundedCornerShape(12.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                val newNumber = it.text
                if (newNumber.length <= 1 && newNumber.isDigitsOnly()) {
                    onNumberChange(newNumber.toIntOrNull())
                }
            },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            singleLine = true,
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                    onFocusChange(it.isFocused)
                }
                .onKeyEvent { event ->
                    val pressed = event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
                    if (pressed && number == null) {
                        onKeyboardBack()
                    }
                    false
                },
            decorationBox = { inner ->
                inner()
                if (number == null) {
                    Text(
                        text = "-",
                        style = textStyle,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
            }
        )
    }
}