package dot.curse.matule.ui.screens.otpcode.comp

import android.os.CountDownTimer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dot.curse.matule.R
import dot.curse.matule.ui.screens.otpcode.OtpCodeViewModel
import dot.curse.matule.ui.utils.Adaptive

@Composable
fun OtpCodeScreen(
    modifier: Modifier = Modifier,
    viewModel: OtpCodeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var timeRemain by remember { mutableIntStateOf(59) }
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val timer = remember {
        object : CountDownTimer(59000, 1000) {
            override fun onTick(p0: Long) {
                timeRemain -= 1
            }

            override fun onFinish() {}

        }
    }
    LaunchedEffect(Unit) { timer.start() }
    LaunchedEffect(state.focusedIndex) {
        state.focusedIndex?.let { index ->
            focusRequesters.getOrNull(index)?.requestFocus()
        }
    }
    LaunchedEffect(state.code, keyboard) {
        val allNumberEntered = state.code.none { it == null }
        if (allNumberEntered) {
            focusRequesters.forEach { it.freeFocus() }
            focusManager.clearFocus()
            keyboard?.hide()
        }
    }

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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.otpcode_title),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.otpcode_text),
                    color = MaterialTheme.colorScheme.outlineVariant,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
                Column(modifier = Modifier
                    .padding(top = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    Text(
                        text = stringResource(R.string.otpcode_label),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                    ) {
                        state.code.forEachIndexed { index, number ->
                            OtpCodeInputField(
                                modifier = Modifier,
                                number = number,
                                error = state.isValid == false,
                                focusRequester = focusRequesters[index],
                                onFocusChange = { isFocused ->
                                    if (isFocused) viewModel.onChangeFieldFocused(index)
                                },
                                onNumberChange = { newNumber ->
                                    viewModel.apply { navController.onEnterNumber(newNumber, index) }
                                },
                                onKeyboardBack = {
                                    viewModel.onKeyboardBack()
                                }
                            )
                        }
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                    ) {
                        Text(
                            text = stringResource(R.string.otpcode_again),
                            color = MaterialTheme.colorScheme.outlineVariant,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .clickable(null, null) { viewModel.sendOtpAgain() }
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "00:$timeRemain",
                            color = MaterialTheme.colorScheme.outlineVariant,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}