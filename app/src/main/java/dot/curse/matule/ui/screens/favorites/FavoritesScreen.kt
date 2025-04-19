package dot.curse.matule.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dot.curse.matule.ui.items.DummyShoeItem
import dot.curse.matule.ui.items.ShoeItem
import dot.curse.matule.ui.utils.Adaptive

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Adaptive()
                .adaptiveElementWidthMedium(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Adaptive(minSize = 160.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (state.list.isNotEmpty()) {
                    items(state.list) { shoe ->
                        val inCart = state.userCart.contains(shoe)

                        ShoeItem(
                            modifier = Modifier.animateItem(),
                            shoe = shoe,
                            inFavorite = true,
                            inCart = inCart,
                            onTagClick = { viewModel.apply { navController.onTagClick(shoe.tag) } },
                            onHeartClick = { viewModel.deleteFromFavorite(shoe) },
                            onCartClick = { if (!inCart) viewModel.addToCart(shoe) else viewModel.deleteFromCart(shoe) },
                            onItemClick = { viewModel.apply { navController.shoeDetail(shoe) } }
                        )
                    }
                } else {
                    repeat(6) { item { DummyShoeItem(modifier = Modifier.animateItem()) } }
                }
            }
        }
    }
}