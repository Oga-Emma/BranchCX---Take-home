package app.seven.branchcx.core.service.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import app.seven.branchcx.modules.messaging.data.data_source.local.MessageDao
import app.seven.branchcx.modules.messaging.data.model.Message
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Database(
    entities = [Message::class],
    version = 1)
@TypeConverters(Converters::class)
abstract  class AppDatabaseRoomImpl: RoomDatabase() {
    abstract fun messageDao(): MessageDao
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): ZonedDateTime? {
        return value?.let { ZonedDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: ZonedDateTime?): String? {
        return date?.toString()
    }
}