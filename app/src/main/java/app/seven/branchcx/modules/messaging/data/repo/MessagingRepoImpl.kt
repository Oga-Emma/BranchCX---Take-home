package app.seven.branchcx.modules.messaging.data.repo

import app.seven.branchcx.modules.messaging.data.data_source.local.MessageDao
import app.seven.branchcx.modules.messaging.data.data_source.remote.MessagingApi
import app.seven.branchcx.modules.messaging.data.model.Message
import app.seven.branchcx.modules.messaging.data.model.MessageThread
import app.seven.branchcx.modules.messaging.data.model.response.MessageResponseItem
import app.seven.branchcx.modules.messaging.data.model.response.toMessage
import javax.inject.Inject


class MessagingRepoImpl @Inject constructor(
    private val api: MessagingApi,
    private val db: MessageDao): MessagingRepo {

    var cache: List<MessageResponseItem>? = null

    override suspend fun fetchMessages(threadId: Int, forceRefresh: Boolean): List<Message> {
        return _fetchMessages(forceRefresh).filter {
            it.threadId == threadId
        }.map {
            it.toMessage()
        }
    }

    override suspend fun fetchThreads(forceRefresh: Boolean): List<MessageThread> {

        val set = mutableSetOf<MessageThread>()

        val message = _fetchMessages(forceRefresh)
        message.forEach {
            set.add(MessageThread(
                id = it.threadId,
                userId = it.userId)
            )
        }

        set.forEach {
            it.lastMessage = _getLastMessage(it, message)
        }

        return set.toList()
    }

    private fun _getLastMessage(it: MessageThread, message: List<MessageResponseItem>): Message? {
        var lastMessage: Message? = null
        message.forEach {
            if(lastMessage == null || it.timestamp.isAfter(lastMessage!!.timestamp)){
                lastMessage = it.toMessage()
            }
        }

        return lastMessage
    }

    private suspend fun _fetchMessages(forceRefresh: Boolean): List<MessageResponseItem>{
        if(cache == null || forceRefresh){
            cache = api.fetchMessages()
        }
        return cache!!
    }
}