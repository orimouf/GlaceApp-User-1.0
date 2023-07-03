package com.foodapp.app.model

import java.util.*

class OrderSummaryModel (

    var id: Int,

    var appId: String? = null,

    val _id: String? = null,

    val server_id: String,

    val client_name: String,

    val client_id: Int,

    val product_list_id : Int,

    val total_to_pay: Int,

    val verssi: String,

    val rest: String,

    val iscredit: Int,

    val date: String,

    val is_check: Int,

    val createdAt: String,

    val updatedAt: String,

    val __v: String,

    val up_to_server: Int

    )
//class OrderSummaryModel {
//    private var total_price: String? = null
//
//    private var item_id: String? = null
//
//    private var item_price: String? = null
//
//    private var qty: String? = null
//
//    private var item_name: String? = null
//
//    private var id: String? = null
//
//    private var itemimage: FoodItemImageModel? = null
//
//    private var item_notes: String? = null
//
//    private var addons: ArrayList<AddonsModel>?=null
//
//    private var addons_id: String? = null
//
//
//
//    fun getTotal_price(): String? {
//        return total_price
//    }
//
//    fun setTotal_price(total_price: String?) {
//        this.total_price = total_price
//    }
//
//    fun getItem_id(): String? {
//        return item_id
//    }
//
//    fun setItem_id(item_id: String?) {
//        this.item_id = item_id
//    }
//
//    fun getItem_price(): String? {
//        return item_price
//    }
//
//    fun setItem_price(item_price: String?) {
//        this.item_price = item_price
//    }
//
//    fun getQty(): String? {
//        return qty
//    }
//
//    fun setQty(qty: String?) {
//        this.qty = qty
//    }
//
//    fun getItem_name(): String? {
//        return item_name
//    }
//
//    fun setItem_name(item_name: String?) {
//        this.item_name = item_name
//    }
//
//    fun getId(): String? {
//        return id
//    }
//
//    fun setId(id: String?) {
//        this.id = id
//    }
//
//    fun getItemimage(): FoodItemImageModel {
//        return itemimage!!
//    }
//
//    fun setItemimage(itemimage: FoodItemImageModel) {
//        this.itemimage = itemimage
//    }
//
//    fun getItem_notes(): String? {
//        return item_notes
//    }
//
//    fun setItem_notes(item_notes: String?) {
//        this.item_notes = item_notes
//    }
//
//    fun getAddons(): ArrayList<AddonsModel> {
//        return addons!!
//    }
//
//    fun setAddons(addons: ArrayList<AddonsModel>?) {
//        this.addons = addons
//    }
//
//    fun getAddons_id(): String? {
//        return addons_id
//    }
//
//    fun setAddons_id(addons_id: String?) {
//        this.addons_id = addons_id
//    }
//
//}