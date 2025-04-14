package dot.curse.matule.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dot.curse.matule.R

@Composable
fun MatuleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    error: Boolean = false,
    visual: VisualTransformation = VisualTransformation.None,
    type: KeyboardType = KeyboardType.Text,
    actionType: ImeAction = ImeAction.Default,
    actions: KeyboardActions = KeyboardActions(),
    options: KeyboardOptions = KeyboardOptions(keyboardType = type, imeAction = actionType),
    background: Color = Color.Transparent,
    tint: Color = MaterialTheme.colorScheme.outlineVariant,
    leadingContent: @Composable () -> Unit = {}
) {
    var isVisible by remember { mutableStateOf(false) }
    val isPassword = visual == PasswordVisualTransformation()
    val textStyle = TextStyle(
        color = if (!error) tint else MaterialTheme.colorScheme.error,
        fontSize = 15.sp
    )

    Row(modifier = modifier
        .height(50.dp)
        .background(
            color = background,
            shape = RoundedCornerShape(12.dp)
        )
        .then(if (error) Modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.error,
            shape = RoundedCornerShape(12.dp)
        ) else Modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingContent()
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            enabled = enabled,
            readOnly = !enabled,
            keyboardActions = actions,
            keyboardOptions = options,
            visualTransformation = if (isPassword && isVisible) VisualTransformation.None else visual,
            textStyle = textStyle,
            decorationBox = { innerTextField ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 15.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = textStyle.copy(
                                color = MaterialTheme.colorScheme.outline
                            ),
                            maxLines = 1,
                            modifier = Modifier
                                .padding(start = 1.dp)
                        )
                    }
                }

            }
        )
        if (isPassword) {
            Icon(
                imageVector = ImageVector.vectorResource( if (isVisible) R.drawable.eye_open else R.drawable.eye_close),
                contentDescription = null,
                tint = if (error) MaterialTheme.colorScheme.error else if (isVisible) MaterialTheme.colorScheme.onBackground else tint,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .scale(0.5f)
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
                    .clickable(null, null) { isVisible = !isVisible }
            )
        }
    }
}