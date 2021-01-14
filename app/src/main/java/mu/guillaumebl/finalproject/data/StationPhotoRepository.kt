package mu.guillaumebl.finalproject.data

import androidx.lifecycle.LiveData

class StationPhotoRepository(private val stationPhotoDao: StationPhotoDao, stationId: String) {

    val readStationPhotoData: LiveData<List<StationPhoto>>

    init {
        readStationPhotoData = getStationPhoto(stationId)
    }

    suspend fun addPhoto(stationPhoto: StationPhoto) {
        stationPhotoDao.insertStationPhoto(stationPhoto)
    }

    suspend fun deletePhoto(stationPhoto: StationPhoto) {
        stationPhotoDao.deleteStationPhotos(stationPhoto)
    }

    suspend fun deleteAll() {
        stationPhotoDao.deleteAll()
    }

    fun getStationPhoto(stationId: String): LiveData<List<StationPhoto>> {
        return stationPhotoDao.getPhotosForStation(stationId)
    }

}