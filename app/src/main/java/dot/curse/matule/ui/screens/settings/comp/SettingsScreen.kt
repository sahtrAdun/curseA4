package dot.curse.matule.ui.screens.settings.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dot.curse.matule.ui.screens.settings.SettingsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dot.curse.matule.R
import dot.curse.matule.ui.utils.Adaptive

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    callBackTheme: (Boolean) -> Unit,
    callBackLang: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var menuExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Adaptive()
                .adaptiveElementWidthMedium()
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp)
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.settings_theme_label),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
                Checkbox(
                    checked = state.darkTheme == true,
                    onCheckedChange = { callBackTheme(viewModel.setDarkTheme()) },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = MaterialTheme.colorScheme.outline,
                        checkedColor = MaterialTheme.colorScheme.primary,
                        checkmarkColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.size(24.dp).padding(10.dp).padding(end = 10.dp)
                )
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(null, null) {
                    menuExpanded = !menuExpanded
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.settings_lang_label),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.settings_lang_en),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        onClick = { callBackLang(viewModel.setLanguage("en")) }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.settings_lang_ru),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        onClick = { callBackLang(viewModel.setLanguage("ru")) }
                    )
                }
                Text(
                    text = stringResource(if (state.language == "ru") R.string.settings_lang_ru else R.string.settings_lang_en),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(10.dp)
                )
            }

        }
    }
}