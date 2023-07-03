package com.foodapp.app.api

import com.foodapp.app.model.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    //Login Api 1
//    @POST("client")
//    fun setClient(@Body map: HashMap<String, String>): Call<RestResponse<ClientModel>>

    //Login Api 1
    @POST("auth/login")
    fun getLogin(@Body map: HashMap<String, String>): Call<RestResponse<LoginModel>>

    //Registration Api 2
    @POST("auth/register")
    fun setRegistration(@Body map: HashMap<String, String>): Call<RestResponse<RegistrationModel>>

    //Send Clients Data To Server Api 0
    @POST("appdata/dataclients")
    fun setClients(@Body map: HashMap<String, ArrayList<ClientModel>>): Call<RestResponse<ClientModel>>

    //Get Clients Data From Server Api 0
    @GET("clients/")
    fun getClients(): Call<ListResponse<ClientServerModel>>

    //Get users Data From Server Api 0
    @GET("users/")
    fun getUsers(): Call<ListResponse<UserServerModel>>

    //Get Regions Data From Server Api 0
    @GET("regions/")
    fun getRegions(): Call<ListResponse<RegionServerModel>>

    //Get Payments Data From Server Api 0
    @GET("payments/")
    fun getPayments(): Call<ListResponse<VerssementServerModel>>

    //Get Products Data From Server Api 0
    @GET("products/")
    fun getProducts(): Call<ListResponse<ItemServerModel>>

    //Get Orders Data From Server Api 0
    @GET("orders/")
    fun getOrders(): Call<ListResponse<OrderServerModel>>

    //Get Orders Data From Server Api 0
    @GET("allproducts/")
    fun getAllProducts(): Call<ListResponse<AllProductServerModel>>

    //Send Users Data To Server Api 0
    @POST("appdata/datausers")
    fun setUsers(@Body map: HashMap<String, ArrayList<UserModel>>): Call<RestResponse<UserModel>>

    //Send Payments Data To Server Api 0
    @POST("appdata/datapayments")
    fun setPayments(@Body map: HashMap<String, ArrayList<VerssementModel>>): Call<ListResponse<VerssementModel>>

    //Send Users Data To Server Api 0
    @POST("appdata/dataregions")
    fun setRegions(@Body map: HashMap<String, ArrayList<RegionModel>>): Call<RestResponse<RegionModel>>

    //Send Users Data To Server Api 0
    @POST("appdata/dataproducts")
    fun setProducts(@Body map: HashMap<String, ArrayList<ItemModel>>): Call<RestResponse<ItemModel>>

    //Send Users Data To Server Api 0
    @POST("appdata/dataorders")
    fun setOrders(@Body map: HashMap<String, ArrayList<OrderSummaryModel>>): Call<ListResponse<OrderSummaryModel>>

    //Send Users Data To Server Api 0
    @POST("appdata/dataorderproducts")
    fun setOrderedProducts(@Body map: HashMap<String, ArrayList<AllProductModel>>): Call<ListResponse<AllProductModel>>

    //Profile Api 3
    @POST("auth/getprofile")
    fun getProfile(@Body map: HashMap<String, String>): Call<RestResponse<ProfileModel>>

    //EditProfile Api 4
    @Multipart
    @POST("editprofile")
    fun setProfile(@Part("user_id") userId: RequestBody,@Part("name") name: RequestBody, @Part profileimage: MultipartBody.Part?): Call<SingleResponse>

    //Chnage Password  Api 5
    @POST("changepassword")
    fun setChangePassword(@Body map: HashMap<String, String>):Call<SingleResponse>

    //Category  Api 6
    @GET("category")
    fun getRegion():Call<ListResponse<AllRegionModel>>

    //Item  Api 7
    @POST("item")
    fun getFoodItem(@Body map: HashMap<String, String>,@Query("page")strPageNo:String):Call<RestResponse<FoodItemResponseModel>>

    //Ratting  Api 12
    @POST("product")
    fun setProduct(@Body map: HashMap<String, String>):Call<SingleResponse>

    //Item  Api 9
    @POST("orderhistory")
    fun getOrderHistory(@Body map: HashMap<String, String>):Call<ListResponse<OrderHistoryModel>>

    //Item  Api 10
    @GET("rattinglist")
    fun getRatting():Call<ListResponse<RattingModel>>

    //Getcart  Api 11
    @POST("getcart")
    fun getCartItem(@Body map: HashMap<String, String>):Call<ListResponse<CartItemModel>>

    //Ratting  Api 12
    @POST("ratting")
    fun setRatting(@Body map: HashMap<String, String>):Call<SingleResponse>

    //ItemDetail  Api 13
    @POST("itemdetails")
    fun setItemDetail(@Body map: HashMap<String, String>):Call<RestResponse<ItemDetailModel>>

    //cart  Api 14
    @POST("cart")
    fun setAddToCart(@Body map: HashMap<String, String>):Call<SingleResponse>

    //QtyUpdate Api 15
    @POST("qtyupdate")
    fun setQtyUpdate(@Body map: HashMap<String, String>):Call<SingleResponse>

    //DeleteCartItem Api 16
    @POST("deletecartitem")
    fun setDeleteCartItem(@Body map: HashMap<String, String>):Call<SingleResponse>

    //Summary Api 17
    @POST("summary")
    fun setSummary(@Body map: HashMap<String, String>):Call<RestSummaryResponse>

    //OrderPayment Api 18
    @POST("order")
    fun setOrderPayment(@Body map: HashMap<String, String>):Call<SingleResponse>

    //forgotPassword Api 19
    @POST("forgotPassword")
    fun setforgotPassword(@Body map: HashMap<String, String>):Call<SingleResponse>

    //OrderDetail Api 20
    @POST("getorderdetails")
    fun setgetOrderDetail(@Body map: HashMap<String, String>):Call<RestOrderDetailResponse>

    //Search Api 21
    @POST("searchitem")
    fun setSearch(@Body map: HashMap<String, String>,@Query("page")strPageNo:String):Call<RestResponse<FoodItemResponseModel>>

    //PromoCode Api 23
    @POST("favoritelist")
    fun getFavouriteList(@Body map: HashMap<String, String>,@Query("page")strPageNo:String):Call<RestResponse<FoodFavouriteResponseModel>>

    //AddFavorite Api 24
    @POST("addfavorite")
    fun setAddFavorite(@Body map: HashMap<String, String>):Call<SingleResponse>

    //Removefavorite Api 25
    @POST("removefavorite")
    fun setRemovefavorite(@Body map: HashMap<String, String>):Call<SingleResponse>

    //PromoCode Api 26
    @GET("promocodelist")
    fun getPromoCodeList():Call<ListResponse<PromocodeModel>>

    //ApplyPromocode Api 27
    @POST("promocode")
    fun setApplyPromocode(@Body map: HashMap<String, String>):Call<RestResponse<GetPromocodeModel>>

    //ApplyPromocode Api 27
    @POST("cartcount")
    fun getCartCount(@Body map: HashMap<String, String>):Call<CartCountModel>

    //ApplyPromocode Api 28
    @GET("banner")
    fun getBanner():Call<ListResponse<BannerModel>>

    //LocationApi 29
    @GET("restaurantslocation")
    fun getLocation():Call<RestResponse<LocationModel>>

    //check Status Api 30
    @GET("isopenclose")
    fun getCheckStatusRestaurant():Call<SingleResponse>

    //Checkpincode Api 31
    @POST("checkpincode")
    fun setCheckPinCode(@Body map: HashMap<String,String>):Call<SingleResponse>

    //Checkpincode Api 32
    @POST("resendemailverification")
    fun setResendEmailVerification(@Body map: HashMap<String,String>):Call<SingleResponse>

    //getChatList Api 33
    @POST("emailverify")
    fun setEmailVerify(@Body map: HashMap<String, String>): Call<JsonObject>

}