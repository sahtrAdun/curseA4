package dot.curse.matule.ui.utils

import android.content.Context
import android.content.res.Configuration
import dot.curse.matule.data.storage.SharedManager
import java.util.Locale

object AppLanguage {

    @Suppress("DEPRECATION")
    private fun Context.updateLanguage(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    fun getSystemLanguage(): String {
        return Locale.getDefault().language
    }

    fun Context.initializeLanguage() {
        val sharedManager = SharedManager(this)
        val language = sharedManager.getLanguage()?: getSystemLanguage()
        updateLanguage(language)
    }

    fun Context.changeLanguage(language: String) {
        val sharedManager = SharedManager(this)
        sharedManager.setLanguage(language)
        updateLanguage(language)
    }

}