package app.seven.branchcx.modules.auth.data.repo

import app.seven.branchcx.modules.auth.data.model.request.LoginRequest
import app.seven.branchcx.modules.auth.data.model.response.AuthResponse
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking


class FakeLoginRepo{
    private val authResponse = AuthResponse(authToken = "SJF5QvfvDOYEvXmy0Qbd3A")
    private val shouldReturnNetworkError = false
//    override suspend fun login(request: LoginRequest): Result<AuthResponse> {
//        return launch {
//            if(shouldReturnNetworkError){
//                return@runBlocking Result.failure(Exception("something went wrong"))
//            }
//            return@runBlocking Result.success(authResponse)
//        }
//    }
}

class LoginRepoImplTest : TestCase() {

    fun testLogin() {}
}