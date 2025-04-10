package dot.curse.matule.data.storage

import android.content.Context
import androidx.core.content.edit
import dot.curse.matule.domain.model.user.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("Matule", Context.MODE_PRIVATE)

    fun getSharedUserId(): Int {
        return sharedPreferences.getInt("user_id", -1)
    }

    fun setSharedUserId(value: Int) {
        sharedPreferences.edit { putInt("user_id", value) }
    }

    fun getSharedFirstTime(): Boolean {
        return sharedPreferences.getBoolean("first_time", true)
    }

    fun setSharedFirstTime(value: Boolean) {
        sharedPreferences.edit { putBoolean("first_time", value) }
    }

    fun getSharedCurrentUser(): User {
        val shared = sharedPreferences.getString("current_user", null)?: ""
        return Json.decodeFromString<User>(shared)
    }

    fun setSharedCurrentUser(user: User) {
        Json.encodeToString<User>(user).let {
            sharedPreferences.edit { putString("current_user", it) }
        }
    }


}