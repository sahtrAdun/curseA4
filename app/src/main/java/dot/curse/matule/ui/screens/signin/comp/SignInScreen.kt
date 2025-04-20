package dot.curse.matule.ui.screens.signin.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import dot.curse.matule.R
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dot.curse.matule.ui.items.MatuleButton
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
    val passwordFocusRequester = remember { FocusRequester() }
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(R.string.signin_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.signin_text),
            color = MaterialTheme.colorScheme.outlineVariant,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 25.dp)
        )
        SignInputRow(
            modifier = Adaptive()
                .adaptiveElementWidthMedium()
                .padding(top = 30.dp),
            label = stringResource(R.string.email)
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
                enabled = !state.loading,
                actions = KeyboardActions(onNext = {
                    passwordFocusRequester.requestFocus()
                })
            )
        }
        SignInputRow(
            modifier = Adaptive().adaptiveElementWidthMedium(),
            label = stringResource(R.string.password)
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
                enabled = !state.loading,
                actions = KeyboardActions(onDone = {
                    keyboard?.hide()
                    focusManager.clearFocus()
                })
            )
        }
        Row(modifier = Adaptive()
            .adaptiveElementWidthMedium(),
            horizontalArrangement = Arrangement.End
        ) { 
            Text(
                text = stringResource(R.string.signin_restore),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.clickable(null, null) {
                    viewModel.apply { navController.toOtp() }
                }
            )
        }
        MatuleButton(
            modifier = Adaptive().adaptiveElementWidthMedium().padding(top = 25.dp),
            text = stringResource(R.string.b_signin),
            active = !state.loading,
            action = { viewModel.apply { navController.buttonClick() } }
        )
        Column(modifier = Adaptive()
            .adaptiveElementWidthMedium()
            .weight(1f)
            .padding(vertical = 30.dp),
            verticalArrangement = Arrangement.Bottom
        ) { 
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.signin_first_time),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                Text(
                    text = stringResource(R.string.signin_create_account),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable(null, null) {
                        viewModel.apply { navController.toSignUp() }
                    }
                )
            }
        }
    }

}