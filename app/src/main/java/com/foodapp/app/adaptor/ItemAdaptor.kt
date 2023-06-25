package com.foodapp.app.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.app.R
import com.foodapp.app.activity.CartActivity
import com.foodapp.app.model.AllProductModel
import com.foodapp.app.model.ItemModel
import com.foodapp.app.utils.Common.alertErrorOrValidationDialog
import kotlinx.android.synthetic.main.activity_cart.view.*
import kotlinx.android.synthetic.main.dlg_updateproduct.*
import kotlinx.android.synthetic.main.row_cart.view.*
import java.lang.Integer.parseInt

class ItemAdapter(val context: Context, val items: ArrayList<ItemModel>, private val products: ArrayList<AllProductModel>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    /**
     * Inflates the item views which is designed in the XML layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_cart,
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

        val item = items[position]
        val product = products[0]
        val itemQty = holder.tvFoodQty.text.toString()

        outputView(holder, product, item, itemQty, item.qty_par_one.toString())

        holder.itemView.ivPlus2.setOnClickListener {
            val currentQtyUnity = parseInt(holder.tvFoodQtyUnity.text.toString())
            val currentQty = holder.tvFoodQty.text.toString()
            val newQty = cartQTYUpdate(currentQtyUnity, position, true)

            if (context is CartActivity) {
                context.updateAllProduct(item, product, newQty, parseInt(currentQty), product.id)
            }
            outputView(holder, product, item, currentQty, newQty.toString())
        }

        holder.itemView.ivMinus2.setOnClickListener {
            val currentQtyUnity = parseInt(holder.tvFoodQtyUnity.text.toString())
            val currentQty = holder.tvFoodQty.text.toString()
            var newQty = 0
            if (currentQtyUnity > 0) {
                newQty = cartQTYUpdate(currentQtyUnity, position, false)
            } else {
                if (context is CartActivity) {
                    alertErrorOrValidationDialog(
                        context,
                        "Minimum quantity allowed 0."
                    )
                }
            }

            if (context is CartActivity) {
                context.updateAllProduct(item, product, newQty, parseInt(currentQty), product.id)
            }
            outputView(holder, product, item, currentQty, newQty.toString())
        }

        holder.itemView.ivPlus.setOnClickListener {
            val currentQtyUnity = holder.tvFoodQtyUnity.text.toString()
            val currentQty = parseInt(holder.tvFoodQty.text.toString())
            val newQty = cartQTYUpdate(currentQty, position, true)
//            holder.tvFoodQty.text = newQty.toString()

            if (context is CartActivity) {
                context.updateAllProduct(item, product, parseInt(currentQtyUnity), newQty, product.id)
            }
            outputView(holder, product, item, newQty.toString(), currentQtyUnity)
        }

        holder.itemView.ivMinus.setOnClickListener {
            val currentQtyUnity = holder.tvFoodQtyUnity.text.toString()
            val currentQty = parseInt(holder.tvFoodQty.text.toString())
            var newQty = 0
            if (currentQty > 0) {
                newQty = cartQTYUpdate(currentQty, position, false)
            } else {
                if (context is CartActivity) {
                    alertErrorOrValidationDialog(
                        context,
                        "Minimum quantity allowed 0."
                    )
                }
            }

            if (context is CartActivity) {
                context.updateAllProduct(item, product, parseInt(currentQtyUnity), newQty, product.id)
            }
//            holder.tvFoodQty.text = newQty.toString()
            outputView(holder, product, item, newQty.toString(), currentQtyUnity)
        }

        holder.ivEditItem.setOnClickListener { view ->
            if (context is CartActivity) {
                context.updateRecordDialog(item)
            }
        }

        holder.ivDeleteCartItem.setOnClickListener { view ->

            if (context is CartActivity) {
                context.deleteRecordAlertDialog(item, product.id)
            }
        }

        holder.ivClearItem.setOnClickListener { view ->

            holder.tvFoodQtyUnity.text = item.qty_par_one.toString()
            holder.tvFoodQty.text = "0"
            holder.tvFoodPrice.text = "0"
        }

    }

    private fun outputView (holder: ViewHolder,product: AllProductModel, item: ItemModel, itemQty: String, currentQtyParOne: String) {
        var a = currentQtyParOne
        var b = itemQty
        var mini_q_u = product.mini_q_u; var mini_qty = product.mini_qty
        var trio_q_u = product.trio_q_u; var trio_qty = product.trio_qty
        var solo_q_u = product.solo_q_u; var solo_qty = product.solo_qty
        var pot_q_u = product.pot_q_u; var pot_qty = product.pot_qty
        var gini_q_u = product.gini_q_u; var gini_qty = product.gini_qty
        var big_q_u = product.big_q_u; var big_qty = product.big_qty
        var cornito_4_q_u = product.cornito_4_q_u; var cornito_4_qty = product.cornito_4_qty
        var cornito_5_q_u = product.cornito_5_q_u; var cornito_5_qty = product.cornito_5_qty
        var cornito_g_q_u = product.cornito_g_q_u; var cornito_g_qty = product.cornito_g_qty
        var gofrito_q_u = product.gofrito_q_u; var gofrito_qty = product.gofrito_qty
        var pot_v_q_u = product.pot_v_q_u; var pot_v_qty = product.pot_v_qty
        var g8_q_u = product.g8_q_u; var g8_qty = product.g8_qty
        var gold_q_u = product.gold_q_u; var gold_qty = product.gold_qty
        var skiper_q_u = product.skiper_q_u; var skiper_qty = product.skiper_qty
        var scobido_q_u = product.scobido_q_u; var scobido_qty = product.scobido_qty
        var mini_scobido_q_u = product.mini_scobido_q_u; var mini_scobido_qty = product.mini_scobido_qty
        var venezia_q_u = product.venezia_q_u; var venezia_qty = product.venezia_qty
        var bf_400_q_u = product.bf_400_q_u
        var bf_250_q_u = product.bf_250_q_u
        var bf_200_q_u = product.bf_200_q_u
        var bf_230_q_u = product.bf_230_q_u
        var bf_150_q_u = product.bf_150_q_u
        var buch_q_u = product.buch_q_u
        var tarte_q_u = product.tarte_q_u
        var mosta_q_u = product.mosta_q_u
        var misso_q_u = product.misso_q_u
        var juliana_q_u = product.juliana_q_u
        var bac_5_q_u = product.bac_5_q_u
        var bac_6_q_u = product.bac_6_q_u

        when (item.name) {
            "mini" -> { a = mini_q_u.toString(); b = mini_qty.toString() }
            "trio" -> { a = trio_q_u.toString(); b = trio_qty.toString() }
            "solo" -> { a = solo_q_u.toString(); b = solo_qty.toString() }
            "pot" -> { a = pot_q_u.toString(); b = pot_qty.toString() }
            "gini" -> { a = gini_q_u.toString(); b = gini_qty.toString() }
            "big" -> { a = big_q_u.toString(); b = big_qty.toString() }
            "cornito 45" -> { a = cornito_4_q_u.toString(); b = cornito_4_qty.toString() }
            "cornito 50" -> { a = cornito_5_q_u.toString(); b = cornito_5_qty.toString() }
            "cornito gini" -> { a = cornito_g_q_u.toString(); b = cornito_g_qty.toString() }
            "gofrito" -> { a = gofrito_q_u.toString(); b = gofrito_qty.toString() }
            "pot vally" -> { a = pot_v_q_u.toString(); b = pot_v_qty.toString() }
            "g8" -> { a = g8_q_u.toString(); b = g8_qty.toString() }
            "gold" -> { a = gold_q_u.toString(); b = gold_qty.toString() }
            "skiper" -> { a = skiper_q_u.toString(); b = skiper_qty.toString() }
            "scobido" -> { a = scobido_q_u.toString(); b = scobido_qty.toString() }
            "mini scobido" -> { a = mini_scobido_q_u.toString(); b = mini_scobido_qty.toString() }
            "venezia" -> { a = venezia_q_u.toString(); b = venezia_qty.toString() }
            "B.F 2L" -> a = bf_400_q_u.toString()
            "B.F 1L" -> a = bf_250_q_u.toString()
            "B.F 900ml" -> a = bf_230_q_u.toString()
            "B.F 750ml" -> a = bf_200_q_u.toString()
            "B.F 0,5L" -> a = bf_150_q_u.toString()
            "buch" -> a = buch_q_u.toString()
            "tarte" -> a = tarte_q_u.toString()
            "mosta" -> a = mosta_q_u.toString()
            "misso" -> a = misso_q_u.toString()
            "juliana" -> a = juliana_q_u.toString()
            "bac 5L" -> a = bac_5_q_u.toString()
            "bac 6L" -> a = bac_6_q_u.toString()

            else -> {
                print("x is neither 1 nor 2")
            }
        }

        if (item.name == "B.F 2L" || item.name == "B.F 1L" || item.name == "B.F 900ml" || item.name == "B.F 750ml" || item.name == "B.F 0,5L"
            || item.name == "buch" || item.name == "tarte" || item.name == "mosta" || item.name == "misso"
            || item.name == "juliana" || item.name == "bac 5L" || item.name == "bac 6L") {
            val itemPrice = parseInt(a) * parseInt(item.price)
            holder.tvFoodName.text = item.name
            holder.tvFoodPriceUnity.text = item.price
            holder.tvFoodQtyUnity.text = a
            holder.tvFoodPrice.text = itemPrice.toString()
            holder.tvFoodQty.visibility = View.GONE
            holder.ivMinus.visibility = View.GONE
            holder.ivPlus.visibility = View.GONE
            holder.tvImage.setImageResource(R.mipmap.ic_launcher_foreground)
        } else {
            val itemPrice = (parseInt(b) * parseInt(a)) * parseInt(item.price)
            holder.tvFoodName.text = item.name
            holder.tvFoodPriceUnity.text = item.price
            holder.tvFoodQtyUnity.text = a
            holder.tvFoodPrice.text = itemPrice.toString()
            holder.tvFoodQty.visibility = View.VISIBLE
            holder.ivMinus.visibility = View.VISIBLE
            holder.ivPlus.visibility = View.VISIBLE
            holder.tvFoodQty.text = b
            holder.tvImage.setImageResource(R.mipmap.ic_launcher_foreground)
//        holder.tvStatus.text = item.status
        }

    }

    private fun cartQTYUpdate(cartModel: Int, pos: Int, isPlus: Boolean):Int {
        var qty = 0
        print(pos)
        if (isPlus) {
            qty = cartModel + 1
        } else {
            qty = cartModel - 1
        }
        return qty
    }


    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
//        val llMain = view.llMain
        val tvFoodName = view.tvFoodName
        val tvFoodPriceUnity = view.tvFoodPriceUnity
        val tvFoodQty = view.tvFoodQty
        //        val tvStatus = view.tvStatus
        val tvFoodQtyUnity = view.tvFoodQtyUnity
        val tvFoodPrice = view.tvFoodPrice
        val tvImage = view.ivFoodCart
        val ivEditItem = view.ivEditItem
        val ivDeleteCartItem = view.ivDeleteCartItem
        val ivClearItem = view.ivClearItem
        val ivMinus = view.ivMinus
        val ivPlus = view.ivPlus
    }
}