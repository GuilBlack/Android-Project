package mu.guillaumebl.finalproject.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "station_photos")
data class StationPhoto (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "photo_title")
    val photoTitle: String,
    @ColumnInfo(name = "photo_path")
    val photoPath: String,
    @ColumnInfo(name = "station_id")
    val stationId: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Date
    ): Parcelable