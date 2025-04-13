package dot.curse.matule.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BaseLayout(
    viewModel: HeaderViewModel = hiltViewModel(),
    background: Color,
    content: @Composable () -> Unit
) {
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(color = background),
        topBar = {
            MatuleHeaderView(view = viewModel)
        }
    ) { pd ->
        Column(modifier = Modifier
            .padding(pd)
        ) {
            content()
        }
    }
}