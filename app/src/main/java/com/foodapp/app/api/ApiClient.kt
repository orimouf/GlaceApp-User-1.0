package com.foodapp.app.api

import android.widget.Toast
import com.foodapp.app.activity.ServerActivity
import com.foodapp.app.utils.Common
import com.foodapp.app.utils.SharePreference
import com.foodapp.app.utils.SharePreference.Companion.loginToken
//import com.foodapp.app.utils.TokenManager
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

object ApiClient {

    val BASE_URL= "https://glace-api.onrender.com/api/"//"http://127.0.0.1:8800/api/"//"http://teslimat-dz.com/Glace-App/admin/api/"
    val PrivicyPolicy="http://teslimat-dz.com/Glace-App/admin/privacypolicy"
    val termscondition="http://teslimat-dz.com/Glace-App/admin/termscondition"
    val MapKey="AIzaSyCIpp3OeQU8jzcMcjk0kKbjMGrkui20-vU"
    val Stripe="Your_stripe_public_key"

//    @Inject
//    lateinit var tokenManager: TokenManager

    var TIMEOUT: Long = 60 * 2 * 1.toLong()
    val getClient: ApiInterface get() {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient=OkHttpClient.Builder().connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)

//        httpClient.addInterceptor { chain ->
//            val request: Request = chain.request().newBuilder().addHeader("token", "Bearer ${tokenManager.getToken()}").build()
//            chain.proceed(request)
//        }

        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterface::class.java)

    }

}