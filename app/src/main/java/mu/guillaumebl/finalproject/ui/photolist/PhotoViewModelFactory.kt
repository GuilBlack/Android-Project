package mu.guillaumebl.finalproject.ui.photolist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException


class PhotoViewModelFactory(private val app: Application, private val stationId: String): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            return PhotoViewModel(app, stationId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class was provided")
    }
}