package com.decodex.sosadmin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_request_details.*

class request_details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_details)

        val intent = intent
        val name = intent.getStringExtra("name")
        val location = intent.getStringExtra("city")
        val time = intent.getStringExtra("time")
        val phone = intent.getStringExtra("phone")
        val lat = intent.getStringExtra("latitude")
        val long = intent.getStringExtra("longitude")

        name_reqdetails.setText(name)
        time_reqdetails.setText(time)
        location_reqdetails.setText(location)
        phone_reqdetails.setText(phone)
        latlong.setText("${lat}, ${long}")

        btn_maps.setOnClickListener {
            openInmap(lat!!, long!!)
        }



    }

    fun openInmap(lat:String, long:String){
        // Create a Uri from an intent string. Use the result to create an Intent.
        val gmmIntentUri = Uri.parse("google.navigation:q=" + "$lat,$long")

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
// Make the Intent explicit by setting the Google Maps package

        mapIntent.setPackage("com.google.android.apps.maps")

// Attempt to start an activity that can handle the Intent
        startActivity(mapIntent)
    }

}