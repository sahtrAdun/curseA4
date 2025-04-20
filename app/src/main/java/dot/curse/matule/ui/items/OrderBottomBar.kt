package dot.curse.matule.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dot.curse.matule.R

@Composable
fun OrderBottomBar(
    price: String,
    tax: String,
    withBackground: Boolean = true,
    onClick: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.35f)
        .background(if (withBackground) MaterialTheme.colorScheme.surface else Color.Transparent)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
        ) {
            OrderBottomBarRow(
                leftText = stringResource(R.string.cart_summ),
                rightText = price
            )
            OrderBottomBarRow(
                leftText = stringResource(R.string.cart_delivery),
                rightText = tax
            )
            HorizontalDivider(modifier = Modifier
                .fillMaxWidth(),
                thickness = 1.dp,
                color = Color.LightGray
            )
            OrderBottomBarRow(
                leftText = stringResource(R.string.cart_total),
                rightText = "${price.toFloat() + tax.toFloat()}"
            )
            Spacer(Modifier.weight(1f).height(40.dp))
            /* Кнопка оформления заказа */
            MatuleButton(
                text = stringResource(R.string.b_cart)
            ) { onClick() }
        }
    }
}