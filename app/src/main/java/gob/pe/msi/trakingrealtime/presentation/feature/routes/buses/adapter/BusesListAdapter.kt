package gob.pe.msi.trakingrealtime.presentation.feature.routes.buses.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.presentation.feature.routes.buses.model.Bus

class BusesListAdapter(private val buses: List<Bus>, selectedBus: Bus?, private val listener: BusesListener): RecyclerView.Adapter<BusesListAdapter.BusesListViewHolder>() {

    private var currentSelected: String? = selectedBus?.plate

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusesListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_list_buses, parent, false)
        return BusesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusesListViewHolder, position: Int) {
        val bus = buses[position]
        holder.plate.text = bus.plate
        holder.brand.text = bus.brand
        holder.imgViewCheck.isSelected = bus.plate == currentSelected

        holder.itemView.setOnClickListener {
            val previousPosition = currentSelected?.let { plate ->
                buses.indexOfFirst { it.plate == plate }
            } ?: -1


            currentSelected = bus.plate
            updateSelection(previousPosition, position)
            listener.updateProduct(bus, position)
        }
    }

    private fun updateSelection(previousPosition: Int, newPosition: Int) {
        if (previousPosition != -1) {
            buses[previousPosition].selected = false
            notifyItemChanged(previousPosition)
        }
        buses[newPosition].selected = true
        notifyItemChanged(newPosition)
    }

    override fun getItemCount(): Int {
        return buses.size
    }


    class BusesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plate: TextView = itemView.findViewById(R.id.txtPlate)
        val brand: TextView = itemView.findViewById(R.id.txtBrand)
        val imgViewCheck: ImageView = itemView.findViewById(R.id.imgViewCheck)
    }

    interface BusesListener {
        fun updateProduct(bus: Bus, position: Int)
    }

}