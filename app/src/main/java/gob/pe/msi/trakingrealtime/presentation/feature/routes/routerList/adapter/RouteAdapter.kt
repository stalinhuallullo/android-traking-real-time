package gob.pe.msi.trakingrealtime.presentation.feature.routes.routerList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.domain.RoutePresentation

class RouteAdapter() : RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {
/*
    //private lateinit var routes: List<RoutePresentation>
    private lateinit var listener: RouteListener

    private lateinit var routePresentations: List<RoutePresentation>
    private var isFirstTime = true
    //private var currentPresentationIdSelected = -1
    private var currentPresentationIdSelected = ""*/
private lateinit var listener: RouteListener
    private var routePresentations: List<RoutePresentation> = listOf()
    private var currentPresentationIdSelected: String? = null

    constructor(routes: List<RoutePresentation>, listener: RouteListener) : this() {
        //this.routePresentations = routes
        //this.listener = listener

        this.routePresentations = routes
        this.listener = listener
        if (routes.isNotEmpty()) {
            currentPresentationIdSelected = routes[0].route
            listener.updateProduct(routes[0], 0)
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_list_route_2, parent, false)
        return RouteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        println("==================== YA INICIO ====================")
        val routePresentation = routePresentations[position]

        holder.routeName.text = routePresentation.routeName
        holder.route.text = routePresentation.route
        holder.direction.text = routePresentation.direction

        holder.imgViewCheck.isSelected = routePresentation.presentationSelected == true
        holder.updateRouteStyle(routePresentation.presentationSelected == true)

        holder.itemView.setOnClickListener {
            val previousPosition = currentPresentationIdSelected?.let { id ->
                routePresentations.indexOfFirst { it.route == id }
            } ?: -1

            currentPresentationIdSelected = routePresentation.route
            updateSelection(previousPosition, position)
            listener.updateProduct(routePresentation, position)
        }

        /*val routePresentationList: RoutePresentation = routePresentations[holder.adapterPosition]

        if (isFirstTime) {
            routePresentationList.presentationSelected = true
            isFirstTime = false

            if (currentPresentationIdSelected.isNotEmpty()) {
                routePresentationList.presentationSelected = false
                if ((routePresentationList.route === currentPresentationIdSelected)) {
                    routePresentationList.presentationSelected = true
                }
            }
            println("==================== Primer inicio ====================")
            listener.updateProduct(routePresentationList, position)
        } else {
            if (currentPresentationIdSelected.isNotEmpty() && (routePresentationList.route === currentPresentationIdSelected)) {
                routePresentationList.presentationSelected = true

                listener.updateProduct(routePresentationList, position)
            } else {
                routePresentationList.presentationSelected = false
            }
            println("==================== YA INICIO ====================")
        }

        holder.itemView.setOnClickListener{
            currentPresentationIdSelected = routePresentationList.route!!
            for (pp in routePresentations) {
                pp.presentationSelected = false
            }

            routePresentationList.presentationSelected = true

            if (listener != null) {
                listener.updateProduct(routePresentationList, position)
            }
            println("==================== CLICK ====================")
//            notifyDataSetChanged()
            notifyItemChanged(currentPresentationIdSelected)
        }

        holder.routeName.text = routePresentationList.routeName
        holder.route.text = routePresentationList.route
        holder.direction.text = routePresentationList.direction

        holder.imgViewCheck.isSelected = routePresentationList.presentationSelected!!
*/
        /*val pharmacy = routes[position]
        holder.routeName.text = pharmacy.routeName
        holder.route.text = pharmacy.route
        holder.direction.text = pharmacy.direction
        //holder.imgViewCheck.isSelected = false

        holder.itemView.isActivated = selectedItemPosition == position
        holder.itemView.setOnClickListener {
            selectedItemPosition = holder.adapterPosition
            //listener.onPharmacyClick(pharmacy)
            holder.imgViewCheck.isSelected = true
            notifyItemChanged(selectedItemPosition)
        }*/


    }

    private fun updateSelection(previousPosition: Int, newPosition: Int) {
        if (previousPosition != -1) {
            routePresentations[previousPosition].presentationSelected = false
            notifyItemChanged(previousPosition)
        }
        routePresentations[newPosition].presentationSelected = true
        notifyItemChanged(newPosition)
    }

    override fun getItemCount(): Int {
        return routePresentations.size
    }


    class RouteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //ButterKnife.bind(this, itemView)
        val routeName: TextView = itemView.findViewById(R.id.txtRouteName)
        val route: TextView = itemView.findViewById(R.id.txtRoute)
        val direction: TextView = itemView.findViewById(R.id.txtDirection)
        val imgViewCheck: ImageView = itemView.findViewById(R.id.imgViewCheck)

        fun updateRouteStyle(isSelected: Boolean) {
            if (isSelected) {
                route.setBackgroundResource(R.drawable.bg_tag_active_blue)  // Cambiar a un drawable seleccionado
                route.setTextColor(itemView.context.resources.getColor(R.color.white))  // Cambiar color de texto seleccionado
                route.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.map_arrow_down_white, 0)  // Cambiar color de texto seleccionado
            } else {
                route.setBackgroundResource(R.drawable.shape_marketplace)  // Cambiar a un drawable no seleccionado
                route.setTextColor(itemView.context.resources.getColor(R.color.subtitle_label))  // Cambiar color de texto no seleccionado
                route.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.map_arrow_down_grey, 0)  // Cambiar color de texto seleccionado
            }
        }
    }

    interface RouteListener {
        fun updateProduct(presentation: RoutePresentation, position: Int)
    }

}