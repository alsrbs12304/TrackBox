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
import java.util.*

class MainAdapter : ListAdapter<Delivery, MainAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)

        holder.itemView.findViewById<LinearLayout>(R.id.swipe_view).setOnClickListener {
            listener?.onItemClick(it,current,position)
        }

        holder.itemView.findViewById<TextView>(R.id.tvRemove).setOnClickListener{
            RemoveListener?.onRemoveClick(it,current,position)
        }
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        private val txtNickName : TextView = itemView.findViewById(R.id.text_nickName)
        private val txtStatus : TextView = itemView.findViewById(R.id.text_status)
        private val txtCarrierName : TextView = itemView.findViewById(R.id.text_carrierName)
        private val txtTrackId : TextView = itemView.findViewById(R.id.text_trackId)
        private val imgStatus : ImageView = itemView.findViewById(R.id.img_status)

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

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<Delivery>(){
            override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun getCurrentList(): MutableList<Delivery> {
        return super.getCurrentList()
    }

    interface OnItemClickListener{
        fun onItemClick(v:View, data: Delivery, pos : Int)
    }
    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    interface OnRemoveClickListener{
        fun onRemoveClick(v:View, data: Delivery, pos : Int)
    }

    private var RemoveListener : OnRemoveClickListener? = null

    fun setOnRemoveClickListener(listener : OnRemoveClickListener){
        this.RemoveListener = listener
    }

    // 현재 선택된 데이터와 드래그한 위치에 있는 데이터를 교환
    fun swapData(fromPos: Int, toPos: Int) {
        val newList = currentList.toMutableList()
        Collections.swap(newList, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
    }

    // position 위치의 데이터를 삭제 후 어댑터 갱신
    fun removeData(position: Int) {
        val newList = currentList.toMutableList()
        newList.removeAt(position)
        notifyItemRemoved(position)
    }
}