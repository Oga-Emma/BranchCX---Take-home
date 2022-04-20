package app.seven.branchcx.modules.auth.data.model.response


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("auth_token")
    val authToken: String = ""
)