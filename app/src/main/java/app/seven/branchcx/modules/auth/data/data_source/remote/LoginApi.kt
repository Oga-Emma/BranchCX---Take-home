package app.seven.branchcx.modules.auth.data.data_source.remote

import app.seven.branchcx.modules.auth.data.model.request.LoginRequest
import app.seven.branchcx.modules.auth.data.model.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
interface LoginApi {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse
}