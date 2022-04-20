package app.seven.branchcx.modules.auth.use_case

import app.seven.branchcx.core.model.SuspendUseCase
import app.seven.branchcx.modules.auth.data.model.request.LoginRequest
import app.seven.branchcx.modules.auth.data.model.response.AuthResponse
import app.seven.branchcx.modules.auth.data.repo.LoginRepo
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LoginUseCase @Inject constructor(private val repo: LoginRepo): SuspendUseCase<LoginRequest, AuthResponse>() {
    override suspend  fun call(request: LoginRequest): AuthResponse {
       return repo.login(request)
    }

}