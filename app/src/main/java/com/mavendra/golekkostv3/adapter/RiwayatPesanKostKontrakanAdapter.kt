package com.mavendra.golekkostv3.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mavendra.golekkostv3.R
import com.mavendra.golekkostv3.helper.Helper
import com.mavendra.golekkostv3.model.PesanKostkontrakan
import kotlin.collections.ArrayList

class RiwayatPesanKostKontrakanAdapter(var data:ArrayList<PesanKostkontrakan>, var listener: Listeners):RecyclerView.Adapter<RiwayatPesanKostKontrakanAdapter.Holder>(){

    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvName = view.findViewById<TextView>(R.id.tvNamaRiwayatPesanKostKontrakan)
        val tvTanggal = view.findViewById<TextView>(R.id.tvTanggalRiwayatPesanKostKontrakan)
        val tvStatus = view.findViewById<TextView>(R.id.tvStatusRiwayatPesanKostKontrakan)
        /*val tvDetail = view.findViewById<TextView>(R.id.tvDetailRiwayat)*/
        val layout = view.findViewById<CardView>(R.id.layoutRiwayatPesanKostKontrakan)

    }

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat_pesan_kost_kontrakan, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]


        val name = a.details[0].kostkontrakan.name

        holder.tvName.text = name


        /*holder.tvDetail.text = a.name*/
        holder.tvStatus.text = a.status

        //new format date
        val newFormat = "d MMM yyyy"

        holder.tvTanggal.text = Helper().convertDate(a.created_att, newFormat)

        //change color status
        var color = context.getColor(R.color.menunggu)
        if (a.status == "SELESAI") color = context.getColor(R.color.selesai)
        else if (a.status == "BATAL") color = context.getColor(R.color.batal)

        holder.tvStatus.setTextColor(color)

        holder.layout.setOnClickListener {
            listener.onClicked(a)
        }
    }

    interface  Listeners{
        fun onClicked(data: PesanKostkontrakan)
    }
}