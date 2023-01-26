package com.company.assignment.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.company.assignment.R
import com.company.assignment.adapters.MainAdapter
import com.company.assignment.databinding.ActivityFirstScreenBinding
import org.json.JSONArray
import org.json.JSONObject

/**
 * This activity is initial Activity for our Application
 */

class FirstScreen : AppCompatActivity() {
    private val TAG = "NameError"
    private var binding: ActivityFirstScreenBinding? = null
    private val genreUrl: String =
        "https://ws.audioscrobbler.com/2.0/?method=chart.gettoptags&api_key=ab695c986ecbb3d5726f9f59040961f8&format=json"

    var genreNameList = ArrayList<String>()
    var genreNameList2 = ArrayList<String>()
    var show: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val requestQ = Volley.newRequestQueue(this@FirstScreen)

        val request = JsonObjectRequest(
            Request.Method.GET, genreUrl, null,
            { response ->
                val json: JSONObject = response.get("tags") as JSONObject
                val jsonArr: JSONArray = json.getJSONArray("tag")
                var count: Int = 0
                for (nameElement in 0.until(jsonArr.length())) {
                    genreNameList.add(
                        jsonArr.getJSONObject(nameElement).getString("name").toString()
                    )

                }
                for (nameElement in 0.until(genreNameList.size)) {
                    if (count == 10) break

                    genreNameList2.add(genreNameList[nameElement])

                    count++
                }
                val firsAdapter = MainAdapter(
                    genreNameList2
                )
                binding!!.coverRv.adapter = firsAdapter

                binding!!.imageView.setOnClickListener {
                    if (show) {
                        val cartAdapter = MainAdapter(
                            genreNameList
                        )
                        binding!!.imageView.setImageResource(R.drawable.baseline_arrow_circle_down_24)
                        show = false
                        binding!!.coverRv.adapter = cartAdapter
                    } else {
                        val cartAdapter = MainAdapter(
                            genreNameList2
                        )
                        binding!!.imageView.setImageResource(R.drawable.baseline_arrow_circle_up_24)
                        show = true
                        binding!!.coverRv.adapter = cartAdapter
                    }
                }


            }
        ) { error -> Log.d("error", error.toString()) }

        requestQ.add(request)

    }
}