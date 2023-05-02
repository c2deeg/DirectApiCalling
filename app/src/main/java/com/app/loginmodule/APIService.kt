package com.app.loginmodule

import com.app.loginmodule.dataclass.ListDataExample
import com.app.loginmodule.dataclass.LoginRequest
import com.app.loginmodule.dataclass.LoginResponse
import com.app.loginmodule.dataclass.User
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {

    @POST("/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("users")
    suspend fun getUsers(): List<User>
}