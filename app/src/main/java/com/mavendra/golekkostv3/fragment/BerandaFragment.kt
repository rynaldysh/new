package com.mavendra.golekkostv3.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mavendra.golekkostv3.R
import com.mavendra.golekkostv3.activity.LoginActivity
import com.mavendra.golekkostv3.adapter.BarangAdapter
import com.mavendra.golekkostv3.adapter.JasaAngkutAdapter
import com.mavendra.golekkostv3.adapter.KostKontrakanAdapter
import com.mavendra.golekkostv3.app.ApiConfig
import com.mavendra.golekkostv3.helper.SharedPref
import com.mavendra.golekkostv3.model.Barang
import com.mavendra.golekkostv3.model.Jasaangkut
import com.mavendra.golekkostv3.model.KostKontrakan
import com.mavendra.golekkostv3.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class BerandaFragment : Fragment() {

    lateinit var rvKostKontrakan: RecyclerView
    lateinit var rvJasaAngkut: RecyclerView
    lateinit var rvBarangJualan: RecyclerView
    lateinit var s :SharedPref
    lateinit var tvUsername :TextView
    lateinit var tvInisial :TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_beranda, container, false)

        initKostKontrakan(view)
        initJasaAngkut(view)
        initBarangJualan(view)
        /*initToolbarProfil(view)*/

        getKostKontrakan()
        getJasaAngkut()
        getBarang()

        s = SharedPref(requireActivity())
        prefBeranda()

        return view
    }



    /*private fun initToolbarProfil(view: View){
        tvUsername = view.findViewById(R.id.tv_username)
        tvInisial = view.findViewById(R.id.tvInisial)

    }*/

    private fun prefBeranda(){
        if (s.getUser() == null){
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            return
        }


    }

    private var listBarang :ArrayList<Barang> = ArrayList()
    fun getBarang(){
        ApiConfig.instanceRetrofit.getBarang().enqueue(object :
            Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1){
                    listBarang = res.barangs
                    displayBarang()
                }
            }
        })

    }

    private var listKostKontrakan :ArrayList<KostKontrakan> = ArrayList()
    fun getKostKontrakan(){
        ApiConfig.instanceRetrofit.getKostKontrakan().enqueue(object :
            Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1){
                    listKostKontrakan = res.kostkontrakans
                    displayKostKontrakan()
                }
            }
        })

    }

    private var listJasaangkut :ArrayList<Jasaangkut> = ArrayList()
    fun getJasaAngkut(){
        ApiConfig.instanceRetrofit.getJasaAngkut().enqueue(object :
            Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1){
                    listJasaangkut = res.jasaangkuts
                    displayJasaAngkut()
                }
            }
        })

    }

    fun displayBarang(){
        val layoutManagerBarang = LinearLayoutManager(activity)
        layoutManagerBarang.orientation = LinearLayoutManager.HORIZONTAL

        rvBarangJualan.adapter = BarangAdapter(requireActivity(), listBarang)
        rvBarangJualan.layoutManager = layoutManagerBarang

    }

    fun displayKostKontrakan(){
        val layoutManagerKost = LinearLayoutManager(activity)
        layoutManagerKost.orientation = LinearLayoutManager.HORIZONTAL

        rvKostKontrakan.adapter = KostKontrakanAdapter(requireActivity(), listKostKontrakan)
        rvKostKontrakan.layoutManager = layoutManagerKost

    }

    fun displayJasaAngkut(){
        val layoutManagerJasaAngkut = LinearLayoutManager(activity)
        layoutManagerJasaAngkut.orientation = LinearLayoutManager.VERTICAL

        rvJasaAngkut.adapter = JasaAngkutAdapter(requireActivity(),listJasaangkut)
        rvJasaAngkut.layoutManager = layoutManagerJasaAngkut

    }

    fun initBarangJualan(view: View){
        rvBarangJualan = view.findViewById(R.id.rvBarangJualan)
    }

    fun initKostKontrakan(view: View){
        rvKostKontrakan = view.findViewById(R.id.rvKostKontrakan)
    }

    fun initJasaAngkut(view: View){
        rvJasaAngkut = view.findViewById(R.id.rvJasaAngkut)
    }


}