package dot.curse.matule.ui.screens.order.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dot.curse.matule.R
import dot.curse.matule.ui.items.EditableText
import dot.curse.matule.ui.items.OrderBottomBar
import dot.curse.matule.ui.screens.order.OrderViewModel
import dot.curse.matule.ui.utils.Adaptive

@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val emailFocus = remember { FocusRequester() }
    val phoneFocus = remember { FocusRequester() }
    val addressFocus = remember { FocusRequester() }
    val paymentFocus = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier
        .fillMaxSize()
        .clickable(null, null) {
            keyboard?.hide()
            focusManager.clearFocus()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            bottomBar = {
                OrderBottomBar(
                    price = state.order.price.toString(),
                    tax = state.order.tax.toString().toString(),
                    withBackground = false
                ) { viewModel.apply { navController.onButtonClick() } }
            }
        ) { pd ->
            Column(
                modifier = Adaptive()
                    .adaptiveElementWidthMedium()
                    .padding(horizontal = 20.dp)
                    .padding(pd)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier
                    .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.order_label_inf),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    OrderInputItem(
                        icon = Icons.Default.Email,
                        label = stringResource(R.string.email),
                        editable = !state.loading,
                        focus = emailFocus
                    ) { canEdit ->
                        EditableText(
                            modifier = Modifier.focusRequester(emailFocus),
                            text = state.email,
                            onTextChanged = viewModel::updateEmailInput,
                            placeholder = "example@mail.com",
                            maxLength = 60,
                            enabled = canEdit,
                            type = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                            action = KeyboardActions(onNext = {
                                phoneFocus.requestFocus()
                            })
                        )
                    }
                    OrderInputItem(
                        icon = Icons.Default.Phone,
                        label = stringResource(R.string.profile_phone),
                        editable = !state.loading,
                        focus = phoneFocus
                    ) { canEdit ->
                        EditableText(
                            modifier = Modifier.focusRequester(phoneFocus),
                            text = state.phone,
                            onTextChanged = viewModel::updatePhoneInput,
                            placeholder = "8-900-333-22-11",
                            maxLength = 11,
                            enabled = canEdit,
                            type = KeyboardType.Phone,
                            imeAction = ImeAction.Next,
                            action = KeyboardActions(onNext = {
                                addressFocus.requestFocus()
                            })
                        )
                    }
                    Text(
                        text = stringResource(R.string.order_label_address),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    OrderInputItem(
                        icon = Icons.Default.Place,
                        label = stringResource(R.string.order_label_address),
                        editable = !state.loading,
                        focus = addressFocus
                    ) { canEdit ->
                        EditableText(
                            modifier = Modifier.focusRequester(addressFocus),
                            text = state.address,
                            onTextChanged = viewModel::updateAddressInput,
                            placeholder = "Negro-aroe 12",
                            maxLength = 60,
                            enabled = canEdit,
                            type = KeyboardType.Text,
                            imeAction = if (state.order.payment.length < 16) ImeAction.Next else ImeAction.Done,
                            action = KeyboardActions(
                                onNext = {
                                    paymentFocus.requestFocus()
                                },
                                onDone = {
                                    keyboard?.hide()
                                    focusManager.clearFocus()
                                }
                            )
                        )
                    }
                    Text(
                        text = stringResource(R.string.order_label_payment),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    CreditCardInputItem(
                        cards = state.payments,
                        onCardSelection = viewModel::selectPayment,
                        editable = !state.loading
                    ) { canEdit ->
                        EditableText(
                            modifier = Modifier.height(30.dp).focusRequester(paymentFocus),
                            text = state.order.payment,
                            onTextChanged = viewModel::updatePayment,
                            placeholder = "0000-1111-2222-3333",
                            maxLength = 16,
                            enabled = canEdit,
                            type = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                            action = KeyboardActions(onDone = {
                                keyboard?.hide()
                                focusManager.clearFocus()
                            })
                        )
                    }
                }
            }
        }
    }
}