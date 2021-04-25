    package com.decodex.sosadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

const val TOPIC = "/topics/admin"


class MainActivity : AppCompatActivity(), request_recycler_adapter.itemclick {
    val request_collection = Firebase.firestore.collection("requests").orderBy("datee")
    var list = ArrayList<recycler_row>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)


        var adapter = request_recycler_adapter(list, this)

        requests_recyclerview.adapter = adapter
        requests_recyclerview.layoutManager = LinearLayoutManager(this)
        requests_recyclerview.setHasFixedSize(true)
        //getList(adapter, list)
        subscribetoUpdates(adapter)







    }

    private fun getList(adapter:request_recycler_adapter, list:ArrayList<recycler_row>){

        CoroutineScope(Dispatchers.IO).launch {
            var query_snapshot = request_collection.get().await()

            //todo
            for(document in query_snapshot.documents){
                var re_row = recycler_row(document["name"].toString(), document["city"].toString(), document["phone_no"].toString()
                ,document["time"].toString(), document["latitude"].toString(), document["longitude"].toString())
                Log.d("hrishi", "List = ${re_row}")

                list.add(re_row)
            }
            Log.d("hrishi", "List = ${list}")
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
            }


        }

    }

    private fun subscribetoUpdates(adapter:request_recycler_adapter){
        var temp_list = ArrayList<recycler_row>()

        request_collection.addSnapshotListener { querySnapshot, error ->
            error?.let {
                Toast.makeText(this, error.message, Toast.LENGTH_LONG)
                return@addSnapshotListener
            }

            querySnapshot?.let {
                val sb = StringBuilder()
                list.clear()


                for(document in it){
                    var temp_request = recycler_row(document["name"].toString(), document["city"].toString(), document["phone_no"].toString()
                        ,document["time"].toString(), document["latitude"].toString(), document["longitude"].toString())


                    Log.d("hrishi", "updatw ${temp_request}")

                    list.add(temp_request)





                }
                list.reverse()
                adapter.notifyDataSetChanged()
                Log.d("hrishi", "up list ${list}")



            }
        }



    }

    override fun onitemclick(position: Int) {

        val intent = Intent(this, request_details::class.java)
        intent.putExtra("city", list[position].city)
        intent.putExtra("latitude", list[position].latitude)
        intent.putExtra("longitude", list[position].longitude)
        intent.putExtra("name", list[position].name)
        intent.putExtra("time", list[position].time)
        intent.putExtra("phone", list[position].phone)
        startActivity(intent)
    }
}