package app.seven.branchcx.core.model
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiError(
    val error: String = ""
)