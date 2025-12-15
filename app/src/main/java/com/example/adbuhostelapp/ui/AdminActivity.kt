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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.adbuhostelapp.AdminAnnouncementActivity
import com.example.adbuhostelapp.AdminComplaintActivity
import com.example.adbuhostelapp.R
import com.example.adbuhostelapp.admin.AdminPaymentHistoryActivity

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        // 🔹 Connect UI elements to code
        val btnRoomStatus = findViewById<LinearLayout>(R.id.btn_applicants)
        val btnComplaint = findViewById<LinearLayout>(R.id.btn_complaint)
        val btnFeePending = findViewById<LinearLayout>(R.id.btn_fee_pending)
        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        val studentinfo = findViewById<LinearLayout>(R.id.btn_student_info)
        val btnannouncement = findViewById<LinearLayout>(R.id.btn_announcement)
        val paymenthistory = findViewById<LinearLayout>(R.id.btn_fee_pending)

        val menuIcon = findViewById<ImageView>(R.id.menu_icon)



        // 🔹 Handle button clicks
        btnRoomStatus.setOnClickListener {
            Toast.makeText(this, "Opening Applicants form", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ViewApplicantActivity::class.java)
            startActivity(intent)
        }
        paymenthistory.setOnClickListener {
            Toast.makeText(this, "Opening Payment history", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AdminPaymentHistoryActivity::class.java)
            startActivity(intent)
        }

        studentinfo.setOnClickListener {
            Toast.makeText(this, "Opening Student Info", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AllStudentsActivity::class.java)
            startActivity(intent)
        }

        btnComplaint.setOnClickListener {
            Toast.makeText(this, "Opening Complaints", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AdminComplaintActivity::class.java)
            startActivity(intent)
        }


        btnannouncement.setOnClickListener {
            Toast.makeText(this, "Opening Announcement ", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AdminAnnouncementActivity::class.java)
            startActivity(intent)
        }
//
//        btnFeePending.setOnClickListener {
//            Log.d("AdminActivity", "Fee Pending clicked")
//            val intent = Intent(this, FeePendingActivity::class.java)
//            startActivity(intent)
//        }


        // 🔹 Search functionality
        searchEditText.addTextChangedListener { editable ->
            val query = editable.toString()
            Log.d("Search", "User typed: $query")
            // TODO: Implement filtering logic
        }

        // 🔹 Menu icon click
        menuIcon.setOnClickListener {
            showCustomPopup(it)
        }
    }

    private fun showCustomPopup(anchor: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.main_popup_menu, null)

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(ColorDrawable(0))
        popupWindow.elevation = 12f

        // wire menu item clicks
        val Home = popupView.findViewById<TextView>(R.id.home)
        val logout = popupView.findViewById<TextView>(R.id.logout)
//        val menuMessages = popupView.findViewById<TextView>(R.id.menu_messages)

        Home.setOnClickListener {
            Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            Toast.makeText(this, "Topics clicked", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
//
//        menuMessages.setOnClickListener {
//            Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
//            popupWindow.dismiss()
//        }


        val xOffset = -20 // shift left a bit (adjust)
        val yOffset = 0
        popupWindow.showAsDropDown(anchor, xOffset, yOffset, Gravity.NO_GRAVITY)
    }
}
