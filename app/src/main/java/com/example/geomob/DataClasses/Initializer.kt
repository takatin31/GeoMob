package com.example.geomob.DataClasses

import android.content.Context
import com.example.geomob.Database.PaysDatabase
import com.example.geomob.R
import com.example.geomob.Threads.AppExecutors

class Initializer{
    companion object{
        private lateinit var paysDatabase : PaysDatabase

        fun initializeDatabase(context : Context){
            paysDatabase = PaysDatabase.getDatabase(context)

            val dz = Pays("DZ","Algeria", "Algiers", "", 2381741, 42972878, R.raw.dz, false)
            val eg = Pays("EG","Egypt", "Cairo", "", 1010408, 100075480, R.raw.eg, false)
            val sa = Pays("SA", "Saudi Arabia", "Riyadh", "", 2149690, 34218169, R.raw.sa, false)
            val jp = Pays("JP", "Japan", "Tokyo", "", 377975, 126150000, R.raw.jp, false)
            val my = Pays("MY", "Malaysia", "Kuala Lumpur", "", 330803, 32730000, R.raw.my, false)

            val res1 = Ressource(null, "Oil", "Algeria is one of the biggest oil producers in the world.", "DZ")
            val res2 = Ressource(null, "Natural gas", "Algeria has abundant natural gas reserves.", "DZ")
            val res3 = Ressource(null, "Helium", "Algeria has approximately 21% of the world’s helium deposits, and is the second largest producer of helium, after the United States.", "DZ")
            val res4 = Ressource(null, "Gold", "Algeria has many gold deposits within its borders.", "DZ")

            val res5 = Ressource(null, "Petroleum", " The search for petroleum began earlier in Egypt than elsewhere in the Middle East", "EG")
            val res6 = Ressource(null, "Natural gas", "A joint Egyptian-Italian gas discovery was made in the north delta near Abū Māḍī in 1970", "EG")
            val res7 = Ressource(null, "Iron ore", "ron ore is extracted from deposits at Aswān, and development work has continued at Al-Baḥriyyah Oasis. ", "EG")
            val res8 = Ressource(null, "Phosphates", "Several of Egypt’s major known phosphate deposits are mined at Isnā, Ḥamrāwayn, and Safājah.", "EG")

            val res9 = Ressource(null, "Metals", "MGBM currently operates five gold mines in Saudi Arabia and since 1988 has produced more than 4 million ounces of gold.", "SA")
            val res10 = Ressource(null, "Fossil Fuels", "The country is a leading producer of oil and natural gas, and is ranked eighth in terms of petroleum refining capacity in the world.", "SA")
            val res11 = Ressource(null, "Investment", "According to experts, Saudi Arabia will continue to be the most significant supplier of petroleum to meet the ever-increasing global demand. ", "SA")

            val res12 = Ressource(null, "Minerals", "With few exceptions, Japan’s mineral reserves are small, and the quality of those mined is often poor.", "JP")
            val res13 = Ressource(null, "Mining and quarrying", "Mining for iron and copper essentially ceased after 2000, and Japan now imports virtually all its needs for those two ores.", "JP")
            val res14 = Ressource(null, "Power", "The largest single source of energy is oil;", "JP")
            val res15 = Ressource(null, "The most notable feature of Japan’s economic growth since World War II is the rapid development of manufacturing", "", "JP")

            val res16 = Ressource(null, "Petroleum", "Petroleum products such as crude oil are the most valuable natural resources of the country. ", "MY")
            val res17 = Ressource(null, "Precious Minerals", "Malaysia also has a number of mining sites that mine minerals like gold, copper, tin, silica sand, bauxite, and limestone.", "MY")
            val res18 = Ressource(null, "Agriculture and Forestry", "Agriculture is a major sector in Malaysia since it accounts for around 12% of the national GDP. ", "MY")

            val paysList = arrayListOf(dz, eg, sa, jp, my)

            val resourcesList = arrayListOf(res1, res2, res3, res4, res5, res6, res7, res7, res8, res9, res10,
                                            res11, res12, res13, res14, res15, res16, res17, res18, res18)
            for (pays in paysList){
                addPays(pays)
            }

            for (resource in resourcesList){
                addRessource(resource)
            }
        }

        private fun addPays(pays: Pays){
            AppExecutors.instance!!.diskIO().execute {
                paysDatabase.paysDao().addPays(pays)
            }
        }

        private fun addRessource(ress : Ressource){
            AppExecutors.instance!!.diskIO().execute {
                paysDatabase.paysDao().addRessource(ress)
            }
        }

        private fun addPersonalite(pers : Personalite){
            AppExecutors.instance!!.diskIO().execute {
                paysDatabase.paysDao().addPersonalite(pers)
            }
        }

        private fun addPaysPhoto(paysPhoto : PaysPhoto){
            AppExecutors.instance!!.diskIO().execute {
                paysDatabase.paysDao().addPaysPhoto(paysPhoto)
            }
        }

        private fun addPaysVideo(paysVideo : PaysVideo){
            AppExecutors.instance!!.diskIO().execute {
                paysDatabase.paysDao().addPaysVideo(paysVideo)
            }
        }

        private fun addPersonalitePhoto(personalitePhoto : PersonalitePhoto){
            AppExecutors.instance!!.diskIO().execute {
                paysDatabase.paysDao().addPersonalitePhoto(personalitePhoto)
            }
        }

        private fun addTweet(tweet : Tweet){
            AppExecutors.instance!!.diskIO().execute {
                paysDatabase.paysDao().addTweet(tweet)
            }
        }
    }
}