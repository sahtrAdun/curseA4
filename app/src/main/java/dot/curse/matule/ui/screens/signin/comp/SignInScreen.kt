package dot.curse.matule.ui.screens.signin.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dot.curse.matule.ui.items.MatuleTextField
import dot.curse.matule.ui.screens.signin.SignInViewModel
import dot.curse.matule.ui.utils.Adaptive

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel<SignInViewModel>(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val passwordFocusRequester = remember { FocusRequester() }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp)
        .clickable(null, null) {
            keyboard?.hide()
            focusManager.clearFocus()
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SignInputRow(
            modifier = Adaptive().adaptiveElementWidthMedium(),
            label = "Email"
        ) {
            MatuleTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.email,
                onValueChange = viewModel::updateEmailInput,
                placeholder = "email@mail.ru",
                type = KeyboardType.Email,
                actionType = ImeAction.Next,
                error = state.email == state.emailError,
                actions = KeyboardActions(onNext = {
                    passwordFocusRequester.requestFocus()
                })
            )
        }
        SignInputRow(
            modifier = Adaptive().adaptiveElementWidthMedium(),
            label = "Password"
        ) {
            MatuleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordFocusRequester),
                value = state.password,
                onValueChange = viewModel::updatePasswordInput,
                placeholder = "********",
                type = KeyboardType.Password,
                visual = PasswordVisualTransformation(),
                actionType = ImeAction.Done,
                error = state.password == state.passwordError,
                actions = KeyboardActions(onDone = {
                    keyboard?.hide()
                    focusManager.clearFocus()
                })
            )
        }
    }

}