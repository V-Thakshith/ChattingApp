package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnlogin:Button
    private lateinit var btnsignup:Button
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        mAuth= FirebaseAuth.getInstance()
        edtEmail=findViewById(R.id.Email)
        edtPassword=findViewById(R.id.Password)
        btnlogin=findViewById(R.id.login)
        btnsignup=findViewById(R.id.signup)
        btnsignup.setOnClickListener{
            val intent= Intent(this,SignIn::class.java)
            startActivity(intent)
        }
        btnlogin.setOnClickListener{
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            login(email,password)

        }
    }
    private fun login(email:String,password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent=Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"User doesnt exist",Toast.LENGTH_SHORT).show()
                }
            }
    }
}