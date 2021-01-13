package mu.guillaumebl.finalproject.ui.photolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.photo_list_fragment.*
import mu.guillaumebl.finalproject.LOG_TAG
import mu.guillaumebl.finalproject.R
import mu.guillaumebl.finalproject.TakePhotoActivity
import mu.guillaumebl.finalproject.data.Station

class PhotoListFragment : Fragment() {

    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val station = this.arguments?.getParcelable<Station>("StationData")
        if (station != null) {
            Log.i(LOG_TAG, station.city)
        }

        return inflater.inflate(R.layout.photo_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraFloatingActionButton.setOnClickListener {
            val intent = Intent(requireContext(), TakePhotoActivity::class.java)
            intent.putExtra("dispatchTakePictureIntent", "dispatchTakePictureIntent")
            startActivity(intent)
        }
    }

}