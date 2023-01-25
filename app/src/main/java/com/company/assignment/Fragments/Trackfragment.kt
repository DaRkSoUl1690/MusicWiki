package com.company.assignment.Fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.company.assignment.R
import com.company.assignment.activities.SecondScreen
import com.company.assignment.adapters.AlbumAdapter
import com.company.assignment.databinding.FragmentAlbumBinding
import com.company.assignment.databinding.FragmentTrackfragmentBinding
import com.company.assignment.models.Album.AlbumModel
import org.json.JSONArray
import org.json.JSONObject


class Trackfragment : Fragment() {
    private var _binding: FragmentTrackfragmentBinding? = null
    private val binding
        get() = _binding
    var trackList: ArrayList<AlbumModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackfragmentBinding.inflate(inflater, container, false)
        
        val activity: SecondScreen? = activity as SecondScreen?
        val myDataFromActivity: String = activity!!.getMyData()
        callAlbumTagFunction(myDataFromActivity)
        
        return binding!!.root
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
    private fun callAlbumTagFunction(nameTag: String) {
        val urlAlbumInfo: String =
            "https://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag=${nameTag}&api_key=ab695c986ecbb3d5726f9f59040961f8&format=json"

        val requestQ = Volley.newRequestQueue(context)

        val request = JsonObjectRequest(
            Request.Method.GET, urlAlbumInfo, null,
            { response ->

                val json: JSONObject = response.get("tracks") as JSONObject
                val jsonArr: JSONArray = json.getJSONArray("track")
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
                                .getString("size") == getSizeName(requireContext())
                        ) {
                            imageUrl = imageJson.getJSONObject(index).getString("#text")
                            break;
                        }

                    }
                    trackList.add(AlbumModel(albumName, artistName, imageUrl))


                }

                val adapter = AlbumAdapter(trackList, 2)
                binding!!.trackrv.adapter = adapter
            }
        ) { error -> Log.d("error", error.toString()) }
        requestQ.add(request)
    }
}