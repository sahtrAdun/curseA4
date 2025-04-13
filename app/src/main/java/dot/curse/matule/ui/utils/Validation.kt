package dot.curse.matule.ui.utils

import android.content.Context
import android.util.Patterns
import dot.curse.matule.R

object Validation {

    fun Context.validateEmail(email: String): Boolean {
        val answer = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (!answer) this.myToast(this.getString(R.string.er_password_len))
        return answer
    }

    fun Context.validatePassword(password: String): Boolean {
        if (password.length !in 8..20) {
            this.myToast(this.getString(R.string.er_password_len))
            return false // Длинна пароля только в допустимом диапазоне
        }
        if (!password.any { it.isLetter() || it.isDigit() }) {
            this.myToast(this.getString(R.string.er_password_let_or_digit))
            return false // В пароле должна быть хотя бы одна буква или цифра
        }
        if (password.any { it.isLetter() && (it !in 'a'..'z' && it !in 'A'..'Z') }) {
            this.myToast(this.getString(R.string.er_password_lat_only))
            return false // Разрешена только латиница
        }
        if (!password.any { it.isLetter() && it.isUpperCase() }) {
            this.myToast(this.getString(R.string.er_password_upper_case))
            return false // В пароле должна быть хотя бы одна заглавная буква
        }
        if (password.any { !it.isLetter() && !it.isDigit() && it !in "_-!@&:." }) {
            this.myToast(this.getString(R.string.er_password_spec_chars))
            return false // Только определенные спец символы
        }

        return true
    }

}