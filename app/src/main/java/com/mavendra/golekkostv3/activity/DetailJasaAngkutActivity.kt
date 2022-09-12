package com.mavendra.golekkostv3.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.mavendra.golekkostv3.R
import com.mavendra.golekkostv3.app.Constants.JASAANGKUT_URL
import com.mavendra.golekkostv3.helper.Helper
import com.mavendra.golekkostv3.model.Jasaangkut
import com.mavendra.golekkostv3.room.MyDatabase
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_jasaangkut.*
import kotlinx.android.synthetic.main.activity_detail_jasaangkut.ivgambar
import kotlinx.android.synthetic.main.activity_detail_jasaangkut.tvDeskripsi
import kotlinx.android.synthetic.main.activity_detail_jasaangkut.tvLokasi
import kotlinx.android.synthetic.main.toolbar_custom_bottom_jasa_detail.*
import kotlinx.android.synthetic.main.toolbar_custom_keranjang_detail.*
import kotlinx.android.synthetic.main.toolbar_custom_top_jasa_detail.*

class DetailJasaAngkutActivity : AppCompatActivity() {

    lateinit var jasaangkut: Jasaangkut

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_jasaangkut)

        getInfo()
        mainButton()

    }

    private fun mainButton() {
        ivSaveJasa.setOnClickListener {
            insert()
        }
    }

    private fun insert(){
        val mydDb: MyDatabase = MyDatabase.getInstance(this)!!
        CompositeDisposable().add(Observable.fromCallable { mydDb.daoSimpanJasa().insert(jasaangkut) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respon", "data masuk")
                Toast.makeText(this, "Berhasil menambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            })
    }


    private fun getInfo() {
        val data = intent.getStringExtra("jasaangkut")
        jasaangkut = Gson().fromJson<Jasaangkut>(data, Jasaangkut::class.java)

        //set Value
        tvNamaJasa.text = jasaangkut.name
        tvHargaJasa.text = Helper().gantiRupiah(jasaangkut.harga)
        tvLokasi.text = jasaangkut.lokasi
        tvDeskripsi.text = jasaangkut.deskripsi

        val image = JASAANGKUT_URL + jasaangkut.image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.beranda_ex_kostt)
            .error(R.drawable.beranda_ex_kostt)
            .into(ivgambar)

        Helper().setToolbar(this, toolbarDisimpanAtas, jasaangkut.name)

    }

    private fun checkKeranjang(){
        val myDb: MyDatabase = MyDatabase.getInstance(this)!!
        val dataSimpanJasa = myDb.daoSimpanJasa().getAll()
        if(dataSimpanJasa.isNotEmpty()){
            div_angkaSimpanJasa.visibility = View.VISIBLE
            tv_angkaSimpanJasa.text = dataSimpanJasa.size.toString()

        } else {
            div_angkaSimpanJasa.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
