package dot.curse.matule.ui.screens.main.comp

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dot.curse.matule.R
import dot.curse.matule.domain.model.shoe.ShoeCategory
import dot.curse.matule.ui.screens.main.MainViewModel
import dot.curse.matule.ui.utils.Adaptive

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
            DummySearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                onDummyClick = { viewModel.apply { navController.onDummySearchFieldClick() } },
                onFilterClick = { viewModel.apply { navController.onFilterClick() } }
            )
            MainLabelRow(
                label = stringResource(R.string.main_label_category),
                withAll = false,
                onAllClick = { /* Ничего */ }
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
                ) {
                    item {
                        CategoryRowItem(
                            label = "All"
                        ) { viewModel.apply { navController.onCategoryClick() } } // TODO Переход ко всем shoe
                    }
                    items(ShoeCategory.entries) { category ->
                        CategoryRowItem(
                            label = category.value
                        ) { viewModel.apply { navController.onCategoryClick() } }
                    }
                }
            }
            MainLabelRow(
                label = stringResource(R.string.main_label_popular),
                withAll = true,
                onAllClick = { viewModel.apply { navController.allPopular() } }
            ) {
                // TODO Популярные
            }
            MainLabelRow(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.main_label_sales),
                withAll = true,
                onAllClick = { viewModel.apply { navController.allSales() } }
            ) {
                Image(
                    painter = painterResource(R.drawable.dummy_sale),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
            }
        }
    }
}