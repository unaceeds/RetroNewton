package com.example.retronewton

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in 1..15) {
            val btn = findViewById<Button>(resources.getIdentifier("button$i", "id", packageName))
            btn.setOnClickListener {
                buttonClick(it)
            }
        }
    }

    fun buttonClick(view: View) {
        val clicked = findViewById<Button>(view.id)
        var index = 0
        do index++ while (resources.getIdentifier("button$index", "id", packageName).equals(clicked.id).not())
        val operation = when (index) {
            1 -> "simplify"
            2 -> "factor"
            3 -> "derive"
            4 -> "integrate"
            5 -> "zeroes"
            6 -> "tangent"
            7 -> "area"
            8 -> "cos"
            9 -> "sin"
            10 -> "tan"
            11 -> "arccos"
            12 -> "arcsin"
            13 -> "arctan"
            14 -> "abs"
            else -> "log"
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newton.now.sh")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val newton = retrofit.create(NewtonAPI::class.java)
        if (index != 5) {
            val call = newton.solveNormal(operation, editText.text.toString())
            call.enqueue( object : Callback<NewtonDTO1> {
                override fun onFailure(call: Call<NewtonDTO1>, t: Throwable) {
                    Log.d("am2019", "ups")
                }

                override fun onResponse(call: Call<NewtonDTO1>, response: Response<NewtonDTO1>) {
                    val body = response.body()
                    if (response.isSuccessful)
                        textView.text = body!!.result
                }
            })
        } else {
            val call = newton.solveZeroes(editText.text.toString())
            call.enqueue( object : Callback<NewtonDTO2> {
                override fun onFailure(call: Call<NewtonDTO2>, t: Throwable) {
                    Log.d("am2019", "ups")
                }

                override fun onResponse(call: Call<NewtonDTO2>, response: Response<NewtonDTO2>) {
                    val body = response.body()
                    if (response.isSuccessful) {
                        var s: String = "["
                        for (i in 0 until body!!.result.size) {
                            s += body!!.result.get(i)
                            if (i != body!!.result.size - 1)
                                s += ", "
                        }
                        s += "]"
                        textView.text = s
                    }
                }
            })
        }
    }
}
