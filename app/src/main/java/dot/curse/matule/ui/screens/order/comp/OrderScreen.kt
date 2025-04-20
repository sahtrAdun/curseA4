package dot.curse.matule.ui.screens.order.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dot.curse.matule.R
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

    Column(modifier = modifier
        .fillMaxSize(),
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
                        value = state.order.email,
                        placeholder = "example@mail.ru",
                        onValueChange = viewModel::updateEmailInput
                    )
                    OrderInputItem(
                        icon = Icons.Default.Phone,
                        label = stringResource(R.string.profile_phone),
                        value = state.order.phone,
                        placeholder = "+7-900-111-22-33",
                        onValueChange = viewModel::updatePhoneInput
                    )
                    Text(
                        text = stringResource(R.string.order_label_address),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    OrderInputItem(
                        icon = Icons.Default.Place,
                        label = stringResource(R.string.order_label_address),
                        value = state.address,
                        placeholder = "1082, Nigeria",
                        onValueChange = viewModel::updateAddressInput
                    )
                    Text(
                        text = stringResource(R.string.order_label_payment),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    CreditCardInputItem(
                        icon = R.drawable.card,
                        cards = state.payments,
                        onAddCard = viewModel::addPayment,
                        onMainCardChanged = viewModel::updatePayment
                    )
                }
            }
        }
    }
}