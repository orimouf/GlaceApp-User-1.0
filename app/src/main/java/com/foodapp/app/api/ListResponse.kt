package com.foodapp.app.api

class ListResponse<T> {
    val users: Array<T>?=null
    val clients: Array<T>?=null
    val regions: Array<T>?=null
    val payments: Array<T>?=null
    val products: Array<T>?=null
    val orders: Array<T>?=null
    val orderedProducts: Array<T>?=null

    private var data: ArrayList<T>?=null

    private var message: String? = null

    private var status: String? = null

    fun getData(): ArrayList<T> {
        return data!!
    }

    fun setData(data: ArrayList<T>) {
        this.data = data
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }
}