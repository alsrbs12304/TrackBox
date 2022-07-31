package com.gyunni.trackbox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gyunni.trackbox.data.model.Delivery
import java.util.*
import kotlin.collections.ArrayList

class SubMainAdapter(private val context: Context):RecyclerView.Adapter<SubMainAdapter.ViewHolder>() {

    private var deliveryList : MutableList<Delivery> = ArrayList()

    inner class ViewHolder(view : View):RecyclerView.ViewHolder(view){
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

            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                swipeView.setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
                }
            }

            tvRemove.setOnClickListener {
                RemoveListener?.onRemoveClick(itemView,item,pos)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_main,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(deliveryList[position]) }

    override fun getItemCount(): Int {
        return deliveryList.size
    }

    fun setList(delivery: MutableList<Delivery>){
        this.deliveryList = delivery
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
        Collections.swap(deliveryList, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
    }

    // position 위치의 데이터를 삭제 후 어댑터 갱신
    fun removeData(position: Int) {
        deliveryList.removeAt(position)
        notifyItemRemoved(position)
    }
}