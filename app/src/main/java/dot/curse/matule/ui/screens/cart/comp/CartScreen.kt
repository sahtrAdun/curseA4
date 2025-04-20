package dot.curse.matule.ui.screens.cart.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dot.curse.matule.R
import dot.curse.matule.ui.items.OrderBottomBar
import dot.curse.matule.ui.screens.cart.CartViewModel
import dot.curse.matule.ui.utils.Adaptive

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel(),
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
                    price = state.totalPrice.toString(),
                    tax = state.totalTax.toString()
                ) { viewModel.apply { navController.onButtonClick() } }
            }
        ) { pd ->
            Column(
                modifier = Adaptive()
                    .adaptiveElementWidthMedium()
                    .padding(horizontal = 20.dp)
                    .padding(pd),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${state.items.size} " + stringResource(R.string.cart_count),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                LazyColumn(modifier = Modifier
                    .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(state.items) { item ->
                        CartShoeItem(
                            modifier = Modifier.animateItem(),
                            shoe = item,
                        ) { viewModel.deleteFromCart(item) }
                    }
                }
            }
        }
    }
}