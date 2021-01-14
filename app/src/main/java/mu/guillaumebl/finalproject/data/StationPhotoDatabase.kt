package mu.guillaumebl.finalproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mu.guillaumebl.finalproject.helpers.Converters

@Database(entities = [StationPhoto::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class StationPhotoDatabase: RoomDatabase() {

    abstract fun stationPhotoDao(): StationPhotoDao

    companion object {
        @Volatile
        private var INSTANCE: StationPhotoDatabase? = null


        fun getDatabase(context: Context): StationPhotoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    StationPhotoDatabase::class.java,
                    "station_photos"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}