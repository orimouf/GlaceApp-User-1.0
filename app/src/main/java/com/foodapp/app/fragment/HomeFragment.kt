package com.foodapp.app.fragment

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodapp.app.R
import com.foodapp.app.activity.*
import com.foodapp.app.base.BaseFragmnet
import com.foodapp.app.model.*
import com.foodapp.app.utils.SharePreference
import com.foodapp.app.activity.DatabaseHandler
import com.foodapp.app.adaptor.ClientAdaptor
import com.foodapp.app.adaptor.RegionAdaptor
import com.foodapp.app.api.*
import com.foodapp.app.utils.Common.getCurrentLanguage
import com.foodapp.app.utils.SharePreference.Companion.getStringSharedPrefs
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.ivCart
import kotlinx.android.synthetic.main.fragment_home.ivMenu
import kotlinx.android.synthetic.main.fragment_home.swiperefresh
import kotlin.collections.ArrayList

@Suppress("UNSAFE_CALL_ON_PARTIALLY_DEFINED_RESOURCE")
class HomeFragment : BaseFragmnet() , Communicator {

    var manager1: GridLayoutManager? = null
    var indexItemRV : Int = 0

    override fun setView(): Int {
        return R.layout.fragment_home
    }

    /**
     * Function is used to show the list of inserted data.
     */
    private fun setupListOfClientDataIntoRecyclerView(regionName: String = "") {
        if (getClientList(regionName).size > 0) {
            rvClientList.visibility = View.VISIBLE

            rvClientList.layoutManager = LinearLayoutManager(requireContext())
            val clientAdapter = ClientAdaptor(requireContext(), getClientList(regionName), false)
            rvClientList.adapter = clientAdapter

        } else {
            rvClientList.visibility = View.GONE
        }
    }

    private fun setupListOfRegionDataIntoRecyclerView(position: Int = 0) {
        if (getRegionList().size > 0) {
            rvClientRegion.visibility = View.VISIBLE

            rvClientRegion.layoutManager = LinearLayoutManager(requireContext())

            val regionAdapter = RegionAdaptor(this.requireContext(), getRegionList(), this@HomeFragment, position)
            rvClientRegion.adapter = regionAdapter
            rvClientRegion.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            rvClientRegion.itemAnimator = DefaultItemAnimator()
            rvClientRegion.isNestedScrollingEnabled = true

            scrollToPosition(indexItemRV)
        } else {
            rvClientRegion.visibility = View.GONE
        }
    }

    private fun scrollToPosition(position: Int) {
        rvClientRegion.stopScroll()
        (rvClientRegion.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(position, 0)
    }

    /**
     * Function is used to get the Client List from the database table.
     */
    private fun getClientList(regionName: String): ArrayList<ClientModel> {
        val databaseHandler: DatabaseHandler = DatabaseHandler(requireContext())

        return databaseHandler.viewClient(-1, false, regionName)
    }

    /**
     * Function is used to get the Region List from the database table.
     */
    private fun getRegionList(): ArrayList<RegionModel> {
        val databaseHandler: DatabaseHandler = DatabaseHandler(requireContext())

        return databaseHandler.viewRegion()
    }

    override fun Init(view: View) {
        requireActivity().getCurrentLanguage(false)

        if (getStringSharedPrefs(requireContext(), SharePreference.isAdmin).equals("true")) {
            ivUsers.visibility = View.VISIBLE
        }

        setupListOfClientDataIntoRecyclerView()
        setupListOfRegionDataIntoRecyclerView()

//        if (SharePreference.getBooleanPref(activity!!, isLinearLayoutManager)) {
//            manager1 = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
//            ic_grid.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_listitem,null))
//        } else {
//            manager1 = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
//            ic_grid.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_grid,null))
//        }
//        rvFoodSubcategory.layoutManager = manager1
//
//        if (isCheckNetwork(activity!!)) {
//            //callApiBanner()
//        } else {
//            alertErrorOrValidationDialog(activity!!, resources.getString(R.string.no_internet))
//        }
        ivMenu.setOnClickListener {
            (activity as DashboardActivity?)!!.onDrawerToggle()
        }

        ivCart.setOnClickListener {
//            if (SharePreference.getBooleanPref(activity!!, SharePreference.isLogin)) {
//                openActivity(CartActivity::class.java)
//            }else{
                openActivity(LoginActivity::class.java)
                requireActivity().finish()
                requireActivity().finishAffinity()
//            }
        }

        tvSearch.setOnClickListener {
            openActivity(SearchActivity::class.java)
            requireActivity().finish()
        }

        ivUsers.setOnClickListener {
            openActivity(UsersActivity::class.java)
            requireActivity().finish()
        }

        swiperefresh.setOnRefreshListener { // Your code to refresh the list here.
            setupListOfClientDataIntoRecyclerView()
            swiperefresh.isRefreshing=false
//            if(isCheckNetwork(activity!!)){
//                swiperefresh.isRefreshing=false
//                foodList!!.clear()
//                isLoding = true
//                CurrentPageNo = 1
//                TOTAL_PAGES = 0
//                if (isCheckNetwork(activity!!)) {
//                    //callApiBanner()
//                } else {
//                    alertErrorOrValidationDialog(activity!!,resources.getString(R.string.no_internet))
//                }
//            }else{
//                alertErrorOrValidationDialog(
//                    activity!!,
//                    resources.getString(R.string.no_internet)
//                )
//            }
        }
    }

    override fun passData(position: Int, name: String) {
//        val bundle = Bundle()
//        bundle.putInt("input_pos", position)
//        bundle.putString("input_name", name)
        indexItemRV = position
        setupListOfRegionDataIntoRecyclerView(position)
        setupListOfClientDataIntoRecyclerView(name)
    }

//    override fun onPause() {
//        super.onPause()
//        if (timer != null)
//            timer!!.cancel()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Common.getCurrentLanguage(activity!!, false)
////        if (SharePreference.getBooleanPref(activity!!, SharePreference.isLogin)) {
////            if (Common.isCartTrueOut) {
////                if (isCheckNetwork(activity!!)) {
////                    Common.isCartTrueOut = false
//////                    callApiCartCount(false, true)
////                    setupListOfClientDataIntoRecyclerView()
////                } else {
////                    alertErrorOrValidationDialog(
////                        activity!!,
////                        resources.getString(R.string.no_internet)
////                    )
////                }
////            }
////        } else {
////            rlCount!!.visibility = View.GONE
////        }
//
//    }
}

