package com.mavendra.golekkostv3.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.drjacky.imagepicker.ImagePicker
import com.google.gson.Gson
import com.inyongtisto.myhelper.base.BaseActivity
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showSuccessDialog
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toMultipartBody
import com.mavendra.golekkostv3.R
import com.mavendra.golekkostv3.adapter.BarangTransaksiAdapter
import com.mavendra.golekkostv3.app.ApiConfig
import com.mavendra.golekkostv3.helper.Helper
import com.mavendra.golekkostv3.model.Barang
import com.mavendra.golekkostv3.model.DetailTransaksi
import com.mavendra.golekkostv3.model.ResponModel
import com.mavendra.golekkostv3.model.Transaksi
import kotlinx.android.synthetic.main.activity_detail_transfer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_transfer.btBayarDetailTransfer
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_upload_foto_barang.*
import kotlinx.android.synthetic.main.toolbar_beranda.*
import kotlinx.android.synthetic.main.toolbar_biasa.*
import java.io.File


class UploadFotoBarangActivity : BaseActivity() {

    var barang = Barang()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_foto_barang)

        Helper().setToolbar(this, toolbarBiasa, "Upload Foto")

        val json = intent.getStringExtra("barangpush")
        barang = Gson().fromJson(json, Barang::class.java)

        mainButton()
    }

    fun mainButton(){
        btUploadGambarBarang.setOnClickListener {
            imagePick()
        }
    }

    private var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val uri = it.data?.data!!
            Log.d("TAG", "URI IMAGE: "+uri)
            val fileUri: Uri = uri
            dialodUpload(File(fileUri.path))
        }
    }

    var alertDialog : AlertDialog? = null

    @SuppressLint("inflateParams")
    private fun dialodUpload(file: File){

        val view = layoutInflater
        val layout = view.inflate(R.layout.view_upload_gambar_barang, null)

        val imageView : ImageView = layout.findViewById(R.id.imageBarang)
        val btUpload : Button = layout.findViewById(R.id.btUploadBarang)
        val btTake : Button = layout.findViewById(R.id.btAmbilGambarBarang)

        Picasso.get()
            .load(file)
            .into(imageView)

        btUpload.setOnClickListener {
            upload(file)
        }

        btTake.setOnClickListener {
            imagePick()
        }

        alertDialog = AlertDialog.Builder(this).create()
        alertDialog!!.setView(layout)
        alertDialog!!.setCancelable(true)
        alertDialog!!.show()

    }

    private fun imagePick(){
        ImagePicker.with(this)
            .crop()
            .maxResultSize(512,512)
            .createIntentFromDialog { launcher.launch(it) }
    }

    private fun upload(file: File){

        val json = intent.getStringExtra("barangpush")
        barang = Gson().fromJson(json, Barang::class.java)
        val fileImage = file.toMultipartBody()

        progress.show()
        ApiConfig.instanceRetrofit.uploadBarangFoto(barang.id, fileImage!!).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                progress.dismiss()
                showErrorDialog(t.message!!)
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                progress.dismiss()
                if(response.isSuccessful){
                        if (response.body()!!.success == 1){
                            showSuccessDialog("berhasil"){
                                alertDialog!!.dismiss()
                                onBackPressed()
                            }

                        } else {
                            showErrorDialog(response.body()!!.message)
                        }

                } else {
                    showErrorDialog(response.message())
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}