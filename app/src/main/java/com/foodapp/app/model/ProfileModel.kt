package com.foodapp.app.model

class ProfileModel {
    private var username: String? = null

    private var profilePic: String? = null

    private var isAdmin: Boolean? = false

    private var _id: String? = null

    private var email: String? = null

    private var createdAt: String? = null

    private var updatedAt: String? = null

    private var __v: Int? = null

    private var accessToken: String? = null

    fun getName(): String? {
        return username
    }

    fun setName(username: String?) {
        this.username = username
    }

    fun getAccessToken(): String? {
        return accessToken
    }

    fun get__v(): Int? {
        return __v
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun getIsAdmin(): Boolean? {
        return isAdmin
    }

    fun setIsAdmin(isAdmin: Boolean?) {
        this.isAdmin = isAdmin
    }

    fun getProfilePic(): String? {
        return profilePic
    }

    fun getProfilePic(profilePic: String?) {
        this.profilePic = profilePic
    }

    fun getId(): String? {
        return _id
    }

    fun setId(id: String?) {
        this._id = id
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }
}