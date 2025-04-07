package dot.curse.matule.model.repository

import dot.curse.matule.model.storage.SharedPrefsManager

class SplashRepository(private val sharedPrefsManager: SharedPrefsManager) {

    fun getFirstTime(): Boolean {
        return sharedPrefsManager.getFirstTime()
    }

    fun getUserId(): Int {
        return sharedPrefsManager.getUserId()
    }

}