package com.company.assignment.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.company.assignment.Fragments.adapters.FragmentAdapter
import com.company.assignment.databinding.ActivitySecondScreenBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.json.JSONObject

class SecondScreen : AppCompatActivity() {

    private var binding: ActivitySecondScreenBinding? = null
    lateinit var tagName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.hide()
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.viewPager.adapter = FragmentAdapter(
            supportFragmentManager,
            lifecycle, this, binding!!.tabLayout.tabCount
        )

        tagName = intent.getStringExtra("nameTag").toString()
        callSummaryTagFunction(tagName)

        

        TabLayoutMediator(
            binding!!.tabLayout, binding!!.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            if (position == 0) {
                tab.text = "ALBUMS"
            }
            if (position == 1) {
                tab.text = "ARTISTS"
            }
            if (position == 2) {
                tab.text = "TRACKS"
            }
        }.attach()
    }

    fun getMyData(): String {
        return tagName
    }

    private fun callSummaryTagFunction(nameTag: String) {
        val urlInfo: String =
            "https://ws.audioscrobbler.com/2.0/?method=tag.getinfo&tag=${nameTag}&api_key=ab695c986ecbb3d5726f9f59040961f8&format=json"

        val requestQ = Volley.newRequestQueue(this@SecondScreen)

        val request = JsonObjectRequest(
            Request.Method.GET, urlInfo, null,
            { response ->
                val json: JSONObject = response.get("tag") as JSONObject
                val jsonWiki: JSONObject = json.get("wiki") as JSONObject
                val jsonSummary: String = jsonWiki.get("summary").toString()
                binding!!.title.text = nameTag
                var nameString: String? = ""
                for (idx in jsonSummary.indices) {
                    if (jsonSummary[idx] == '<') break

                    nameString += jsonSummary[idx]


                }
                binding!!.summary.text = nameString
            }
        ) { error -> Log.d("error", error.toString()) }
        requestQ.add(request)
    }

}