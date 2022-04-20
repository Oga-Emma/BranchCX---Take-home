package app.seven.branchcx.modules.messaging.data.model

data class MessageThread(
    val id: Int,
    val userId: String,
    var lastMessageId: Int? = null,
    var lastMessage: Message? = null
){
    override fun equals(other: Any?): Boolean {
        return (other is MessageThread) && id == other.id && userId == other.userId
                && lastMessageId == other.lastMessageId
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userId.hashCode()
        result = 31 * result + (lastMessageId ?: 0)
        result = 31 * result + (lastMessage?.hashCode() ?: 0)
        return result
    }

}
