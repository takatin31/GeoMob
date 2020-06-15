package com.example.geomob.Activities


import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.blongho.country_data.World
import com.example.geomob.Adapters.CountriesAdapter
import com.example.geomob.DataClasses.Initializer
import com.example.geomob.DataClasses.Pays
import com.example.geomob.Database.PaysDatabase
import com.example.geomob.Other.CountryLocation
import com.example.geomob.R
import com.example.geomob.Threads.AppExecutors
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.expressions.Expression.*
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor
import com.mapbox.mapboxsdk.style.layers.TransitionOptions
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URI
import java.net.URISyntaxException
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var paysDatabase : PaysDatabase
    lateinit var adapter: CountriesAdapter
    lateinit var layoutManager : LinearLayoutManager
    var countriesList = mutableListOf<Pays>()
    var mapStyleLoaded = false

    private lateinit var mapboxMap: MapboxMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
        setContentView(R.layout.activity_main)

        World.init(this)
        CountryLocation.init()

        val pref = getSharedPreferences("PREF",0)
        var isInitialized = pref.getBoolean("init", false)

        isInitialized = false

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = CountriesAdapter(this, countriesList)
        recyclerView.adapter = adapter


        if (!isInitialized){
            Initializer.initializeDatabase(this)
            val editor = pref.edit()
            editor.putBoolean("init", true)
            editor.commit()
        }

        paysDatabase =
            PaysDatabase.getDatabase(this)

        getPays()


        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { mapboxMap ->
            this.mapboxMap = mapboxMap
            mapboxMap.setStyle(
                Style.Builder().fromUri("mapbox://styles/ali31/ckbgmjxbh4xzd1io15jrj4m0z")
            ) {

                // Custom map style has been loaded and map is now ready
                Log.i("Succes", "Map loaded Succefully")

                mapStyleLoaded = true

                try {
                    it.addSource(
                        GeoJsonSource(
                            "countriesSrc",
                            URI("asset://countries2.geojson")
                        )
                    )
                } catch (exception: URISyntaxException) {
                    Log.d("exception", exception.reason)
                }

            }
        }

    }

    fun selectCountry(countryCode : String){
        if (mapStyleLoaded && mapboxMap.style != null){
            mapboxMap.style!!.removeLayer("countriesLayer")

            val depthPolygonFillLayer = FillLayer("countriesLayer", "countriesSrc")

            depthPolygonFillLayer.withProperties(fillColor(rgb(102,178,255)))

            mapboxMap.style!!.addLayerAt(depthPolygonFillLayer, mapboxMap.style!!.layers.size-2)

            depthPolygonFillLayer.setFilter(
                eq(
                    id(),
                    literal(countryCode)
                )
            )


            val latLng = CountryLocation.getLatLng(countryCode)
            if (latLng != null){
                val position = CameraPosition.Builder()
                    .target(LatLng(latLng.latitude, latLng.longitude))
                    .zoom(3.0)
                    .tilt(20.0)
                    .build()

                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position))
            }

        }

    }

    fun getPays(){
        AppExecutors.instance!!.diskIO().execute {
            countriesList.clear()
            val resultList = paysDatabase.paysDao().loadAllPays()
            countriesList.addAll(resultList)
            adapter.notifyDataSetChanged()
        }
    }


    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }


}
