package app.seven.branchcx.modules.auth.data.repo

import app.seven.branchcx.modules.auth.data.model.request.LoginRequest
import app.seven.branchcx.modules.auth.data.model.response.AuthResponse

interface LoginRepo {
    suspend fun login(request: LoginRequest): AuthResponse
}