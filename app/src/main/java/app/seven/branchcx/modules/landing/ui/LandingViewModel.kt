package app.seven.branchcx.modules.landing.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.seven.branchcx.core.service.cache.LocalCache
import app.seven.branchcx.modules.landing.data.model.LandingUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(private val cache: LocalCache): ViewModel() {
    init {
        checkUserAuthenticationState()
    }

    private val _uiEvents = MutableLiveData<LandingUIEvent>()
    val uiEvents: LiveData<LandingUIEvent>
        get() = _uiEvents

    private fun checkUserAuthenticationState() {
        viewModelScope.launch {
            delay(500L)
            val token = cache.getAuthToken()
            if (token.isNotBlank()) {
                _uiEvents.postValue(LandingUIEvent.UserAuthenticated)
            } else {
                _uiEvents.postValue(LandingUIEvent.UserNotAuthenticated)
            }
        }
    }
}