package mu.guillaumebl.finalproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import mu.guillaumebl.finalproject.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

//    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        viewModel.nearStationData.observe(this, {
//            Log.i(LOG_TAG, it.data.nearstations.distinct().toString())
//            for (station in it.data.nearstations) {
//                Log.i(LOG_TAG, station.street_name)
//            }
//            Log.i(LOG_TAG, it.data.nearstations.distinct().count().toString())
//        })
        setContentView(R.layout.activity_main)
    }
}