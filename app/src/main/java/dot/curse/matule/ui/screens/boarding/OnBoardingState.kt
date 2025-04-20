package dot.curse.matule.ui.screens.boarding

data class OnBoardingState(
    val pages: List<BoardingPage> = emptyList()
)

data class BoardingPage(
    val image: Int,
    val header: String,
    val text: String? = null,
    val buttonText: String
)
