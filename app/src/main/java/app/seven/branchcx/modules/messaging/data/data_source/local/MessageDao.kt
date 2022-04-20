package app.seven.branchcx.modules.messaging.data.data_source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.seven.branchcx.modules.messaging.data.model.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun getAll(): LiveData<List<Message>>

    @Insert
    fun insertAll(vararg messages: Message)
}