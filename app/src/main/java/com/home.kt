package com

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.decodex.sosadmin.MainActivity
import com.decodex.sosadmin.R
import com.decodex.sosadmin.TOPIC
import com.decodex.sosadmin.users
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
const val rc_login = 1010


class home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)


        user_btn.setOnClickListener {
            val intent = Intent(this, users::class.java)
            startActivity(intent)
        }

        request_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}