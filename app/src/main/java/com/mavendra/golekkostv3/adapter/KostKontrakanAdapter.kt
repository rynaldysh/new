package com.mavendra.golekkostv3.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mavendra.golekkostv3.R
import com.mavendra.golekkostv3.activity.DetailKostKontrakanActivity
import com.mavendra.golekkostv3.app.Constants.KOSTKONTRAKAN_URL
import com.mavendra.golekkostv3.model.KostKontrakan
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class KostKontrakanAdapter(var activity: Activity, var data:ArrayList<KostKontrakan>):RecyclerView.Adapter<KostKontrakanAdapter.Holder>(){

    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvName = view.findViewById<TextView>(R.id.tvNameKost)
        val tvHarga = view.findViewById<TextView>(R.id.tvHargaKost)
        val tvLokasi = view.findViewById<TextView>(R.id.tvLokasiKost)
        val tvMayoritas = view.findViewById<TextView>(R.id.tvMayoritas)
        val ivKost = view.findViewById<ImageView>(R.id.ivKost)
        val layout = view.findViewById<CardView>(R.id.layoutKos)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_kostkontrakan, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvName.text = data[position].name
        holder.tvHarga.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(data[position].harga))
        holder.tvLokasi.text = data[position].lokasi
        holder.tvMayoritas.text = data[position].mayoritas
        //holder.ivKost.setImageResource(data[position].image)
        val image =  KOSTKONTRAKAN_URL + data[position].image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.beranda_ex_kostt)
            .error(R.drawable.beranda_ex_kostt)
            .into(holder.ivKost)

        holder.layout.setOnClickListener {
            val kostKontrakan = Intent(activity, DetailKostKontrakanActivity::class.java)
            val str = Gson().toJson(data[position], KostKontrakan::class.java)
            kostKontrakan.putExtra("kostkontrakan", str)
            activity.startActivity(kostKontrakan)
        }
    }
}