package mu.guillaumebl.finalproject.ui.photolist

import android.R.id
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_take_photo.*
import mu.guillaumebl.finalproject.LOG_TAG
import mu.guillaumebl.finalproject.R
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class TakePhotoActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 1
    lateinit var currentPhotoPath: String
    private var isSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_photo)

        if (intent.getStringExtra("dispatchTakePictureIntent") == "dispatchTakePictureIntent") {
            dispatchTakePictureIntent()
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        saveBtn.setOnClickListener {
            errorText.text = ""
            val title = pictureName.text.toString()
            if (title == "") {
                errorText.setText(R.string.no_title)
            } else if (!title.contains("[a-zA-Z0-9]".toRegex())) {
                errorText.setText(R.string.no_alphenumeric)
            } else if (title.contains("[!|@#$%^&*()_+{}\\[\\]:;\"<>,./?]".toRegex())) {
                errorText.setText(R.string.no_special_chars_allowed)
            } else if (title.contains("\\")) {
                errorText.setText(R.string.no_special_chars_allowed)
            } else if (title.length > 20) {
                errorText.setText(R.string.title_max_length)
            } else {
                Log.i(LOG_TAG, title)
                isSaved = true
                finish()
            }
        }
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
        }
    }

}