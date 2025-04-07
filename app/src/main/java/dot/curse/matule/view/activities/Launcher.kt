package dot.curse.matule.view.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dot.curse.matule.model.api.ApiClient
import dot.curse.matule.model.api.HttpClientProvider
import dot.curse.matule.model.repository.UserRepository
import dot.curse.matule.model.storage.SharedPrefsManager
import dot.curse.matule.view.activities.ui.theme.MatuleTheme
import dot.curse.matule.viewmodel.UserViewModel

class Launcher : ComponentActivity() {
    private lateinit var userRepository: UserRepository
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiClient = ApiClient(HttpClientProvider.client)
        val sharedPrefsManager = SharedPrefsManager(this)
        userRepository = UserRepository(apiClient, sharedPrefsManager)
        userViewModel = UserViewModel(userRepository)



        enableEdgeToEdge()
        setContent {
            MatuleTheme {




            }
        }
    }
}
