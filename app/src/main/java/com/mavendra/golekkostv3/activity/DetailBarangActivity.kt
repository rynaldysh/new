package com.mavendra.golekkostv3.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import com.mavendra.golekkostv3.R
import com.mavendra.golekkostv3.app.Constants.barang_url
import com.mavendra.golekkostv3.helper.Helper
import com.mavendra.golekkostv3.model.Barang
import com.mavendra.golekkostv3.room.MyDatabase
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_barang.*
import kotlinx.android.synthetic.main.toolbar_custom_bottom_barang_detail.*
import kotlinx.android.synthetic.main.toolbar_custom_keranjang_detail.*

class DetailBarangActivity : AppCompatActivity() {

    lateinit var barang: Barang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_barang)

        getInfo()
        mainButton()
        checkKeranjang()
        Helper().setToolbar(this, toolbarKeranjangAtas, barang.name)

    }

    private fun mainButton() {
        ivKeranjang.setOnClickListener {
            val mydDb: MyDatabase = MyDatabase.getInstance(this)!!
            val data = mydDb.daoKeranjang().getBarang(barang.id)
            if (data == null){
                insert()
            } else {
                barang.jumlah =+ 1
                update(data)
            }

        }

        rlKeranjang.setOnClickListener {
            val intent = Intent("event:keranjang")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            onBackPressed()
        }
    }

    fun insert(){
        val mydDb: MyDatabase = MyDatabase.getInstance(this)!!
        CompositeDisposable().add(Observable.fromCallable { mydDb.daoKeranjang().insert(barang) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respon", "data masuk")
                Toast.makeText(this, "Berhasil menambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            })
    }

    fun update(data: Barang){
        val mydDb: MyDatabase = MyDatabase.getInstance(this)!!
        CompositeDisposable().add(Observable.fromCallable { mydDb.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respon", "data masuk")
                Toast.makeText(this, "Berhasil menambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            })
    }

    private fun checkKeranjang(){
        val myDb: MyDatabase = MyDatabase.getInstance(this)!!
        val dataKeranjang = myDb.daoKeranjang().getAll()
        if(dataKeranjang.isNotEmpty()){
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataKeranjang.size.toString()

        } else {
            div_angka.visibility = View.GONE
        }
    }

    private fun getInfo() {
        val data = intent.getStringExtra("barang")
        barang = Gson().fromJson<Barang>(data, Barang::class.java)

        //set Value
        tvNamaBarang.text = barang.name
        tvNamaPemilik.text = barang.nama_pemilik
        tvHargaBarang.text = Helper().gantiRupiah(barang.harga)
        tvLokasiBarang.text = barang.lokasi
        tvDeskripsiBarang.text = barang.deskripsi


        val image =  barang_url + barang.image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.beranda_ex_kostt)
            .error(R.drawable.beranda_ex_kostt)
            .into(ivgambarBarang)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}
