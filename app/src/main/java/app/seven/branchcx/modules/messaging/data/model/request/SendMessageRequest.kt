package app.seven.branchcx.modules.messaging.data.model.request

import com.google.gson.annotations.SerializedName

data class SendMessageRequest(
    @SerializedName("thread_id")
    val threadId: Int,
    val body: String
)