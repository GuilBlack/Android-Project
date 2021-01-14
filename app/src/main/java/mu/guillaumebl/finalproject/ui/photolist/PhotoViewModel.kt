package mu.guillaumebl.finalproject.ui.photolist

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mu.guillaumebl.finalproject.data.StationPhoto
import mu.guillaumebl.finalproject.data.StationPhotoDatabase
import mu.guillaumebl.finalproject.data.StationPhotoRepository

class PhotoViewModel(app: Application, stationId: String): AndroidViewModel(app) {

    val readAllData: LiveData<List<StationPhoto>>
    private val repository: StationPhotoRepository

    init {
        val stationPhotoDao = StationPhotoDatabase.getDatabase(app).stationPhotoDao()
        repository = StationPhotoRepository(stationPhotoDao, stationId)
        readAllData = repository.readStationPhotoData
    }

    fun addPhoto(stationPhoto: StationPhoto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPhoto(stationPhoto)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun deletePhoto(stationPhoto: StationPhoto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePhoto(stationPhoto)
        }
    }

}