package com.mavendra.golekkostv3.model

class InputBarang {
    lateinit var user_id: String
    lateinit var name: String
    lateinit var phone: String
    lateinit var lokasi: String
    lateinit var harga: String

    var barangs = ArrayList<Item>()

    class Item{
        lateinit var id: String

    }
}