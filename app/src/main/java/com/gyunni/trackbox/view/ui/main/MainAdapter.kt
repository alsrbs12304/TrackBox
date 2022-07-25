package com.gyunni.trackbox.view.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gyunni.trackbox.Delivery
import com.gyunni.trackbox.R

class MainAdapter(private val context: Context):RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var deliveryList : List<Delivery> = ArrayList()

    inner class ViewHolder(view : View):RecyclerView.ViewHolder(view){
        private val txtStatus : TextView = itemView.findViewById(R.id.text_status)
        private val txtCarrierName : TextView = itemView.findViewById(R.id.text_carrierName)
        private val txtTrackId : TextView = itemView.findViewById(R.id.text_trackId)
        private val imgStatus : ImageView = itemView.findViewById(R.id.img_status)

        fun bind(item : Delivery){
            txtStatus.text = item.status
            txtCarrierName.text= item.carrierName
            txtTrackId.text = item.trackId

            when (item.status) {
                "상품인수" -> {
                    imgStatus.setImageResource(R.drawable.takeover)
                }
                "상품이동중", "배달출발" -> {
                    imgStatus.setImageResource(R.drawable.moving)
                }
                else -> {
                    imgStatus.setImageResource(R.drawable.complete)
                }
            }

            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
                }
            }
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

    fun setList(delivery: List<Delivery>){
        this.deliveryList = delivery
    }

    interface OnItemClickListener{
        fun onItemClick(v:View, data: Delivery, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }
}