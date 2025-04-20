package dot.curse.matule.data.storage

import android.content.Context
import androidx.core.content.edit
import dot.curse.matule.domain.model.user.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("Matule", Context.MODE_PRIVATE)

    fun getLocalUserId(): Int {
        return sharedPreferences.getInt("user_id", -1)
    }

    fun setLocalUserId(value: Int) {
        sharedPreferences.edit { putInt("user_id", value) }
    }

    fun getLocalFirstTime(): Boolean {
        return sharedPreferences.getBoolean("first_time", true)
    }

    fun setLocalFirstTime(value: Boolean) {
        sharedPreferences.edit { putBoolean("first_time", value) }
    }

    fun getLocalCurrentUser(): User {
        return sharedPreferences.getString("current_user", null)?.let {
            Json.decodeFromString<User>(it)
        }?: User(id = getLocalUserId())
    }

    fun setLocalCurrentUser(user: User) {
        Json.encodeToString<User>(user).let {
            sharedPreferences.edit { putString("current_user", it) }
        }
    }

    fun clearUserData() {
        setLocalUserId(-1)
        sharedPreferences.edit {
            remove("current_user")
        }
    }

    fun getDarkTheme(): Boolean {
        return sharedPreferences.getBoolean("dark_theme", false)
    }

    fun setDarkTheme(value: Boolean) {
        sharedPreferences.edit { putBoolean("dark_theme", value) }
    }

    fun getLanguage(): String? {
        return sharedPreferences.getString("app_language_code", null)
    }

    fun setLanguage(lang: String) {
        sharedPreferences.edit { putString("app_language_code", lang) }
    }

    fun setLocalSearchHistory(history: List<String>) {
        sharedPreferences.edit {
            putString("search_history", Json.encodeToString<List<String>>(history))
        }
    }

    fun getLocalSearchHistory(): List<String> {
        return sharedPreferences.getString("search_history", null)?.let {
            Json.decodeFromString<List<String>>(it)
        }?: emptyList<String>()
    }


}