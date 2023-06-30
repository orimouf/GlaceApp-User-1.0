package com.foodapp.app.activity

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.foodapp.app.model.*

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "GlaceDatabase"

        const val TABLE_ITEMS = "ItemsTable"
        const val TABLE_STOCK = "StockTable"
        const val TABLE_USER = "UserTable"
        const val TABLE_ORDER_SUMMARY = "OrderSummaryTable"
        const val TABLE_ALL_PRODUCT = "AllProductTable"
        const val TABLE_CLIENT = "clientTable"
        const val TABLE_REGION = "regionTable"
        const val TABLE_VERSSEMENT = "verssementTable"

        const val KEY_ID = "_id"
        const val KEY_SERVER_ID = "server_id"
        const val KEY_NAME = "name"
        const val KEY_CLIENT_NAME = "client_name"
        const val KEY_CLIENT_ID = "client_id"
        const val KEY_ORDER_ID = "order_id"
        const val KEY_PRODUCT_LIST_ID = "product_list_id"
        const val KEY_TOTAL_TO_PAY = "total_to_pay"
        const val KEY_IS_CREDIT = "is_credit"
        const val KEY_USERNAME = "username"
        const val KEY_EMAIL = "email"
        const val KEY_PASSWORD = "password"
        const val KEY_PROFILE_PIC = "profile_pic"
        const val KEY_CAMION = "camion"
        const val KEY_ISADMIN = "isadmin"
        const val KEY_PRICE = "price"
        const val KEY_IMAGE = "image"
        const val KEY_STATUS = "status"
        const val KEY_QTY_PAR_ONE = "qty_par_one"
        const val KEY_QTY = "qty"
        const val KEY_REGION = "region"
        const val KEY_PHONE = "phone"
        const val KEY_PRICES = "prices"
        const val KEY_ISPROMO = "is_promo"
        const val KEY_ISFRIGO = "is_frigo"
        const val KEY_OLDCREDIT = "old_credit"
        const val KEY_LASTSERVE = "last_serve"
        const val KEY_CREDITBON = "credit_bon"
        const val KEY_UP_TO_SERVER = "up_to_server"
        const val KEY_DATE = "date"
        const val KEY_IS_CHECK = "is_check"
        const val KEY_OLD_SOMME = "old_somme"
        const val KEY_VERSSI = "verssi"
        const val KEY_REST = "rest"
        const val KEY_CREATEAT = "createdAt"
        const val KEY_UPDATEAT = "updatedAt"
        const val KEY___V = "__v"

