package mu.guillaumebl.finalproject

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_take_photo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mu.guillaumebl.finalproject.data.Station
import mu.guillaumebl.finalproject.data.StationPhoto
import mu.guillaumebl.finalproject.ui.photolist.PhotoViewModel
import mu.guillaumebl.finalproject.ui.photolist.PhotoViewModelFactory
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class TakePhotoActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 1
    lateinit var currentPhotoPath: String
    private var isSaved = false
    private lateinit var station: Station
    private lateinit var stationPhotoViewModel: PhotoViewModel
    private lateinit var stationPhotoViewModelFactory: PhotoViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_photo)

        if (intent.getStringExtra("dispatchTakePictureIntent") == "dispatchTakePictureIntent") {
            dispatchTakePictureIntent()
        }
        station = intent.getParcelableExtra("stationData")!!
        stationPhotoViewModelFactory = PhotoViewModelFactory(application, station.id)
        stationPhotoViewModel = ViewModelProvider(this, stationPhotoViewModelFactory)
            .get(PhotoViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        saveBtn.setOnClickListener {
            saveDataToDatabase()
        }
    }

    private fun saveDataToDatabase() {
        val title = pictureName.text.toString()
        if (checkInput(title)) {
            //create a station photo and add it to database
            Log.i(LOG_TAG, title)
            val stationPhoto = StationPhoto(0, title, currentPhotoPath, station.id, Date(System.currentTimeMillis()))
            stationPhotoViewModel.addPhoto(stationPhoto)
            isSaved = true
            finish()
        }
    }

    private fun checkInput(title: String): Boolean {
        errorText.text = ""
        if (title == "") {
            errorText.setText(R.string.no_title)
        } else if (!title.contains("[a-zA-Z0-9]".toRegex())) {
            errorText.setText(R.string.no_alphenumeric)
        } else if (title.contains("[|@#$%^&*()_+{}\\[\\]\"<>/]".toRegex())) {
            errorText.setText(R.string.no_special_chars_allowed)
        } else if (title.contains("\\")) {
            errorText.setText(R.string.no_special_chars_allowed)
        } else if (title.length > 30) {
            errorText.setText(R.string.title_max_length)
        } else {
            return true
        }
        return false
    }

    private fun dispatchTakePictureIntent() {
//        var photoUri: Uri

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
//            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    ex.printStackTrace()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoUri = FileProvider.getUriForFile(
                        this,
                        "mu.guillaumebl.finalproject.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
//            }
        }
    }

    private fun createImageFile(): File? {
// Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        Picasso.get().load("file://$currentPhotoPath").into(imageThumbnail)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isSaved) {
            val file = File(currentPhotoPath)
            val deleted: Boolean = file.delete()
            Log.i(LOG_TAG, "deleted = $deleted")
        }
    }

}