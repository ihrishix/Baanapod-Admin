package com.decodex.sosadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_users_details.*

class usersDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_details)

        val intent = intent

        firstname_userdetails.setText(intent.getStringExtra("firstname"))
        lastname_userdetails.setText(intent.getStringExtra("lastname"))
        age_userdetails.setText(intent.getStringExtra("age"))
        contact_userdetails.setText(intent.getStringExtra("contact"))
    }
}