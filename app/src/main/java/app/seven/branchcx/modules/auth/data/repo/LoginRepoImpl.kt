package app.seven.branchcx.modules.auth.data.repo

import app.seven.branchcx.modules.auth.data.data_source.remote.LoginApi
import app.seven.branchcx.modules.auth.data.model.request.LoginRequest
import app.seven.branchcx.modules.auth.data.model.response.AuthResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepoImpl @Inject constructor(private val api: LoginApi): LoginRepo {
    override suspend fun login(request: LoginRequest): AuthResponse {
        return api.login(request)
    }
}