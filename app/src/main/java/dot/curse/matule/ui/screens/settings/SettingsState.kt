package dot.curse.matule.ui.screens.settings

data class SettingsState(
    val loading: Boolean = false,
    val darkTheme: Boolean = false,
    val language: String = "ru"
)