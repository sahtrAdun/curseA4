package dot.curse.matule.ui.screens.search.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import dot.curse.matule.ui.items.MatuleTextField
import dot.curse.matule.ui.utils.Adaptive
import dot.curse.matule.R
import dot.curse.matule.ui.screens.search.SearchViewModel
import dot.curse.matule.ui.screens.searchresult.comp.SearchHistoryItem

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val textFocusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) { textFocusRequester.requestFocus() }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp, vertical = 20.dp)
        .clickable(null, null) {
            keyboard?.hide()
            focusManager.clearFocus()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Adaptive()
                .adaptiveElementWidthMedium(),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MatuleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(textFocusRequester),
                value = state.text,
                onValueChange = viewModel::updateTextInput,
                placeholder = stringResource(R.string.main_search),
                background = MaterialTheme.colorScheme.surface,
                actionType = ImeAction.Search,
                actions = KeyboardActions(onSearch = {
                    keyboard?.hide()
                    focusManager.clearFocus()
                    viewModel.onSearch(navController)
                }),
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .clickable(null, null) {
                            keyboard?.hide()
                            focusManager.clearFocus()
                            viewModel.onSearch(navController)
                        }
                    )
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                items(state.history) { item ->
                    SearchHistoryItem(
                        modifier = Modifier.animateItem(),
                        name = item,
                    ) {
                        keyboard?.hide()
                        focusManager.clearFocus()
                        viewModel.updateTextInput(item)
                        viewModel.onSearch(navController)
                    }
                }
            }

        }
    }
}