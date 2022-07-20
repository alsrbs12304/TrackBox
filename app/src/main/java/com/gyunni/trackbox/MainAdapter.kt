package com.gyunni.trackbox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(private val context: Context):RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var deliveryList = mutableListOf<Delivery>()

    inner class ViewHolder(view : View):RecyclerView.ViewHolder(view){
        private val txtStatus : TextView = itemView.findViewById(R.id.text_status)
        private val txtCarrierName : TextView = itemView.findViewById(R.id.text_carrierName)
        private val txtTrackId : TextView = itemView.findViewById(R.id.text_trackId)

        fun bind(item : Delivery){
            txtStatus.text = item.status
            txtCarrierName.text= item.carrierName
            txtTrackId.text = item.trackId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_main,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(deliveryList[position])
    }

    override fun getItemCount(): Int {
        return deliveryList.size
    }
}