package com.mavendra.golekkostv3.model

class ResponModel {
    var success = 0
    lateinit var message:String
    var usergeneral = Usergeneral()
    var kostkontrakans :ArrayList<KostKontrakan> = ArrayList()
    var jasaangkuts :ArrayList<Jasaangkut> = ArrayList()
    var barangs :ArrayList<Barang> = ArrayList()
    var transaksis :ArrayList<Transaksi> = ArrayList()

    var rajaongkir = AlamatModel()
    var transaksi = Transaksi()
    /*var pesanjasa = Pesanjasa()*/

    var provinsi :ArrayList<AlamatModel> = ArrayList()
    var kota_kabupaten :ArrayList<AlamatModel> = ArrayList()
    var kecamatan :ArrayList<AlamatModel> = ArrayList()
}