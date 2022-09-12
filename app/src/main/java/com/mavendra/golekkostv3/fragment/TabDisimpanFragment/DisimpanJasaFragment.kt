package com.mavendra.golekkostv3.fragment.TabDisimpanFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mavendra.golekkostv3.R
import com.mavendra.golekkostv3.helper.SharedPref
import com.mavendra.golekkostv3.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 */
@SuppressLint("NotifyDataSetChanged")
class DisimpanJasaFragment : Fragment() {

    lateinit var myDb: MyDatabase
    lateinit var s :SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_disimpan_jasa, container, false)

        myDb = MyDatabase.getInstance(requireActivity())!!
        s = SharedPref(requireActivity())

        init(view)

        return view
    }

    private fun init(view: View) {

    }

    override fun onResume() {
        /*displayBarang()*/
        super.onResume()
    }
}