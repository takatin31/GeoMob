package com.example.geomob.Fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.blongho.country_data.World
import com.example.geomob.Activities.PaysActivity
import com.example.geomob.DataClasses.Pays
import com.example.geomob.Database.PaysDatabase
import com.example.geomob.Other.RequestHandler
import com.example.geomob.Other.RequestHandler.Companion.getInstance

import com.example.geomob.R
import com.example.geomob.Threads.AppExecutors
import kotlinx.android.synthetic.main.fragment_main.*
import java.time.LocalDate


class MainFragment : Fragment() {

    private var countryCode = ""
    private lateinit var country : Pays
    private lateinit var paysDatabase : PaysDatabase
    var player : MediaPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paysDatabase = PaysDatabase.getDatabase(activity!!)
        countryCode = (activity as PaysActivity).getCountryCode()
        getCountryByCode(countryCode)

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

                val jsonRequestNbrDeaths = JsonObjectRequest(
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

                getInstance(activity!!).addToRequestQueue(jsonRequestNbrDeaths)

            }
        }

    }

}
