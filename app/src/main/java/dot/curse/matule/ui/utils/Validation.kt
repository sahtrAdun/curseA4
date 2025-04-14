package dot.curse.matule.ui.utils

import android.content.Context
import android.util.Patterns
import dot.curse.matule.R

object Validation {

    fun Context.validateName(name: String): Boolean {
        if (name.length !in 2..40) {
            myToast(getString(R.string.er_name_len))
            return false // Имя должно состоять как минимум из 2-х букв, максимум - 40-ка
        }

        if (name.any { !it.isLetter() }) {
            myToast(getString(R.string.er_name_spec))
            return false // Имя не должно содержать цифры или специальные символы
        }

        if (!name.first().isUpperCase()) {
            myToast(getString(R.string.er_name_upper))
            return false // Имя должно писаться с заглавной буквы
        }

        return true
    }

    fun Context.validateEmail(email: String): Boolean {
        val answer = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (!answer) myToast(getString(R.string.er_email))
        return answer
    }

    fun Context.validatePassword(password: String): Boolean {
        if (password.length !in 8..20) {
            myToast(getString(R.string.er_password_len))
            return false // Длинна пароля только в допустимом диапазоне
        }
        if (!password.any { it.isLetter() || it.isDigit() }) {
            myToast(getString(R.string.er_password_let_or_digit))
            return false // В пароле должна быть хотя бы одна буква или цифра
        }
        if (password.any { it.isLetter() && (it !in 'a'..'z' && it !in 'A'..'Z') }) {
            myToast(getString(R.string.er_password_lat_only))
            return false // Разрешена только латиница
        }
        if (!password.any { it.isLetter() && it.isUpperCase() }) {
            myToast(getString(R.string.er_password_upper_case))
            return false // В пароле должна быть хотя бы одна заглавная буква
        }
        if (password.any { !it.isLetter() && !it.isDigit() && it !in "_-!@&:." }) {
            myToast(getString(R.string.er_password_spec_chars))
            return false // Только определенные спец символы
        }

        return true
    }

}