package mu.guillaumebl.finalproject.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mu.guillaumebl.finalproject.LOG_TAG
import mu.guillaumebl.finalproject.WEB_SERVICE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NearStationDictionary(val app: Application) {

    val nearStationData = MutableLiveData<JsonData>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
        Log.i(LOG_TAG, "network available: ${networkAvailable()}")
    }

    //will create a new background thread to retrieve the data from the json in the url
    //then put the data in the MutableLiveData so that other things can sub to it
    @WorkerThread
    suspend fun callWebService() {
        if (networkAvailable()) {
            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            val service = retrofit.create(NearStationsService::class.java)
            val serviceData = service.getNearStationData().body()
            nearStationData.postValue(serviceData)
        }
    }

    //just to see if we are connected to a network (any network)
    private fun networkAvailable(): Boolean {
        var result = false
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        return result
    }
}