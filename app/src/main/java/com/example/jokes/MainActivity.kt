package com.example.jokes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.jokes.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
const val BASE_URL = "https://official-joke-api.appspot.com/"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_get_new_joke.setOnClickListener {
            getRandomJokes()
        }
    }

    private fun getRandomJokes(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(JokesService::class.java)

        val retrofitData = retrofitBuilder.getJokes()

        retrofitData.enqueue(object : Callback<JokesData?> {
            override fun onResponse(call: Call<JokesData?>, response: Response<JokesData?>) {
                val responseBody = response.body()!!
                Log.e("Jokes Data: ",responseBody.toString())
                tv_setUp.text = responseBody.setup.toString()
                tv_punchLine.text = responseBody.punchline.toString()
            }

            override fun onFailure(call: Call<JokesData?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to get data", Toast.LENGTH_SHORT).show()
            }
        })
    }


}