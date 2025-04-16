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

    fun Context.validatePassword(password: String): Int {
        var strength = 1
        if (password.length !in 8..20) {
            myToast(getString(R.string.er_password_len))
            return 0 // Длинна пароля только в допустимом диапазоне
        }
        if (!password.any { it.isLetter() || it.isDigit() }) {
            myToast(getString(R.string.er_password_let_or_digit))
            return 0 // В пароле должна быть хотя бы одна буква или цифра
        }
        if (password.any { it.isLetter() && (it !in 'a'..'z' && it !in 'A'..'Z') }) {
            myToast(getString(R.string.er_password_lat_only))
            return 0 // Разрешена только латиница
        }
        if (password.any { it.isLetter() && it.isUpperCase() }) {
            strength += 1 // Надбавка за заглавную букву
        }
        if (password.any { it in "_-!@&:." }) {
            strength += 1 // Надбавка за спец. символы
        }
        if (strength == 1) {
            myToast(getString(R.string.er_password_weak))
        }

        return strength
    }

    fun Context.validatePasswordWithoutNotice(password: String): Int {
        var strength = 1
        if (password.length !in 8..20) {
            return 0 // Длинна пароля только в допустимом диапазоне
        }
        if (!password.any { it.isLetter() || it.isDigit() }) {
            return 0 // В пароле должна быть хотя бы одна буква или цифра
        }
        if (password.any { it.isLetter() && (it !in 'a'..'z' && it !in 'A'..'Z') }) {
            return 0 // Разрешена только латиница
        }
        if (password.any { it.isLetter() && it.isUpperCase() }) {
            strength += 1 // Надбавка за заглавную букву
        }
        if (password.any { it in "_-!@&:." }) {
            strength += 1 // Надбавка за спец. символы
        }

        return strength
    }

}