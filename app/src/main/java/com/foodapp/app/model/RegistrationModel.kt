package com.foodapp.app.model

class RegistrationModel {

    private var username: String? = null

    private var password: String? = null

    private var _id: String? = null

    private var email: String? = null

    private var profilePic: String? = null

    private var isAdmin: Boolean? = false

    private var _v: Int? = null

    fun profilePic(): String? {
        return profilePic
    }

    fun setProfile_image(profilePic: String?) {
        this.profilePic = profilePic
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    fun getName(): String? {
        return username
    }

    fun setName(name: String?) {
        this.username = name
    }

    fun getId(): String? {
        return _id
    }

    fun setId(id: String?) {
        this._id = id
    }

    fun getIsAdmin(): Boolean? {
        return isAdmin
    }

    fun setIsAdmin(isAdmin: Boolean?) {
        this.isAdmin = isAdmin
    }

    fun get_v(): Int? {
        return _v
    }

    fun set_v(v: Int?) {
        this._v = v
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }
}