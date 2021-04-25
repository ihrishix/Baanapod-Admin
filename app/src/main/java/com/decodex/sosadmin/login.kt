package com.decodex.sosadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.home
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception


class login : AppCompatActivity() {

    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        checkloggedinState()

        btn_login.setOnClickListener{
            login_user()

        }



    }

    private fun login_user(){
        val email = emailbox_login.text.toString()
        val pass = password_login.text.toString()

        if(email.isNotEmpty() && pass.isNotEmpty()){

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, pass).await()
                    withContext(Dispatchers.Main){
                        checkloggedinState()
                    }



                }catch (e: Exception){
                    Log.e("Login", "Error : ${e.message}")
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@login, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }else{
            Toast.makeText(this, "WRONG ID PASS", Toast.LENGTH_LONG).show()
        }
    }

    fun checkloggedinState(){
        if(auth.currentUser == null){
            Toast.makeText(this, "Not Logged in",Toast.LENGTH_LONG).show()
        }
        else{
            val intent = Intent(this, home::class.java)
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
            finish()
        }
    }
}