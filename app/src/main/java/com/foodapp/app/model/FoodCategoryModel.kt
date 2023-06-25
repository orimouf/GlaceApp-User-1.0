package com.foodapp.app.model

class AllRegionModel {
    private var region_name: String? = null

    private var id: String? = null

    private var isSelect:Boolean?=false

    fun getRegion_name(): String? {
        return region_name
    }

    fun setRegion_name(category_name: String?) {
        this.region_name = category_name
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun isSelect(): Boolean? {
        return isSelect
    }

    fun setSelect(isSelect: Boolean?) {
        this.isSelect = isSelect
    }

}