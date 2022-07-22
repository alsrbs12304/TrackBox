package com.gyunni.trackbox.view.ui.lookup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gyunni.trackbox.Delivery
import com.gyunni.trackbox.DeliveryResponse
import com.gyunni.trackbox.R
import com.gyunni.trackbox.view.ui.main.MainAdapter

class LookUpAdapter(private val context: Context): RecyclerView.Adapter<LookUpAdapter.ViewHolder>() {

    private var progressList : List<DeliveryResponse.Progresses> = ArrayList()

    inner class ViewHolder(view : View):RecyclerView.ViewHolder(view){
        private val progressStatus : TextView = itemView.findViewById(R.id.textView_timeline_progress_status)
        private val locationName : TextView = itemView.findViewById(R.id.text_location_name)
        private val description : TextView = itemView.findViewById(R.id.text_description)

        fun bind(item : DeliveryResponse.Progresses){
            progressStatus.text = item.status.text
            locationName.text = item.location.name
            description.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_timeline,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(progressList[position])
    }

    override fun getItemCount(): Int {
        return progressList.size
    }

    fun setList(progresses: List<DeliveryResponse.Progresses>){
        this.progressList = progresses
    }
}