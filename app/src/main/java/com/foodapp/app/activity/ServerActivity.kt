package com.foodapp.app.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.foodapp.app.R
import com.foodapp.app.api.ApiClient
import com.foodapp.app.api.ListResponse
import com.foodapp.app.api.RestResponse
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.model.*
import com.foodapp.app.utils.Common
import com.foodapp.app.utils.Common.betweenDate
import com.foodapp.app.utils.Common.getCurrentDateTime
import com.foodapp.app.utils.Common.getCurrentLanguage
import com.foodapp.app.utils.Common.getToast
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_cart.ivBack
import kotlinx.android.synthetic.main.activity_cart.ivHome
import kotlinx.android.synthetic.main.activity_server.*
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.dlg_updateuser.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.parseInt
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class ServerActivity : BaseActivity() {

    override fun setLayout(): Int {
        return R.layout.activity_server
    }

    private fun getUpdateAt(id: String, table: String): String {
        val databaseHandler = DatabaseHandler(this)
        return databaseHandler.viewUpdateAt(id, table)
    }

    private fun getUsersList(): ArrayList<UserModel> {
        val databaseHandler = DatabaseHandler(this)
        return databaseHandler.viewUser(true)
    }

    private fun getPaymentsList(): ArrayList<VerssementModel> {
        val databaseHandler = DatabaseHandler(this)
        return databaseHandler.viewVerssemnt()
    }

    private fun getClientsList(): ArrayList<ClientModel> {
        val databaseHandler = DatabaseHandler(this)
        return databaseHandler.viewClient(0, false,"up_to_server", true)
    }

    private fun getRegionsList(): ArrayList<RegionModel> {
        val databaseHandler = DatabaseHandler(this)
        return databaseHandler.viewRegion(true)
    }

    private fun getProductsList(): ArrayList<ItemModel> {
        val databaseHandler = DatabaseHandler(this)
        return databaseHandler.viewItem(true)
    }

    private fun getOrdersList(): ArrayList<OrderSummaryModel> {
        val databaseHandler = DatabaseHandler(this)
        return databaseHandler.viewOrderSummary(0,0,true)
    }

    private fun getOrderedProductsList(): ArrayList<AllProductModel> {
        val databaseHandler = DatabaseHandler(this)
        return databaseHandler.viewAllProduct(0,true)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun InitView() {
        this@ServerActivity.getCurrentLanguage(false)
        FirebaseApp.initializeApp(this@ServerActivity)

        btnSendData.setOnClickListener{

            val getClientList = getClientsList()
            val getUserList = getUsersList()
            val getRegionList = getRegionsList()
            val getProductList = getProductsList()
            val getOrderList = getOrdersList()
            val getPaymentList = getPaymentsList()
            val getOrderedProductList = getOrderedProductsList()

            val hashmapClient = HashMap<String, ArrayList<ClientModel>>()
            hashmapClient["data"] = getClientList
            val hashmapUser = HashMap<String, ArrayList<UserModel>>()
            hashmapUser["data"] = getUserList
            val hashmapRegion = HashMap<String, ArrayList<RegionModel>>()
            hashmapRegion["data"] = getRegionList
            val hashmapProduct = HashMap<String, ArrayList<ItemModel>>()
            hashmapProduct["data"] = getProductList
            val hashmapPayment = HashMap<String, ArrayList<VerssementModel>>()
            hashmapPayment["data"] = getPaymentList
            val hashmapOrder = HashMap<String, ArrayList<OrderSummaryModel>>()
            hashmapOrder["data"] = getOrderList
            val hashmapOrderedProduct = HashMap<String, ArrayList<AllProductModel>>()
            hashmapOrderedProduct["data"] = getOrderedProductList

            tvShow.text = getClientList.toString() +  getUserList.toString() + getRegionList.toString() + getProductList.toString() + getOrderList.toString() + getOrderedProductList.toString()

            if (Common.isCheckNetwork(this@ServerActivity)) {
                if ( getClientList.isEmpty() && getUserList.isEmpty() && getRegionList.isEmpty()
                    && getProductList.isEmpty() && getOrderList.isEmpty() && getPaymentList.isEmpty() && getOrderedProductList.isEmpty() ) {
                    Common.alertErrorOrValidationDialog(this@ServerActivity,"All data was synchrony with server")
                    operationDone("client");operationDone("user");operationDone("region");operationDone("product")
                    operationDone("order");operationDone("orderproduct");operationDone("payment")
                } else {
                    if(getClientList.isEmpty()){ operationDone("client") } else { callApiClients(hashmapClient, getClientList) }
                    if (getUserList.isEmpty()){ operationDone("user") } else { callApiUsers(hashmapUser, getUserList) }
                    if (getRegionList.isEmpty()){ operationDone("region") } else { callApiRegions(hashmapRegion, getRegionList) }
                    if (getProductList.isEmpty()){ operationDone("product") } else { callApiProducts(hashmapProduct, getProductList) }
                    if (getOrderList.isEmpty()){ operationDone("order") } else { callApiOrders(hashmapOrder) }
                    if (getPaymentList.isEmpty()){ operationDone("payment") } else { callApiPayments(hashmapPayment) }
                    if (getOrderedProductList.isEmpty()){ operationDone("orderproduct") } else { callApiOrderedProducts(hashmapOrderedProduct) }
                }
            } else {
                Common.alertErrorOrValidationDialog(this@ServerActivity,resources.getString(R.string.no_internet))
            }
        }

        btnGetData.setOnClickListener{

            if (Common.isCheckNetwork(this@ServerActivity)) {
                callApiGetUsers()
                callApiGetClients()
                callApiGetRegions()
//                callApiGetPayments()
                callApiGetProducts()
//                callApiGetOrders()
//                callApiGetOrderedProducts()

//                if (ClientList.isEmpty()) operationDone("client")

//                if ( ClientList.isEmpty() ) {
//                    Common.alertErrorOrValidationDialog(this@ServerActivity,"All data was synchrony with server")
//                }
            } else {
                Common.alertErrorOrValidationDialog(this@ServerActivity,resources.getString(R.string.no_internet))
            }
        }

        ivBack.setOnClickListener {
            val intent=Intent(this@ServerActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        ivHome.setOnClickListener {
            val intent=Intent(this@ServerActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun operationDone(info: String){

        when(info){
            "client" -> {
                tvSendingCheck1.text = "Clients Data Synchrony"
                tvSendingCheck1.setTextColor(ContextCompat.getColor(applicationContext,R.color.quantum_googblue))
                ivStatus1.setBackgroundResource(R.drawable.ic_round_uncheck_orange)
                ivStatus1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                ivStatusUpDown1.setBackgroundResource(android.R.drawable.ic_menu_upload)
                ivStatusUpDown1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext,R.color.quantum_googblue))
                view1.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
            }
            "user" -> {
                tvSendingCheck2.text = "Users Data Synchrony"
                tvSendingCheck2.setTextColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                ivStatus2.setBackgroundResource(R.drawable.ic_round_uncheck_orange)
                ivStatus2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                view2.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
            }
            "region" -> {
                tvSendingCheck3.text = "Regions Data Synchrony"
                tvSendingCheck3.setTextColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                ivStatus3.setBackgroundResource(R.drawable.ic_round_uncheck_orange)
                ivStatus3.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                view3.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
            }
            "product" -> {
                tvSendingCheck4.text = "Products Data Synchrony"
                tvSendingCheck4.setTextColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                ivStatus4.setBackgroundResource(R.drawable.ic_round_uncheck_orange)
                ivStatus4.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                view4.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
            }
            "order" -> {
                tvSendingCheck5.text = "Orders Data Synchrony"
                tvSendingCheck5.setTextColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                ivStatus5.setBackgroundResource(R.drawable.ic_round_uncheck_orange)
                ivStatus5.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                view5.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
            }
            "orderproduct" -> {
                tvSendingCheck6.text = "Ordered Data Products Synchrony"
                tvSendingCheck6.setTextColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                ivStatus6.setBackgroundResource(R.drawable.ic_round_uncheck_orange)
                ivStatus6.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                view6.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
            }
            "payment" -> {
                tvSendingCheck7.text = "Payments Data Synchrony"
                tvSendingCheck7.setTextColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                ivStatus7.setBackgroundResource(R.drawable.ic_round_uncheck_orange)
                ivStatus7.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
            }
            else -> "ERROR"
        }

    }

    // API CALL SEND DATA TO SERVER
    @OptIn(DelicateCoroutinesApi::class)
    private fun callApiUsers(hashmap: HashMap<String, ArrayList<UserModel>>, userCollection: ArrayList<UserModel>) {
        Common.showLoadingProgress(this@ServerActivity)
        GlobalScope.launch(Dispatchers.IO) {
            // Async / await Method
            withContext(Dispatchers.Default) {

                val call = ApiClient.getClient.setUsers(hashmap)
                call.enqueue(object : Callback<RestResponse<UserModel>> {
                override fun onResponse(
                    call: Call<RestResponse<UserModel>>,
                    response: Response<RestResponse<UserModel>>
                ) {
                    if (response.code() == 201) {
                        val serverResponse: RestResponse<UserModel> = response.body()!!
                        if(serverResponse.getStatus().equals("1")) {
                            for (item in userCollection) {
                                val databaseHandler = DatabaseHandler(applicationContext)
                                databaseHandler.updateUpToServer(item.id, "TABLE_USER")
                            }
                            Common.dismissLoadingProgress()
                            successfulDialog(this@ServerActivity,serverResponse.getMessage())
                            // make view checked
                            operationDone("user")
                        }else if (serverResponse.getStatus().equals("0")) {
                            Common.dismissLoadingProgress()
                            Common.alertErrorOrValidationDialog(
                                this@ServerActivity,
                                serverResponse.getMessage()
                            )
                        }
                    } else {
                        Common.dismissLoadingProgress()
                        Common.showErrorFullMsg(this@ServerActivity,"Error with apis")
                    }
                }

                override fun onFailure(call: Call<RestResponse<UserModel>>, t: Throwable) {
                    Common.dismissLoadingProgress()
                    Common.alertErrorOrValidationDialog(
                        this@ServerActivity,
                        resources.getString(R.string.error_msg)
                    )
                }
            })

            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun callApiClients(hashmap: HashMap<String, ArrayList<ClientModel>>, clientCollection: ArrayList<ClientModel>) {
        Common.showLoadingProgress(this@ServerActivity)
        GlobalScope.launch(Dispatchers.IO) {
            // Async / await Method
            withContext(Dispatchers.Default) {

                val call = ApiClient.getClient.setClients(hashmap)
                call.enqueue(object : Callback<RestResponse<ClientModel>> {
                    override fun onResponse(
                        call: Call<RestResponse<ClientModel>>,
                        response: Response<RestResponse<ClientModel>>
                    ) {
                        tvShow.text = response.code().toString()
                        if (response.code() == 201) {
                            val serverResponse: RestResponse<ClientModel> = response.body()!!
                            if (serverResponse.getStatus().equals("1")) {
                                for (item in clientCollection) {
                                    val databaseHandler = DatabaseHandler(applicationContext)
                                    databaseHandler.updateUpToServer(item.id, "TABLE_CLIENT")
                                }
                                Common.dismissLoadingProgress()
                                successfulDialog(this@ServerActivity, serverResponse.getMessage())
                                // make view checked
                                operationDone("client")
                            } else if (serverResponse.getStatus().equals("0")) {
                                Common.dismissLoadingProgress()
                                Common.alertErrorOrValidationDialog(
                                    this@ServerActivity,
                                    serverResponse.getMessage()
                                )
                            }
                        } else {
                            tvShow.text = response.body().toString()
                            Common.dismissLoadingProgress()
                            Common.showErrorFullMsg(this@ServerActivity, "Error with apis")
                        }
                    }

                    override fun onFailure(call: Call<RestResponse<ClientModel>>, t: Throwable) {
                        Common.dismissLoadingProgress()
                        Common.alertErrorOrValidationDialog(
                            this@ServerActivity,
                            resources.getString(R.string.error_msg)
                        )
                    }
                })

            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun callApiRegions(hashmap: HashMap<String, ArrayList<RegionModel>>, regionCollection: ArrayList<RegionModel>) {
        Common.showLoadingProgress(this@ServerActivity)
        GlobalScope.launch(Dispatchers.IO) {
            // Async / await Method
            withContext(Dispatchers.Default) {

                val call = ApiClient.getClient.setRegions(hashmap)
                call.enqueue(object : Callback<RestResponse<RegionModel>> {
                    override fun onResponse(
                        call: Call<RestResponse<RegionModel>>,
                        response: Response<RestResponse<RegionModel>>
                    ) {
                        if (response.code() == 201) {
                            val serverResponse: RestResponse<RegionModel> = response.body()!!
                            if (serverResponse.getStatus().equals("1")) {
                                for (item in regionCollection) {
                                    val databaseHandler = DatabaseHandler(applicationContext)
                                    databaseHandler.updateUpToServer(item.id, "TABLE_REGION")
                                }
                                Common.dismissLoadingProgress()
                                successfulDialog(this@ServerActivity, serverResponse.getMessage())
                                // make view checked
                                operationDone("region")
                            } else if (serverResponse.getStatus().equals("0")) {
                                Common.dismissLoadingProgress()
                                Common.alertErrorOrValidationDialog(
                                    this@ServerActivity,
                                    serverResponse.getMessage()
                                )
                            }
                        } else {
                            Common.dismissLoadingProgress()
                            Common.showErrorFullMsg(this@ServerActivity, "Error with apis")
                        }
                    }

                    override fun onFailure(call: Call<RestResponse<RegionModel>>, t: Throwable) {
                        Common.dismissLoadingProgress()
                        Common.alertErrorOrValidationDialog(
                            this@ServerActivity,
                            resources.getString(R.string.error_msg)
                        )
                    }
                })

            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun callApiPayments(hashmap: HashMap<String, ArrayList<VerssementModel>>) {
        Common.showLoadingProgress(this@ServerActivity)
        GlobalScope.launch(Dispatchers.IO) {
            // Async / await Method
            withContext(Dispatchers.Default) {

                val call = ApiClient.getClient.setPayments(hashmap)
                call.enqueue(object : Callback<ListResponse<VerssementModel>> {
                    override fun onResponse(
                        call: Call<ListResponse<VerssementModel>>,
                        response: Response<ListResponse<VerssementModel>>
                    ) {
                        if (response.code() == 201) {
                            val serverResponse: ListResponse<VerssementModel> = response.body()!!
                            val serverData = serverResponse.idObj

                            if (serverData?.size != 0) {
                                for (i in serverData?.indices!!) {
                                    val databaseHandler = DatabaseHandler(applicationContext)
                                    serverData[i].appId?.let { databaseHandler.updateUpToServer(it.toInt(), "TABLE_VERSSEMENT") }
                                    serverData[i].appId?.let { databaseHandler.updateServerId(it.toInt(), "TABLE_VERSSEMENT", serverData[i]._id) }
                                }
                                Common.dismissLoadingProgress()
                                successfulDialog(this@ServerActivity, "Payments Up To Server Successfully")
                            } else {
                                Common.dismissLoadingProgress()
                                successfulDialog(this@ServerActivity, "All Payments On The Server")
                            }
                            // make view checked
                            operationDone("payment")
                        } else {
                            Common.dismissLoadingProgress()
                            Common.showErrorFullMsg(this@ServerActivity, "Error with apis")
                        }
                    }

                    override fun onFailure(
                        call: Call<ListResponse<VerssementModel>>,
                        t: Throwable
                    ) {
                        Common.dismissLoadingProgress()
                        Common.alertErrorOrValidationDialog(
                            this@ServerActivity,
                            resources.getString(R.string.error_msg)
                        )
                    }
                })

            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun callApiProducts(hashmap: HashMap<String, ArrayList<ItemModel>>, productCollection: ArrayList<ItemModel>) {
        Common.showLoadingProgress(this@ServerActivity)
        GlobalScope.launch(Dispatchers.IO) {
            // Async / await Method
            withContext(Dispatchers.Default) {

                val call = ApiClient.getClient.setProducts(hashmap)
                call.enqueue(object : Callback<RestResponse<ItemModel>> {
                    override fun onResponse(
                        call: Call<RestResponse<ItemModel>>,
                        response: Response<RestResponse<ItemModel>>
                    ) {
                        if (response.code() == 201) {
                            val serverResponse: RestResponse<ItemModel> = response.body()!!
                            if (serverResponse.getStatus().equals("1")) {
                                for (item in productCollection) {
                                    val databaseHandler = DatabaseHandler(applicationContext)
                                    databaseHandler.updateUpToServer(item.id, "TABLE_ITEMS")
                                }
                                Common.dismissLoadingProgress()
                                successfulDialog(this@ServerActivity, serverResponse.getMessage())
                                // make view checked
                                operationDone("product")
                            } else if (serverResponse.getStatus().equals("0")) {
                                Common.dismissLoadingProgress()
                                Common.alertErrorOrValidationDialog(
                                    this@ServerActivity,
                                    serverResponse.getMessage()
                                )
                            }
                        } else {
                            Common.dismissLoadingProgress()
                            Common.showErrorFullMsg(this@ServerActivity, "Error with apis")
                        }
                    }

                    override fun onFailure(call: Call<RestResponse<ItemModel>>, t: Throwable) {
                        Common.dismissLoadingProgress()
                        Common.alertErrorOrValidationDialog(
                            this@ServerActivity,
                            resources.getString(R.string.error_msg)
                        )
                    }
                })

            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun callApiOrders(hashmap: HashMap<String, ArrayList<OrderSummaryModel>>) {
        Common.showLoadingProgress(this@ServerActivity)
        GlobalScope.launch(Dispatchers.IO) {
            // Async / await Method
            withContext(Dispatchers.Default) {

                val call = ApiClient.getClient.setOrders(hashmap)
                call.enqueue(object : Callback<ListResponse<OrderSummaryModel>> {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(
                        call: Call<ListResponse<OrderSummaryModel>>,
                        response: Response<ListResponse<OrderSummaryModel>>
                    ) {
                        if (response.code() == 201) {
                            val serverResponse: ListResponse<OrderSummaryModel> = response.body()!!
                            val serverData = serverResponse.idObj

                            if (serverData?.size != 0) {
                                for (i in serverData?.indices!!) {
                                    val databaseHandler = DatabaseHandler(applicationContext)
                                    serverData[i].appId?.let { databaseHandler.updateUpToServer(it.toInt(), "TABLE_ORDER_SUMMARY") }
                                    serverData[i].appId?.let { databaseHandler.updateServerId(it.toInt(), "TABLE_ORDER_SUMMARY", serverData[i]._id) }
                                }
                                Common.dismissLoadingProgress()
                                successfulDialog(this@ServerActivity, "Orders Up To Server Successfully")
                            } else {
                                Common.dismissLoadingProgress()
                                successfulDialog(this@ServerActivity, "All Orders On The Server")
                            }
                            // make view checked
                            operationDone("order")
                        } else {
                            Common.dismissLoadingProgress()
                            Common.showErrorFullMsg(this@ServerActivity, "Error with apis")
                        }
                    }

                    override fun onFailure(
                        call: Call<ListResponse<OrderSummaryModel>>,
                        t: Throwable
                    ) {
                        Common.dismissLoadingProgress()
                        Common.alertErrorOrValidationDialog(
                            this@ServerActivity,
                            resources.getString(R.string.error_msg)
                        )
                    }
                })

            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun callApiOrderedProducts(hashmap: HashMap<String, ArrayList<AllProductModel>>) {
        Common.showLoadingProgress(this@ServerActivity)
        GlobalScope.launch(Dispatchers.IO) {
            // Async / await Method
            withContext(Dispatchers.Default) {

                val call = ApiClient.getClient.setOrderedProducts(hashmap)
                call.enqueue(object : Callback<ListResponse<AllProductModel>> {
                    override fun onResponse(
                        call: Call<ListResponse<AllProductModel>>,
                        response: Response<ListResponse<AllProductModel>>
                    ) {
                        if (response.code() == 201) {
                            val serverResponse: ListResponse<AllProductModel> = response.body()!!
                            val serverData = serverResponse.idObj

                            if (serverData?.size != 0) {
                                for (i in serverData?.indices!!) {
                                    val databaseHandler = DatabaseHandler(applicationContext)
                                    serverData[i].appId?.let { databaseHandler.updateUpToServer(it.toInt(), "TABLE_ALL_PRODUCT") }
                                    serverData[i].appId?.let { databaseHandler.updateServerId(it.toInt(), "TABLE_ALL_PRODUCT", serverData[i]._id) }
                                }
                                Common.dismissLoadingProgress()
                                successfulDialog(this@ServerActivity, "Ordered Products Up To Server Successfully")
                            } else {
                                Common.dismissLoadingProgress()
                                successfulDialog(this@ServerActivity, "All Ordered Products On The Server")
                            }
                            // make view checked
                            operationDone("orderproduct")
                        } else {
                            getToast(this@ServerActivity, response.code().toString())
                            Common.dismissLoadingProgress()
                            Common.showErrorFullMsg(this@ServerActivity, "Error with apis")
                        }
                    }

                    override fun onFailure(
                        call: Call<ListResponse<AllProductModel>>,
                        t: Throwable
                    ) {
                        Common.dismissLoadingProgress()
                        Common.alertErrorOrValidationDialog(
                            this@ServerActivity,
                            resources.getString(R.string.error_msg)
                        )
                    }
                })

            }
        }
    }

    // API CALL GET DATA FROM SERVER

    @OptIn(DelicateCoroutinesApi::class)
    private fun callApiGetUsers() {
        Common.showLoadingProgress(this@ServerActivity)
        val call = ApiClient.getClient.getUsers()
        val databaseHandler = DatabaseHandler(this)
        var status: Any = 0

        GlobalScope.launch(Dispatchers.IO) {
            // Async / await Method
            withContext(Dispatchers.Default) {

                call.enqueue(object : Callback<ListResponse<UserServerModel>> {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(
                        call: Call<ListResponse<UserServerModel>>,
                        response: Response<ListResponse<UserServerModel>>
                    ) {
                        if (response.code() == 200) {
                            val serverResponse: ListResponse<UserServerModel> = response.body()!!
                            val serverData = serverResponse.users
                            if (serverData?.size != 0) {
                                for (i in serverData?.indices!!) {

                                    if (serverData[i].username.isNotEmpty() && serverData[i].email.isNotEmpty()) {
                                        val getUpdateAt = getUpdateAt(serverData[i]._id, "user")
                                        val userCheck =
                                            databaseHandler.viewCheckUser(serverData[i]._id)
                                        val isAfter =
                                            betweenDate(serverData[i].updatedAt, getUpdateAt)
                                        val isAdmin = if (serverData[i].isAdmin) 1 else 0

                                        if (!userCheck) {
                                            status = databaseHandler.addUser(
                                                UserModel(
                                                    0,
                                                    serverData[i]._id,
                                                    serverData[i].username,
                                                    serverData[i].email,
                                                    serverData[i].password,
                                                    serverData[i].profilePic,
                                                    serverData[i].camion,
                                                    isAdmin,
                                                    serverData[i].createdAt,
                                                    serverData[i].updatedAt,
                                                    serverData[i].__v,
                                                    1
                                                )
                                            )
                                        } else if (!isAfter) {
                                            status = databaseHandler.addUser(
                                                UserModel(
                                                    serverData[i].appId.toInt(),
                                                    serverData[i]._id,
                                                    serverData[i].username,
                                                    serverData[i].email,
                                                    serverData[i].password,
                                                    serverData[i].profilePic,
                                                    serverData[i].camion,
                                                    isAdmin,
                                                    serverData[i].createdAt,
                                                    serverData[i].updatedAt,
                                                    serverData[i].__v,
                                                    1
                                                )
                                            )
                                        }

                                    } else {
                                        Toast.makeText(
                                            applicationContext,
                                            "Error Data missing",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                                if (status != -1) {
                                    successfulDialog(this@ServerActivity, "User Updated")
                                }
                            }
                            Common.dismissLoadingProgress()

                        } else {
                            Common.dismissLoadingProgress()
                            Common.showErrorFullMsg(this@ServerActivity, "Error with apis")
                        }
                    }

                    override fun onFailure(
                        call: Call<ListResponse<UserServerModel>>,
                        t: Throwable
                    ) {
                        Common.dismissLoadingProgress()
                        tvShow.text = t.toString()
                        println(t.toString())
                        Common.alertErrorOrValidationDialog(
                            this@ServerActivity,
                            resources.getString(R.string.error_msg)
                        )
                    }

                })

            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun callApiGetClients() {
        Common.showLoadingProgress(this@ServerActivity)
        val call = ApiClient.getClient.getClients()
        val databaseHandler = DatabaseHandler(this)
        var status: Any = 0

        GlobalScope.launch(Dispatchers.IO) {
            // Async / await Method
            withContext(Dispatchers.Default) {

                call.enqueue(object : Callback<ListResponse<ClientServerModel>> {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(
                        call: Call<ListResponse<ClientServerModel>>,
                        response: Response<ListResponse<ClientServerModel>>
                    ) {
                        if (response.code() == 200) {
                            val serverResponse: ListResponse<ClientServerModel> = response.body()!!
                            val serverData = serverResponse.clients
                            if (serverData?.size != 0) {
                                for (i in serverData?.indices!!) {

                                    if (serverData[i].clientName.isNotEmpty() && serverData[i].region.isNotEmpty() && serverData[i].phone.isNotEmpty() &&
                                        serverData[i].prices.isNotEmpty() && serverData[i].oldCredit.isNotEmpty() && serverData[i].creditBon.isNotEmpty() &&
                                        serverData[i].lastServe.isNotEmpty()
                                    ) {
                                        val getUpdateAt = getUpdateAt(serverData[i]._id, "client")
                                        val clientCheck =
                                            databaseHandler.viewCheckClient(serverData[i]._id)
                                        val isAfter =
                                            betweenDate(serverData[i].updatedAt, getUpdateAt)
                                        val isCredit = if (serverData[i].isCredit) 1 else 0
                                        val isPromo = if (serverData[i].isPromo) 1 else 0
                                        val isFrigo = if (serverData[i].isFrigo) 1 else 0

                                        if (!clientCheck) {
                                            status = databaseHandler.addClient(
                                                ClientModel(
                                                    0,
                                                    serverData[i]._id,
                                                    serverData[i].clientName,
                                                    serverData[i].phone,
                                                    serverData[i].prices,
                                                    serverData[i].region,
                                                    serverData[i].oldCredit,
                                                    isFrigo,
                                                    isPromo,
                                                    isCredit,
                                                    parseInt(serverData[i].creditBon),
                                                    serverData[i].lastServe,
                                                    serverData[i].createdAt,
                                                    serverData[i].updatedAt,
                                                    serverData[i].__v,
                                                    1
                                                )
                                            )
                                        } else if (!isAfter) {
                                            status = databaseHandler.updateClient(
                                                ClientModel(
                                                    serverData[i].appId.toInt(),
                                                    serverData[i]._id,
                                                    serverData[i].clientName,
                                                    serverData[i].phone,
                                                    serverData[i].prices,
                                                    serverData[i].region,
                                                    serverData[i].oldCredit,
                                                    isFrigo,
                                                    isPromo,
                                                    isCredit,
                                                    parseInt(serverData[i].creditBon),
                                                    serverData[i].lastServe,
                                                    serverData[i].createdAt,
                                                    getCurrentDateTime(),
                                                    serverData[i].__v,
                                                    1
                                                )
                                            )
                                        }

                                    } else {
                                        Toast.makeText(
                                            applicationContext,
                                            "Error Data missing",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                                if (status != -1) {
                                    successfulDialog(this@ServerActivity, "Client Updated")
                                }
                            }
                            Common.dismissLoadingProgress()

                        } else {
                            Common.dismissLoadingProgress()
                            Common.showErrorFullMsg(this@ServerActivity, "Error with apis")
                        }
                    }

                    override fun onFailure(
                        call: Call<ListResponse<ClientServerModel>>,
                        t: Throwable
                    ) {
                        Common.dismissLoadingProgress()
                        tvShow.text = t.toString()
                        println(t.toString())
                        Common.alertErrorOrValidationDialog(
                            this@ServerActivity,
                            resources.getString(R.string.error_msg)
                        )
                    }

                })

            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun callApiGetRegions() {
        Common.showLoadingProgress(this@ServerActivity)
        val call = ApiClient.getClient.getRegions()
        val databaseHandler = DatabaseHandler(this)
        var status: Any = 0

        GlobalScope.launch(Dispatchers.IO) {
            // Async / await Method
            withContext(Dispatchers.Default) {

                call.enqueue(object : Callback<ListResponse<RegionServerModel>> {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(
                        call: Call<ListResponse<RegionServerModel>>,
                        response: Response<ListResponse<RegionServerModel>>
                    ) {
                        if (response.code() == 200) {
                            val serverResponse: ListResponse<RegionServerModel> = response.body()!!
                            val serverData = serverResponse.regions

                            if (serverData?.size != 0) {
                                for (i in serverData?.indices!!) {

                                    if (serverData[i].regionName.isNotEmpty()) {
                                        val getUpdateAt = getUpdateAt(serverData[i]._id, "region")
                                        val regionCheck =
                                            databaseHandler.viewCheckRegion(serverData[i].regionName)
                                        val isAfter =
                                            betweenDate(serverData[i].updatedAt, getUpdateAt)

                                        if (!regionCheck) {
                                            status = databaseHandler.addRegion(
                                                RegionModel(
                                                    0,
                                                    serverData[i]._id,
                                                    serverData[i].regionName,
                                                    serverData[i].createdAt,
                                                    serverData[i].updatedAt,
                                                    serverData[i].__v,
                                                    1
                                                )
                                            )
                                        } else if (!isAfter) {
                                            status = databaseHandler.updateRegion(
                                                RegionModel(
                                                    serverData[i].appId.toInt(),
                                                    serverData[i]._id,
                                                    serverData[i].regionName,
                                                    serverData[i].createdAt,
                                                    getCurrentDateTime(),
                                                    serverData[i].__v,
                                                    1
                                                )
                                            )
                                        }

                                    } else {
                                        Toast.makeText(
                                            applicationContext,
                                            "Error Data missing",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                                if (status != -1) {
                                    successfulDialog(this@ServerActivity, "Regions Updated")
                                }
                            }
                            Common.dismissLoadingProgress()

                        } else {
                            Common.dismissLoadingProgress()
                            Common.showErrorFullMsg(this@ServerActivity, "Error with apis")
                        }
                    }

                    override fun onFailure(
                        call: Call<ListResponse<RegionServerModel>>,
                        t: Throwable
                    ) {
                        Common.dismissLoadingProgress()
                        tvShow.text = t.toString()
                        println(t.toString())
                        Common.alertErrorOrValidationDialog(
                            this@ServerActivity,
                            resources.getString(R.string.error_msg)
                        )
                    }

                })

            }
        }
    }

//    @OptIn(DelicateCoroutinesApi::class)
//    private fun callApiGetPayments() {
//        Common.showLoadingProgress(this@ServerActivity)
//        val call = ApiClient.getClient.getPayments()
//        val databaseHandler = DatabaseHandler(this)
//        var status: Any = 0
//
//        GlobalScope.launch(Dispatchers.IO) {
//            // Async / await Method
//            withContext(Dispatchers.Default) {
//
//                call.enqueue(object : Callback<ListResponse<VerssementServerModel>> {
//                    @RequiresApi(Build.VERSION_CODES.O)
//                    override fun onResponse(
//                        call: Call<ListResponse<VerssementServerModel>>,
//                        response: Response<ListResponse<VerssementServerModel>>
//                    ) {
//                        if (response.code() == 200) {
//                            val serverResponse: ListResponse<VerssementServerModel> =
//                                response.body()!!
//                            val serverData = serverResponse.payments
//
//                            if (serverData?.size != 0) {
//                                for (i in serverData?.indices!!) {
//
//                                    if (serverData[i].clientName.isNotEmpty() && serverData[i].clientId.isNotEmpty() && serverData[i].region.isNotEmpty() && serverData[i].oldSomme.isNotEmpty() &&
//                                        serverData[i].verssi.isNotEmpty() && serverData[i].rest.isNotEmpty() && serverData[i].date.isNotEmpty()
//                                    ) {
//                                        val getUpdateAt = getUpdateAt(serverData[i]._id, "payment")
//                                        val paymentCheck =
//                                            databaseHandler.viewCheckPayment(serverData[i]._id)
//                                        val isAfter =
//                                            betweenDate(serverData[i].updatedAt, getUpdateAt)
//                                        val isCheck = if (serverData[i].isCheck) 1 else 0
//
//                                        if (!paymentCheck) {
//                                            status = databaseHandler.addVerssement(
//                                                VerssementModel(0, serverData[i]._id, serverData[i].clientId,
//                                                    serverData[i].clientName, serverData[i].region, serverData[i].oldSomme, serverData[i].verssi, serverData[i].rest,
//                                                    1, isCheck, serverData[i].createdAt, serverData[i].updatedAt, serverData[i].__v, serverData[i].date
//                                                )
//                                            )
//                                        } else if (!isAfter) {
//                                            status = databaseHandler.updateVerssement(
//                                                VerssementModel(serverData[i].appId.toInt(), serverData[i]._id, serverData[i].clientId,
//                                                    serverData[i].clientName, serverData[i].region, serverData[i].oldSomme, serverData[i].verssi, serverData[i].rest,
//                                                    1, isCheck, serverData[i].createdAt, getCurrentDateTime(), serverData[i].__v, serverData[i].date
//                                                )
//                                            )
//                                        }
//
//                                    } else {
//                                        Toast.makeText(applicationContext, "Error Data missing", Toast.LENGTH_LONG).show()
//                                    }
//                                }
//                                if (status != -1) {
//                                    successfulDialog(this@ServerActivity, "Payment Updated")
//                                }
//                            }
//                            Common.dismissLoadingProgress()
//
//                        } else {
//                            Common.dismissLoadingProgress()
//                            Common.showErrorFullMsg(this@ServerActivity, "Error with apis")
//                        }
//                    }
//
//                    override fun onFailure(
//                        call: Call<ListResponse<VerssementServerModel>>,
//                        t: Throwable
//                    ) {
//                        Common.dismissLoadingProgress()
//                        tvShow.text = t.toString()
//                        println(t.toString())
//                        Common.alertErrorOrValidationDialog(
//                            this@ServerActivity,
//                            resources.getString(R.string.error_msg)
//                        )
//                    }
//
//                })
//
//            }
//        }
//    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun callApiGetProducts() {
        Common.showLoadingProgress(this@ServerActivity)
        val call = ApiClient.getClient.getProducts()
        val databaseHandler = DatabaseHandler(this)
        var status: Any = 0
        GlobalScope.launch(Dispatchers.IO) {
            // Async / await Method
            withContext(Dispatchers.Default) {

                call.enqueue(object : Callback<ListResponse<ItemServerModel>> {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(
                        call: Call<ListResponse<ItemServerModel>>,
                        response: Response<ListResponse<ItemServerModel>>
                    ) {
                        if (response.code() == 200) {
                            val serverResponse: ListResponse<ItemServerModel> = response.body()!!
                            val serverData = serverResponse.products

                            if (serverData?.size != 0) {
                                for (i in serverData?.indices!!) {

                                    if (serverData[i].name.isNotEmpty() && serverData[i].price.isNotEmpty() && serverData[i].image.isNotEmpty()) {
                                        val getUpdateAt = getUpdateAt(serverData[i]._id, "product")
                                        val paymentCheck =
                                            databaseHandler.viewCheckProduct(serverData[i]._id)
                                        val isAfter =
                                            betweenDate(serverData[i].updatedAt, getUpdateAt)
                                        val itStatus = if (serverData[i].status) 1 else 0

                                        if (!paymentCheck) {
                                            status = databaseHandler.addItem(
                                                ItemModel(
                                                    0,
                                                    serverData[i]._id,
                                                    serverData[i].name,
                                                    serverData[i].price,
                                                    itStatus,
                                                    serverData[i].qty_par_one,
                                                    serverData[i].image,
                                                    serverData[i].createdAt,
                                                    serverData[i].updatedAt,
                                                    serverData[i].__v,
                                                    1
                                                )
                                            )
                                        } else if (!isAfter) {
                                            status = databaseHandler.updateItem(
                                                ItemModel(
                                                    serverData[i].appId.toInt(),
                                                    serverData[i]._id,
                                                    serverData[i].name,
                                                    serverData[i].price,
                                                    itStatus,
                                                    serverData[i].qty_par_one,
                                                    serverData[i].image,
                                                    serverData[i].createdAt,
                                                    getCurrentDateTime(),
                                                    serverData[i].__v,
                                                    1
                                                )
                                            )
                                        }

                                    } else {
                                        Toast.makeText(
                                            applicationContext,
                                            "Error Data missing",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                                if (status != -1) {
                                    successfulDialog(this@ServerActivity, "Product Updated")
                                }
                            }
                            Common.dismissLoadingProgress()

                        } else {
                            Common.dismissLoadingProgress()
                            Common.showErrorFullMsg(this@ServerActivity, "Error with apis")
                        }
                    }

                    override fun onFailure(
                        call: Call<ListResponse<ItemServerModel>>,
                        t: Throwable
                    ) {
                        Common.dismissLoadingProgress()
                        tvShow.text = t.toString()
                        println(t.toString())
                        Common.alertErrorOrValidationDialog(
                            this@ServerActivity,
                            resources.getString(R.string.error_msg)
                        )
                    }

                })

            }
        }
    }

//    @OptIn(DelicateCoroutinesApi::class)
//    private fun callApiGetOrders() {
//        Common.showLoadingProgress(this@ServerActivity)
//        val call = ApiClient.getClient.getOrders()
//        val databaseHandler = DatabaseHandler(this)
//        var status: Any = 0
//
//        GlobalScope.launch(Dispatchers.IO) {
//            // Async / await Method
//            withContext(Dispatchers.Default) {
//
//                call.enqueue(object : Callback<ListResponse<OrderServerModel>> {
//                    @RequiresApi(Build.VERSION_CODES.O)
//                    override fun onResponse(
//                        call: Call<ListResponse<OrderServerModel>>,
//                        response: Response<ListResponse<OrderServerModel>>
//                    ) {
//                        if (response.code() == 200) {
//                            val serverResponse: ListResponse<OrderServerModel> = response.body()!!
//                            val serverData = serverResponse.orders
//
//                            if (serverData?.size != 0) {
//                                for (i in serverData?.indices!!) {
//
//                                    if (serverData[i].clientName.isNotEmpty() && serverData[i].clientId.isNotEmpty() && serverData[i].productListId.isNotEmpty() &&
//                                        serverData[i].totalToPay.isNotEmpty() && serverData[i].verssi.isNotEmpty() && serverData[i].rest.isNotEmpty() && serverData[i].date.isNotEmpty()
//                                    ) {
//                                        val getUpdateAt = getUpdateAt(serverData[i]._id, "order")
//                                        val paymentCheck =
//                                            databaseHandler.viewCheckOrders(serverData[i]._id)
//                                        val isAfter =
//                                            betweenDate(serverData[i].updatedAt, getUpdateAt)
//                                        val isCheck = if (serverData[i].isCheck) 1 else 0
//                                        val isCredit = if (serverData[i].isCredit) 1 else 0
//
//                                        if (!paymentCheck) {
//                                            status = databaseHandler.addOrderSummary(
//                                                OrderSummaryModel(
//                                                    0,
//                                                    serverData[i]._id,
//                                                    serverData[i].clientName,
//                                                    serverData[i].clientId.toInt(),
//                                                    serverData[i].productListId.toInt(),
//                                                    serverData[i].totalToPay.toInt(),
//                                                    serverData[i].verssi,
//                                                    serverData[i].rest,
//                                                    isCredit,
//                                                    serverData[i].date,
//                                                    isCheck,
//                                                    serverData[i].createdAt,
//                                                    serverData[i].updatedAt,
//                                                    serverData[i].__v,
//                                                    1
//                                                )
//                                            )
//                                        } else if (!isAfter) {
//                                            status = databaseHandler.updateOrderSummary(
//                                                OrderSummaryModel(
//                                                    serverData[i].appId.toInt(),
//                                                    serverData[i]._id,
//                                                    serverData[i].clientName,
//                                                    serverData[i].clientId.toInt(),
//                                                    serverData[i].productListId.toInt(),
//                                                    serverData[i].totalToPay.toInt(),
//                                                    serverData[i].verssi,
//                                                    serverData[i].rest,
//                                                    isCredit,
//                                                    serverData[i].date,
//                                                    isCheck,
//                                                    serverData[i].createdAt,
//                                                    getCurrentDateTime(),
//                                                    serverData[i].__v,
//                                                    1
//                                                ), "All"
//                                            )
//                                        }
//
//                                    } else {
//                                        Toast.makeText(
//                                            applicationContext,
//                                            "Error Data missing",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    }
//                                }
//                                if (status != -1) {
//                                    successfulDialog(this@ServerActivity, "Order Updated")
//                                }
//                            }
//                            Common.dismissLoadingProgress()
//
//                        } else {
//                            Common.dismissLoadingProgress()
//                            Common.showErrorFullMsg(this@ServerActivity, "Error with apis")
//                        }
//                    }
//
//                    override fun onFailure(
//                        call: Call<ListResponse<OrderServerModel>>,
//                        t: Throwable
//                    ) {
//                        Common.dismissLoadingProgress()
//                        tvShow.text = t.toString()
//                        println(t.toString())
//                        Common.alertErrorOrValidationDialog(
//                            this@ServerActivity,
//                            resources.getString(R.string.error_msg)
//                        )
//                    }
//
//                })
//
//            }
//        }
//    }

//    @OptIn(DelicateCoroutinesApi::class)
//    private fun callApiGetOrderedProducts() {
//        Common.showLoadingProgress(this@ServerActivity)
//        val call = ApiClient.getClient.getAllProducts()
//        val databaseHandler = DatabaseHandler(this)
//        var status: Any = 0
//
//        GlobalScope.launch(Dispatchers.IO) {
//            // Async / await Method
//            withContext(Dispatchers.Default) {
//
//                call.enqueue(object : Callback<ListResponse<AllProductServerModel>> {
//                    @RequiresApi(Build.VERSION_CODES.O)
//                    override fun onResponse(
//                        call: Call<ListResponse<AllProductServerModel>>,
//                        response: Response<ListResponse<AllProductServerModel>>
//                    ) {
//                        if (response.code() == 200) {
//                            val serverResponse: ListResponse<AllProductServerModel> =
//                                response.body()!!
//                            val serverData = serverResponse.orderedProducts
//
//                            if (serverData?.size != 0) {
//                                for (i in serverData?.indices!!) {
//
//                                    if (serverData[i].orderId.isNotEmpty()) {
//                                        val getUpdateAt = getUpdateAt(serverData[i]._id, "orderedproducts")
//                                        val paymentCheck =
//                                            databaseHandler.viewCheckOrderedProducts(serverData[i]._id)
//                                        val isAfter =
//                                            betweenDate(serverData[i].updatedAt, getUpdateAt)
//
//                                        if (!paymentCheck) {
//                                            status = databaseHandler.addAllProduct(
//                                                AllProductModel(
//                                                    0,
//                                                    serverData[i]._id,
//                                                    serverData[i].orderId.toInt(),
//                                                    serverData[i].mini_qty.toInt(),
//                                                    serverData[i].mini_q_u.toInt(),
//                                                    serverData[i].trio_qty.toInt(),
//                                                    serverData[i].trio_q_u.toInt(),
//                                                    serverData[i].solo_qty.toInt(),
//                                                    serverData[i].solo_q_u.toInt(),
//                                                    serverData[i].pot_qty.toInt(),
//                                                    serverData[i].pot_q_u.toInt(),
//                                                    serverData[i].gini_qty.toInt(),
//                                                    serverData[i].gini_q_u.toInt(),
//                                                    serverData[i].big_qty.toInt(),
//                                                    serverData[i].big_q_u.toInt(),
//                                                    serverData[i].cornito_4_qty.toInt(),
//                                                    serverData[i].cornito_4_q_u.toInt(),
//                                                    serverData[i].cornito_5_qty.toInt(),
//                                                    serverData[i].cornito_5_q_u.toInt(),
//                                                    serverData[i].cornito_g_qty.toInt(),
//                                                    serverData[i].cornito_g_q_u.toInt(),
//                                                    serverData[i].gofrito_qty.toInt(),
//                                                    serverData[i].gofrito_q_u.toInt(),
//                                                    serverData[i].pot_v_qty.toInt(),
//                                                    serverData[i].pot_v_q_u.toInt(),
//                                                    serverData[i].g8_qty.toInt(),
//                                                    serverData[i].g8_q_u.toInt(),
//                                                    serverData[i].gold_qty.toInt(),
//                                                    serverData[i].gold_q_u.toInt(),
//                                                    serverData[i].skiper_qty.toInt(),
//                                                    serverData[i].skiper_q_u.toInt(),
//                                                    serverData[i].scobido_qty.toInt(),
//                                                    serverData[i].scobido_q_u.toInt(),
//                                                    serverData[i].mini_scobido_qty.toInt(),
//                                                    serverData[i].mini_scobido_q_u.toInt(),
//                                                    serverData[i].venezia_qty.toInt(),
//                                                    serverData[i].venezia_q_u.toInt(),
//                                                    serverData[i].bf_400_q_u.toInt(),
//                                                    serverData[i].bf_250_q_u.toInt(),
//                                                    serverData[i].bf_230_q_u.toInt(),
//                                                    serverData[i].bf_200_q_u.toInt(),
//                                                    serverData[i].bf_150_q_u.toInt(),
//                                                    serverData[i].buch_q_u.toInt(),
//                                                    serverData[i].tarte_q_u.toInt(),
//                                                    serverData[i].mosta_q_u.toInt(),
//                                                    serverData[i].misso_q_u.toInt(),
//                                                    serverData[i].juliana_q_u.toInt(),
//                                                    serverData[i].bac_5_q_u.toInt(),
//                                                    serverData[i].bac_6_q_u.toInt(),
//                                                    serverData[i].createdAt,
//                                                    serverData[i].updatedAt,
//                                                    serverData[i].__v,
//                                                    1
//                                                )
//                                            )
//                                        } else if (!isAfter) {
//                                            status = databaseHandler.updateAllProduct(
//                                                AllProductModel(
//                                                    serverData[i].appId.toInt(),
//                                                    serverData[i]._id,
//                                                    serverData[i].orderId.toInt(),
//                                                    serverData[i].mini_qty.toInt(),
//                                                    serverData[i].mini_q_u.toInt(),
//                                                    serverData[i].trio_qty.toInt(),
//                                                    serverData[i].trio_q_u.toInt(),
//                                                    serverData[i].solo_qty.toInt(),
//                                                    serverData[i].solo_q_u.toInt(),
//                                                    serverData[i].pot_qty.toInt(),
//                                                    serverData[i].pot_q_u.toInt(),
//                                                    serverData[i].gini_qty.toInt(),
//                                                    serverData[i].gini_q_u.toInt(),
//                                                    serverData[i].big_qty.toInt(),
//                                                    serverData[i].big_q_u.toInt(),
//                                                    serverData[i].cornito_4_qty.toInt(),
//                                                    serverData[i].cornito_4_q_u.toInt(),
//                                                    serverData[i].cornito_5_qty.toInt(),
//                                                    serverData[i].cornito_5_q_u.toInt(),
//                                                    serverData[i].cornito_g_qty.toInt(),
//                                                    serverData[i].cornito_g_q_u.toInt(),
//                                                    serverData[i].gofrito_qty.toInt(),
//                                                    serverData[i].gofrito_q_u.toInt(),
//                                                    serverData[i].pot_v_qty.toInt(),
//                                                    serverData[i].pot_v_q_u.toInt(),
//                                                    serverData[i].g8_qty.toInt(),
//                                                    serverData[i].g8_q_u.toInt(),
//                                                    serverData[i].gold_qty.toInt(),
//                                                    serverData[i].gold_q_u.toInt(),
//                                                    serverData[i].skiper_qty.toInt(),
//                                                    serverData[i].skiper_q_u.toInt(),
//                                                    serverData[i].scobido_qty.toInt(),
//                                                    serverData[i].scobido_q_u.toInt(),
//                                                    serverData[i].mini_scobido_qty.toInt(),
//                                                    serverData[i].mini_scobido_q_u.toInt(),
//                                                    serverData[i].venezia_qty.toInt(),
//                                                    serverData[i].venezia_q_u.toInt(),
//                                                    serverData[i].bf_400_q_u.toInt(),
//                                                    serverData[i].bf_250_q_u.toInt(),
//                                                    serverData[i].bf_230_q_u.toInt(),
//                                                    serverData[i].bf_200_q_u.toInt(),
//                                                    serverData[i].bf_150_q_u.toInt(),
//                                                    serverData[i].buch_q_u.toInt(),
//                                                    serverData[i].tarte_q_u.toInt(),
//                                                    serverData[i].mosta_q_u.toInt(),
//                                                    serverData[i].misso_q_u.toInt(),
//                                                    serverData[i].juliana_q_u.toInt(),
//                                                    serverData[i].bac_5_q_u.toInt(),
//                                                    serverData[i].bac_6_q_u.toInt(),
//                                                    serverData[i].createdAt,
//                                                    getCurrentDateTime(),
//                                                    serverData[i].__v,
//                                                    1
//                                                )
//                                            )
//                                        }
//
//                                    } else {
//                                        Toast.makeText(
//                                            applicationContext,
//                                            "Error Data missing",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    }
//                                }
//                                if (status != -1) {
//                                    successfulDialog(this@ServerActivity, "All Product Updated")
//                                }
//                            }
//                            Common.dismissLoadingProgress()
//
//                        } else {
//                            Common.dismissLoadingProgress()
//                            Common.showErrorFullMsg(this@ServerActivity, "Error with apis")
//                        }
//                    }
//
//                    override fun onFailure(
//                        call: Call<ListResponse<AllProductServerModel>>,
//                        t: Throwable
//                    ) {
//                        Common.dismissLoadingProgress()
//                        tvShow.text = t.toString()
//                        println(t.toString())
//                        Common.alertErrorOrValidationDialog(
//                            this@ServerActivity,
//                            resources.getString(R.string.error_msg)
//                        )
//                    }
//
//                })
//
//            }
//        }
//    }

    fun successfulDialog(act: Activity, msg: String?) {
        var dialog: Dialog? = null
        try {
            dialog?.dismiss()
            dialog = Dialog(act, R.style.AppCompatAlertDialogStyleBig)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            val mInflater = LayoutInflater.from(act)
            val mView = mInflater.inflate(R.layout.dlg_validation, null, false)
            val textDesc: TextView = mView.findViewById(R.id.tvMessage)
            textDesc.text = msg
            val tvOk: TextView = mView.findViewById(R.id.tvOk)
            val finalDialog: Dialog = dialog
            tvOk.setOnClickListener {
                finalDialog.dismiss()
//              startActivity(Intent(this@RegistrationActivity,OTPVerificatinActivity::class.java).putExtra("email", edEmail.text.toString()))
//                openActivity(LoginActivity::class.java)
//                finish()
            }
            dialog.setContentView(mView)
            dialog.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        val intent=Intent(this@ServerActivity,DashboardActivity::class.java).putExtra("pos","1")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        this@ServerActivity.getCurrentLanguage(false)
    }
}
