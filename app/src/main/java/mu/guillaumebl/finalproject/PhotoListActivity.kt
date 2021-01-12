package mu.guillaumebl.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import mu.guillaumebl.finalproject.data.Station
import mu.guillaumebl.finalproject.ui.photolist.PhotoListFragment

class PhotoListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val station = intent.getParcelableExtra<Station>("StationData")
        val bundle = Bundle()
        bundle.putParcelable("StationData", station)
        val photoListFragObj = PhotoListFragment()
        photoListFragObj.arguments = bundle
        replaceFragment(photoListFragObj)

        Log.i(LOG_TAG, station.toString())

        setContentView(R.layout.activity_photo_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun replaceFragment(frag: Fragment) {
        val fragTransaction = supportFragmentManager.beginTransaction()
        fragTransaction.replace(R.id.photoListContainer, frag)
        fragTransaction.addToBackStack(null)
        fragTransaction.commit()
    }
}