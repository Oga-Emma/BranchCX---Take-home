package app.seven.branchcx.core.service.cache

interface LocalCache {
    fun saveAuthToken(value: String): Unit
    fun getAuthToken(): String
    fun clear()
}