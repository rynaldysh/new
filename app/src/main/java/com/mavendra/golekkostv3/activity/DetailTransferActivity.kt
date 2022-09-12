package com.mavendra.golekkostv3.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mavendra.golekkostv3.R
import com.mavendra.golekkostv3.adapter.BarangTransaksiAdapter
import com.mavendra.golekkostv3.adapter.RiwayatAdapter
import com.mavendra.golekkostv3.app.ApiConfig
import com.mavendra.golekkostv3.helper.Helper
import com.mavendra.golekkostv3.model.DetailTransaksi
import com.mavendra.golekkostv3.model.ResponModel
import com.mavendra.golekkostv3.model.Transaksi
import kotlinx.android.synthetic.main.activity_detail_transfer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.toolbar_beranda.*
import kotlinx.android.synthetic.main.toolbar_biasa.*


class DetailTransferActivity : AppCompatActivity() {

    var transaksi = Transaksi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transfer)

        Helper().setToolbar(this, toolbarBiasa, "Riwayat Belanja")

        val json = intent.getStringExtra("transaksi")
        transaksi = Gson().fromJson(json, Transaksi::class.java)

        setData(transaksi)
        displayDetailTransfer(transaksi.details)
        mainButton()
    }

    fun mainButton(){
        btBatalDetailTransfer.setOnClickListener {
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Apakah anda yakin?")
                .setContentText("Transaksi akan dibatalkan dan tidak bisa dikembalikan!")
                .setConfirmText("Ya, batalkan!")
                .setConfirmClickListener {
                        it.dismissWithAnimation()
                        batalCheckout()
                }
                .setCancelText("Tutup")
                .setCancelClickListener {
                    it.dismissWithAnimation()
                }.show()
        }
    }

    fun batalCheckout(){
        val loading = SweetAlertDialog(this@DetailTransferActivity, SweetAlertDialog.PROGRESS_TYPE)
        loading.setTitleText("Memuat...").show()
        ApiConfig.instanceRetrofit.batalCheckout(transaksi.id).enqueue(object :
            Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                loading.dismiss()
                error(t.message.toString())
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                loading.dismiss()
                val res = response.body()!!
                if (res.success == 1){

                    SweetAlertDialog(this@DetailTransferActivity, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Berhasil")
                        .setContentText("Transaksi berhasil dibatalkan")
                        .setConfirmClickListener {
                            it.dismissWithAnimation()
                            onBackPressed()
                        }.show()

                /*
                    Toast.makeText(this@DetailTransferActivity, "Transfer berhasil dibatalkan", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                    *//*displayRiwayat(res.transaksis)*/
                } else {
                    error(res.message)
                }
            }
        })
    }

    fun error(pesan: String){
        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Maaf")
            .setContentText(pesan)
            .show()
    }

    @SuppressLint("SetTextI18n")
    fun setData(transaksi: Transaksi) {

        val newFormat = "dd MMMM yyyy, kk:mm:ss"
        tvTanggalDetailTransfer.text = Helper().convertDate(transaksi.created_att, newFormat)
        /*tvTanggalDetailTransfer.text = transaksi.created_att*/

        tvStatusDetailTransfer.text = transaksi.status
        tvPenerimaDetailTransfer.text = transaksi.name + " - " + transaksi.phone
        tvAlamatDetailTransfer.text = transaksi.detail_lokasi
        tvKodeUnikDetailTransfer.text = transaksi.kode_unik
        tvTotalBelanjaDetailTransfer.text = Helper().gantiRupiah(transaksi.total_harga)
        tvBiayaKirimDetailTransfer.text = Helper().gantiRupiah(transaksi.ongkir)
        tvTotalDetailTransfer.text = Helper().gantiRupiah(transaksi.total_transfer)

        if(transaksi.status != "MENUNGGU") llBawah.visibility = View.GONE

        var color = getColor(R.color.menunggu)
        if (transaksi.status == "SELESAI") color = getColor(R.color.selesai)
        else if (transaksi.status == "BATAL") color = getColor(R.color.batal)

        tvStatusDetailTransfer.setTextColor(color)
    }

    fun displayDetailTransfer(transaksis: ArrayList<DetailTransaksi>) {

        val layoutManagerRiwayat = LinearLayoutManager(this)
        layoutManagerRiwayat.orientation = LinearLayoutManager.VERTICAL

        rvBarangDetailTransfer.adapter = BarangTransaksiAdapter(transaksis)
        rvBarangDetailTransfer.layoutManager = layoutManagerRiwayat


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}