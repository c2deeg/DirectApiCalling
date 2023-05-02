package com.app.loginmodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.loginmodule.adapter.RecyclerAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity2 : AppCompatActivity() {
    var apiService: APIService? = null
    private val coroutineScope = MainScope()
    var recyclerAdapter: RecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

         apiService = retrofit.create(APIService::class.java)
        apicall()
    }

    private fun apicall() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        coroutineScope.launch {
            try {
                val users = apiService?.getUsers()
                recyclerAdapter = RecyclerAdapter(this@MainActivity2, users)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity2, LinearLayoutManager.VERTICAL, false)
                    adapter = recyclerAdapter
                }
            } catch (t: Throwable) {
                Toast.makeText(applicationContext, "Error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        }

//        coroutineScope.launch {
//            try {
//                val listData: List<ListDataExample> = apiService?.getUsers() ?: emptyList()
//                recyclerAdapter = RecyclerAdapter(this@MainActivity2, listData)
//                recyclerView.apply {
//                    layoutManager = LinearLayoutManager(this@MainActivity2, LinearLayoutManager.VERTICAL, false)
//                    adapter = recyclerAdapter
//                }
//            } catch (t: Throwable) {
//                Toast.makeText(applicationContext, "Error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}
