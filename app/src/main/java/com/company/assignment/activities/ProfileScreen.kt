package com.company.assignment.activities

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.company.assignment.databinding.ActivityProfileScreenBinding
import com.company.assignment.models.MainModel
import org.json.JSONArray
import org.json.JSONObject

class ProfileScreen : AppCompatActivity() {

    private lateinit var binding: ActivityProfileScreenBinding

    private var albumList: ArrayList<MainModel> = ArrayList()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()


    }

    private fun getSizeName(context: Context): String {
        var screenLayout: Int = context.resources.configuration.screenLayout
        screenLayout = screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        return when (screenLayout) {
            Configuration.SCREENLAYOUT_SIZE_SMALL -> "small"
            Configuration.SCREENLAYOUT_SIZE_NORMAL -> "large"
            Configuration.SCREENLAYOUT_SIZE_LARGE -> "extralarge"
            4 -> "extralarge"
            else -> "undefined"
        }
    }

    fun profileDetailsInfo(artist: String) {

        val infoUrl =
            "https://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=${artist}&api_key=ab695c986ecbb3d5726f9f59040961f8&format=json"

        val requestQ = Volley.newRequestQueue(this@ProfileScreen)

        val request = JsonObjectRequest(
            Request.Method.GET, infoUrl, null,
            { response ->

                val json: JSONObject = response.get("albums") as JSONObject
                val jsonArr: JSONArray = json.getJSONArray("album")
                val listeners = json.getJSONObject("stats").getString("listeners")
                val playcount = json.getJSONObject("stats").getString("playcount")
                for (nameElement in 0.until(jsonArr.length())) {
                    val initialName = jsonArr.getJSONObject(nameElement)

                    //artist Name ::
                    val artistName = initialName.getJSONObject("artist").getString("name")

                    // Album Name :: 
                    val albumName = initialName.getString("name").toString()

                    //Image Url ::
                    val imageJson = initialName.getJSONArray("image")
                    var imageUrl = ""

                    for (index in 0.until(imageJson.length())) {

                        if (imageJson.getJSONObject(index)
                                .getString("size") == getSizeName(this)
                        ) {
                            imageUrl = imageJson.getJSONObject(index).getString("#text")
                            break
                        }

                    }
                    albumList.add(MainModel(albumName, artistName, imageUrl))


                }
            }
        ) { error -> Log.d("error", error.toString()) }


        requestQ.add(request)

    }
}