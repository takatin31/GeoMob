package com.example.geomob.Activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.geomob.Threads.AppExecutors
import com.example.geomob.DataClasses.Pays
import com.example.geomob.Database.PaysDatabase
import com.example.geomob.R

class MainActivity : AppCompatActivity() {

    private lateinit var paysDatabase : PaysDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        paysDatabase =
            PaysDatabase.getDatabase(this)
        val pays = Pays(
            1,
            "Algeria",
            "DZ",
            "Algiers",
            "Great",
            100f,
            4500,
            1
        )

        //addPays(pays)
        //addRessource()
        getPays()

    }

    fun getPays(){
        AppExecutors.instance!!.diskIO().execute {
            val list = paysDatabase.paysDao().loadAllPays()
            Log.i("list", list.toString())
        }
    }

    /*fun deletePays(pays: Pays){
        AppExecutors.instance!!.diskIO().execute {
            paysDatabase.paysDao().deletePays(pays)
        }

    }

    fun getRessources(){
        AppExecutors.instance!!.diskIO().execute {
            val list = paysDatabase.paysDao().loadAllRessources()
            Log.i("listRes", list.toString())
        }
    }

    fun addPays(pays: Pays){
        AppExecutors.instance!!.diskIO().execute {
            paysDatabase.paysDao().addPays(pays)
        }
    }

    fun addRessource(){
        AppExecutors.instance!!.diskIO().execute {
            val res = Ressource(null, "Me", "Later", 1)
            paysDatabase.paysDao().addRessource(res)
        }
    }

    fun addPersonalite(){
        AppExecutors.instance!!.diskIO().execute {

        }
    }*/
}
