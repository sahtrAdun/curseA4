package dot.curse.matule.ui.screens.searchresult.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dot.curse.matule.R
import dot.curse.matule.domain.model.shoe.ShoeCategory
import dot.curse.matule.ui.items.CategoryRowItem
import dot.curse.matule.ui.items.DummySearchTextField
import dot.curse.matule.ui.items.LabelRow
import dot.curse.matule.ui.screens.searchresult.SearchResultViewModel
import dot.curse.matule.ui.utils.Adaptive
import dot.curse.matule.ui.utils.AppLanguage.translateToSystemDefault

@Composable
fun SearchResultScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val showSearchField = state.searchFilter.text.isNullOrEmpty() == false
    val showCategoriesRow = state.searchFilter.shoeCategory != null

    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp, vertical = 20.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Adaptive()
                .adaptiveElementWidthMedium(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showSearchField) {
                DummySearchTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    onDummyClick = { navController.popBackStack() },
                    onFilterClick = { /* Ничего */ }
                )
            }
            if (showCategoriesRow) {
                LabelRow(
                    label = stringResource(R.string.main_label_category)
                ) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
                    ) {
                        item {
                            CategoryRowItem(
                                label = stringResource(R.string.main_row_item_all)
                            ) { viewModel.apply { navController.allShoes() } }
                        }
                        items(ShoeCategory.entries) { category ->
                            CategoryRowItem(
                                label = category.value.translateToSystemDefault()
                            ) { viewModel.apply { navController.onCategoryClick(category) } }
                        }
                    }
                }
            }

        }
    }
}