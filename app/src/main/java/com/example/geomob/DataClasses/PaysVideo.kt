package com.example.geomob.DataClasses


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.geomob.DataClasses.Pays

@Entity(tableName = "PaysVideo",
    foreignKeys = [ForeignKey(
        entity = Pays::class,
        parentColumns = arrayOf("paysId"),
        childColumns = arrayOf("paysId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class PaysVideo (@PrimaryKey(autoGenerate = true) val idPaysVideo: Long?,
                      val resId : Int,
                      val paysId : Long) {}