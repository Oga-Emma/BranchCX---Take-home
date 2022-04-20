package app.seven.branchcx.modules.messaging.data.data_source.remote

import app.seven.branchcx.modules.messaging.data.model.request.SendMessageRequest
import app.seven.branchcx.modules.messaging.data.model.response.MessageResponseItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
interface MessagingApi {
    @POST(MessagingEndpoints.messaging)
    suspend fun sendMessage(@Body request: SendMessageRequest): MessageResponseItem

    @GET(MessagingEndpoints.messaging)
    suspend fun fetchMessages(): List<MessageResponseItem>
}

object MessagingEndpoints{
    const val messaging = "api/messages"
}