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
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.main_fragment.*
import mu.guillaumebl.finalproject.LOG_TAG
import mu.guillaumebl.finalproject.PhotoListActivity
import mu.guillaumebl.finalproject.R
import mu.guillaumebl.finalproject.data.Station

class MainListFragment : Fragment(), MainRecyclerAdapter.StationItemListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerView = view.findViewById(R.id.listStationRecyclerView)

        //this code is to swipe up and refresh the data
        swipeLayout = view.findViewById(R.id.swipeLayoutMainList)
        swipeLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.nearStationData.observe(viewLifecycleOwner, {
            val adapter = MainRecyclerAdapter(requireContext(), it.data.nearstations, this)
            recyclerView.adapter = adapter
            swipeLayout.isRefreshing = false
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonToGoogleMaps.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_mainGoogleMapsFragment)
        }
    }

    override fun onStationItemClick(station: Station) {
        Log.i(LOG_TAG, "selected station: ${station.street_name}")
        val intent = Intent(requireContext(), PhotoListActivity::class.java)
        intent.putExtra("StationData", station)
        startActivity(intent)
    }
}