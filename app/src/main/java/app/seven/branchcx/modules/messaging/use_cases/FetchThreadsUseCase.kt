package app.seven.branchcx.modules.messaging.use_cases

import androidx.lifecycle.LiveData
import app.seven.branchcx.core.model.NoParamUseCase
import app.seven.branchcx.core.model.SuspendUseCase
import app.seven.branchcx.core.model.UseCase
import app.seven.branchcx.modules.auth.data.model.request.LoginRequest
import app.seven.branchcx.modules.auth.data.model.response.AuthResponse
import app.seven.branchcx.modules.auth.data.repo.LoginRepo
import app.seven.branchcx.modules.messaging.data.model.MessageThread
import app.seven.branchcx.modules.messaging.data.repo.MessagingRepo
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FetchThreadsUseCase @Inject constructor(private val repo: MessagingRepo): SuspendUseCase<Boolean, List<MessageThread>>() {
    override suspend fun call(request: Boolean): List<MessageThread> {
        return repo.fetchThreads(request)
    }
}