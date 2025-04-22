package dot.curse.matule.ui.screens.order.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dot.curse.matule.R

@Composable
fun CreditCardInputItem(
    cards: List<String>,
    editable: Boolean,
    onCardSelection: (String) -> Unit,
    inputContent: @Composable (Boolean) -> Unit
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var canEdit by remember { mutableStateOf(false) }
    val hasCards = cards.isNotEmpty()

    fun maskCardNumber(cardNumber: String): String =
        if (cardNumber.length >= 16) "**** **** **** ${cardNumber.takeLast(4)}}" else cardNumber

    Column(modifier = Modifier
        .fillMaxWidth()
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(modifier = Modifier
                .size(32.dp)
                .background(
                    color =  MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(8.dp)
                ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.card),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = if (hasCards) "DbL Card" else stringResource(R.string.order_add),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    inputContent(canEdit && editable)
                }
                if (editable) {
                    if (hasCards) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { isDropdownExpanded = !isDropdownExpanded }
                        )
                    } else {
                        Icon(
                            imageVector = if (!canEdit) Icons.Default.Add else Icons.Default.Check,
                            contentDescription = null,
                            tint = if (!canEdit) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable { canEdit = true }
                        )
                    }
                }

            }

        }

        DropdownMenu(
            modifier = Modifier,
            expanded = isDropdownExpanded && hasCards,
            onDismissRequest = { isDropdownExpanded = false },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            cards.forEach { card ->
                DropdownMenuItem(
                    onClick = {
                        onCardSelection(card)
                        isDropdownExpanded = false
                    },
                    text = { Text(
                        maskCardNumber(card)
                    ) }
                )
            }
        }
    }

}

