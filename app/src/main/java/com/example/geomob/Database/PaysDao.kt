package com.example.geomob.Database

import androidx.room.*
import com.example.geomob.DataClasses.*

@Dao
interface PaysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPays(pays: Pays)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRessource(ressource: Ressource)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEvenement(evenement: Evenement)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPaysPhoto(paysPhoto: PaysPhoto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPaysVideo(paysVideo: PaysVideo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPersonalite(personalite: Personalite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPersonalitePhoto(personalitePhoto: PersonalitePhoto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTweet(tweet: Tweet)

    @Update
    fun modifyPays(pays: Pays)

    @Update
    fun modifyRessource(ressource: Ressource)

    @Update
    fun modifyEvenement(evenement: Evenement)

    @Update
    fun modifyPaysPhoto(paysPhoto: PaysPhoto)

    @Update
    fun modifyPaysVideo(paysVideo: PaysVideo)

    @Update
    fun modifyPersonalite(personalite: Personalite)

    @Update
    fun modifyPersonalitePhoto(personalitePhoto: PersonalitePhoto)

    @Update
    fun modifyTweet(tweet: Tweet)

    @Delete
    fun deletePays(pays: Pays)

    @Delete
    fun deleteRessource(ressource: Ressource)

    @Delete
    fun deleteEvenement(evenement: Evenement)

    @Delete
    fun deletePaysPhoto(paysPhoto: PaysPhoto)

    @Delete
    fun deletePaysVideo(paysVideo: PaysVideo)

    @Delete
    fun deletePersonalite(personalite: Personalite)

    @Delete
    fun deletePersonalitePhoto(personalitePhoto: PersonalitePhoto)

    @Delete
    fun deleteTweet(tweet: Tweet)

    @Query("Select * from Pays")
    fun loadAllPays(): List<Pays>

    @Query("Select * from Pays Where paysId = :id")
    fun findPaysById(id : Long): List<Pays>

    @Query("Select * from Ressource")
    fun loadAllRessources(): List<Ressource>

    @Query("Select * from Ressource where paysId = :paysId")
    fun loadAllRessourcesByPaysId(paysId : Long): List<Ressource>

    @Query("Select * from Personlaite where paysId = :paysId")
    fun loadAllPersonalitesByPaysId(paysId : Long): List<Personalite>

    @Query("Select * from Tweet where paysId = :paysId")
    fun loadAllTweetsByPaysId(paysId : Long): List<Tweet>

    @Query("Select * from PaysPhoto where paysId = :paysId")
    fun loadAllPaysPhotoByPaysId(paysId : Long): List<PaysPhoto>

    @Query("Select * from PaysVideo where paysId = :paysId")
    fun loadAllPaysVideoByPaysId(paysId : Long): List<PaysVideo>

    @Query("Select * from Evenement where paysId = :paysId")
    fun loadAllEvenementByPaysId(paysId : Long): List<Evenement>

    @Query("Select * from PersonalitePhoto where idPersonalite = :personaliteId")
    fun loadAllPersonalitesPhotoByPaysId(personaliteId : Long): List<PersonalitePhoto>
}