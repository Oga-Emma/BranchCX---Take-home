package app.seven.branchcx.modules.messaging.data.model.response


import app.seven.branchcx.modules.messaging.data.model.Message
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.ZonedDateTime

data class MessageResponseItem(
    val id: Int,
    @SerializedName("thread_id")
    val threadId: Int,
    @SerializedName("user_id")
    val userId: String = "",
    val body: String = "",
    val timestamp: ZonedDateTime,
    @SerializedName("agent_id")
    val agentId: Int? = null
)

fun MessageResponseItem.toMessage(): Message {
    return Message(
        id,
        threadId,
        userId,
        body,
        timestamp,
        agentId,
    )
}