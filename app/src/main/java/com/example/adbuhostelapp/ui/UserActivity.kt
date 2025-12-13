package com.example.adbuhostelapp.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adbuhostelapp.ComplaintActivity
import com.example.adbuhostelapp.FeaturedAdapter
import com.example.adbuhostelapp.FeaturedItem
import com.example.adbuhostelapp.PaymentActivity
import com.example.adbuhostelapp.R
import com.google.firebase.auth.FirebaseAuth

class UserActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var featuredAdapter: FeaturedAdapter
    private val featuredList = mutableListOf<FeaturedItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val btnRoomRequest = findViewById<LinearLayout>(R.id.btn_room_request)
        val btnRoomInfo = findViewById<LinearLayout>(R.id.btn_roomdetail)
        val btnUserPayment = findViewById<LinearLayout>(R.id.btn_user_payment)
        val btnUserComplaint = findViewById<LinearLayout>(R.id.btn_user_complaint)
        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        val menuIcon = findViewById<ImageView>(R.id.menu_icon)

        // 🔹 Book room
        btnRoomRequest.setOnClickListener {
            startActivity(Intent(this, RoomApplicationActivity::class.java))
        }

        // 🔹 View allotted room (UID-based)
        btnRoomInfo.setOnClickListener {

            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid.isNullOrEmpty()) {
                return@setOnClickListener
            }

            val intent = Intent(this, MyRoomActivity::class.java)
            intent.putExtra("UID", uid)
            startActivity(intent)
        }

        // 🔹 Payment
        btnUserPayment.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }

        // 🔹 Complaint
        btnUserComplaint.setOnClickListener {
            startActivity(Intent(this, ComplaintActivity::class.java))
        }

        // 🔹 Search (optional)
        searchEditText.addTextChangedListener { editable ->
            Log.d("Search", "User typed: ${editable.toString()}")
        }

        // 🔹 Featured hostels
        recyclerView = findViewById(R.id.featured_recycler)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        featuredList.add(
            FeaturedItem(
                "ADBU Azara",
                R.drawable.adbu2,
                3.5f,
                26.1158,
                91.6136
            )
        )

        featuredList.add(
            FeaturedItem(
                "ADBU Sonapur",
                R.drawable.adbu3,
                4.0f,
                26.1380,
                91.7720
            )
        )

        featuredList.add(
            FeaturedItem(
                "ADBU Tezpur",
                R.drawable.abdu1,
                3.0f,
                26.6528,
                92.7926
            )
        )


        featuredAdapter = FeaturedAdapter(featuredList)
        recyclerView.adapter = featuredAdapter

        // 🔹 Menu popup
        menuIcon.setOnClickListener {
            showCustomPopup(it)
        }
    }

    private fun showCustomPopup(anchor: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_user, null)

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(ColorDrawable(0))
        popupWindow.elevation = 12f

        val home = popupView.findViewById<TextView>(R.id.home)
        val logout = popupView.findViewById<TextView>(R.id.logout)

        home.setOnClickListener {
            popupWindow.dismiss()
        }

        logout.setOnClickListener {
            popupWindow.dismiss()
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        popupWindow.showAsDropDown(anchor, -20, 0, Gravity.NO_GRAVITY)
    }
}
