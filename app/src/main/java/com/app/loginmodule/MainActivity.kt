package com.app.loginmodule

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.loginmodule.dataclass.LoginRequest
import com.app.loginmodule.dataclass.LoginResponse
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var apiService:APIService?=null
    private val coroutineScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

         apiService = retrofit.create(APIService::class.java)

        apicall()
    }

    private fun apicall(){
        val btn_login = findViewById<Button>(R.id.btn_login)
        val et_username = findViewById<EditText>(R.id.et_username)
        val progressbar = findViewById<ProgressBar>(R.id.progressbar)
        val tvname = findViewById<TextView>(R.id.tvname)
        val tvemail = findViewById<TextView>(R.id.tvemail)
        btn_login.setOnClickListener{
            progressbar.visibility = View.VISIBLE
            coroutineScope.launch {
                val loginRequest = LoginRequest("kminchelle", "0lelplR")
                try {
                    val loginResponse = apiService?.login(loginRequest)
                    tvname.text = loginResponse?.firstName.toString()
                    tvemail.text = loginResponse?.email.toString()
                    progressbar.visibility = View.GONE
                    delay(200)
                    showDialog(loginResponse)
                }catch (t:Throwable){
                    Toast.makeText(applicationContext, "User does not exist", Toast.LENGTH_SHORT).show()

                }



                // do something with the login response
            }




        }
    }



     fun showDialog(loginResponse: LoginResponse?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialogitem)
        val window = dialog.window
        val wlp = window!!.attributes
        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        val mainlay = dialog.findViewById<LinearLayout>(R.id.mainlay)
        val tvname = dialog.findViewById<TextView>(R.id.tvname)
        val tvemail = dialog.findViewById<TextView>(R.id.tvemail)
        val tvid = dialog.findViewById<TextView>(R.id.tvid)
        val tvgender = dialog.findViewById<TextView>(R.id.tvgender)
         tvname.text = "Name: "+loginResponse?.username
         tvemail.text = "Email: "+loginResponse?.email
         tvid.text = "ID: "+loginResponse?.id
         tvgender.text = "Gender: "+loginResponse?.gender
         mainlay.setOnClickListener{
             val intent = Intent(this@MainActivity,MainActivity2::class.java)
             startActivity(intent)
         }

        dialog.show()
    }

}