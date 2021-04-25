package com.decodex.sosadmin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class request_recycler_adapter(private var request_list : List<recycler_row>, private var listner:itemclick) : RecyclerView.Adapter<request_recycler_adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_request_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var current_item = request_list[position]

        holder.name.setText(current_item.name)
        holder.location.setText(current_item.city)
        holder.time.setText(current_item.time)
        holder.phoneNo.setText(current_item.phone)

    }

    override fun getItemCount() = request_list.size

    inner class ViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview), View.OnClickListener{

        val name : TextView = itemview.findViewById(R.id.user_name)
        val phoneNo : TextView = itemview.findViewById(R.id.user_phone)
        val time : TextView = itemview.findViewById(R.id.time_recyclerrow)
        val location : TextView = itemview.findViewById(R.id.user_age)

        init {
            itemview.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listner.onitemclick(position)
            }
        }
    }

    interface itemclick{
        fun onitemclick(position: Int)
    }
}