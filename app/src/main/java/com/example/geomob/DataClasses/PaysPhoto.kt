package com.example.geomob.DataClasses


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.geomob.DataClasses.Pays

@Entity(tableName = "PaysPhoto",
    foreignKeys = [ForeignKey(
        entity = Pays::class,
        parentColumns = arrayOf("paysId"),
        childColumns = arrayOf("paysId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class PaysPhoto (@PrimaryKey(autoGenerate = true) val idPaysPhoto: Long?,
                        val resId : Int,
                        val paysId : Long) {}