package mu.guillaumebl.finalproject.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface StationPhotoDao {
    @Query("SELECT * FROM station_photos WHERE station_id = :stationId ORDER BY created_at DESC")
    fun getPhotosForStation(stationId: String): LiveData<List<StationPhoto>>

    @Query("SELECT * FROM station_photos")
    fun getAllData(): LiveData<List<StationPhoto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStationPhoto(stationPhoto: StationPhoto)

    @Delete
    suspend fun deleteStationPhotos(stationPhoto: StationPhoto)

    @Query("DELETE FROM station_photos")
    suspend fun deleteAll()
}