package com.example.geomob.Fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.blongho.country_data.World
import com.example.geomob.Activities.PaysActivity
import com.example.geomob.Adapters.CountriesAdapter
import com.example.geomob.Adapters.PhotosAdapter
import com.example.geomob.DataClasses.*
import com.example.geomob.Database.PaysDatabase
import com.example.geomob.Other.RequestHandler
import com.example.geomob.Other.RequestHandler.Companion.getInstance

import com.example.geomob.R
import com.example.geomob.Threads.AppExecutors
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.time.LocalDate


class MainFragment : Fragment() {

    private var countryCode = ""
    private lateinit var country : Pays
    private lateinit var paysDatabase : PaysDatabase
    var player : MediaPlayer? = null
    var photosList = arrayListOf<PaysPhoto>()
    var resourcesList = arrayListOf<Ressource>()
    var eventsList = arrayListOf<Evenement>()
    var personsList = arrayListOf<Personalite>()

    lateinit var photosAdapter: PhotosAdapter
    lateinit var photosLayoutManager : LinearLayoutManager

    lateinit var eventsAdapter: PhotosAdapter
    lateinit var eventsLayoutManager : LinearLayoutManager

    lateinit var personsAdapter: PhotosAdapter
    lateinit var PersonsLayoutManager : LinearLayoutManager

    lateinit var resourceAdapter: PhotosAdapter
    lateinit var resourceManager : LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paysDatabase = PaysDatabase.getDatabase(activity!!)
        countryCode = (activity as PaysActivity).getCountryCode()
        getCountryByCode(countryCode)

        photosLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerPhotos.layoutManager = photosLayoutManager

        photosAdapter = PhotosAdapter(activity!! as PaysActivity, photosList)
        recyclerPhotos.adapter = photosAdapter

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    fun getCountryByCode(code : String){

        AppExecutors.instance!!.diskIO().execute {
            val resultList = paysDatabase.paysDao().findPaysByCountryCode(code)
            if (resultList.isNotEmpty()){
                val pays = resultList[0]
                countryTitleView.text = pays.nomPays
                countryCapitalView.text = pays.capital
                countryFlagView.setImageResource(World.getFlagOf(pays.codePays))
                populationView.text = pays.population.toString()
                surfaceView.text = pays.surface.toString() + " Km²"

                btnHymn.setOnClickListener {
                    if (player == null || !player!!.isPlaying){
                        player = MediaPlayer.create(activity, pays.hymne)
                        player!!.start()
                        btnHymn.setImageResource(R.drawable.ic_stop)
                    }else{
                        if (player != null && player!!.isPlaying){
                            btnHymn.setImageResource(R.drawable.ic_flag)
                            player!!.stop()
                        }
                    }
                }

                val urlDesc =
                    "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exintro=&titles=${pays.nomPays}"

                val jsonRequestDescription = JsonObjectRequest(
                    Request.Method.GET, urlDesc, null,
                    Response.Listener { response ->
                        val pages = response.getJSONObject("query").getJSONObject("pages")
                        if (pages.length() > 0){
                            val nPage = pages.names().get(0).toString()
                            val description = pages.getJSONObject(nPage).getString("extract")
                            descriptionWebView.loadData(description, "text/html; charset=utf-8", "utf-8")
                        }else{
                            val description = "No Description was found"
                            descriptionWebView.loadData(description, "text/html; charset=utf-8", "utf-8")
                        }

                    },
                    Response.ErrorListener {
                        Log.d("Error", "Request error")

                    })

                getInstance(activity!!).addToRequestQueue(jsonRequestDescription)

                val photosUrl = "https://pixabay.com/api/?key=16824293-84fa13cffe00ccd9ffa414c78&q=${pays.nomPays}&image_type=photo"
                val jsonRequestPhotos = JsonObjectRequest(
                    Request.Method.GET, photosUrl, null,
                    Response.Listener { response ->
                        photosList.clear()
                        val items = response.getJSONArray("hits")
                        for (i in 0 until items.length()){
                            val pic = items.getJSONObject(i)
                            val id = pic.getInt("id")
                            val url = pic.getString("webformatURL")
                            photosList.add(PaysPhoto(id, url, pays.codePays))
                        }
                        photosAdapter.notifyDataSetChanged()
                    },
                    Response.ErrorListener {
                        Log.d("Error", "Request error")

                    })

                getInstance(activity!!).addToRequestQueue(jsonRequestPhotos)



            }
        }

    }

}
