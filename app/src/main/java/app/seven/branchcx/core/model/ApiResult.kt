package app.seven.branchcx.core.model

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ApiResult {
    data class Success<out T : Any>(val data: T) : ApiResult()
    data class Error(val message: String) : ApiResult()
    object Loading : ApiResult()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$message]"
            is Loading ->  "Loading"
        }
    }
}