package com.gyunni.trackbox.view.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gyunni.trackbox.R
import com.gyunni.trackbox.data.model.Delivery

class TestMainAdapter : ListAdapter<Delivery, TestMainAdapter.ViewHolder>(ContactComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val txtNickName : TextView = itemView.findViewById(R.id.text_nickName)
        private val txtStatus : TextView = itemView.findViewById(R.id.text_status)
        private val txtCarrierName : TextView = itemView.findViewById(R.id.text_carrierName)
        private val txtTrackId : TextView = itemView.findViewById(R.id.text_trackId)
        private val imgStatus : ImageView = itemView.findViewById(R.id.img_status)
        private val tvRemove : TextView = itemView.findViewById(R.id.tvRemove)
        private val swipeView : LinearLayout = itemView.findViewById(R.id.swipe_view)

        fun bind(item : Delivery){
            txtNickName.text = item.nickName
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
        }
        companion object{
            fun create(parent: ViewGroup):ViewHolder{
                val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_main,parent, false)
                return ViewHolder(view)
            }
        }
    }

    class ContactComparator:DiffUtil.ItemCallback<Delivery>(){
        override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun getCurrentList(): MutableList<Delivery> {
        return super.getCurrentList()
    }
}