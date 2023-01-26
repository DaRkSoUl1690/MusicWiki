package com.company.assignment.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.company.assignment.Utils.getSizeName
import com.company.assignment.adapters.MainAdapter
import com.company.assignment.adapters.TabLayoutAdapter
import com.company.assignment.databinding.ActivityProfileScreenBinding
import com.company.assignment.models.MainModel
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat

class ProfileScreen : AppCompatActivity() {

    private lateinit var binding: ActivityProfileScreenBinding

    private var mainList: ArrayList<MainModel> = ArrayList()
    private var mainList2: ArrayList<MainModel> = ArrayList()
    private var tagListName: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        profileDetailsInfo(intent.getStringExtra("artistName").toString())
        topAlbumDetailsFromArtist(intent.getStringExtra("artistName").toString())
        topTrackListFunction(intent.getStringExtra("artistName").toString())
    }

    fun prettyCount(number: Number): String? {
        val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
        val numValue = number.toLong()
        val value = Math.floor(Math.log10(numValue.toDouble())).toInt()
        val base = value / 3
        return if (value >= 3 && base < suffix.size) {
            DecimalFormat("#0.0").format(
                numValue / Math.pow(
                    10.0,
                    (base * 3).toDouble()
                )
            ) + suffix[base]
        } else {
            DecimalFormat("#,##0").format(numValue)
        }
    }

    private fun topAlbumDetailsFromArtist(artist: String) {
        val topAlbumUrl =
            "https://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=${artist}&api_key=ab695c986ecbb3d5726f9f59040961f8&format=json"
        val requestQ = Volley.newRequestQueue(this@ProfileScreen)
        val albumRequest = JsonObjectRequest(
            Request.Method.GET, topAlbumUrl, null,
            { response ->

                val json: JSONObject = response.get("topalbums") as JSONObject
                val jsonArr: JSONArray = json.getJSONArray("album")

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
                            break;
                        }

                    }
                    mainList.add(MainModel(albumName, artistName, imageUrl))

                }

                val adapter = TabLayoutAdapter(mainList, 0)
                val RecyclerViewLayoutManager = LinearLayoutManager(
                    applicationContext
                )
                binding.talbumrv.layoutManager = RecyclerViewLayoutManager
                val horizontalLayout = LinearLayoutManager(
                    this@ProfileScreen,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                binding.talbumrv.layoutManager = horizontalLayout
                binding.talbumrv.adapter = adapter

            }
        ) { error -> Log.d("error", error.toString()) }


        requestQ.add(albumRequest)
    }

    /**
     * Fetching details from URL of artist
     */
    private fun profileDetailsInfo(artist: String) {

        val infoUrl =
            "https://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=${artist}&api_key=ab695c986ecbb3d5726f9f59040961f8&format=json"

        val requestQ = Volley.newRequestQueue(this@ProfileScreen)

        val request = JsonObjectRequest(
            Request.Method.GET, infoUrl, null,
            { response ->

                val json: JSONObject = response.get("artist") as JSONObject
                val jsonArr: JSONArray = json.getJSONArray("image")
                val listeners = json.getJSONObject("stats").getString("listeners")
                val playcount = json.getJSONObject("stats").getString("playcount")
                val summary = json.getJSONObject("bio").getString("summary")
                val tagList = json.getJSONObject("tags").getJSONArray("tag")

                for (nameElement in 0.until(tagList.length())) {

                    val initialStep = tagList.getJSONObject(nameElement).getString("name")
                    tagListName.add(initialStep)

                }

                val adapter = MainAdapter(tagListName)
                val RecyclerViewLayoutManager = LinearLayoutManager(
                    applicationContext
                )
                binding.coverRv.layoutManager = RecyclerViewLayoutManager
                val horizontalLayout = LinearLayoutManager(
                    this@ProfileScreen,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                binding.coverRv.layoutManager = horizontalLayout
                binding.coverRv.adapter = adapter

                var descString = ""
                for (idx in summary.indices) {
                    if (summary[idx] == '<') break
                    descString += summary[idx]
                }

                binding.info.text = descString
                binding.playCountText.text = prettyCount(Integer.parseInt(playcount))
                binding.name.text = artist
                binding.followerText.text = prettyCount(Integer.parseInt(listeners))

                var imageUrl = ""
                for (index in 0.until(jsonArr.length())) {

                    if (jsonArr.getJSONObject(index)
                            .getString("size") == getSizeName(this)
                    ) {
                        imageUrl = jsonArr.getJSONObject(index).getString("#text")
                        break
                    }
                }
                Picasso.get().load(imageUrl).into(binding.profilePic)

            }
        ) { error -> Log.d("error", error.toString()) }


        requestQ.add(request)
    }

    /**
     * Fetching top track list details from URL of artist
     */
    private fun topTrackListFunction(artist: String) {

        val topTrackUrl =
            "https://ws.audioscrobbler.com/2.0/?method=artist.gettoptracks&artist=${artist}&api_key=ab695c986ecbb3d5726f9f59040961f8&format=json"

        val requestQ = Volley.newRequestQueue(this@ProfileScreen)

        val albumRequest = JsonObjectRequest(
            Request.Method.GET, topTrackUrl, null,
            { response ->

                val json: JSONObject = response.get("toptracks") as JSONObject
                val jsonArr: JSONArray = json.getJSONArray("track")

                for (nameElement in 0.until(jsonArr.length())) {
                    val initialName = jsonArr.getJSONObject(nameElement)

                    val artistName = initialName.getJSONObject("artist").getString("name")

                    val albumName = initialName.getString("name").toString()

                    val imageJson = initialName.getJSONArray("image")
                    var imageUrl = ""

                    for (index in 0.until(imageJson.length())) {

                        if (imageJson.getJSONObject(index)
                                .getString("size") == getSizeName(this)
                        ) {
                            imageUrl = imageJson.getJSONObject(index).getString("#text")
                            break;
                        }

                    }
                    mainList2.add(MainModel(albumName, artistName, imageUrl))

                }

                val adapter = TabLayoutAdapter(mainList2, 2)
                val RecyclerViewLayoutManager = LinearLayoutManager(
                    applicationContext
                )
                binding.ttrackrv.layoutManager = RecyclerViewLayoutManager
                val horizontalLayout = LinearLayoutManager(
                    this@ProfileScreen,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                binding.ttrackrv.layoutManager = horizontalLayout
                binding.ttrackrv.adapter = adapter

            }
        ) { error -> Log.d("error", error.toString()) }


        requestQ.add(albumRequest)

    }
}