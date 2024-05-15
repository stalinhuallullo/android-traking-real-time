package gob.pe.msi.trakingrealtime.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.presentation.model.LocationModel

class LocationRealTimeAdapter (private var locations: MutableList<LocationModel>) : RecyclerView.Adapter<LocationRealTimeAdapter.LocationRealTimeViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationRealTimeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_location_layout, parent, false)
        return LocationRealTimeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationRealTimeViewHolder, position: Int) {
        val location = locations[position]
        holder.textViewCount.text = location.count.toString()
        holder.textViewLatitude.text = location.latitude
        holder.textViewLongitude.text = location.longitude
        holder.textViewHours.text = location.hours
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    class LocationRealTimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewCount: TextView = itemView.findViewById(R.id.textViewCount)
        val textViewLatitude: TextView = itemView.findViewById(R.id.textViewLatitude)
        val textViewLongitude: TextView = itemView.findViewById(R.id.textViewLongitude)
        val textViewHours: TextView = itemView.findViewById(R.id.textViewHours)
    }

    fun addLocation(location: LocationModel) {
        locations.add(location)
        notifyItemInserted(locations.size - 1)
    }

    fun setData(newPeople: MutableList<LocationModel>) {
        locations = newPeople
    }

}