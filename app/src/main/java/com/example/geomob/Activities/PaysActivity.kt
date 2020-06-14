package com.example.geomob.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geomob.Adapters.CountryPagesAdapter
import com.example.geomob.R
import kotlinx.android.synthetic.main.activity_pays.*

class PaysActivity : AppCompatActivity() {

    private val NUM_PAGES = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pays)




        val pagerAdapter = CountryPagesAdapter(this,supportFragmentManager, NUM_PAGES)
        mPager.adapter = pagerAdapter

        tabLayout.setupWithViewPager(mPager)
    }
}
