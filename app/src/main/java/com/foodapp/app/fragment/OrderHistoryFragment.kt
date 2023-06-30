package com.foodapp.app.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.app.R
import com.foodapp.app.activity.DashboardActivity
import com.foodapp.app.activity.OrderDetailActivity
import com.foodapp.app.base.BaseAdaptor
import com.foodapp.app.base.BaseFragmnet
import com.foodapp.app.model.OrderHistoryModel
import com.foodapp.app.utils.Common
import com.foodapp.app.utils.Common.alertErrorOrValidationDialog
import com.foodapp.app.utils.Common.getCurrentLanguage
import com.foodapp.app.utils.Common.getDate
import com.foodapp.app.utils.SharePreference
import com.foodapp.app.utils.SharePreference.Companion.getStringSharedPrefs
import kotlinx.android.synthetic.main.fragment_orderhistory.*
import java.util.*


class OrderHistoryFragment : BaseFragmnet() {
    override fun setView(): Int {
        return R.layout.fragment_orderhistory
    }

    override fun Init(view: View) {
        requireActivity().getCurrentLanguage(false)
        if (Common.isCheckNetwork(requireActivity())) {
            //callApiOrderHistory()
        } else {
            alertErrorOrValidationDialog(requireActivity(),resources.getString(R.string.no_internet))
        }

        ivMenu.setOnClickListener {
            (activity as DashboardActivity?)!!.onDrawerToggle()
        }

        swiperefresh.setOnRefreshListener {
            if (Common.isCheckNetwork(requireActivity())) {
                swiperefresh.isRefreshing=false
                //callApiOrderHistory()
            } else {
                alertErrorOrValidationDialog(requireActivity(),resources.getString(R.string.no_internet))
            }
        }
    }

//    private fun callApiOrderHistory() {
//        showLoadingProgress(activity!!)
//        val map = HashMap<String, String>()
//        map.put("user_id", getStringPref(activity!!, userId)!!)
//        val call = ApiClient.getClient.getOrderHistory(map)
//        call.enqueue(object : Callback<ListResponse<OrderHistoryModel>> {
//            override fun onResponse(
//                call: Call<ListResponse<OrderHistoryModel>>,
//                response: Response<ListResponse<OrderHistoryModel>>
//            ) {
//                if (response.code() == 200) {
//                    dismissLoadingProgress()
//                    val restResponce: ListResponse<OrderHistoryModel> = response.body()!!
//                    if (restResponce.getStatus().equals("1")) {
//                        if (restResponce.getData().size > 0) {
//                            rvOrderHistory.visibility = View.VISIBLE
//                            tvNoDataFound.visibility = View.GONE
//                            val foodCategoryList = restResponce.getData()
//                            setFoodCategoryAdaptor(foodCategoryList)
//                        } else {
//                            rvOrderHistory.visibility = View.GONE
//                            tvNoDataFound.visibility = View.VISIBLE
//                        }
//
//                    } else if (restResponce.getStatus().equals("0")) {
//                        dismissLoadingProgress()
//                        rvOrderHistory.visibility = View.GONE
//                        tvNoDataFound.visibility = View.VISIBLE
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ListResponse<OrderHistoryModel>>, t: Throwable) {
//                dismissLoadingProgress()
//                alertErrorOrValidationDialog(
//                    activity!!,
//                    resources.getString(R.string.error_msg)
//                )
//            }
//        })
//    }
//
    fun setFoodCategoryAdaptor(orderHistoryList: ArrayList<OrderHistoryModel>) {
        val orderHistoryAdapter =
            object : BaseAdaptor<OrderHistoryModel>(requireActivity(), orderHistoryList) {
                @SuppressLint("SetTextI18n", "NewApi", "UseCompatLoadingForDrawables")
                override fun onBindData(
                    holder: RecyclerView.ViewHolder?,
                    `val`: OrderHistoryModel,
                    position: Int
                ) {
                    val tvOrderNumber: TextView = holder!!.itemView.findViewById(R.id.tvOrderNumber)
                    val tvPrice: TextView = holder.itemView.findViewById(R.id.tvPrice)
                    val tvQtyNumber: TextView = holder.itemView.findViewById(R.id.tvQtyNumber)
                    val tvOrderStatus: TextView = holder.itemView.findViewById(R.id.tvOrderStatus)
                    val tvPaymentType: TextView = holder.itemView.findViewById(R.id.tvPaymentType)
                    val tvOrderDate: TextView = holder.itemView.findViewById(R.id.tvOrderDate)
                    val llStatusImage: LinearLayout = holder.itemView.findViewById(R.id.llData)
                    val llStatusView: LinearLayout = holder.itemView.findViewById(R.id.llView)
                    val ivArrow: ImageView = holder.itemView.findViewById(R.id.ivArrow)

                    val tvOrderStatus1: TextView = holder.itemView.findViewById(R.id.tvOrderStatus1)
                    val tvOrderStatus2: TextView = holder.itemView.findViewById(R.id.tvOrderStatus2)
                    val tvOrderStatus3: TextView = holder.itemView.findViewById(R.id.tvOrderStatus3)
                    val tvOrderStatus4: TextView = holder.itemView.findViewById(R.id.tvOrderStatus4)

                    val ivStatus1: ImageView = holder.itemView.findViewById(R.id.ivStatus1)
                    val ivStatus2: ImageView = holder.itemView.findViewById(R.id.ivStatus2)
                    val ivStatus3: ImageView = holder.itemView.findViewById(R.id.ivStatus3)
                    val ivStatus4: ImageView = holder.itemView.findViewById(R.id.ivStatus4)

                    val view1: View = holder.itemView.findViewById(R.id.view1)
                    val view2: View = holder.itemView.findViewById(R.id.view2)
                    val view3: View = holder.itemView.findViewById(R.id.view3)
                    val v3: View = holder.itemView.findViewById(R.id.v3)


                    tvOrderNumber.text = orderHistoryList.get(position).getOrder_number()
                    tvPrice.text = getStringSharedPrefs(activity!!,SharePreference.isCurrancy) +String.format(Locale.US,"%,.2f",orderHistoryList.get(position).getTotal_price()!!.toDouble())
                    tvQtyNumber.text = orderHistoryList.get(position).getQty()


                    if(orderHistoryList.get(position).getOrder_type().equals("1")){
                        view3.visibility=View.VISIBLE
                        v3.visibility=View.VISIBLE
                        ivStatus4.visibility=View.VISIBLE
                        tvOrderStatus4.visibility=View.VISIBLE
                        if(orderHistoryList[position].getStatus().equals("1")){
                            tvOrderStatus.text=resources.getString(R.string.order_place)

                            tvOrderStatus1.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus2.setTextColor(ContextCompat.getColor(activity!!,R.color.gray))
                            tvOrderStatus3.setTextColor(ContextCompat.getColor(activity!!,R.color.gray))
                            tvOrderStatus4.setTextColor(ContextCompat.getColor(activity!!,R.color.gray))

                            ivStatus1.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus2.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_check))
                            ivStatus3.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_check))
                            ivStatus4.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_check))

                            ivStatus1.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus2.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.gray))
                            ivStatus3.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.gray))
                            ivStatus4.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.gray))

                            view1.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.gray))
                            view2.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.gray))
                            view3.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.gray))

                        }else if(orderHistoryList[position].getStatus().equals("2")) {
                            tvOrderStatus.text=resources.getString(R.string.order_ready)

                            tvOrderStatus1.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus2.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus3.setTextColor(ContextCompat.getColor(activity!!,R.color.gray))
                            tvOrderStatus4.setTextColor(ContextCompat.getColor(activity!!,R.color.gray))

                            ivStatus1.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus2.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus3.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_check))
                            ivStatus4.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_check))

                            ivStatus1.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus2.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus3.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.gray))
                            ivStatus4.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.gray))

                            view1.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            view2.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.gray))
                            view3.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.gray))

                        }else if(orderHistoryList[position].getStatus().equals("3")){
                            tvOrderStatus.text=resources.getString(R.string.on_the_way)


                            tvOrderStatus1.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus2.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus3.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus4.setTextColor(ContextCompat.getColor(activity!!,R.color.gray))

                            ivStatus1.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus2.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus3.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus4.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_check))

                            ivStatus1.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus2.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus3.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus4.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.gray))

                            view1.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            view2.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            view3.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.gray))

                        }else if(orderHistoryList.get(position).getStatus().equals("4")){
                            tvOrderStatus.text=resources.getString(R.string.order_delivered)

                            tvOrderStatus1.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus2.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus3.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus4.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))

                            ivStatus1.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus2.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus3.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus4.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))

                            ivStatus1.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus2.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus3.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus4.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))

                            view1.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            view2.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            view3.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                        }
                    }else{
                        view3.visibility=View.GONE
                        v3.visibility=View.GONE
                        ivStatus4.visibility=View.GONE
                        tvOrderStatus4.visibility=View.GONE
                        tvOrderStatus3.text=resources.getString(R.string.order_delivered)
                        if(orderHistoryList.get(position).getStatus().equals("1")){
                            tvOrderStatus.text=resources.getString(R.string.order_place)

                            tvOrderStatus1.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus2.setTextColor(ContextCompat.getColor(activity!!,R.color.gray))
                            tvOrderStatus3.setTextColor(ContextCompat.getColor(activity!!,R.color.gray))

                            ivStatus1.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus2.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_check))
                            ivStatus3.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_check))

                            ivStatus1.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus2.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.gray))
                            ivStatus3.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.gray))

                            view1.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.gray))
                            view2.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.gray))

                        }else if(orderHistoryList.get(position).getStatus().equals("2")) {
                            tvOrderStatus.text=resources.getString(R.string.order_ready)

                            tvOrderStatus1.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus2.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus3.setTextColor(ContextCompat.getColor(activity!!,R.color.gray))

                            ivStatus1.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus2.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus3.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_check))


                            ivStatus1.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus2.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus3.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.gray))


                            view1.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            view2.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.gray))


                        }else if(orderHistoryList.get(position).getStatus().equals("4")){
                            tvOrderStatus.text=resources.getString(R.string.order_delivered)


                            tvOrderStatus1.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus2.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            tvOrderStatus3.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))

                            ivStatus1.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus2.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))
                            ivStatus3.setImageDrawable(ContextCompat.getDrawable(activity!!,R.drawable.ic_round_uncheck))

                            ivStatus1.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus2.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            ivStatus3.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(activity!!,R.color.colorPrimary))

                            view1.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))
                            view2.setBackgroundColor(ContextCompat.getColor(activity!!,R.color.colorPrimary))

                        }
                    }

                    if(orderHistoryList.get(position).isCheck()!!){
                        llStatusImage.visibility=View.VISIBLE
                        llStatusView.visibility=View.VISIBLE
                        tvOrderStatus.visibility=View.GONE
                        ivArrow.rotation=180f
                    }else{
                        tvOrderStatus.visibility=View.VISIBLE
                        llStatusImage.visibility=View.GONE
                        llStatusView.visibility=View.GONE
                        ivArrow.rotation=360f
                    }

                    ivArrow.setOnClickListener {
                        if(orderHistoryList.get(position).isCheck()==true){
                            orderHistoryList.get(position).setCheck(false)
                            notifyDataSetChanged()
                        }else{
                            for (i in 0 until orderHistoryList.size){
                                orderHistoryList.get(i).setCheck(false)
                            }
                            orderHistoryList.get(position).setCheck(true)
                            notifyDataSetChanged()
                        }

                    }


                    tvOrderDate.text=getDate(orderHistoryList.get(position).getDate()!!)

                    if(orderHistoryList.get(position).getPayment_type()!!.toInt()==0){
                        tvPaymentType.text = "PAY BY CASH"
                    }else if(orderHistoryList.get(position).getPayment_type()!!.toInt()==1) {
                        tvPaymentType.text = "Razorpay"
                    }else{
                        tvPaymentType.text = "Stripe"
                    }

                    holder.itemView.setOnClickListener {
                      startActivity(Intent(activity!!,OrderDetailActivity::class.java).putExtra("order_id",orderHistoryList.get(position).getId()).putExtra("order_status",orderHistoryList.get(position).getStatus()))
                    }
                }

                override fun setItemLayout(): Int {
                    return R.layout.row_orderdelivery
                }

                override fun setNoDataView(): TextView? {
                    return null
                }
            }
        rvOrderHistory.adapter = orderHistoryAdapter
        rvOrderHistory.layoutManager = LinearLayoutManager(requireActivity())
        rvOrderHistory.itemAnimator = DefaultItemAnimator()
        rvOrderHistory.isNestedScrollingEnabled = true
    }

    override fun onResume() {
        super.onResume()
        requireActivity().getCurrentLanguage(false)
    }
}