package dot.curse.matule.ui.screens.order.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dot.curse.matule.R
import dot.curse.matule.ui.items.EditableText

@Composable
fun CreditCardInputItem(
    icon: Int,
    cards: List<String>,
    onAddCard: (String) -> Unit,
    onMainCardChanged: (String) -> Unit
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var selectedCard by remember { mutableStateOf(if (cards.isNotEmpty()) cards.first() else "") }
    var canEdit by remember { mutableStateOf(false) }

    val hasCards = cards.isNotEmpty()
    val maskedCardNumber = if (hasCards && selectedCard.isNotEmpty()) maskCardNumber(selectedCard) else ""

    Column(modifier = Modifier
        .fillMaxWidth()
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            /* Иконка */
            Box(modifier = Modifier
                .size(32.dp)
                .background(Color.LightGray.copy(alpha = 0.25f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
            /* Значение */
            Column(modifier = Modifier
                .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (hasCards) "DbL Card" else stringResource(R.string.order_add),
                    fontSize = 16.sp,
                    color = Color.Black
                )
                // Редактируемое поле ввода
                EditableText(
                    initialText = if (canEdit) "" else maskedCardNumber,
                    placeholder = "**** ****",
                    onTextChanged = { cardNumber ->
                        if (canEdit && cardNumber.length == 16) {
                            onAddCard(cardNumber)
                            canEdit = false
                        }
                    },
                    enabled = canEdit
                )
            }
            /* Стрелочка вниз или подтверждение */
            if (hasCards) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { isDropdownExpanded = !isDropdownExpanded }
                )
            } else {
                Icon(
                    imageVector = if (!canEdit) Icons.Default.Add else Icons.Default.Check,
                    contentDescription = null,
                    tint = if (!canEdit) Color.Black else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { canEdit = true }
                )
            }
        }

        /* Раскрывающийся список карт */
        DropdownMenu(
            expanded = isDropdownExpanded && hasCards,
            onDismissRequest = { isDropdownExpanded = false },
            containerColor = MaterialTheme.colorScheme.background
        ) {
            cards.forEach { card ->
                DropdownMenuItem(
                    onClick = {
                        selectedCard = card
                        onMainCardChanged(card)
                        isDropdownExpanded = false
                    },
                    text = { Text(maskCardNumber(card)) }
                )
            }
        }
    }
}

private fun maskCardNumber(cardNumber: String): String =
    if (cardNumber.length >= 12) "**** **** **** ${cardNumber.takeLast(4)}}" else cardNumber