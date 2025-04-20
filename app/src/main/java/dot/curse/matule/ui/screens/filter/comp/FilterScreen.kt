package dot.curse.matule.ui.screens.filter.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import dot.curse.matule.domain.model.shoe.ShoeCategory
import dot.curse.matule.domain.model.shoe.ShoeColors
import dot.curse.matule.domain.model.shoe.ShoeTag
import dot.curse.matule.ui.items.LabelRow
import dot.curse.matule.ui.items.MatuleButton
import dot.curse.matule.ui.items.MatuleTextField
import dot.curse.matule.ui.screens.filter.FilterViewModel
import dot.curse.matule.ui.utils.Adaptive
import dot.curse.matule.ui.utils.AppLanguage.translateToSystemDefault

@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    viewModel: FilterViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val priceToFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp)
        .clickable(null, null) {
            keyboard?.hide()
            focusManager.clearFocus()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            bottomBar = {
                Column(modifier = Modifier
                    .padding(vertical = 15.dp)
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MatuleButton(
                        modifier = Modifier.fillMaxWidth(0.75f),
                        text = stringResource(R.string.b_apply)
                    ) {
                        viewModel.apply { navController.onButtonClick() }
                    }
                }
            }
        ) { pd ->
            LazyColumn(modifier = Adaptive()
                .adaptiveElementWidthMedium()
                .padding(pd),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    LabelRow(
                        label = stringResource(R.string.filter_label_price),
                        background = MaterialTheme.colorScheme.surface
                    ) {
                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            FilterInputField(
                                modifier = Modifier.weight(1f),
                                label = stringResource(R.string.filter_label_price_from)
                            ) {
                                MatuleTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = (state.filter.priceBetween?.entries?.first()?.key?: 0).toString(),
                                    onValueChange = viewModel::updatePriceFrom,
                                    placeholder = "0",
                                    background = MaterialTheme.colorScheme.background,
                                    type = KeyboardType.Number,
                                    actionType = ImeAction.Next,
                                    actions = KeyboardActions(onNext = {
                                        priceToFocusRequester.requestFocus()
                                    })
                                )
                            }
                            FilterInputField(
                                modifier = Modifier.weight(1f),
                                label = stringResource(R.string.filter_label_price_to)
                            ) {
                                MatuleTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .focusRequester(priceToFocusRequester)
                                    ,
                                    value = (state.filter.priceBetween?.entries?.first()?.value?: 10000).toString(),
                                    onValueChange = viewModel::updatePriceTo,
                                    placeholder = "10000",
                                    background = MaterialTheme.colorScheme.background,
                                    type = KeyboardType.Number,
                                    actionType = ImeAction.Next,
                                    actions = KeyboardActions(onNext = {
                                        keyboard?.hide()
                                        focusManager.clearFocus()
                                    })
                                )
                            }
                        }
                    }
                }
                item {
                    LabelRow(
                        label = stringResource(R.string.filter_label_category),
                        background = MaterialTheme.colorScheme.surface
                    ) {
                        ShoeCategory.entries.forEach { category ->
                            FilterListItem(
                                checked = state.filter.shoeCategory == category,
                                onChange = { viewModel.onCategoryClick(category) }
                            ) {
                                Text(
                                    text = category.value.translateToSystemDefault(),
                                    color = MaterialTheme.colorScheme.outline,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
                item { LabelRow(
                    label = stringResource(R.string.filter_label_tag),
                    background = MaterialTheme.colorScheme.surface
                ) {
                    ShoeTag.entries.forEach { tag ->
                        FilterListItem(
                            checked = state.filter.shoeTag == tag,
                            onChange = { viewModel.onTagClick(tag) }
                        ) {
                            Text(
                                text = tag.value.translateToSystemDefault(),
                                color = MaterialTheme.colorScheme.outline,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                } }
                item { LabelRow(
                    label = stringResource(R.string.filter_label_color),
                    background = MaterialTheme.colorScheme.surface
                ) {
                    ShoeColors.entries.forEach { color ->
                        FilterListItem(
                            checked = state.filter.colors?.contains(color.value) == true,
                            onChange = { viewModel.onColorClick(color) }
                        ) {
                            Box(modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = color.color,
                                    shape = RoundedCornerShape(8.dp)
                                )
                            )
                        }
                    }
                } }
            }
        }

    }
}