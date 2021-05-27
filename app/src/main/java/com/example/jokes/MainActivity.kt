package com.example.jokes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://official-joke-api.appspot.com/"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_start_laughing.setOnClickListener {
            progressBar_cyclic.visibility = View.VISIBLE
            tv_next_joke.visibility = View.VISIBLE
            tv_setUp.visibility = View.VISIBLE
            tv_punchLine.visibility = View.VISIBLE
            tv_start_laughing.visibility = View.GONE
            ll_buttons.visibility = View.VISIBLE
            getRandomJokes()

        }
        tv_next_joke.setOnClickListener {
            progressBar_cyclic.visibility = View.VISIBLE
            getRandomJokes()
        }

        tv_share.setOnClickListener {
            shareJoke()
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
                progressBar_cyclic.visibility = View.GONE
            }

            override fun onFailure(call: Call<JokesData?>, t: Throwable) {
                progressBar_cyclic.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Failed to get data", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun shareJoke() {
        val setup: String = tv_setUp.text.toString()
        val punchLine: String = tv_punchLine.text.toString()
        val joke: String = setup +"\n"+punchLine

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, joke)
        shareIntent.type = "text/plain"
        startActivity(Intent.createChooser(shareIntent, "send to"))
    }


}