//        ------------------------------------------------------

        const val KEY_MINI_QTY = "mini_qty"
        const val KEY_MINI_QU = "mini_q_u"
        const val KEY_TRIO_QTY = "trio_qty"
        const val KEY_TRIO_QU = "trio_q_u"
        const val KEY_SOLO_QTY = "solo_qty"
        const val KEY_SOLO_QU = "solo_q_u"
        const val KEY_POT_QTY = "pot_qty"
        const val KEY_POT_QU = "pot_q_u"
        const val KEY_GINI_QTY = "gini_qty"
        const val KEY_GINI_QU = "gini_q_u"
        const val KEY_BIG_QTY = "big_qty"
        const val KEY_BIG_QU = "big_q_u"
        const val KEY_CORNITO_4_QTY = "cornito_4_qty"
        const val KEY_CORNITO_4_QU = "cornito_4_q_u"
        const val KEY_CORNITO_5_QTY = "cornito_5_qty"
        const val KEY_CORNITO_5_QU = "cornito_5_q_u"
        const val KEY_CORNITO_G_QTY = "cornito_g_qty"
        const val KEY_CORNITO_G_QU = "cornito_g_q_u"
        const val KEY_GOFRITO_QTY = "gofrito_qty"
        const val KEY_GOFRITO_QU = "gofrito_q_u"
        const val KEY_POT_V_QTY = "pot_v_qty"
        const val KEY_POT_V_QU = "pot_v_q_u"
        const val KEY_BF_400_QU = "bf_400_q_u"
        const val KEY_BF_250_QU = "bf_250_q_u"
        const val KEY_BF_230_QU = "bf_230_q_u"
        const val KEY_BF_200_QU = "bf_200_q_u"
        const val KEY_BF_150_QU = "bf_150_q_u"
        const val KEY_BUCH_QU = "buch_q_u"
        const val KEY_TARTE_QU = "tarte_q_u"
        const val KEY_MOSTA_QU = "mosta_q_u"
        const val KEY_MISSO_QU = "misso_q_u"
        const val KEY_G8_QTY = "g8_qty"
        const val KEY_G8_QU = "g8_q_u"
        const val KEY_GOLD_QTY = "gold_qty"
        const val KEY_GOLD_QU = "gold_q_u"
        const val KEY_SKIPER_QTY = "skiper_qty"
        const val KEY_SKIPER_QU = "skiper_q_u"
        const val KEY_SCOBIDO_QTY = "scobido_qty"
        const val KEY_SCOBIDO_QU = "scobido_q_u"
        const val KEY_MINI_SCOBIDO_QTY = "mini_scobido_qty"
        const val KEY_MINI_SCOBIDO_QU = "mini_scobido_q_u"
        const val KEY_JULIANA_QU = "juliana_q_u"
        const val KEY_BAC_5_QU = "bac_5_q_u"
        const val KEY_BAC_6_QU = "bac_6_q_u"
        const val KEY_VENEZIA_QTY = "venezia_qty"
        const val KEY_VENEZIA_QU = "venezia_q_u"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating items table with fields
        val createItemsTable = ("CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PRICE + " TEXT," + KEY_STATUS + " INTEGER,"
                + KEY_QTY_PAR_ONE + " INTEGER," + KEY_IMAGE + " TEXT,"
                + KEY_CREATEAT + " TEXT," + KEY_UPDATEAT + " TEXT," + KEY___V + " TEXT,"
                + KEY_SERVER_ID + " TEXT," + KEY_UP_TO_SERVER + " INTEGER" + ")")

        db?.execSQL(createItemsTable)

        //creating region table with fields
        val createRegionTable = ("CREATE TABLE " + TABLE_REGION + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_REGION + " TEXT,"
                + KEY_CREATEAT + " TEXT," + KEY_UPDATEAT + " TEXT," + KEY___V + " TEXT,"
                + KEY_SERVER_ID + " TEXT," + KEY_UP_TO_SERVER + " INTEGER" + ")")
        db?.execSQL(createRegionTable)

        //creating stock table with fields
        val createStockTable = ("CREATE TABLE " + TABLE_STOCK + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_CREATEAT + " TEXT," + KEY_UPDATEAT + " TEXT," + KEY___V + " TEXT,"
                + KEY_SERVER_ID + " TEXT," + KEY_QTY + " INTEGER," + KEY_UP_TO_SERVER + " INTEGER" + ")")
        db?.execSQL(createStockTable)

        //creating user table with fields
        val createUserTable = ("CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PROFILE_PIC + " TEXT," + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT," + KEY_CAMION + " TEXT,"
                + KEY_CREATEAT + " TEXT," + KEY_UPDATEAT + " TEXT," + KEY___V + " TEXT,"
                + KEY_SERVER_ID + " TEXT," + KEY_ISADMIN + " INTEGER," + KEY_UP_TO_SERVER + " INTEGER" + ")")
        db?.execSQL(createUserTable)

        //creating client table with fields
        val createClientTable = ("CREATE TABLE " + TABLE_CLIENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CLIENT_NAME + " TEXT,"
                + KEY_REGION + " TEXT," + KEY_PHONE + " TEXT," + KEY_PRICES + " TEXT,"
                + KEY_OLDCREDIT + " TEXT," + KEY_IS_CREDIT + " INTEGER," + KEY_ISFRIGO + " INTEGER,"
                + KEY_ISPROMO + " INTEGER," + KEY_CREDITBON + " INTEGER," + KEY_LASTSERVE + " TEXT,"
                + KEY_CREATEAT + " TEXT," + KEY_UPDATEAT + " TEXT," + KEY___V + " TEXT,"
                + KEY_SERVER_ID + " TEXT," + KEY_UP_TO_SERVER + " INTEGER" + ")")
        db?.execSQL(createClientTable)

        //creating order summary table with fields
        val createOrderSummaryTable = ("CREATE TABLE " + TABLE_ORDER_SUMMARY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CLIENT_NAME + " TEXT,"
                + KEY_CLIENT_ID + " INTEGER," + KEY_PRODUCT_LIST_ID + " INTEGER,"
                + KEY_VERSSI + " TEXT," + KEY_REST + " TEXT,"
                + KEY_TOTAL_TO_PAY + " INTEGER," + KEY_IS_CREDIT + " INTEGER,"
                + KEY_UP_TO_SERVER + " INTEGER," + KEY_IS_CHECK + " INTEGER,"
                + KEY_CREATEAT + " TEXT," + KEY_UPDATEAT + " TEXT," + KEY___V + " TEXT,"
                + KEY_SERVER_ID + " TEXT," + KEY_DATE + " TEXT" + ")")
        db?.execSQL(createOrderSummaryTable)

        //creating verssment table with fields
        val createVerssementTable = ("CREATE TABLE " + TABLE_VERSSEMENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CLIENT_ID + " TEXT,"
                + KEY_CLIENT_NAME + " TEXT," + KEY_REGION + " TEXT," + KEY_OLD_SOMME + " TEXT,"
                + KEY_VERSSI + " TEXT," + KEY_REST + " TEXT,"
                + KEY_UP_TO_SERVER + " INTEGER," + KEY_IS_CHECK + " INTEGER,"
                + KEY_CREATEAT + " TEXT," + KEY_UPDATEAT + " TEXT," + KEY___V + " TEXT,"
                + KEY_SERVER_ID + " TEXT," + KEY_DATE + " TEXT" + ")")
        db?.execSQL(createVerssementTable)

        //creating all product table with fields
        val createAllProductTable = ("CREATE TABLE " + TABLE_ALL_PRODUCT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ORDER_ID + " INTEGER,"
                + KEY_MINI_QTY + " INTEGER," + KEY_MINI_QU + " INTEGER,"
                + KEY_TRIO_QTY + " INTEGER," + KEY_TRIO_QU + " INTEGER,"
                + KEY_POT_QTY + " INTEGER," + KEY_POT_QU + " INTEGER,"
                + KEY_POT_V_QTY + " INTEGER," + KEY_POT_V_QU + " INTEGER,"
                + KEY_SOLO_QTY + " INTEGER," + KEY_SOLO_QU + " INTEGER,"
                + KEY_GINI_QTY + " INTEGER," + KEY_GINI_QU + " INTEGER,"
                + KEY_GOFRITO_QTY + " INTEGER," + KEY_GOFRITO_QU + " INTEGER,"
                + KEY_GOLD_QTY + " INTEGER," + KEY_GOLD_QU + " INTEGER,"
                + KEY_G8_QTY + " INTEGER," + KEY_G8_QU + " INTEGER,"
                + KEY_SCOBIDO_QTY + " INTEGER," + KEY_SCOBIDO_QU + " INTEGER,"
                + KEY_SKIPER_QTY + " INTEGER," + KEY_SKIPER_QU + " INTEGER,"
                + KEY_MINI_SCOBIDO_QTY + " INTEGER," + KEY_MINI_SCOBIDO_QU + " INTEGER,"
                + KEY_CORNITO_4_QTY + " INTEGER," + KEY_CORNITO_4_QU + " INTEGER,"
                + KEY_CORNITO_5_QTY + " INTEGER," + KEY_CORNITO_5_QU + " INTEGER,"
                + KEY_CORNITO_G_QTY + " INTEGER," + KEY_CORNITO_G_QU + " INTEGER,"
                + KEY_BIG_QTY + " INTEGER," + KEY_BIG_QU + " INTEGER,"
                + KEY_VENEZIA_QTY + " INTEGER," + KEY_VENEZIA_QU + " INTEGER,"
                + KEY_MISSO_QU + " INTEGER," + KEY_MOSTA_QU + " INTEGER,"
                + KEY_BAC_5_QU + " INTEGER," + KEY_BAC_6_QU + " INTEGER,"
                + KEY_BF_400_QU + " INTEGER," + KEY_BF_250_QU + " INTEGER,"
                + KEY_BF_230_QU + " INTEGER," + KEY_BF_200_QU + " INTEGER,"
                + KEY_BF_150_QU + " INTEGER," + KEY_JULIANA_QU + " INTEGER,"
                + KEY_BUCH_QU + " INTEGER," + KEY_TARTE_QU + " INTEGER,"
                + KEY_CREATEAT + " TEXT," + KEY_UPDATEAT + " TEXT," + KEY___V + " TEXT,"
                + KEY_SERVER_ID + " TEXT," + KEY_UP_TO_SERVER + " INTEGER" + ")")
        db?.execSQL(createAllProductTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_ITEMS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STOCK")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDER_SUMMARY")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ALL_PRODUCT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_REGION")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_VERSSEMENT")
        onCreate(db)
    }

    /**
     * Function to insert data
     */
    fun addItem(item: ItemModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, item.server_id)
        contentValues.put(KEY_NAME, item.name)
        contentValues.put(KEY_PRICE, item.price)
        contentValues.put(KEY_IMAGE, item.image)
        contentValues.put(KEY_STATUS, item.status)
        contentValues.put(KEY_QTY_PAR_ONE, item.qty_par_one)
        contentValues.put(KEY_CREATEAT, item.createdAt)
        contentValues.put(KEY_UPDATEAT, item.updatedAt)
        contentValues.put(KEY___V, item.__v)
        contentValues.put(KEY_UP_TO_SERVER, item.up_to_server)

        // Inserting Item details using insert query.
        val success = db.insert(TABLE_ITEMS, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    /**
     * Function to insert region
     */
    fun addRegion(region: RegionModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, region.server_id)
        contentValues.put(KEY_REGION, region.region_name)
        contentValues.put(KEY_CREATEAT, region.createdAt)
        contentValues.put(KEY_UPDATEAT, region.updatedAt)
        contentValues.put(KEY___V, region.__v)
        contentValues.put(KEY_UP_TO_SERVER, region.up_to_server)

        // Inserting Item details using insert query.
        val success = db.insert(TABLE_REGION, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    /**
     * Function to insert client
     */
    fun addClient(client: ClientModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, client.server_id)
        contentValues.put(KEY_CLIENT_NAME, client.client_name)
        contentValues.put(KEY_REGION, client.region)
        contentValues.put(KEY_PHONE, client.phone)
        contentValues.put(KEY_PRICES, client.prices)
        contentValues.put(KEY_ISPROMO, client.is_promo)
        contentValues.put(KEY_ISFRIGO, client.is_frigo)
        contentValues.put(KEY_IS_CREDIT, client.is_credit)
        contentValues.put(KEY_OLDCREDIT, client.old_credit)
        contentValues.put(KEY_CREDITBON, client.credit_bon)
        contentValues.put(KEY_LASTSERVE, client.last_serve)
        contentValues.put(KEY_CREATEAT, client.createdAt)
        contentValues.put(KEY_UPDATEAT, client.updatedAt)
        contentValues.put(KEY___V, client.__v)
        contentValues.put(KEY_UP_TO_SERVER, client.up_to_server)

        // Inserting Item details using insert query.
        val success = db.insert(TABLE_CLIENT, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    /**
     * Function to insert data order in ALL PRODUCT
     */
    fun addAllProduct(item: AllProductModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, item.server_id)
        contentValues.put(KEY_ORDER_ID, item.orderId)
        contentValues.put(KEY_MINI_QTY, item.mini_qty)
        contentValues.put(KEY_MINI_QU, item.mini_q_u)
        contentValues.put(KEY_TRIO_QTY, item.trio_qty)
        contentValues.put(KEY_TRIO_QU, item.trio_q_u)
        contentValues.put(KEY_SOLO_QTY, item.solo_qty)
        contentValues.put(KEY_SOLO_QU, item.solo_q_u)
        contentValues.put(KEY_POT_QTY, item.pot_qty)
        contentValues.put(KEY_POT_QU, item.pot_q_u)
        contentValues.put(KEY_GINI_QTY, item.gini_qty)
        contentValues.put(KEY_GINI_QU, item.gini_q_u)
        contentValues.put(KEY_BIG_QTY, item.big_qty)
        contentValues.put(KEY_BIG_QU, item.big_q_u)
        contentValues.put(KEY_CORNITO_4_QTY, item.cornito_4_qty)
        contentValues.put(KEY_CORNITO_4_QU, item.cornito_4_q_u)
        contentValues.put(KEY_CORNITO_5_QTY, item.cornito_5_qty)
        contentValues.put(KEY_CORNITO_5_QU, item.cornito_5_q_u)
        contentValues.put(KEY_CORNITO_G_QTY, item.cornito_g_qty)
        contentValues.put(KEY_CORNITO_G_QU, item.cornito_g_q_u)
        contentValues.put(KEY_GOFRITO_QTY, item.gofrito_qty)
        contentValues.put(KEY_GOFRITO_QU, item.gofrito_q_u)
        contentValues.put(KEY_POT_V_QTY, item.pot_v_qty)
        contentValues.put(KEY_POT_V_QU, item.pot_v_q_u)
        contentValues.put(KEY_G8_QTY, item.g8_qty)
        contentValues.put(KEY_G8_QU, item.g8_q_u)
        contentValues.put(KEY_GOLD_QTY, item.gold_qty)
        contentValues.put(KEY_GOLD_QU, item.gold_q_u)
        contentValues.put(KEY_SKIPER_QTY, item.skiper_qty)
        contentValues.put(KEY_SKIPER_QU, item.skiper_q_u)
        contentValues.put(KEY_SCOBIDO_QTY, item.scobido_qty)
        contentValues.put(KEY_SCOBIDO_QU, item.scobido_q_u)
        contentValues.put(KEY_MINI_SCOBIDO_QTY, item.mini_scobido_qty)
        contentValues.put(KEY_MINI_SCOBIDO_QU, item.mini_scobido_q_u)
        contentValues.put(KEY_VENEZIA_QTY, item.venezia_qty)
        contentValues.put(KEY_VENEZIA_QU, item.venezia_q_u)
        contentValues.put(KEY_BF_400_QU, item.bf_400_q_u)
        contentValues.put(KEY_BF_250_QU, item.bf_250_q_u)
        contentValues.put(KEY_BF_230_QU, item.bf_230_q_u)
        contentValues.put(KEY_BF_200_QU, item.bf_200_q_u)
        contentValues.put(KEY_BF_150_QU, item.bf_150_q_u)
        contentValues.put(KEY_BUCH_QU, item.buch_q_u)
        contentValues.put(KEY_TARTE_QU, item.tarte_q_u)
        contentValues.put(KEY_MOSTA_QU, item.mosta_q_u)
        contentValues.put(KEY_MISSO_QU, item.misso_q_u)
        contentValues.put(KEY_JULIANA_QU, item.juliana_q_u)
        contentValues.put(KEY_BAC_5_QU, item.bac_5_q_u)
        contentValues.put(KEY_BAC_6_QU, item.bac_6_q_u)
        contentValues.put(KEY_CREATEAT, item.createdAt)
        contentValues.put(KEY_UPDATEAT, item.updatedAt)
        contentValues.put(KEY___V, item.__v)
        contentValues.put(KEY_UP_TO_SERVER, item.up_to_server)

        // Inserting Item details using insert query.
        val success = db.insert(TABLE_ALL_PRODUCT, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    fun addVerssement(item: VerssementModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, item.server_id)
        contentValues.put(KEY_CLIENT_NAME, item.client_name)
        contentValues.put(KEY_CLIENT_ID, item.client_id)
        contentValues.put(KEY_REGION, item.region)
        contentValues.put(KEY_OLD_SOMME, item.old_somme)
        contentValues.put(KEY_VERSSI, item.verssi)
        contentValues.put(KEY_REST, item.rest)
        contentValues.put(KEY_DATE, item.date)
        contentValues.put(KEY_IS_CHECK, item.is_check)
        contentValues.put(KEY_CREATEAT, item.createdAt)
        contentValues.put(KEY_UPDATEAT, item.updatedAt)
        contentValues.put(KEY___V, item.__v)
        contentValues.put(KEY_UP_TO_SERVER, item.up_to_server)

        // Inserting Item details using insert query.
        val success = db.insert(TABLE_VERSSEMENT, null, contentValues)

        db.close() // Closing database connection
        return success
    }

    fun addOrderSummary(order: OrderSummaryModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, order.server_id)
        contentValues.put(KEY_CLIENT_NAME, order.client_name)
        contentValues.put(KEY_CLIENT_ID, order.client_id)
        contentValues.put(KEY_PRODUCT_LIST_ID, order.product_list_id)
        contentValues.put(KEY_TOTAL_TO_PAY, order.total_to_pay)
        contentValues.put(KEY_VERSSI, order.verssi)
        contentValues.put(KEY_REST, order.rest)
        contentValues.put(KEY_IS_CREDIT, order.iscredit)
        contentValues.put(KEY_DATE, order.date)
        contentValues.put(KEY_IS_CHECK, order.is_check)
        contentValues.put(KEY_CREATEAT, order.createdAt)
        contentValues.put(KEY_UPDATEAT, order.updatedAt)
        contentValues.put(KEY___V, order.__v)
        contentValues.put(KEY_UP_TO_SERVER, order.up_to_server)

        // Inserting Item details using insert query.
        val success = db.insert(TABLE_ORDER_SUMMARY, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    fun addUser(user: UserModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, user.server_id)
        contentValues.put(KEY_USERNAME, user.username)
        contentValues.put(KEY_USERNAME, user.email)
        contentValues.put(KEY_PASSWORD, user.password)
        contentValues.put(KEY_CAMION, user.camion)
        contentValues.put(KEY_PROFILE_PIC, user.profile_pic)
        contentValues.put(KEY_ISADMIN, user.isadmin)
        contentValues.put(KEY_CREATEAT, user.createdAt)
        contentValues.put(KEY_UPDATEAT, user.updatedAt)
        contentValues.put(KEY___V, user.__v)
        contentValues.put(KEY_UP_TO_SERVER, user.up_to_server)

        // Inserting Item details using insert query.
        val success = db.insert(TABLE_USER, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    fun addStock(item: CamionStockModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, item.server_id)
        contentValues.put(KEY_NAME, item.name)
        contentValues.put(KEY_QTY, item.qty)
        contentValues.put(KEY_CREATEAT, item.createdAt)
        contentValues.put(KEY_UPDATEAT, item.updatedAt)
        contentValues.put(KEY___V, item.__v)
        contentValues.put(KEY_UP_TO_SERVER, item.up_to_server)

        // Inserting Item details using insert query.
        val success = db.insert(TABLE_STOCK, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    fun viewUpdateAt(id: Int): String {
        var responce: String = ""

        // Query to select all the records from the table.
        val selectQuery = "SELECT * FROM $TABLE_REGION WHERE $KEY_ID = $id"


        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return responce
        }

        if (cursor.moveToFirst()) {
            do {
                responce = cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATEAT))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return responce
    }

    //Method to read the records from database in form of ArrayList
    fun viewItem(upToServer: Boolean = false): ArrayList<ItemModel> {

        val itemList: ArrayList<ItemModel> = ArrayList()

        val selectQuery = if(upToServer) "SELECT  * FROM $TABLE_ITEMS WHERE $KEY_UP_TO_SERVER = 0"
        else "SELECT  * FROM $TABLE_ITEMS"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var server_id: String
        var name: String
        var price: String
        var image: String
        var status: Int
        var qty_par_one: Int
        var createdAt: String
        var updatedAt: String
        var __v: String
        var up_to_server: Int

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                server_id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SERVER_ID))
                name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
                price = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRICE))
                image = cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE))
                status = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_STATUS))
                qty_par_one = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_QTY_PAR_ONE))
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATEAT))
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATEAT))
                __v = cursor.getString(cursor.getColumnIndexOrThrow(KEY___V))
                up_to_server = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_UP_TO_SERVER))

                val item = ItemModel(id = id, server_id = server_id, name = name, price = price, status = status, qty_par_one = qty_par_one, image = image
                    , createdAt = createdAt, updatedAt = updatedAt, __v = __v, up_to_server = up_to_server)
                itemList.add(item)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return itemList
    }

    fun viewRegion(upToServer: Boolean = false): ArrayList<RegionModel> {

        val regionList: ArrayList<RegionModel> = ArrayList()

        val selectQuery = if (upToServer) "SELECT * FROM $TABLE_REGION WHERE $KEY_UP_TO_SERVER = 0"
        else "SELECT * FROM $TABLE_REGION"


        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var server_id: String
        var regionName: String
        var createdAt: String
        var updatedAt: String
        var __v: String
        var up_to_server: Int

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                server_id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SERVER_ID))
                regionName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_REGION))
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATEAT))
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATEAT))
                __v = cursor.getString(cursor.getColumnIndexOrThrow(KEY___V))
                up_to_server = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_UP_TO_SERVER))

                val region = RegionModel(id = id, server_id = server_id, region_name = regionName, createdAt = createdAt, updatedAt = updatedAt, __v = __v, up_to_server = up_to_server)
                regionList.add(region)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return regionList
    }

    fun viewCheckUser(id: Int): Boolean {

        var responce = false

        // Query to select all the records from the table.
        val selectQuery = "SELECT * FROM $TABLE_USER WHERE $KEY_ID = '$id'"


        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return responce
        }

        if (cursor.moveToFirst()) {
            do {
                responce = true
            } while (cursor.moveToNext())
        }
        cursor.close()
        return responce
    }

    fun viewCheckClient(id: Int): Boolean {

        var responce = false

        // Query to select all the records from the table.
        val selectQuery = "SELECT * FROM $TABLE_CLIENT WHERE $KEY_ID = '$id'"


        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return responce
        }

        if (cursor.moveToFirst()) {
            do {
                responce = true
            } while (cursor.moveToNext())
        }
        cursor.close()
        return responce
    }

    fun viewCheckRegion(regionName: String): Boolean {

        var responce = false

        // Query to select all the records from the table.
        val selectQuery = "SELECT * FROM $TABLE_REGION WHERE $KEY_REGION = '$regionName'"


        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return responce
        }

        if (cursor.moveToFirst()) {
            do {
                responce = true
            } while (cursor.moveToNext())
        }
        cursor.close()
        return responce
    }

    fun viewCheckPayment(id: Int): Boolean {

        var responce = false

        // Query to select all the records from the table.
        val selectQuery = "SELECT * FROM $TABLE_VERSSEMENT WHERE $KEY_ID = '$id'"


        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return responce
        }

        if (cursor.moveToFirst()) {
            do {
                responce = true
            } while (cursor.moveToNext())
        }
        cursor.close()
        return responce
    }

    fun viewCheckProduct(id: Int): Boolean {

        var responce = false

        // Query to select all the records from the table.
        val selectQuery = "SELECT * FROM $TABLE_ITEMS WHERE $KEY_ID = '$id'"


        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return responce
        }

        if (cursor.moveToFirst()) {
            do {
                responce = true
            } while (cursor.moveToNext())
        }
        cursor.close()
        return responce
    }

    fun viewCheckOrders(id: Int): Boolean {

        var responce = false

        // Query to select all the records from the table.
        val selectQuery = "SELECT * FROM $TABLE_ORDER_SUMMARY WHERE $KEY_ID = '$id'"


        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return responce
        }

        if (cursor.moveToFirst()) {
            do {
                responce = true
            } while (cursor.moveToNext())
        }
        cursor.close()
        return responce
    }

    fun viewCheckOrderedProducts(id: Int): Boolean {

        var responce = false

        // Query to select all the records from the table.
        val selectQuery = "SELECT * FROM $TABLE_ALL_PRODUCT WHERE $KEY_ID = '$id'"


        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return responce
        }

        if (cursor.moveToFirst()) {
            do {
                responce = true
            } while (cursor.moveToNext())
        }
        cursor.close()
        return responce
    }

    //Method to search the client from database in form of ArrayList
    fun viewSearchClient(string: String): ArrayList<ClientModel> {

        val clientList: ArrayList<ClientModel> = ArrayList()

        // Query to select all the records from the table.
        val selectQuery = "SELECT * FROM $TABLE_CLIENT WHERE $KEY_CLIENT_NAME LIKE '%$string%' "

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var server_id: String
        var client_name: String
        var region: String
        var phone: String
        var prices: String
        var is_promo: Int
        var is_frigo: Int
        var is_credit: Int
        var old_credit: String
        var credit_bon: Int
        var last_serve: String
        var createdAt: String
        var updatedAt: String
        var __v: String
        var up_to_server: Int

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                server_id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SERVER_ID))
                client_name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CLIENT_NAME))
                region = cursor.getString(cursor.getColumnIndexOrThrow(KEY_REGION))
                phone = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE))
                prices = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRICES))
                is_promo = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ISPROMO))
                is_frigo = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ISFRIGO))
                is_credit = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_IS_CREDIT))
                old_credit = cursor.getString(cursor.getColumnIndexOrThrow(KEY_OLDCREDIT))
                credit_bon = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CREDITBON))
                last_serve = cursor.getString(cursor.getColumnIndexOrThrow(KEY_LASTSERVE))
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATEAT))
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATEAT))
                __v = cursor.getString(cursor.getColumnIndexOrThrow(KEY___V))
                up_to_server = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_UP_TO_SERVER))

                val item = ClientModel(id = id, server_id = server_id, client_name = client_name, region = region, phone = phone,
                    prices = prices, is_promo = is_promo, is_frigo = is_frigo, is_credit = is_credit,
                    old_credit = old_credit, credit_bon = credit_bon, last_serve = last_serve, createdAt = createdAt, updatedAt = updatedAt, __v = __v, up_to_server = up_to_server)
                clientList.add(item)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return clientList
    }

    fun viewRegionClient(id: Int): String {

        val selectQuery = "SELECT * FROM $TABLE_CLIENT WHERE $KEY_ID = $id "

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return "null"
        }

        var region = "null"

        if (cursor.moveToFirst()) {
            do {
                region = cursor.getString(cursor.getColumnIndexOrThrow(KEY_REGION))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return region
    }

    //Method to read the records from database in form of ArrayList
    fun viewClient(id: Int, isForOrder: Boolean, regionName: String = "", upToServer: Boolean = false): ArrayList<ClientModel> {

        val clientList: ArrayList<ClientModel> = ArrayList()

        // Query to select all the records from the table.
        val selectQuery = if (id == 0 && isForOrder) {
            "SELECT * FROM $TABLE_CLIENT"
        } else if (id > 0 && isForOrder) {
            "SELECT * FROM $TABLE_CLIENT WHERE $KEY_ID = $id LIMIT 1"
        } else if (regionName == "" && !isForOrder) {
            "SELECT * FROM $TABLE_CLIENT"
        } else if (id == 0 && regionName == "up_to_server" && upToServer) {
            "SELECT * FROM $TABLE_CLIENT WHERE $KEY_UP_TO_SERVER = 0"
        } else {
            "SELECT * FROM $TABLE_CLIENT WHERE $KEY_REGION = '$regionName'"
        }

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var _id: Int
        var server_id: String
        var client_name: String
        var region: String
        var phone: String
        var prices: String
        var is_promo: Int
        var is_frigo: Int
        var is_credit: Int
        var old_credit: String
        var credit_bon: Int
        var last_serve: String
        var createdAt: String
        var updatedAt: String
        var __v: String
        var up_to_server: Int

        if (cursor.moveToFirst()) {
            do {
                _id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                server_id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SERVER_ID))
                client_name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CLIENT_NAME))
                region = cursor.getString(cursor.getColumnIndexOrThrow(KEY_REGION))
                phone = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE))
                prices = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRICES))
                is_promo = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ISPROMO))
                is_frigo = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ISFRIGO))
                is_credit = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_IS_CREDIT))
                old_credit = cursor.getString(cursor.getColumnIndexOrThrow(KEY_OLDCREDIT))
                credit_bon = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CREDITBON))
                last_serve = cursor.getString(cursor.getColumnIndexOrThrow(KEY_LASTSERVE))
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATEAT))
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATEAT))
                __v = cursor.getString(cursor.getColumnIndexOrThrow(KEY___V))
                up_to_server = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_UP_TO_SERVER))

                val item = ClientModel(id = _id, server_id = server_id, client_name = client_name, region = region, phone = phone,
                    prices = prices, is_promo = is_promo, is_frigo = is_frigo, is_credit = is_credit,
                    old_credit = old_credit, credit_bon = credit_bon, last_serve = last_serve
                    , createdAt = createdAt, updatedAt = updatedAt, __v = __v, up_to_server = up_to_server )
                clientList.add(item)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return clientList
    }

    //Method to read the all Product from database in form of ArrayList
    fun viewAllProduct(id: Int, upToServer: Boolean = false): ArrayList<AllProductModel> {

        val itemList: ArrayList<AllProductModel> = ArrayList()

        val selectQuery = if(upToServer) "SELECT * FROM $TABLE_ALL_PRODUCT WHERE $KEY_UP_TO_SERVER = 0 "
        else "SELECT * FROM $TABLE_ALL_PRODUCT WHERE $KEY_ORDER_ID = $id"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var _id: Int
        var server_id: String
        var orderID: Int
        var mini_qty: Int; var mini_q_u: Int
        var trio_qty: Int; var trio_q_u: Int
        var solo_qty: Int; var solo_q_u: Int
        var pot_qty: Int; var pot_q_u: Int
        var gini_qty: Int; var gini_q_u: Int
        var big_qty: Int; var big_q_u: Int
        var cornito_4_qty: Int; var cornito_4_q_u: Int
        var cornito_5_qty: Int; var cornito_5_q_u: Int
        var cornito_g_qty: Int; var cornito_g_q_u: Int
        var gofrito_qty: Int; var gofrito_q_u: Int
        var pot_v_qty: Int; var pot_v_q_u: Int
        var g8_qty: Int; var g8_q_u: Int
        var gold_qty: Int; var gold_q_u: Int
        var skiper_qty: Int; var skiper_q_u: Int
        var scobido_qty: Int; var scobido_q_u: Int
        var mini_scobido_qty: Int; var mini_scobido_q_u: Int
        var venezia_qty: Int; var venezia_q_u: Int
        var bf_400_q_u: Int
        var bf_250_q_u: Int
        var bf_230_q_u: Int
        var bf_200_q_u: Int
        var bf_150_q_u: Int
        var buch_q_u: Int
        var tarte_q_u: Int
        var mosta_q_u: Int
        var misso_q_u: Int
        var juliana_q_u: Int
        var bac_5_q_u: Int
        var bac_6_q_u: Int
        var createdAt: String
        var updatedAt: String
        var __v: String
        var up_to_server: Int

        if (cursor.moveToFirst()) {
            do {
                _id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                server_id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SERVER_ID))
                orderID = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ORDER_ID))
                mini_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MINI_QTY))
                mini_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MINI_QU))
                trio_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TRIO_QTY))
                trio_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TRIO_QU))
                solo_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SOLO_QTY))
                solo_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SOLO_QU))
                pot_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_POT_QTY))
                pot_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_POT_QU))
                gini_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_GINI_QTY))
                gini_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_GINI_QU))
                big_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_BIG_QTY))
                big_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_BIG_QU))
                cornito_4_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CORNITO_4_QTY))
                cornito_4_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CORNITO_4_QU))
                cornito_5_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CORNITO_5_QTY))
                cornito_5_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CORNITO_5_QU))
                cornito_g_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CORNITO_G_QTY))
                cornito_g_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CORNITO_G_QU))
                gofrito_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_GOFRITO_QTY))
                gofrito_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_GOFRITO_QU))
                pot_v_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_POT_V_QTY))
                pot_v_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_POT_V_QU))
                g8_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_G8_QTY))
                g8_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_G8_QU))
                gold_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_GOLD_QTY))
                gold_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_GOLD_QU))
                skiper_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SKIPER_QTY))
                skiper_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SKIPER_QU))
                scobido_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SCOBIDO_QTY))
                scobido_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SCOBIDO_QU))
                mini_scobido_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MINI_SCOBIDO_QTY))
                mini_scobido_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MINI_SCOBIDO_QU))
                venezia_qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_VENEZIA_QTY))
                venezia_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_VENEZIA_QU))
                bf_400_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_BF_400_QU))
                bf_250_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_BF_250_QU))
                bf_230_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_BF_230_QU))
                bf_200_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_BF_200_QU))
                bf_150_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_BF_150_QU))
                buch_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_BUCH_QU))
                tarte_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TARTE_QU))
                mosta_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MOSTA_QU))
                misso_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MISSO_QU))
                juliana_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_JULIANA_QU))
                bac_5_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_BAC_5_QU))
                bac_6_q_u = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_BAC_6_QU))
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATEAT))
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATEAT))
                __v = cursor.getString(cursor.getColumnIndexOrThrow(KEY___V))
                up_to_server = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_UP_TO_SERVER))


                val item = AllProductModel(id = _id, server_id = server_id, orderId = orderID, mini_qty = mini_qty, mini_q_u = mini_q_u,
                    trio_qty = trio_qty, trio_q_u = trio_q_u, solo_qty = solo_qty,
                    solo_q_u = solo_q_u, pot_qty = pot_qty, pot_q_u = pot_q_u, gini_qty = gini_qty, gini_q_u = gini_q_u,
                    big_qty = big_qty, big_q_u = big_q_u, cornito_4_qty = cornito_4_qty, cornito_4_q_u = cornito_4_q_u,
                    cornito_5_qty = cornito_5_qty, cornito_5_q_u = cornito_5_q_u, cornito_g_qty = cornito_g_qty,
                    cornito_g_q_u = cornito_g_q_u, gofrito_qty = gofrito_qty, gofrito_q_u = gofrito_q_u, pot_v_qty = pot_v_qty,
                    pot_v_q_u = pot_v_q_u, g8_qty =g8_qty, g8_q_u = g8_q_u, gold_qty = gold_qty, gold_q_u = gold_q_u,
                    skiper_qty = skiper_qty, skiper_q_u = skiper_q_u, scobido_qty = scobido_qty, scobido_q_u = scobido_q_u,
                    mini_scobido_qty = mini_scobido_qty, mini_scobido_q_u = mini_scobido_q_u, venezia_qty = venezia_qty,
                    venezia_q_u = venezia_q_u, bf_400_q_u = bf_400_q_u, bf_250_q_u = bf_250_q_u, bf_230_q_u = bf_230_q_u,
                    bf_200_q_u = bf_200_q_u, bf_150_q_u = bf_150_q_u, buch_q_u = buch_q_u, tarte_q_u = tarte_q_u,
                    bac_5_q_u = bac_5_q_u, bac_6_q_u = bac_6_q_u, mosta_q_u = mosta_q_u, misso_q_u = misso_q_u,
                    juliana_q_u = juliana_q_u, createdAt = createdAt, updatedAt = updatedAt, __v = __v, up_to_server = up_to_server)

                itemList.add(item)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return itemList
    }

    //Method to read the records from database in form of ArrayList
    fun viewOrderSummary(id: Int, productListId: Int, upToServer: Boolean = false
                         , getAll: Boolean = false, date: String = ""): ArrayList<OrderSummaryModel> {

        val orderSummaryList: ArrayList<OrderSummaryModel> = ArrayList()

        val selectQuery = if(upToServer) "SELECT * FROM $TABLE_ORDER_SUMMARY WHERE $KEY_UP_TO_SERVER = 0 "
        else if(getAll && date != "") "SELECT * FROM $TABLE_ORDER_SUMMARY WHERE $KEY_DATE = '$date' AND $KEY_IS_CHECK = 0 "
        else "SELECT * FROM $TABLE_ORDER_SUMMARY WHERE $KEY_CLIENT_ID = $id AND $KEY_PRODUCT_LIST_ID = $productListId"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var _id: Int
        var server_id: String
        var client_name: String
        var client_id: Int
        var product_list_id: Int
        var total_to_pay: Int
        var verssi: String
        var rest: String
        var iscredit: Int
        var _date: String
        var is_check: Int
        var createdAt: String
        var updatedAt: String
        var __v: String
        var up_to_server: Int

        if (cursor.moveToFirst()) {
            do {
                _id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                server_id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SERVER_ID))
                client_name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CLIENT_NAME))
                client_id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CLIENT_ID))
                product_list_id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PRODUCT_LIST_ID))
                total_to_pay = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TOTAL_TO_PAY))
                verssi = cursor.getString(cursor.getColumnIndexOrThrow(KEY_VERSSI))
                rest = cursor.getString(cursor.getColumnIndexOrThrow(KEY_REST))
                iscredit = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_IS_CREDIT))
                _date = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATE))
                is_check = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_IS_CHECK))
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATEAT))
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATEAT))
                __v = cursor.getString(cursor.getColumnIndexOrThrow(KEY___V))
                up_to_server = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_UP_TO_SERVER))

                val order = OrderSummaryModel(id = _id, server_id = server_id, client_name = client_name, client_id = client_id,
                    product_list_id = product_list_id, total_to_pay = total_to_pay, verssi = verssi,
                    rest = rest, iscredit = iscredit, up_to_server = up_to_server, date = _date, is_check = is_check
                    , createdAt = createdAt, updatedAt = updatedAt, __v = __v)
                orderSummaryList.add(order)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return orderSummaryList
    }

    fun viewVerssemnt(date: String = "", upToServer: Boolean = false): ArrayList<VerssementModel> {

        val verssementList: ArrayList<VerssementModel> = ArrayList()

        val selectQuery = if(upToServer && date.isEmpty()){
            "SELECT * FROM $TABLE_VERSSEMENT WHERE $KEY_UP_TO_SERVER = 0"
        } else if (date.isNotEmpty()){
            "SELECT * FROM $TABLE_VERSSEMENT WHERE $KEY_DATE = '$date' AND $KEY_IS_CHECK = 0 "
        } else "SELECT * FROM $TABLE_VERSSEMENT"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var server_id: String
        var cliant_name: String
        var region: String
        var client_id: String
        var verssi: String
        var rest: String
        var old_somme: String
        var is_check: Int
        var _date: String
        var createdAt: String
        var updatedAt: String
        var __v: String
        var up_to_server: Int

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                server_id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SERVER_ID))
                cliant_name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CLIENT_NAME))
                region = cursor.getString(cursor.getColumnIndexOrThrow(KEY_REGION))
                client_id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CLIENT_ID))
                verssi = cursor.getString(cursor.getColumnIndexOrThrow(KEY_VERSSI))
                old_somme = cursor.getString(cursor.getColumnIndexOrThrow(KEY_OLD_SOMME))
                rest = cursor.getString(cursor.getColumnIndexOrThrow(KEY_REST))
                _date = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATE))
                is_check = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_IS_CHECK))
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATEAT))
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATEAT))
                __v = cursor.getString(cursor.getColumnIndexOrThrow(KEY___V))
                up_to_server = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_UP_TO_SERVER))

                val item = VerssementModel(id, server_id, client_id, cliant_name, region, old_somme, verssi, rest, up_to_server, is_check, _date, createdAt, updatedAt, __v)
                verssementList.add(item)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return verssementList
    }

    fun viewLastOrderSummary(): Int {

        // Query to select all the records from the table.
        val selectQuery = "SELECT * FROM $TABLE_ORDER_SUMMARY ORDER BY $KEY_ID DESC LIMIT 1"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return 0
        }

        var id = 0

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return id
    }

    fun viewLastAllProduct(): Int {

        // Query to select all the records from the table.
        val selectQuery = "SELECT * FROM $TABLE_ALL_PRODUCT ORDER BY $KEY_ID DESC LIMIT 1"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return 0
        }

        var id = 0

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return id
    }

    //Method to read the records from database in form of ArrayList
    fun viewUser(upToServer: Boolean = false): ArrayList<UserModel> {

        val userList: ArrayList<UserModel> = ArrayList()

        val selectQuery = if(upToServer) "SELECT  * FROM $TABLE_USER WHERE $KEY_UP_TO_SERVER = 0"
        else "SELECT  * FROM $TABLE_USER"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var server_id: String
        var username: String
        var email: String
        var password: String
        var camion: String
        var profile_pic: String
        var isadmin: Int
        var createdAt: String
        var updatedAt: String
        var __v: String
        var up_to_server: Int

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                server_id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SERVER_ID))
                username = cursor.getString(cursor.getColumnIndexOrThrow(KEY_USERNAME))
                email = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL))
                password = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASSWORD))
                profile_pic = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PROFILE_PIC))
                camion = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CAMION))
                isadmin = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ISADMIN))
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATEAT))
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATEAT))
                __v = cursor.getString(cursor.getColumnIndexOrThrow(KEY___V))
                up_to_server = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_UP_TO_SERVER))

                val user = UserModel(id = id, server_id = server_id, username = username, email = email, password = password, camion = camion,
                    profile_pic = profile_pic, isadmin = isadmin, createdAt = createdAt, updatedAt = updatedAt, __v = __v, up_to_server = up_to_server)
                userList.add(user)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    //Method to read the records from database in form of ArrayList
    fun viewStock(): ArrayList<CamionStockModel> {

        val stockList: ArrayList<CamionStockModel> = ArrayList<CamionStockModel>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM $TABLE_STOCK"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var server_id: String
        var name: String
        var qty: Int
        var createdAt: String
        var updatedAt: String
        var __v: String
        var up_to_server: Int

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                server_id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SERVER_ID))
                name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
                qty = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_QTY))
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATEAT))
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATEAT))
                __v = cursor.getString(cursor.getColumnIndexOrThrow(KEY___V))
                up_to_server = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_UP_TO_SERVER))

                val item = CamionStockModel(id = id, server_id = server_id, name = name, qty = qty
                    , createdAt = createdAt, updatedAt = updatedAt, __v = __v, up_to_server = up_to_server)
                stockList.add(item)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return stockList
    }

    /**
     * Function to update record
     */
    fun updateItem(item: ItemModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, item.server_id)
        contentValues.put(KEY_NAME, item.name)
        contentValues.put(KEY_PRICE, item.price)
        contentValues.put(KEY_IMAGE, item.image)
        contentValues.put(KEY_STATUS, item.status)
        contentValues.put(KEY_QTY_PAR_ONE, item.qty_par_one)
        contentValues.put(KEY_CREATEAT, item.createdAt)
        contentValues.put(KEY_UPDATEAT, item.updatedAt)
        contentValues.put(KEY___V, item.__v)
        contentValues.put(KEY_UP_TO_SERVER, 0)

        // Updating Row
        val success = db.update(TABLE_ITEMS, contentValues, KEY_ID + "=" + item.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    fun updateVerssement(item: VerssementModel, type: String = ""): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()

        if (type.isEmpty()){
            contentValues.put(KEY_SERVER_ID, item.server_id)
            contentValues.put(KEY_CLIENT_NAME, item.client_name)
            contentValues.put(KEY_CLIENT_ID, item.client_id)
            contentValues.put(KEY_REGION, item.region)
            contentValues.put(KEY_OLD_SOMME, item.old_somme)
            contentValues.put(KEY_VERSSI, item.verssi)
            contentValues.put(KEY_REST, item.rest)
            contentValues.put(KEY_VERSSI, item.verssi)
            contentValues.put(KEY_VERSSI, item.date)
            contentValues.put(KEY_IS_CHECK, 0)
            contentValues.put(KEY_CREATEAT, item.createdAt)
            contentValues.put(KEY_UPDATEAT, item.updatedAt)
            contentValues.put(KEY___V, item.__v)
            contentValues.put(KEY_UP_TO_SERVER, 0)
        } else if (type == "is_check") {
            contentValues.put(KEY_IS_CHECK, 0)
            contentValues.put(KEY_UP_TO_SERVER, 0)
        }

        // Updating Row
        val success = db.update(TABLE_VERSSEMENT, contentValues, KEY_ID + "=" + item.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to update region
     */
    fun updateRegion(region: RegionModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, region.server_id)
        contentValues.put(KEY_REGION, region.region_name)
        contentValues.put(KEY_CREATEAT, region.createdAt)
        contentValues.put(KEY_UPDATEAT, region.updatedAt)
        contentValues.put(KEY___V, region.__v)
        contentValues.put(KEY_UP_TO_SERVER, region.up_to_server)

        // Updating Row
        val success = db.update(TABLE_REGION, contentValues, "$KEY_ID= ${region.id}", null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to update Client
     */
    fun updateClient(client: ClientModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, client.server_id)
        contentValues.put(KEY_CLIENT_NAME, client.client_name)
        contentValues.put(KEY_REGION, client.region)
        contentValues.put(KEY_PHONE, client.phone)
        contentValues.put(KEY_PRICES, client.prices)
        contentValues.put(KEY_ISPROMO, client.is_promo)
        contentValues.put(KEY_ISFRIGO, client.is_frigo)
        contentValues.put(KEY_IS_CREDIT, client.is_credit)
        contentValues.put(KEY_OLDCREDIT, client.old_credit)
        contentValues.put(KEY_CREDITBON, client.credit_bon)
        contentValues.put(KEY_LASTSERVE, client.last_serve)
        contentValues.put(KEY_CREATEAT, client.createdAt)
        contentValues.put(KEY_UPDATEAT, client.updatedAt)
        contentValues.put(KEY___V, client.__v)
        contentValues.put(KEY_UP_TO_SERVER, 0)

        // Updating Row
        val success = db.update(TABLE_CLIENT, contentValues, KEY_ID + "=" + client.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    fun updateUpToServer(id: Int, tableName: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_UP_TO_SERVER, 1)

        val success = when (tableName) {
            "TABLE_ITEMS" -> db.update(TABLE_ITEMS, contentValues, "$KEY_ID = $id", null)
            "TABLE_STOCK" -> db.update(TABLE_STOCK, contentValues, "$KEY_ID = $id", null)
            "TABLE_USER" -> db.update(TABLE_USER, contentValues, "$KEY_ID = $id", null)
            "TABLE_ORDER_SUMMARY" -> db.update(TABLE_ORDER_SUMMARY, contentValues, "$KEY_ID = $id", null)
            "TABLE_ALL_PRODUCT" -> db.update(TABLE_ALL_PRODUCT, contentValues, "$KEY_ID = $id", null)
            "TABLE_CLIENT" -> db.update(TABLE_CLIENT, contentValues, "$KEY_ID = $id", null)
            "TABLE_REGION" -> db.update(TABLE_REGION, contentValues, "$KEY_ID = $id", null)
            else -> -1
        }

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to update all product
     */
    fun updateAllProduct(item: AllProductModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, item.server_id)
        contentValues.put(KEY_ORDER_ID, item.orderId)
        contentValues.put(KEY_MINI_QTY, item.mini_qty)
        contentValues.put(KEY_MINI_QU, item.mini_q_u)
        contentValues.put(KEY_TRIO_QTY, item.trio_qty)
        contentValues.put(KEY_TRIO_QU, item.trio_q_u)
        contentValues.put(KEY_SOLO_QTY, item.solo_qty)
        contentValues.put(KEY_SOLO_QU, item.solo_q_u)
        contentValues.put(KEY_POT_QTY, item.pot_qty)
        contentValues.put(KEY_POT_QU, item.pot_q_u)
        contentValues.put(KEY_GINI_QTY, item.gini_qty)
        contentValues.put(KEY_GINI_QU, item.gini_q_u)
        contentValues.put(KEY_BIG_QTY, item.big_qty)
        contentValues.put(KEY_BIG_QU, item.big_q_u)
        contentValues.put(KEY_CORNITO_4_QTY, item.cornito_4_qty)
        contentValues.put(KEY_CORNITO_4_QU, item.cornito_4_q_u)
        contentValues.put(KEY_CORNITO_5_QTY, item.cornito_5_qty)
        contentValues.put(KEY_CORNITO_5_QU, item.cornito_5_q_u)
        contentValues.put(KEY_CORNITO_G_QTY, item.cornito_g_qty)
        contentValues.put(KEY_CORNITO_G_QU, item.cornito_g_q_u)
        contentValues.put(KEY_GOFRITO_QTY, item.gofrito_qty)
        contentValues.put(KEY_GOFRITO_QU, item.gofrito_q_u)
        contentValues.put(KEY_POT_V_QTY, item.pot_v_qty)
        contentValues.put(KEY_POT_V_QU, item.pot_v_q_u)
        contentValues.put(KEY_G8_QTY, item.g8_qty)
        contentValues.put(KEY_G8_QU, item.g8_q_u)
        contentValues.put(KEY_GOLD_QTY, item.gold_qty)
        contentValues.put(KEY_GOLD_QU, item.gold_q_u)
        contentValues.put(KEY_SKIPER_QTY, item.skiper_qty)
        contentValues.put(KEY_SKIPER_QU, item.skiper_q_u)
        contentValues.put(KEY_SCOBIDO_QTY, item.scobido_qty)
        contentValues.put(KEY_SCOBIDO_QU, item.scobido_q_u)
        contentValues.put(KEY_MINI_SCOBIDO_QTY, item.mini_scobido_qty)
        contentValues.put(KEY_MINI_SCOBIDO_QU, item.mini_scobido_q_u)
        contentValues.put(KEY_VENEZIA_QTY, item.venezia_qty)
        contentValues.put(KEY_VENEZIA_QU, item.venezia_q_u)
        contentValues.put(KEY_BF_400_QU, item.bf_400_q_u)
        contentValues.put(KEY_BF_250_QU, item.bf_250_q_u)
        contentValues.put(KEY_BF_230_QU, item.bf_230_q_u)
        contentValues.put(KEY_BF_200_QU, item.bf_200_q_u)
        contentValues.put(KEY_BF_150_QU, item.bf_150_q_u)
        contentValues.put(KEY_BUCH_QU, item.buch_q_u)
        contentValues.put(KEY_TARTE_QU, item.tarte_q_u)
        contentValues.put(KEY_MOSTA_QU, item.mosta_q_u)
        contentValues.put(KEY_MISSO_QU, item.misso_q_u)
        contentValues.put(KEY_JULIANA_QU, item.juliana_q_u)
        contentValues.put(KEY_BAC_5_QU, item.bac_5_q_u)
        contentValues.put(KEY_BAC_6_QU, item.bac_6_q_u)
        contentValues.put(KEY_CREATEAT, item.createdAt)
        contentValues.put(KEY_UPDATEAT, item.updatedAt)
        contentValues.put(KEY___V, item.__v)
        contentValues.put(KEY_UP_TO_SERVER, 0)

        // Updating Row item.id
        val success = db.update(TABLE_ALL_PRODUCT, contentValues, KEY_ORDER_ID + "=" + item.orderId, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to update Order Summary
     */
    fun updateOrderSummary(order: OrderSummaryModel, type: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        if (type == "up_to_server"){
            contentValues.put(KEY_PRODUCT_LIST_ID, order.product_list_id)
            contentValues.put(KEY_UP_TO_SERVER, 0)
        } else if (type == "All"){
            contentValues.put(KEY_SERVER_ID, order.server_id)
            contentValues.put(KEY_CLIENT_ID, order.client_id)
            contentValues.put(KEY_CLIENT_NAME, order.client_name)
            contentValues.put(KEY_PRODUCT_LIST_ID, order.product_list_id)
            contentValues.put(KEY_TOTAL_TO_PAY, order.total_to_pay)
            contentValues.put(KEY_VERSSI, order.verssi)
            contentValues.put(KEY_REST, order.rest)
            contentValues.put(KEY_DATE, order.date)
            contentValues.put(KEY_IS_CREDIT, order.iscredit)
            contentValues.put(KEY_IS_CHECK, 0)
            contentValues.put(KEY_CREATEAT, order.createdAt)
            contentValues.put(KEY_UPDATEAT, order.updatedAt)
            contentValues.put(KEY___V, order.__v)
            contentValues.put(KEY_UP_TO_SERVER, 0)
        } else {
            contentValues.put(KEY_PRODUCT_LIST_ID, order.product_list_id)
            contentValues.put(KEY_IS_CHECK, order.is_check)
        }

        val success = db.update(TABLE_ORDER_SUMMARY, contentValues, KEY_ID + "=" + order.id, null)

        db.close()
        return success
    }

    fun updateTotalToPayOrderSummary(id: Int, Price: Float, Verssi: Int, Rest: Int, IsCredit: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TOTAL_TO_PAY, Price.toInt())
        contentValues.put(KEY_VERSSI, Verssi)
        contentValues.put(KEY_REST, Rest)
        contentValues.put(KEY_IS_CREDIT, IsCredit)
        contentValues.put(KEY_UP_TO_SERVER, 0)

        val success = db.update(TABLE_ORDER_SUMMARY, contentValues, "$KEY_ID=$id", null)

        db.close()
        return success
    }

    /**
     * Function to update user
     */
    fun updateUser(user: UserModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, user.server_id)
        contentValues.put(KEY_USERNAME, user.username)
        contentValues.put(KEY_EMAIL, user.email)
        contentValues.put(KEY_PASSWORD, user.password)
        contentValues.put(KEY_CAMION, user.camion)
        contentValues.put(KEY_PROFILE_PIC, user.profile_pic)
        contentValues.put(KEY_ISADMIN, user.isadmin)
        contentValues.put(KEY_CREATEAT, user.createdAt)
        contentValues.put(KEY_UPDATEAT, user.updatedAt)
        contentValues.put(KEY___V, user.__v)
        contentValues.put(KEY_UP_TO_SERVER, 0)

        // Updating Row
        val success = db.update(TABLE_USER, contentValues, "$KEY_ID  = ${user.id}", null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to update stock
     */
    fun updateStock(stock: CamionStockModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_SERVER_ID, stock.server_id)
        contentValues.put(KEY_NAME, stock.name)
        contentValues.put(KEY_QTY_PAR_ONE, stock.qty)
        contentValues.put(KEY_CREATEAT, stock.createdAt)
        contentValues.put(KEY_UPDATEAT, stock.updatedAt)
        contentValues.put(KEY___V, stock.__v)
        contentValues.put(KEY_UP_TO_SERVER, 0)

        // Updating Row
        val success = db.update(TABLE_STOCK, contentValues, KEY_ID + "=" + stock.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to delete record
     */
    fun deleteItem(emp: ItemModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)
        // Deleting Row
        val success = db.delete(TABLE_ITEMS, KEY_ID + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    fun deleteVerssement(emp: VerssementModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)
        // Deleting Row
        val success = db.delete(TABLE_VERSSEMENT, KEY_ID + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to delete region
     */
    fun deleteRegion(emp: RegionModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)
        // Deleting Row
        val success = db.delete(TABLE_REGION, KEY_ID + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to delete client
     */
    fun deleteClient(emp: ClientModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)
        // Deleting Row
        val success = db.delete(TABLE_CLIENT, KEY_ID + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to delete all product
     */
    fun deleteAllProduct(orderId: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ORDER_ID, orderId)
        // Deleting Row
//        val success = db.delete(TABLE_ALL_PRODUCT, KEY_ID + "=" + emp.id, null)
        val success = db.delete(TABLE_ALL_PRODUCT, "$KEY_ORDER_ID=$orderId", null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to delete record
     */
    fun deleteOrderSummary(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, id)
        // Deleting Row
        val success = db.delete(TABLE_ORDER_SUMMARY, "$KEY_ID=$id", null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to delete user
     */
    fun deleteUser(emp: UserModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)
        // Deleting Row
        val success = db.delete(TABLE_USER, KEY_ID + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /**
     * Function to delete stock
     */
    fun deleteStock(emp: CamionStockModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)
        // Deleting Row
        val success = db.delete(TABLE_STOCK, KEY_ID + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }
}