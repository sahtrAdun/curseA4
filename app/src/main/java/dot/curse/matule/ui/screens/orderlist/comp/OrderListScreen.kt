package dot.curse.matule.ui.screens.orderlist.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dot.curse.matule.ui.screens.orderlist.OrderListViewModel
import dot.curse.matule.ui.utils.Adaptive

@Composable
fun OrderListScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Adaptive()
                .adaptiveElementWidthMedium()
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(state.orders) { order ->
                OrderListShoeItem(
                    modifier = Modifier.animateItem(),
                    order = order,
                    shoe = viewModel.getFirstItem(order)
                ) {
                    viewModel.deleteOrder(order)
                }
            }

        }
    }
}