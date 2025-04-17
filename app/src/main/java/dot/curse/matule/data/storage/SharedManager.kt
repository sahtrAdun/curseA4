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
        val shared = sharedPreferences.getString("current_user", null)
        return if (shared != null) {
            Json.decodeFromString<User>(shared)
        } else {
            User(id = getLocalUserId())
        }
    }

    fun setLocalCurrentUser(user: User) {
        Json.encodeToString<User>(user).let {
            sharedPreferences.edit { putString("current_user", it) }
        }
    }

    fun getDarkTheme(): Boolean {
        return sharedPreferences.getBoolean("dark_theme", false)
    }

    /*fun setDarkTheme(value: Boolean) {
        sharedPreferences.edit { putBoolean("dark_theme", value) }
    }*/

    fun getLanguage(): String? {
        return sharedPreferences.getString("app_language_code", null)
    }

    fun setLanguage(lang: String) {
        sharedPreferences.edit { putString("app_language_code", lang) }
    }


}