package mu.guillaumebl.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_take_photo.*
import mu.guillaumebl.finalproject.data.Station
import mu.guillaumebl.finalproject.ui.photolist.PhotoListFragment


class PhotoListActivity : AppCompatActivity() {

    private var station: Station? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        station = intent.getParcelableExtra("StationData")

        val bundle = Bundle()
        bundle.putParcelable("StationData", station)
        val photoListFragObj = PhotoListFragment()
        photoListFragObj.arguments = bundle
        replaceFragment(photoListFragObj)

        Log.i(LOG_TAG, station.toString())

        setContentView(R.layout.activity_photo_list)
        initToolbar()
    }

    private fun replaceFragment(frag: Fragment) {
        val fragTransaction = supportFragmentManager.beginTransaction()
        fragTransaction.replace(R.id.photoListContainer, frag)
        fragTransaction.addToBackStack(null)
        fragTransaction.commit()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = station!!.street_name

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}