package app.seven.branchcx.core.utils

import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.handleThrowable(): String {
    try{
        Timber.d(this.toString())
        return when (this) {
            is HttpException -> {
                var message = "Something went wrong"
                val errorBody = this.response()?.errorBody()?.string()

                errorBody?.let {
                    val jsonObject = JSONObject(it.trim())
                    if(jsonObject.has("error")) {
                        val error = jsonObject.getString("error")
                        message = error.toString()
                    }else if(jsonObject.has("message")) {
                        val error = jsonObject.getJSONObject("message")
                        message = error.toString()
                    }
                }
                message
            }
            is UnknownHostException -> "Connection error"
            is SocketTimeoutException -> "Please check your connectivity and try again"
            else -> "Unknown error"
        }
    }catch (err: Exception){
        return err.handleThrowable()
    }
}