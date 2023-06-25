package com.foodapp.app.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.glaceapp.DatabaseHandler
import com.foodapp.app.R
import com.foodapp.app.activity.AddProductActivity
import com.foodapp.app.activity.CartActivity
import com.foodapp.app.activity.OrderDetailActivity
import com.foodapp.app.activity.UsersActivity
import com.foodapp.app.model.*
import com.foodapp.app.utils.Common.alertErrorOrValidationDialog
import kotlinx.android.synthetic.main.activity_orderdetail.*
import kotlinx.android.synthetic.main.activity_orderdetail.view.*
import kotlinx.android.synthetic.main.dlg_updateproduct.*
import kotlinx.android.synthetic.main.row_cart.view.*
import kotlinx.android.synthetic.main.row_orderitemsummary.view.*
import kotlinx.android.synthetic.main.row_user.view.*
import java.lang.Integer.parseInt

class OrderSummuryAdaptor (val context: Context, private val products: ArrayList<AllProductModel>,
                           private val pricesList: Array<String>, private val orderId: Int) :
    RecyclerView.Adapter<OrderSummuryAdaptor.ViewHolder>() {

    var miniDone = false; var trioDone = false; var soloDone = false; var potDone = false; var cornito4Done = false; var cornito5Done = false;
    var cornitogDone = false; var giniDone = false; var bigDone = false; var gofritoDone = false; var potvDone = false;
    var g8Done = false; var goldDone = false; var skiperDone = false; var scobidoDone = false; var miniscobidoDone = false;
    var veneziaDone = false; var bf400Done = false; var bf250Done = false; var bf230Done = false; var bf200Done = false; var bf150Done = false;
    var buchDone = false; var tarteDone = false; var mostaDone = false; var missoDone = false; var julianaDone = false;
    var bac5Done = false; var bac6Done = false; var totalToPay: Float = 0F; var nomberItems = 0; var productMsg = ""

    /**
     * Inflates the item views which is designed in the XML layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_orderitemsummary,
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = products.get(0)
        outputView(holder, product, pricesList)


//        holder.ivEditUser.setOnClickListener { view ->
//            if (context is UsersActivity) {
//                context.updateUserDialog(order)
//            }
//        }
//
//        holder.ivDeleteCartUser.setOnClickListener { view ->
//            if (context is UsersActivity) {
//                context.deleteUserAlertDialog(order)
//            }
//        }

    }


    private fun outputView (holder: ViewHolder, order: AllProductModel, priceUnity: Array<String>) {

//        "mini","trio","pot","solo","gini","pot vally","big","cornito 45"or"cornito 50","cornito gini",
//        "gofrito","g8","gold","skiper","scobido","mini scobido","venezia","B.F 2L","B.F 1L",
//        "B.F 900ml","B.F 750ml","B.F 0,5L","buch","tarte","mosta","misso","juliana","bac 5L","bac 6L"

        if (order.mini_qty !== 0 && !miniDone) {
            miniDone = true
            val UP = (order.mini_qty * order.mini_q_u * priceUnity[0].toFloat()).toInt().toString()
            holder.tvProductName.text = "MINI"
            holder.tvProductDetails.text = order.mini_qty.toString() + "  *  " + order.mini_q_u.toString() + "  *  " + priceUnity[0]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.mini_qty * order.mini_q_u * priceUnity[0].toFloat())
            productMsg += "   MINI            ${order.mini_qty} * ${order.mini_q_u} * ${priceUnity[0]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.trio_qty !== 0 && !trioDone) {
            trioDone = true
            val UP = (order.trio_qty * order.trio_q_u * priceUnity[1].toFloat()).toInt().toString()
            holder.tvProductName.text = "TRIO"
            holder.tvProductDetails.text = order.trio_qty.toString() + "  *  " + order.trio_q_u.toString() + "  *  " + priceUnity[1]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.trio_qty * order.trio_q_u * priceUnity[1].toFloat())
            productMsg += "   TRIO             ${order.trio_qty} * ${order.trio_q_u} * ${priceUnity[1]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.pot_qty !== 0 && !potDone) {
            potDone = true
            val UP = (order.pot_qty * order.pot_q_u * priceUnity[2].toFloat()).toInt().toString()
            holder.tvProductName.text = "POT"
            holder.tvProductDetails.text = order.pot_qty.toString() + "  *  " + order.pot_q_u.toString() + "  *  " + priceUnity[2]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.pot_qty * order.pot_q_u * priceUnity[2].toFloat())
            productMsg += "   POT             ${order.pot_qty} * ${order.pot_q_u} * ${priceUnity[2]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.solo_qty !== 0 && !soloDone) {
            soloDone = true
            val UP = (order.solo_qty * order.solo_q_u * priceUnity[3].toFloat()).toInt().toString()
            holder.tvProductName.text = "SOLO"
            holder.tvProductDetails.text = order.solo_qty.toString() + "  *  " + order.solo_q_u.toString() + "  *  " + priceUnity[3]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.solo_qty * order.solo_q_u * priceUnity[3].toFloat())
            productMsg += "   SOLO            ${order.solo_qty} * ${order.solo_q_u} * ${priceUnity[3]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.gini_qty !== 0 && !giniDone) {
            giniDone = true
            val UP = (order.gini_qty * order.gini_q_u * priceUnity[4].toFloat()).toInt().toString()
            holder.tvProductName.text = "GINI"
            holder.tvProductDetails.text = order.gini_qty.toString() + "  *  " + order.gini_q_u.toString() + "  *  " + priceUnity[4]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.gini_qty * order.gini_q_u * priceUnity[4].toFloat())
            productMsg += "   GINI            ${order.gini_qty} * ${order.gini_q_u} * ${priceUnity[4]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.pot_v_qty !== 0 && !potvDone) {
            potvDone = true
            val UP = (order.pot_v_qty * order.pot_v_q_u * priceUnity[5].toFloat()).toInt().toString()
            holder.tvProductName.text = "POT VALLY"
            holder.tvProductDetails.text = order.pot_v_qty.toString() + "  *  " + order.pot_v_q_u.toString() + "  *  " + priceUnity[5]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.pot_v_qty * order.pot_v_q_u * priceUnity[5].toFloat())
            productMsg += "   POT VALLY       ${order.pot_v_qty} * ${order.pot_v_q_u} * ${priceUnity[5]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.big_qty !== 0 && !bigDone) {
            bigDone = true
            val UP = (order.big_qty * order.big_q_u * priceUnity[6].toFloat()).toInt().toString()
            holder.tvProductName.text = "BIG"
            holder.tvProductDetails.text = order.big_qty.toString() + "  *  " + order.big_q_u.toString() + "  *  " + priceUnity[6]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.big_qty * order.big_q_u * priceUnity[6].toFloat())
            productMsg += "   BIG             ${order.big_qty} * ${order.big_q_u} * ${priceUnity[6]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.cornito_4_qty !== 0 && !cornito4Done) {
            cornito4Done = true
            val UP = (order.cornito_4_qty * order.cornito_4_q_u * priceUnity[7].toFloat()).toInt().toString()
            holder.tvProductName.text = "CORNITO"
            holder.tvProductDetails.text = order.cornito_4_qty.toString() + "  *  " + order.cornito_4_q_u.toString() + "  *  " + priceUnity[7]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.cornito_4_qty * order.cornito_4_q_u * priceUnity[7].toFloat())
            productMsg += "   CORNITO         ${order.cornito_4_qty} * ${order.cornito_4_q_u} * ${priceUnity[7]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.cornito_5_qty !== 0 && !cornito5Done) {
            cornito5Done = true
            val UP = (order.cornito_5_qty * order.cornito_5_q_u * priceUnity[7].toFloat()).toInt().toString()
            holder.tvProductName.text = "CORNITO"
            holder.tvProductDetails.text = order.cornito_5_qty.toString() + "  *  " + order.cornito_5_q_u.toString() + "  *  " + priceUnity[7]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.cornito_5_qty * order.cornito_5_q_u * priceUnity[7].toFloat())
            productMsg += "   CORNITO         ${order.cornito_5_qty} * ${order.cornito_5_q_u} * ${priceUnity[7]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.cornito_g_qty !== 0 && !cornitogDone) {
            cornitogDone = true
            val UP = (order.cornito_g_qty * order.cornito_g_q_u * priceUnity[8].toFloat()).toInt().toString()
            holder.tvProductName.text = "CORNITO GINI"
            holder.tvProductDetails.text = order.cornito_g_qty.toString() + "  *  " + order.cornito_g_q_u.toString() + "  *  " + priceUnity[8]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.cornito_g_qty * order.cornito_g_q_u * priceUnity[8].toFloat())
            productMsg += "   CORNITO GINI    ${order.cornito_g_qty} * ${order.cornito_g_q_u} * ${priceUnity[8]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.gofrito_qty !== 0 && !gofritoDone) {
            gofritoDone = true
            val UP = (order.gofrito_qty * order.gofrito_q_u * priceUnity[9].toFloat()).toInt().toString()
            holder.tvProductName.text = "GOFRITO"
            holder.tvProductDetails.text = order.gofrito_qty.toString() + "  *  " + order.gofrito_q_u.toString() + "  *  " + priceUnity[9]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.gofrito_qty * order.gofrito_q_u * priceUnity[9].toFloat())
            productMsg += "   GOFRITO         ${order.gofrito_qty} * ${order.gofrito_q_u} * ${priceUnity[9]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.g8_qty !== 0 && !g8Done) {
            g8Done = true
            val UP = (order.g8_qty * order.g8_q_u * priceUnity[10].toFloat()).toInt().toString()
            holder.tvProductName.text = "G8"
            holder.tvProductDetails.text = order.g8_qty.toString() + "  *  " + order.g8_q_u.toString() + "  *  " + priceUnity[10]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.g8_qty * order.g8_q_u * priceUnity[10].toFloat())
            productMsg += "   G8              ${order.g8_qty} * ${order.g8_q_u} * ${priceUnity[10]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.gold_qty !== 0 && !goldDone) {
            goldDone = true
            val UP = (order.gold_qty * order.gold_q_u * priceUnity[11].toFloat()).toInt().toString()
            holder.tvProductName.text = "GOLD"
            holder.tvProductDetails.text = order.gold_qty.toString() + "  *  " + order.gold_q_u.toString() + "  *  " + priceUnity[11]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.gold_qty * order.gold_q_u * priceUnity[11].toFloat())
            productMsg += "   GOLD            ${order.gold_qty} * ${order.gold_q_u} * ${priceUnity[11]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.skiper_qty !== 0 && !skiperDone) {
            skiperDone = true
            val UP = (order.skiper_qty * order.skiper_q_u * priceUnity[12].toFloat()).toInt().toString()
            holder.tvProductName.text = "SKIPER"
            holder.tvProductDetails.text = order.skiper_qty.toString() + "  *  " + order.skiper_q_u.toString() + "  *  " + priceUnity[12]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.skiper_qty * order.skiper_q_u * priceUnity[12].toFloat())
            productMsg += "   SKIPER          ${order.skiper_qty} * ${order.skiper_q_u} * ${priceUnity[12]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.scobido_qty !== 0 && !scobidoDone) {
            scobidoDone = true
            val UP = (order.scobido_qty * order.scobido_q_u * priceUnity[13].toFloat()).toInt().toString()
            holder.tvProductName.text = "SCOBIDO"
            holder.tvProductDetails.text = order.scobido_qty.toString() + "  *  " + order.scobido_q_u.toString() + "  *  " + priceUnity[13]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.scobido_qty * order.scobido_q_u * priceUnity[13].toFloat())
            productMsg += "   SCOBIDO         ${order.scobido_qty} * ${order.scobido_q_u} * ${priceUnity[13]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.mini_scobido_qty !== 0 && !miniscobidoDone) {
            miniscobidoDone = true
            val UP = (order.mini_scobido_qty * order.mini_scobido_q_u * priceUnity[14].toFloat()).toInt().toString()
            holder.tvProductName.text = "MINI SCOBIDO"
            holder.tvProductDetails.text = order.mini_scobido_qty.toString() + "  *  " + order.mini_scobido_q_u.toString() + "  *  " + priceUnity[14]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.mini_scobido_qty * order.mini_scobido_q_u * priceUnity[14].toFloat())
            productMsg += "   MINI SCOBIDO    ${order.mini_scobido_qty} * ${order.mini_scobido_q_u} * ${priceUnity[14]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.venezia_qty !== 0 && !veneziaDone) {
            veneziaDone = true
            val UP = (order.venezia_qty * order.venezia_q_u * priceUnity[15].toFloat()).toInt().toString()
            holder.tvProductName.text = "VENEZIA"
            holder.tvProductDetails.text = order.venezia_qty.toString() + "  *  " + order.venezia_q_u.toString() + "  *  " + priceUnity[15]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.venezia_qty * order.venezia_q_u * priceUnity[15].toFloat())
            productMsg += "   VENEZIA         ${order.venezia_qty} * ${order.venezia_q_u} * ${priceUnity[15]}      $UP,00 Da \n"
            nomberItems++
        } else if (order.bf_400_q_u !== 0 && !bf400Done) {
            bf400Done = true
            val UP = (order.bf_400_q_u * priceUnity[16].toFloat()).toInt().toString()
            holder.tvProductName.text = "B.F 2L"
            holder.tvProductDetails.text = order.bf_400_q_u.toString() + "  *  " + priceUnity[16]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.bf_400_q_u * priceUnity[16].toFloat())
            productMsg += "   B.F 2L          ${order.bf_400_q_u} * ${priceUnity[16]}          $UP,00 Da \n"
            nomberItems++
        } else if (order.bf_250_q_u !== 0 && !bf250Done) {
            bf250Done = true
            val UP = (order.bf_250_q_u * priceUnity[17].toFloat()).toInt().toString()
            holder.tvProductName.text = "B.F 1L"
            holder.tvProductDetails.text = order.bf_250_q_u.toString() + "  *  " + priceUnity[17]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.bf_250_q_u * priceUnity[17].toFloat())
            productMsg += "   B.F 1L          ${order.bf_250_q_u} * ${priceUnity[17]}          $UP,00 Da \n"
            nomberItems++
        } else if (order.bf_230_q_u !== 0 && !bf230Done) {
            bf230Done = true
            val UP = (order.bf_230_q_u * priceUnity[18].toFloat()).toInt().toString()
            holder.tvProductName.text = "B.F 900ml"
            holder.tvProductDetails.text = order.bf_230_q_u.toString() + "  *  " + priceUnity[18]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.bf_230_q_u * priceUnity[18].toFloat())
            productMsg += "   B.F 900mL       ${order.bf_230_q_u} * ${priceUnity[18]}          $UP,00 Da \n"
            nomberItems++
        } else if (order.bf_200_q_u !== 0 && !bf200Done) {
            bf200Done = true
            val UP = (order.bf_200_q_u * priceUnity[19].toFloat()).toInt().toString()
            holder.tvProductName.text = "B.F 750ml"
            holder.tvProductDetails.text = order.bf_200_q_u.toString() + "  *  " + priceUnity[19]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.bf_200_q_u * priceUnity[19].toFloat())
            productMsg += "   B.F 750mL       ${order.bf_200_q_u} * ${priceUnity[19]}          $UP,00 Da \n"
            nomberItems++
        } else if (order.bf_150_q_u !== 0 && !bf150Done) {
            bf150Done = true
            val UP = (order.bf_150_q_u * priceUnity[20].toFloat()).toInt().toString()
            holder.tvProductName.text = "B.F 600ml"
            holder.tvProductDetails.text = order.bf_150_q_u.toString() + "  *  " + priceUnity[20]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.bf_150_q_u * priceUnity[20].toFloat())
            productMsg += "   B.F 600mL       ${order.bf_150_q_u} * ${priceUnity[20]}          $UP,00 Da \n"
            nomberItems++
        } else if (order.buch_q_u !== 0 && !buchDone) {
            buchDone = true
            val UP = (order.buch_q_u * priceUnity[21].toFloat()).toInt().toString()
            holder.tvProductName.text = "BUCH"
            holder.tvProductDetails.text = order.buch_q_u.toString() + "  *  " + priceUnity[21]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.buch_q_u * priceUnity[21].toFloat())
            productMsg += "   BUCH            ${order.buch_q_u} * ${priceUnity[21]}          $UP,00 Da \n"
            nomberItems++
        } else if (order.tarte_q_u !== 0 && !tarteDone) {
            tarteDone = true
            val UP = (order.tarte_q_u * priceUnity[22].toFloat()).toInt().toString()
            holder.tvProductName.text = "TARTE"
            holder.tvProductDetails.text = order.tarte_q_u.toString() + "  *  " + priceUnity[22]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.tarte_q_u * priceUnity[22].toFloat())
            productMsg += "   TARTE           ${order.tarte_q_u} * ${priceUnity[22]}          $UP,00 Da \n"
            nomberItems++
        } else if (order.mosta_q_u !== 0 && !mostaDone) {
            mostaDone = true
            val UP = (order.mosta_q_u * priceUnity[23].toFloat()).toInt().toString()
            holder.tvProductName.text = "MOSTA"
            holder.tvProductDetails.text = order.mosta_q_u.toString() + "  *  " + priceUnity[23]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.mosta_q_u * priceUnity[23].toFloat())
            productMsg += "   MOSTA           ${order.mosta_q_u} * ${priceUnity[23]}           $UP,00 Da \n"
            nomberItems++
        } else if (order.misso_q_u !== 0 && !missoDone) {
            missoDone = true
            val UP = (order.misso_q_u * priceUnity[24].toFloat()).toInt().toString()
            holder.tvProductName.text = "MISSO"
            holder.tvProductDetails.text = order.misso_q_u.toString() + "  *  " + priceUnity[24]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.misso_q_u * priceUnity[24].toFloat())
            productMsg += "   MISSO           ${order.misso_q_u} * ${priceUnity[24]}           $UP,00 Da \n"
            nomberItems++
        } else if (order.juliana_q_u !== 0 && !julianaDone) {
            julianaDone = true
            val UP = (order.juliana_q_u * priceUnity[25].toFloat()).toInt().toString()
            holder.tvProductName.text = "JULIANA"
            holder.tvProductDetails.text = order.juliana_q_u.toString() + "  *  " + priceUnity[25]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.juliana_q_u * priceUnity[25].toFloat())
            productMsg += "   JULIANA         ${order.juliana_q_u} * ${priceUnity[25]}           $UP,00 Da \n"
            nomberItems++
        } else if (order.bac_5_q_u !== 0 && !bac5Done) {
            bac5Done = true
            val UP = (order.bac_5_q_u * priceUnity[26].toFloat()).toInt().toString()
            holder.tvProductName.text = "BAC GELATO"
            holder.tvProductDetails.text = order.bac_5_q_u.toString() + "  *  " + priceUnity[26]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.bac_5_q_u * priceUnity[26].toFloat())
            productMsg += "   BAC 5L          ${order.bac_5_q_u} * ${priceUnity[26]}        $UP,00 Da \n"
            nomberItems++
        } else if (order.bac_6_q_u !== 0 && !bac6Done) {
            bac6Done = true
            val UP = (order.bac_6_q_u * priceUnity[27].toFloat()).toInt().toString()
            holder.tvProductName.text = "BAC CASA"
            holder.tvProductDetails.text = order.bac_6_q_u.toString() + "  *  " + priceUnity[27]
            holder.tvPriceRowUnity.text = UP
            totalToPay += (order.bac_6_q_u * priceUnity[27].toFloat())
            productMsg += "   BAC 6L          ${order.bac_6_q_u} * ${priceUnity[27]}       $UP,00 Da \n"
            nomberItems++
        } else {
            holder.productRow.visibility = View.GONE
        }

        if (context is OrderDetailActivity) {
            val databaseHandler = DatabaseHandler(context)
            val status =
                databaseHandler.updateTotalToPayOrderSummary(orderId, totalToPay, 0, 0, 0)
            if (status > -1) {
                context.tvOrderTotalPrice.text = totalToPay.toInt().toString()
                context.tvNumberItems.text = "NOMBRE ARTICLES:    $nomberItems"
                context.tvProductList.text = productMsg
            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return 29 //products.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
        val tvProductName = view.tvProductName
        val tvProductDetails = view.tvProductDetails
        val tvPrice = view.tvPrice
        val tvPriceRowUnity = view.tvPriceRowUnity
        val productRow = view.productRow
        val tvNumberItems = view.tvNumberItems
    }
}