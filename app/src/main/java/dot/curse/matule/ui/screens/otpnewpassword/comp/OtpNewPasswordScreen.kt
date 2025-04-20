package dot.curse.matule.ui.screens.otpnewpassword.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dot.curse.matule.R
import dot.curse.matule.ui.items.MatuleButton
import dot.curse.matule.ui.items.MatuleTextField
import dot.curse.matule.ui.screens.otpnewpassword.OtpNewPasswordViewModel
import dot.curse.matule.ui.screens.signin.comp.SignInputRow
import dot.curse.matule.ui.utils.Adaptive

@Composable
fun OtpNewPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: OtpNewPasswordViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val passwordConfirmFocusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp)
        .verticalScroll(rememberScrollState())
        .clickable(null, null) {
            keyboard?.hide()
            focusManager.clearFocus()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Adaptive()
            .adaptiveElementWidthMedium(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.otpnew_title),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.otpnew_text),
                    color = MaterialTheme.colorScheme.outlineVariant,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
            }
            SignInputRow(
                modifier = Adaptive().adaptiveElementWidthMedium(),
                label = stringResource(R.string.otp_new_label_password)
            ) {
                MatuleTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.password,
                    onValueChange = viewModel::updatePasswordInput,
                    placeholder = "********",
                    type = KeyboardType.Password,
                    visual = PasswordVisualTransformation(),
                    actionType = ImeAction.Next,
                    error = state.password == state.passwordError,
                    enabled = !state.loading,
                    actions = KeyboardActions(onNext = {
                        passwordConfirmFocusRequester.requestFocus()
                    })
                )
            }
            SignInputRow(
                modifier = Adaptive().adaptiveElementWidthMedium(),
                label = stringResource(R.string.otp_new_label_password_confirm)
            ) {
                MatuleTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordConfirmFocusRequester),
                    value = state.passwordConfirm,
                    onValueChange = viewModel::updatePasswordConfirmInput,
                    placeholder = "********",
                    type = KeyboardType.Password,
                    visual = PasswordVisualTransformation(),
                    actionType = ImeAction.Done,
                    error = state.passwordConfirm == state.passwordConfirmError,
                    enabled = !state.loading,
                    actions = KeyboardActions(onDone = {
                        keyboard?.hide()
                        focusManager.clearFocus()
                    })
                )
            }
            Text(
                text = when (state.passwordStrength) {
                    3 -> stringResource(R.string.otpnew_str_strong)
                    2 -> stringResource(R.string.otpnew_str_normal)
                    else -> stringResource(R.string.otpnew_str_weak)
                },
                style = MaterialTheme.typography.bodySmall,
                color = when (state.passwordStrength) {
                    3 -> Color.Green
                    2 -> MaterialTheme.colorScheme.onBackground
                    else -> Color.Red
                },
                textAlign = TextAlign.Start,
                textDecoration = TextDecoration.Underline,
                modifier = Adaptive()
                    .adaptiveElementWidthMedium()
                    .padding(vertical = 20.dp)
            )
            MatuleButton(
                modifier = Adaptive().adaptiveElementWidthMedium().padding(top = 25.dp),
                text = stringResource(R.string.b_otpnew),
                active = !state.loading,
                action = { viewModel.apply { navController.onButtonClick() } }
            )
        }
    }
}