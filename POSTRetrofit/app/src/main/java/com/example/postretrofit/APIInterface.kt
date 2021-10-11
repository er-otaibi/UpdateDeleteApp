package com.example.postretrofit

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @Headers("Content-Type: application/json")
    @POST("/test/")
    fun addUser(@Body userData: Users.UserDetails): Call<List<Users.UserDetails>>

    @Headers("Content-Type: application/json")
    @GET("/test/")
    fun getUser(): Call<List<Users.UserDetails>>

    @Headers("Content-Type: application/json")
    @DELETE("/test/{pk}")
    fun deleteUser(@Path("pk") pk: Int ): Call<Void>

    @Headers("Content-Type: application/json")
    @PUT("/test/{pk}")
    fun updateUser(@Path("pk") pk: Int, @Body userData: Users.UserDetails): Call<List<Users.UserDetails>>

}