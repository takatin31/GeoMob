package com.example.geomob.Activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blongho.country_data.World
import com.example.geomob.Adapters.CountriesAdapter
import com.example.geomob.DataClasses.Initializer
import com.example.geomob.DataClasses.Pays
import com.example.geomob.Threads.AppExecutors
import com.example.geomob.Database.PaysDatabase
import com.example.geomob.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var paysDatabase : PaysDatabase
    lateinit var adapter: CountriesAdapter
    lateinit var layoutManager : LinearLayoutManager
    var countriesList = mutableListOf<Pays>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        World.init(this)

        val pref = getSharedPreferences("PREF",0)
        val isInitialized = pref.getBoolean("init", false)

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

    }

    fun getPays(){
        AppExecutors.instance!!.diskIO().execute {
            countriesList.clear()
            val resultList = paysDatabase.paysDao().loadAllPays()
            countriesList.addAll(resultList)
            adapter.notifyDataSetChanged()
        }
    }

    /*

    fun getRessources(){
        AppExecutors.instance!!.diskIO().execute {
            val list = paysDatabase.paysDao().loadAllRessources()
            Log.i("listRes", list.toString())
        }
    }

    */
}
