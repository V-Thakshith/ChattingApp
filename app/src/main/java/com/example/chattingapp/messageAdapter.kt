package com.example.chattingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class messageAdapter(val context:Context,val messageList:ArrayList<mess>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val Item_recieve=1
    val Item_Sent=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
            val view:View= LayoutInflater.from(context).inflate(R.layout.received,parent,false)
            return RecieveViewHolder(view)
        }
        else{
            val view:View=LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage=messageList[position]
        if(holder.javaClass==SentViewHolder::class.java){

            val viewHolder=holder as SentViewHolder
            holder.sentMessage.text=currentMessage.message
        }
        else{
            val viewHolder=holder as RecieveViewHolder
            holder.rcvMessage.text=currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage=messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return Item_Sent
        }
        else{
            return Item_recieve
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
    class SentViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val sentMessage =itemView.findViewById<TextView>(R.id.txt_sent_msg)

    }
    class RecieveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val rcvMessage =itemView.findViewById<TextView>(R.id.txt_rcv_msg)
    }
}