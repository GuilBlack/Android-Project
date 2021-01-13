package mu.guillaumebl.finalproject.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.google_map_main_fragment.*
import mu.guillaumebl.finalproject.LOG_TAG
import mu.guillaumebl.finalproject.PhotoListActivity
import mu.guillaumebl.finalproject.R
import mu.guillaumebl.finalproject.data.Station

class MainGoogleMapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var mapFrag: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.google_map_main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.googleMaps) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        buttonToGridView.setOnClickListener {
            findNavController().navigate(R.id.action_mainGoogleMapsFragment_to_mainFragment)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mapFrag = googleMap
        viewModel.nearStationData.observe(this, {
            for (station: Station in it.data.nearstations) {
                val coord = LatLng(station.lat.toDouble(), station.lon.toDouble())
                val tempMarker = mapFrag.addMarker(MarkerOptions().position(coord).title(station.street_name))
                tempMarker.tag = station
            }
            mapFrag.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(41.40092, 2.19046), 13.7f))
        })
        mapFrag.setOnInfoWindowClickListener(this)
    }

    override fun onInfoWindowClick(marker: Marker) {
        val station: Station = marker.tag as Station
        Log.i(LOG_TAG, "selected station: ${station.street_name}")
        val intent = Intent(requireContext(), PhotoListActivity::class.java)
        intent.putExtra("StationData", station)
        startActivity(intent)
    }


}