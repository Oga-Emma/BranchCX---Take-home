package app.seven.branchcx.core.service.db

import android.content.SharedPreferences
import app.seven.branchcx.core.service.cache.LocalCache
import timber.log.Timber

class LocalCacheSharedPrefImpl(private  val pref: SharedPreferences): LocalCache {
    override fun saveAuthToken(value: String) {
        Timber.d("Saving auth token $value")
        pref.edit().putString(Keys.authToken, value).apply()
    }

    override fun getAuthToken(): String {
        Timber.d("Getting auth token ${Keys.authToken}")
        return pref.getString(Keys.authToken, "").toString()
    }

    override fun clear() {
        pref.edit().clear().apply()
    }
}

object Keys{
    const val authToken = "authToken"
}