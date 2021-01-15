package mu.guillaumebl.finalproject.ui.photolist

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photo_grid_item.view.*
import mu.guillaumebl.finalproject.R
import mu.guillaumebl.finalproject.data.StationPhoto
import java.text.SimpleDateFormat

class PhotoListRecyclerAdapter(val itemListener: StationPhotoItemListener): RecyclerView.Adapter<PhotoListRecyclerAdapter.PhotoViewHolder>() {

    private var stationPhotos = emptyList<StationPhoto>()

    inner class PhotoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleText = itemView.findViewById<TextView>(R.id.titleText)
        val dateText = itemView.findViewById<TextView>(R.id.dateText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.photo_grid_item, parent, false)
        return PhotoViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val stationPhoto = stationPhotos[position]
        with(holder) {
            Picasso.get().load("file://${stationPhoto.photoPath}").resize(80, 80).centerCrop().into(itemView.stationPreview)
            itemView.stationPreview.tag = stationPhoto
            titleText.text = stationPhoto.photoTitle
            titleText.contentDescription = stationPhoto.photoTitle
            dateText.text = SimpleDateFormat("dd-MMMM-yyyy").format(stationPhoto.createdAt)
            holder.itemView.setOnClickListener {
                itemListener.onStationPhotoClick(stationPhoto)
            }
        }
    }

    override fun getItemCount(): Int {
        return stationPhotos.size
    }

    fun setData(stationPhotos: List<StationPhoto>) {
        this.stationPhotos = stationPhotos
        notifyDataSetChanged()
    }

    interface StationPhotoItemListener {
        fun onStationPhotoClick(stationPhoto: StationPhoto)
    }

}