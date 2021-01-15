package mu.guillaumebl.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import mu.guillaumebl.finalproject.data.Station
import mu.guillaumebl.finalproject.data.StationPhoto
import java.io.File

class DetailsActivity : AppCompatActivity() {

    private lateinit var station: Station

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        station = intent.getParcelableExtra("StationData")!!
        val stationPhoto = intent.getParcelableExtra<StationPhoto>("StationPhoto")!!

        Picasso.get().load(File(stationPhoto.photoPath)).into(findViewById<ImageView>(R.id.fullImage))
        imageTitle.text = stationPhoto.photoTitle

        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.full_image)

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, PhotoListActivity::class.java)
            intent.putExtra("StationData", station)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}