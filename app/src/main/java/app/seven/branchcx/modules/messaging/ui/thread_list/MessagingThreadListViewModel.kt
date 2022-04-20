package app.seven.branchcx.modules.messaging.ui.thread_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.seven.branchcx.core.utils.handleThrowable
import app.seven.branchcx.modules.messaging.data.model.MessageThread
import app.seven.branchcx.modules.messaging.use_cases.FetchThreadsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ThreadListViewModel @Inject constructor(private val fetchThreadsUseCase: FetchThreadsUseCase): ViewModel() {

    private val _uiEvents = MutableLiveData<ThreadListUiEvents>()
    val uiEvents: LiveData<ThreadListUiEvents>
        get() = _uiEvents

    fun fetchData(force: Boolean) {
        postUiEvents(ThreadListUiEvents.Loading)
        viewModelScope.launch (exceptionHandler){
            withContext(Dispatchers.IO) {
                val result = fetchThreadsUseCase.call(force)
                postUiEvents(ThreadListUiEvents.DataFetchingSuccess(result))
            }
        }
    }

    private fun postUiEvents(events: ThreadListUiEvents){
        _uiEvents.postValue(events)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        postUiEvents(ThreadListUiEvents.DataFetchingFailed(exception.handleThrowable()))
    }

}

sealed class ThreadListUiEvents {
    object Loading: ThreadListUiEvents()
    data class DataFetchingSuccess(val data: List<MessageThread>): ThreadListUiEvents()
    data class DataFetchingFailed(val message: String): ThreadListUiEvents()
    data class Error(val message: String): ThreadListUiEvents()
}