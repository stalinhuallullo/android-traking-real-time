package gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.model.Route

class RouteAdapter(private val routes: List<Route>, private val selectedRoute: Route?, private val listener: RouteListener) : RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {

    private var currentSelected: String? = selectedRoute?.route

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_list_route_2, parent, false)
        return RouteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        val route = routes[position]
        holder.routeName.text = route.routeName
        holder.route.text = route.route
        holder.direction.text = route.direction
        holder.imgViewCheck.isSelected = route.route == currentSelected
        holder.updateRouteStyle(route.selected)

        holder.itemView.setOnClickListener {
            val previousPosition = currentSelected?.let { unit ->
                routes.indexOfFirst { it.route == unit }
            } ?: -1

            currentSelected = route.route
            updateSelection(previousPosition, position)
            listener.updateProduct(route, position)
        }
    }

    private fun updateSelection(previousPosition: Int, newPosition: Int) {
        if (previousPosition != -1) {
            routes[previousPosition].selected = false
            notifyItemChanged(previousPosition)
        }
        routes[newPosition].selected = true
        notifyItemChanged(newPosition)
    }

    override fun getItemCount(): Int {
        return routes.size
    }


    class RouteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //ButterKnife.bind(this, itemView)
        val routeName: TextView = itemView.findViewById(R.id.txtRouteName)
        val route: TextView = itemView.findViewById(R.id.txtRoute)
        val direction: TextView = itemView.findViewById(R.id.txtDirection)
        val imgViewCheck: ImageView = itemView.findViewById(R.id.imgViewCheck)

        fun updateRouteStyle(isSelected: Boolean) {
            if (isSelected) {
                route.setBackgroundResource(R.drawable.bg_tag_active_blue)
                route.setTextColor(itemView.context.resources.getColor(R.color.white))
                route.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.map_arrow_down_white, 0)
            } else {
                route.setBackgroundResource(R.drawable.shape_marketplace)
                route.setTextColor(itemView.context.resources.getColor(R.color.subtitle_label))
                route.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.map_arrow_down_grey, 0)
            }
        }
    }

    interface RouteListener {
        fun updateProduct(presentation: Route, position: Int)
    }

}