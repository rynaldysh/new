package com.mavendra.golekkostv3.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.mavendra.golekkostv3.R
import com.mavendra.golekkostv3.app.Constants.KOSTKONTRAKAN_URL
import com.mavendra.golekkostv3.helper.Helper
import com.mavendra.golekkostv3.model.KostKontrakan
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_kostkontrakan.*
import kotlinx.android.synthetic.main.activity_detail_kostkontrakan.ivgambar
import kotlinx.android.synthetic.main.activity_detail_kostkontrakan.tvDeskripsi
import kotlinx.android.synthetic.main.activity_detail_kostkontrakan.tvLokasi
import kotlinx.android.synthetic.main.toolbar_biasa.*

class DetailKostKontrakanActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kostkontrakan)
        getInfo()
        /*mainButton()*/
    }

    private fun getInfo() {
        val data = intent.getStringExtra("kostkontrakan")
        val kostKontrakan = Gson().fromJson<KostKontrakan>(data, KostKontrakan::class.java)

        //set Value
        tvNamaKost.text = kostKontrakan.name
        tvNamaPengelola.text = kostKontrakan.pengelola
        tvHargaKost.text = Helper().gantiRupiah(kostKontrakan.harga)
        tvLokasi.text = kostKontrakan.lokasi
        tvSisaKamar.text = kostKontrakan.sisakamar
        tvTotalKamar.text = kostKontrakan.totalkamar
        tvMayoritas.text = kostKontrakan.mayoritas
        tvDeskripsi.text = kostKontrakan.deskripsi
        tvListrik.text = kostKontrakan.listrik
        tvAir.text = kostKontrakan.air
        tvWifi.text = kostKontrakan.wifi
        tvBed.text = kostKontrakan.bed
        tvAc.text = kostKontrakan.ac
        tvKamarMandi.text = kostKontrakan.kamarmandi
        tvKloset.text = kostKontrakan.kloset
        tvSatpam.text = kostKontrakan.satpam


        val image =  KOSTKONTRAKAN_URL + kostKontrakan.image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.beranda_ex_kostt)
            .error(R.drawable.beranda_ex_kostt)
            .into(ivgambar)

        Helper().setToolbar(this, toolbarBiasa, kostKontrakan.name)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}
