package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {
    private lateinit var messageRecyclerView:RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendButton:ImageView
    private lateinit var messageAdapter: messageAdapter
    private lateinit var messageList: ArrayList<mess>
    private lateinit var mDbRef:DatabaseReference
    var receiverRoom:String?=null
    var senderRoom:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val name=intent.getStringExtra("name")
        val Ruid=intent.getStringExtra("uid")
        val Suid=FirebaseAuth.getInstance().currentUser?.uid
        mDbRef=FirebaseDatabase.getInstance().getReference()
        senderRoom=Ruid+Suid
        receiverRoom=Suid+Ruid
        supportActionBar?.title=name
        messageRecyclerView=findViewById(R.id.charRecyclerView)

        messageRecyclerView=findViewById(R.id.charRecyclerView)
        messageBox=findViewById(R.id.messageBox)
        sendButton=findViewById(R.id.Sent_button)
        messageList= ArrayList()
        messageAdapter= messageAdapter(this,messageList)
        messageRecyclerView.layoutManager=LinearLayoutManager(this)
        messageRecyclerView.adapter=messageAdapter
        mDbRef.child("chat").child(senderRoom!!).child("messages").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()
                for(postSnapshot in snapshot.children){
                    val message=postSnapshot.getValue(mess::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        sendButton.setOnClickListener{
            val message=messageBox.text.toString()
            val messageObject=mess(message,Suid)
            mDbRef.child("chat").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                mDbRef.child("chat").child(receiverRoom!!).child("messages").push().setValue(messageObject)

            }
            messageBox.setText("")
        }
    }
}