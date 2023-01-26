package com.company.assignment.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.company.assignment.adapters.MainAdapter
import com.company.assignment.databinding.ActivityAlbumDetailsBinding
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject


class AlbumDetails : AppCompatActivity() {

    lateinit var binding: ActivityAlbumDetailsBinding
    var arrayTagList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        binding = ActivityAlbumDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val artistName = intent.getStringExtra("artistName")
        val titleName = intent.getStringExtra("titleName")
        val imageUrl = intent.getStringExtra("imageUrl")
        albumDetailsFetch(artistName!!, titleName!!, imageUrl!!)

    }

    private fun albumDetailsFetch(artistName: String, albumName: String, imageUrl: String) {

        albumName.trim()
        albumName.replace(" ", "%20")
        val albumUrl =
            "https://ws.audioscrobbler.com/2.0/?method=album.gettoptags&artist=${artistName}&album=${albumName}&api_key=ab695c986ecbb3d5726f9f59040961f8&format=json"

        val albumInfoUrl =
            "https://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=ab695c986ecbb3d5726f9f59040961f8&artist=${artistName}&album=${albumName}&format=json"

        val requestQ = Volley.newRequestQueue(this@AlbumDetails)
        Picasso.get().load(imageUrl).into(binding.imageView5)
        val request = JsonObjectRequest(
            Request.Method.GET, albumUrl, null,
            { response ->
                val json: JSONObject = response.get("toptags") as JSONObject
                val jsonArr: JSONArray = json.getJSONArray("tag")

                for (nameElement in 0.until(jsonArr.length())) {
                    val initialStep = jsonArr.getJSONObject(nameElement)

                    arrayTagList.add(initialStep.getString("name").toString())

                }
                val adapter = MainAdapter(arrayTagList)
                val RecyclerViewLayoutManager = LinearLayoutManager(
                    applicationContext
                )
                binding.coverRv.layoutManager = RecyclerViewLayoutManager
                val horizontalLayout = LinearLayoutManager(
                    this@AlbumDetails,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                binding.coverRv.layoutManager = horizontalLayout
                binding.coverRv.adapter = adapter

            }
        ) { error -> Log.d("error", error.toString()) }

        requestQ.add(request)


        val request2 = JsonObjectRequest(
            Request.Method.GET, albumInfoUrl, null,
            { response ->
                val json: JSONObject = response.get("album") as JSONObject
                val jsonWiki: JSONObject = json.get("wiki") as JSONObject
                val jsonSummary: String = jsonWiki.get("summary").toString()
                binding.titleInfo.text = albumName
                binding.artistinfo.text = artistName
                var nameString: String? = ""
                for (idx in jsonSummary.indices) {
                    if (jsonSummary[idx] == '<') break

                    nameString += jsonSummary[idx]


                }
                binding.info.text = nameString
            }
        ) { error -> Log.d("error", error.toString()) }
        requestQ.add(request2)
    }

}