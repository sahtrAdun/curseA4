package dot.curse.matule.data.storage

import android.content.Context
import androidx.core.content.edit
import dot.curse.matule.data.model.users.User
import io.ktor.utils.io.concurrent.shared
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("Matule", Context.MODE_PRIVATE)

    fun Context.getSharedUserId(): Int {
        return sharedPreferences.getInt("user_id", -1)
    }

    fun Context.setSharedUserId(value: Int) {
        sharedPreferences.edit {
            putInt("user_id", value)
        }
    }

    fun Context.getSharedFirstTime(): Boolean {
        return sharedPreferences.getBoolean("first_time", true)
    }

    fun Context.setSharedFirstTime(value: Boolean) {
        sharedPreferences.edit {
            putBoolean("first_time", value)
        }
    }

    fun Context.getSharedCurrentUser(): User {
        val shared = sharedPreferences.getString("current_user", null)?: ""
        return Json.decodeFromString<User>(shared)
    }

    fun Context.setSharedCurrentUser(user: User) {
        Json.encodeToString<User>(user).let {
            sharedPreferences.edit {
                putString("current_user", it)
            }
        }
    }


}