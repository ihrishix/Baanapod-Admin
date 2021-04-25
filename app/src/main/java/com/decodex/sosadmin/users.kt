package com.decodex.sosadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class users : AppCompatActivity(), user_recycler_adapter.itemclick {

    val request_collection = Firebase.firestore.collection("users")
    var list = ArrayList<user>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        var adapter = user_recycler_adapter(list, this)

        users_recyclerview.adapter = adapter
        users_recyclerview.layoutManager = LinearLayoutManager(this)
        users_recyclerview.setHasFixedSize(true)
        subscribetoUpdates(adapter)

    }

    private fun getList(adapter:user_recycler_adapter, list:ArrayList<user>){

        CoroutineScope(Dispatchers.IO).launch {
            var query_snapshot = request_collection.get().await()

            //todo
            Log.d("hrishi", "List before = ${list}")


            for(document in query_snapshot.documents){
                var re_row = user(document["firstname"].toString(), document["lastname"].toString(), document["age"].toString().toInt()
                    ,document["contact"].toString())

                Log.d("hrishi", "List = ${re_row}")

                list.add(re_row)
            }
            Log.d("hrishi", "after ${list}")
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
            }


        }

    }

    private fun subscribetoUpdates(adapter:user_recycler_adapter){
        var temp_list = ArrayList<user>()

        request_collection.addSnapshotListener { querySnapshot, error ->
            error?.let {
                Toast.makeText(this, error.message, Toast.LENGTH_LONG)
                return@addSnapshotListener
            }

            querySnapshot?.let {
                val sb = StringBuilder()
                list.clear()


                for(document in it){
                    var temp_request = user(document["firstname"].toString(), document["lastname"].toString(), document["age"].toString().toInt()
                        ,document["contact"].toString())


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

        val intent = Intent(this, usersDetails::class.java)
        intent.putExtra("firstname", list[position].firstname)
        intent.putExtra("lastname", list[position].lastname)
        intent.putExtra("age", list[position].age.toString())
        intent.putExtra("contact", list[position].contact)

        startActivity(intent)
    }
}