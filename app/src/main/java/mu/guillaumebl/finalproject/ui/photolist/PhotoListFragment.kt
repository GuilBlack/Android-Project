package mu.guillaumebl.finalproject.ui.photolist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mu.guillaumebl.finalproject.LOG_TAG
import mu.guillaumebl.finalproject.R
import mu.guillaumebl.finalproject.data.Station

class PhotoListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val station = this.arguments?.getParcelable<Station>("StationData")

        station?.let { Log.i(LOG_TAG, it.city) }

        return inflater.inflate(R.layout.photo_list_fragment, container, false)
    }
}