package app.seven.branchcx.modules.messaging.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Entity
data class Message(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "thread_id", index = true)
    val threadId: Int ,
    @ColumnInfo(name = "user_id")
    val userId: String = "",
    val body: String = "",
    val timestamp: ZonedDateTime,
    @ColumnInfo(name = "agent_id")
    val agentId: Int? = null
)