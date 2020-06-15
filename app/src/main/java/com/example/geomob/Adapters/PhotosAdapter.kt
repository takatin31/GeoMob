package com.example.geomob.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blongho.country_data.World
import com.example.geomob.Activities.MainActivity
import com.example.geomob.Activities.PaysActivity
import com.example.geomob.DataClasses.Pays
import com.example.geomob.DataClasses.PaysPhoto
import com.example.geomob.Fragments.MainFragment
import com.example.geomob.Fragments.TweetsFragment
import com.example.geomob.Fragments.VideosFragment
import com.example.geomob.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class PhotosAdapter(val activity : PaysActivity, val list : ArrayList<PaysPhoto>) : RecyclerView.Adapter<PhotosAdapter.PaysPhotoViewHolder>(){
    class PaysPhotoViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val paysPhotoImage = v.findViewById<ImageView>(R.id.paysPhotoView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaysPhotoViewHolder {
        return PaysPhotoViewHolder(LayoutInflater.from(activity).inflate(R.layout.pays_photo_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PaysPhotoViewHolder, position: Int) {
        val photo = list[position]
        Picasso.get().load(photo.urlPhoto).into(holder.paysPhotoImage)
    }
}