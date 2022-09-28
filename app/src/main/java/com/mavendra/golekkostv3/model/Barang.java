package com.mavendra.golekkostv3.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "keranjang") // the name of tabel
public class Barang implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTb")
    public int idTb;

    public int id;
    public String name;
    public String nama_pemilik;
    public String harga;
    public String lokasi;
    public String deskripsi;
    public String image;
    public String created_at;
    public String updated_at;

    public  int jumlah = 1;
    public  boolean selected = true;

}
