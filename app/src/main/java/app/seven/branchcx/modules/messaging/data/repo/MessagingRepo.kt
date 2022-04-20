package app.seven.branchcx.modules.messaging.data.repo

import app.seven.branchcx.modules.messaging.data.model.Message
import app.seven.branchcx.modules.messaging.data.model.MessageThread

interface MessagingRepo {
    suspend fun fetchMessages(threadId: Int, forceRefresh: Boolean): List<Message>
    suspend fun fetchThreads(forceRefresh: Boolean): List<MessageThread>
}