package dot.curse.matule.ui.screens.signup.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import dot.curse.matule.ui.screens.signin.comp.SignInputRow
import dot.curse.matule.ui.screens.signup.SignUpViewModel
import dot.curse.matule.ui.utils.Adaptive

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val emailFocusRequester = remember { FocusRequester() }
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
            text = stringResource(R.string.signup_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.signup_text),
            color = MaterialTheme.colorScheme.outlineVariant,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 25.dp)
        )
        SignInputRow(
            modifier = Adaptive()
                .adaptiveElementWidthMedium()
                .padding(top = 30.dp),
            label = stringResource(R.string.signup_name)
        ) {
            MatuleTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.name,
                onValueChange = viewModel::updateNameInput,
                placeholder = "Joe",
                type = KeyboardType.Text,
                actionType = ImeAction.Next,
                error = state.name == state.nameError,
                enabled = !state.loading,
                actions = KeyboardActions(onNext = {
                    emailFocusRequester.requestFocus()
                })
            )
        }
        SignInputRow(
            modifier = Adaptive()
                .adaptiveElementWidthMedium(),
            label = stringResource(R.string.email)
        ) {
            MatuleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(emailFocusRequester),
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
            .adaptiveElementWidthMedium()
            .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Checkbox(
                checked = state.confirmed,
                onCheckedChange = viewModel::updateConfirm,
                colors = CheckboxDefaults.colors(
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = stringResource(R.string.signup_permission),
                style = MaterialTheme.typography.bodyLarge,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
        MatuleButton(
            modifier = Adaptive()
                .adaptiveElementWidthMedium()
                .padding(top = 25.dp),
            text = stringResource(R.string.b_signup),
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
                    text = stringResource(R.string.signup_have_account),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                Text(
                    text = stringResource(R.string.signup_login),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable(null, null) {
                        navController.popBackStack()
                    }
                )
            }
        }
    }

}