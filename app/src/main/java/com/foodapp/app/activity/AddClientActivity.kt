package com.foodapp.app.activity

import android.content.Intent
import com.foodapp.app.R
import com.foodapp.app.base.BaseActivity
import com.foodapp.app.utils.Common.getCurrentLanguage
import android.widget.Toast
import com.foodapp.app.model.ClientModel
import com.foodapp.app.model.RegionModel
import kotlinx.android.synthetic.main.activity_addclient.*
import kotlinx.android.synthetic.main.activity_adduser.btnAddUser
import kotlinx.android.synthetic.main.activity_cart.ivBack
import kotlinx.android.synthetic.main.activity_cart.ivHome
import java.lang.Integer.parseInt

class AddClientActivity : BaseActivity() {

    override fun setLayout(): Int {
        return R.layout.activity_addclient
    }

    //Method for saving the client in database
    private fun addClient() {
        val clientName = etClientName.text.toString()
        val region = etRegion.text.toString()
        val phone = etPhone.text.toString()
        // order table of prices
        //"mini","mini trio","pot","solo","gini","pot vally","big","cornito 45"or"cornito 50","cornito gini",
        // "gofrito","g8","gold","skiper","scobido","mini scobido","venezia","B.F 2L","B.F 1L",
        // "B.F 900ml","B.F 750ml","B.F 0,5L","buch","tarte","mosta","misso","juliana","bac 5L","bac 6L"
        val prices = "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100"
        val isPromo = etIsPromo.text.toString()
        val isFrigo = etIsFrigo.text.toString()
        val isCredit = etIsCredit.text.toString()
        val oldCredit = etCredit.text.toString()
        val creditBon = etCreditBon.text.toString()
        val lastServe = etLastServe.text.toString()
        val upToServer = 0
        val databaseHandler = DatabaseHandler(this)

        val regionIsAdded = databaseHandler.viewCheckRegion(region)
        if (!regionIsAdded) {
            val statusRegion = databaseHandler.addRegion(
                RegionModel(0,"", region, "0","0","0",0)
            )
            if (statusRegion > -1) {
                Toast.makeText(applicationContext, "Region saved", Toast.LENGTH_SHORT).show()
            }
        }

        if (clientName.isNotEmpty() && region.isNotEmpty() && phone.isNotEmpty() && prices.isNotEmpty() &&
            isPromo.isNotEmpty() && isFrigo.isNotEmpty() && isCredit.isNotEmpty() && oldCredit.isNotEmpty() &&
            creditBon.isNotEmpty() && lastServe.isNotEmpty()
        ) {
            val status =
                databaseHandler.addClient(ClientModel(0,"", clientName, phone, prices, region, oldCredit, parseInt(isFrigo), parseInt(isPromo),
                    parseInt(isCredit), parseInt(creditBon), lastServe, "0", "0", "0", upToServer))
            if (status > -1) {
                Toast.makeText(applicationContext, "Client saved", Toast.LENGTH_SHORT).show()
                etClientName.text.clear()
                etRegion.text.clear()
                etPhone.text.clear()
                etIsPromo.text.clear()
                etIsFrigo.text.clear()
                etIsCredit.text.clear()
                etCredit.text.clear()
                etCreditBon.text.clear()
                etLastServe.text.clear()
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Data field cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //Method for saving the Clients records in database
    private fun addInitialClients() {
        val clients = arrayListOf<ClientModel>()
        val camion = etCamion.text.toString()

        if (camion == "01") {
            // region 3owana
            clients.add(ClientModel(
                0,"", "salman khebache", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "wlad bounar", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "badis bounar", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "wlad bounar", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "hossine boulahya", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "wlad bounar", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "hossine khebache", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "wlad bounar", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "dawade m.g", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "wlad bounar", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "foade khebache", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "wlad bounar", "0", 1, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "howari", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "wlad bounar", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "yasser bechekit", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "3amala", "0", 1, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "harone bousied", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "3amala", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "hamo bechekit", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "3amala", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "mounir bolakheyot", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "3amala", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "idrisse bouseid", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "ondro", "0", 1, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "halim", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "ondro", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "bordj blida", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "ondro", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "yassine cheraytia", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "ondro", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "ami mohe", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "roche noir", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "yakoub bofasiw", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "roche noir", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "yakoub jalnare", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "roche noir", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "sadak bofasiw", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "timisar", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "walid bofasiw", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "timisar", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "aymen", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "timisar", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "merdasse", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "timisar", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "lokman bela3raj", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "timisar", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "houssam bolakhyot", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "di9ayi", "0", 1, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "jamal bn zayad", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "di9ayi", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "jalole 3ajrod", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "l9arya", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "bourawi", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "l9arya", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "asma", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "bouzarman", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "slamo fdikhe", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "bouzarman", "0", 1, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "idrisse cheraytia", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "bouzarman", "0", 0, 1, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "abd elhamid fdikhe", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "bouzarman", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "ala fenohe", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 1, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "hamza lassace", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "walid harrouche", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 1, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "mahdi bouzmada", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "lokman bela3raj bivet", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "ibrahim 3miyore", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 1, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "samir hank", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 1, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "adel botawi", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 1, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "nadir ajrod", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 1, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "mahmoud balaman", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "kamal cheraytia", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 1, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "radwan khemissa", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 1, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "mekhetar bolkheyot", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 1, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "hicham botawi", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "samir salim", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 1, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "mounir bolkheyot morias", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "hakim pizzeria", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "kamal ma3tib", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "ja3fer m.g", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el 3owana", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "amine kheracha", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "kheracha", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "nasar m.g", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "aftis", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "3lilo", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "aftis", "0", 1, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "zino", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "aftis", "0", 1, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "hamza bofanara", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "aftis", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "hamza 09", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "aftis", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "hossine la vag", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "aftis", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "walid m.g", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "taza", "0", 1, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "ayoub m.g", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "taza", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "mahdi bourich", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "taza", "0", 1, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "khiro birotaba", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "taza", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))

            // bazol S.3 jnah jam3a 3andar milia
            clients.add(ClientModel(
                0,"", "sa3id", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "bazol", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "ibrahim", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "bazol", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "ma9ha deradar", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "bazol", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "zohir m.g", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "bazol", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "lotfi m.g", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "amine S.3", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "taher birotaba", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "sadik cador", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "abd allah cador", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "karim bivet", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "ami hossin S.3", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "monir cremrie", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "islam patiserie", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "bivet 1 plage", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "amine plage S.3", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "abd rahman plage", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "riad cosmitique", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 1, 6, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "hamza cherbal", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "3adlan m.g", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 1, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "tarek cosmitique", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "mossa cosmitique", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "hicham bakouche", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "jamal cosmitique", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "ami l3arbi litime", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "walid superet", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 1, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "marwan placet", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "tarek cherbal", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 1, 2, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "mohe makteba", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "houssam maktaba S.3", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "adel superet", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "mohe maktaba tri9", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "sidi abd el azize", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "gano rogi m.g", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "jnahe", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "ibrahim ma9ha", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "jnahe", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "ami cherif", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "jam3a bni hbibi", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "nacer m.g", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "jam3a bni hbibi", "0", 1, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "amine superet", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "jam3a bni hbibi", "0", 1, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "idris jm3a", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "jam3a bni hbibi", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "medjdi hebchi", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "jam3a bni hbibi", "0", 1, 0, 1, 3, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "ami mouloud", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "3ancer", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "tufik", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "3ancer", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "bilal", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "3ancer", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "m.g 3ancer", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "3ancer", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "samir", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el millia", "0", 0, 0, 1, 1, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "mohemed boubartakh", "0",
                "28:28:28:30:42:33:40:50:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "el millia", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
            clients.add(ClientModel(
                0,"", "azzedine maktba", "0",
                "28:28:28:30:42:33:40:48:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "jam3a bni hbibi", "0", 0, 0, 0, 0, "0", "0", "0", "0", 0
            ))
        } else if (camion == "02") {
            clients.add(ClientModel(
                0,"", "bilal harrouche", "0",
                "28:28:28:30:42:33:40:48:70:35:50:50:62:33:28:43:400:250:230:200:150:450:500:60:55:80:900:1100",
                "lékiti", "0", 1, 0, 0, 0, "0", "0", "0", "0", 0
            ))
        } else if (camion == "") {
            Toast.makeText(
                applicationContext,
                "Camion field cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }



//        clients.add(ClientModel(
//            0, "0", "0",
//            "28:28:28:30:42:33:40:50:70:35:50:50:62:32:28:43:400:250:230:200:150:450:500:55:50:80:900:1100",
//            "0", "0", 0, 0, 0, 0, "0", 0
//        ))
        val databaseHandler = DatabaseHandler(this)

        for (i in 0 until clients.size) {
            val regionIsAdded = databaseHandler.viewCheckRegion(clients[i].region)
            if (!regionIsAdded) {
                val statusRegion = databaseHandler.addRegion(RegionModel(i,"",clients[i].region, "0", "0", "0",0))
                if (statusRegion < 0) {
                    Toast.makeText(applicationContext, "ERROR " , Toast.LENGTH_LONG).show()
                }
            }

            val statusDB = databaseHandler.addClient(clients[i])
            if (statusDB < 0) {
                Toast.makeText(applicationContext, "ERROR " , Toast.LENGTH_LONG).show()
            }
        }
        Toast.makeText(applicationContext, "Initial Clients Done. " , Toast.LENGTH_LONG).show()

    }

    override fun InitView() {
        getCurrentLanguage(this@AddClientActivity,false)

        btnAddUser.setOnClickListener {
            addClient()
        }

        btnAddInitialClientForAdmin.setOnClickListener {
            addInitialClients()
        }

        ivBack.setOnClickListener {
            val intent=Intent(this@AddClientActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        ivHome.setOnClickListener {
            val intent=Intent(this@AddClientActivity,DashboardActivity::class.java).putExtra("pos","1")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val intent=Intent(this@AddClientActivity,DashboardActivity::class.java).putExtra("pos","1")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        getCurrentLanguage(this@AddClientActivity, false)
    }

}
