package com.mavendra.golekkostv3.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mavendra.golekkostv3.fragment.TabDisimpanFragment.DisimpanKostKontrakanFragment
import com.mavendra.golekkostv3.fragment.TabDisimpanFragment.DisimpanJasaFragment

class SectionPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Jasa Angkut"
            1 -> "Kost atau Kontrakan"
            else -> ""
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment
        return  when(position){
            0 -> {
                fragment = DisimpanJasaFragment()
                return  fragment
            }

            1 -> {
                fragment = DisimpanKostKontrakanFragment()
                return  fragment
            }
            else -> {
                fragment = DisimpanJasaFragment()
                return fragment
            }
        }
    }
}