package com.victor.utilitymeters

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var flashLightStatus: Boolean = false
    lateinit var botttomNavigationMenu: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFlashLight()
        botttomNavigationMenu = findViewById(R.id.bottomNav)
        botttomNavigationMenu.setOnItemSelectedListener {item ->
            when(item.itemId) {
                R.id.item1 -> {

                }
                R.id.item2 -> {

                }
                R.id.item3 -> {
                    openFlashLight()
                }
            }
            true
        }
        botttomNavigationMenu.selectedItemId = R.id.item1
//--------------------------------------------------------------------------------------------------
// Текущая дата в TextView

        val currentDate = findViewById<TextView>(R.id.textDate)
        fun getCurrentDate(): String {
            val sdf = SimpleDateFormat("dd MMMM yyyy")
            return sdf.format(Date())
        }
        currentDate.text = getCurrentDate()

//--------------------------------------------------------------------------------------------------
// Ввод показания счётчиков

        findViewById<LinearLayout>(R.id.cwb_btn).setOnClickListener {
            findViewById<TextView>(R.id.text_input_cold_water_bathroom).text = ""
        }
        findViewById<LinearLayout>(R.id.hwb_btn).setOnClickListener {
            findViewById<TextView>(R.id.text_input_hot_water_bathroom).text = ""
        }
        findViewById<LinearLayout>(R.id.cwk_btn).setOnClickListener {
            findViewById<TextView>(R.id.text_input_cold_water_kitchen).text = ""
        }
        findViewById<LinearLayout>(R.id.hwk_btn).setOnClickListener {
            findViewById<TextView>(R.id.text_input_hot_water_kitchen).text = ""
        }
        findViewById<LinearLayout>(R.id.electric_btn).setOnClickListener {
            findViewById<TextView>(R.id.text_input_electricity).text = ""
        }
    }
//--------------------------------------------------------------------------------------------------
// Фонарик

    private fun openFlashLight() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
        if (!flashLightStatus) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode(cameraId, false)
                }
                flashLightStatus = true

            } catch (e: CameraAccessException) {
            }
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode(cameraId, true)
                }
                flashLightStatus = false
            } catch (e: CameraAccessException) {
            }
        }
    }
//--------------------------------------------------------------------------------------------------
}