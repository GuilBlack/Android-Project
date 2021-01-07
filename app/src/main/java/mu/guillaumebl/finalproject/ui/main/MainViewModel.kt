package mu.guillaumebl.finalproject.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import mu.guillaumebl.finalproject.data.NearStationRepository

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val dataRepo = NearStationRepository(app)
    val nearStationData = dataRepo.nearStationData

    fun refreshData() {
        dataRepo.refreshData()
    }
}