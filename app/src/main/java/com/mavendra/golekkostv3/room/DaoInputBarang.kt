package com.mavendra.golekkostv3.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.mavendra.golekkostv3.model.AlamatBarang
import com.mavendra.golekkostv3.model.AlamatPesanJasa
import com.mavendra.golekkostv3.model.AlamatPesanKostKontrakan

@Dao
interface DaoInputBarang {

    @Insert(onConflict = REPLACE)
    fun insert(data: AlamatBarang)

    @Delete
    fun delete(data: AlamatBarang)

    @Update
    fun update(data: AlamatBarang): Int

    @Query("SELECT * from alamatbarang ORDER BY id ASC")
    fun getAll(): List<AlamatBarang>

    @Query("SELECT * FROM alamatbarang WHERE id = :id LIMIT 1")
    fun getBarang(id: Int): AlamatBarang

    @Query("SELECT * FROM alamatbarang WHERE isSelected = :status LIMIT 1")
    fun getByStatus(status: Boolean): AlamatBarang?

    @Query("DELETE FROM alamatbarang")
    fun deleteAll(): Int
}