package mu.guillaumebl.finalproject.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mu.guillaumebl.finalproject.R
import mu.guillaumebl.finalproject.data.Station

class MainRecyclerAdapter(val context: Context,
                          val stations: List<Station>,
                          val itemListener: StationItemListener): RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>()
{

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val streetName = itemView.findViewById<TextView>(R.id.streetName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.station_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val station = stations[position]
        with(holder) {
            streetName?.let {
                it.text = station.street_name
                it.contentDescription = station.street_name
            }
        }
        holder.itemView.setOnClickListener {
            itemListener.onStationItemClick(station)
        }
    }

    override fun getItemCount() = stations.size

    interface StationItemListener {
        fun onStationItemClick(station: Station)
    }
}