package mu.guillaumebl.finalproject.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import mu.guillaumebl.finalproject.LOG_TAG
import mu.guillaumebl.finalproject.R

class MainListFragment : Fragment() {

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
        swipeLayout = view.findViewById(R.id.swipeLayoutMainList)
        swipeLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.nearStationData.observe(viewLifecycleOwner, {
            val adapter = MainRecyclerAdapter(requireContext(), it.data.nearstations)
            recyclerView.adapter = adapter
            swipeLayout.isRefreshing = false
        })

        return view
    }
}