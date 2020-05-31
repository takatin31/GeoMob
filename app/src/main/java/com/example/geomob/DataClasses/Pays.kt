package com.example.geomob.DataClasses

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "Pays")

data class Pays (@PrimaryKey(autoGenerate = true) var paysId : Long?,
                 val nomPays : String,
                 val codePays : String,
                 val capital : String,
                 var descriptionPays : String,
                 var surface : Float,
                 var population : Long,
                 var hymne : Int){}