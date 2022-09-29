package com.mavendra.golekkostv3.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alamatbarang") // the name of tabel
// the name of tabel
class AlamatBarang {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTb")
    var idTb = 0

    var id = 0
    var name = ""
    var phone = ""
    var deskripsi = ""
    var lokasi = ""
    var harga = ""

    var isSelected = true


}