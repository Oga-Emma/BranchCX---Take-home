package app.seven.branchcx.modules.auth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.seven.branchcx.core.service.cache.LocalCache
import app.seven.branchcx.core.utils.handleThrowable
import app.seven.branchcx.modules.auth.data.model.request.LoginRequest
import app.seven.branchcx.modules.auth.use_case.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class AuthLoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase,
                                             private val cache: LocalCache): ViewModel() {

    private val _uiEvents: MutableLiveData<LoginUIEvent> by lazy {
        MutableLiveData<LoginUIEvent>()
    }
    val uiEvents: LiveData<LoginUIEvent>
        get() = _uiEvents

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun performLogin(email: String, password: String) {

        postUiEvent(LoginUIEvent.LoginInProgress)

        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO){
                val result = loginUseCase.call(LoginRequest(email, email.reversed()))
                cache.saveAuthToken(result.authToken)
                postUiEvent(LoginUIEvent.LoginSuccess)
            }
        }
    }


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        postUiEvent(LoginUIEvent.LoginFailed( exception.handleThrowable()))
    }

    private fun postUiEvent(event: LoginUIEvent){
        _uiEvents.postValue(event)
    }
}

sealed class LoginUIEvent {
    data class LoginFailed(val message: String): LoginUIEvent()
    object LoginSuccess: LoginUIEvent()
    object LoginInProgress: LoginUIEvent()
}