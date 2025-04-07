package dot.curse.matule.model.storage

import android.content.Context
import androidx.core.content.edit

class SharedPrefsManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("matule", Context.MODE_PRIVATE)

    fun saveUserId(userId: Int) {
        sharedPreferences.edit { putInt("userId", userId) }
    }

    fun getUserId(): Int {
        return sharedPreferences.getInt("userId", -1)
    }

    fun getDarkTheme(): Boolean {
        return sharedPreferences.getBoolean("dark_theme", false)
    }

    fun getFirstTime(): Boolean {
        return sharedPreferences.getBoolean("first_time", true)
    }
}