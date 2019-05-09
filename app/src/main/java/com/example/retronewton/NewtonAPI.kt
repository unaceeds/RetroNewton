package com.example.retronewton

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewtonAPI {

    @GET("/{operation}/{expression}")
    fun solveNormal(
        @Path("operation") operation: String,
        @Path("expression") expression: String
    ): Call<NewtonDTO1>

    @GET("/zeroes/{expression}")
    fun solveZeroes(
        @Path("expression") expression: String
    ): Call<NewtonDTO2>

}