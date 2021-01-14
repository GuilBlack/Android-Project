package mu.guillaumebl.finalproject.ui.photolist

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.photo_grid_item.view.*
import kotlinx.android.synthetic.main.photo_list_fragment.*
import kotlinx.android.synthetic.main.photo_list_fragment.view.*
import mu.guillaumebl.finalproject.LOG_TAG
import mu.guillaumebl.finalproject.R
import mu.guillaumebl.finalproject.TakePhotoActivity
import mu.guillaumebl.finalproject.data.Station
import mu.guillaumebl.finalproject.data.StationPhoto
import java.io.File


class PhotoListFragment : Fragment(),
    PhotoListRecyclerAdapter.StationPhotoItemListener {

    private var station: Station? = null
    private lateinit var stationPhotoViewModel: PhotoViewModel
    private lateinit var stationPhotoViewModelFactory: PhotoViewModelFactory
    private lateinit var recyclerView: RecyclerView
    val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            deleteItem(viewHolder)
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addBackgroundColor(Color.parseColor("#F1202B"))
                .addActionIcon(R.drawable.ic_baseline_delete_24)
                .addSwipeLeftLabel("Delete")
                .create()
                .decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.photo_list_fragment, container, false)

        station = this.arguments?.getParcelable("StationData")
        station?.let { station ->
            Log.i(LOG_TAG, station.city)
            val adapter = PhotoListRecyclerAdapter(this)
            recyclerView = view.photoListRecyclerView
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            itemTouchHelper.attachToRecyclerView(recyclerView)

            stationPhotoViewModelFactory = PhotoViewModelFactory(
                requireActivity().application,
                station.id
            )
            stationPhotoViewModel = ViewModelProvider(this, stationPhotoViewModelFactory)
                .get(PhotoViewModel::class.java)
            stationPhotoViewModel.readAllData.observe(viewLifecycleOwner, { stationPhotos ->
                adapter.setData(stationPhotos)
            })

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraFloatingActionButton.setOnClickListener {
            val intent = Intent(requireContext(), TakePhotoActivity::class.java)
            intent.putExtra("dispatchTakePictureIntent", "dispatchTakePictureIntent")
            intent.putExtra("stationData", station)
            startActivity(intent)
        }
    }

    fun deleteItem(viewHolder: RecyclerView.ViewHolder) {
        val stationPhoto: StationPhoto = viewHolder.itemView.stationPreview.tag as StationPhoto
        Log.i(LOG_TAG, "I swiped: ${stationPhoto.photoTitle}")
        val file = File(stationPhoto.photoPath)
        file.delete()

        stationPhotoViewModel.deletePhoto(stationPhoto)
        recyclerView.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
    }

    override fun onStationPhotoClick(stationPhoto: StationPhoto) {
        Log.i(LOG_TAG, stationPhoto.toString())
    }

}