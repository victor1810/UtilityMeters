package com.victor.utilitymeters

import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
   private lateinit var cameraManager: CameraManager
   private lateinit var cameraId: String
   private lateinit var toggleButton: ToggleButton
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)

      val currentDate = findViewById<TextView>(R.id.textDate)
        fun getCurrentDate(): String {
            val sdf = SimpleDateFormat("dd MMMM yyyy")
            return sdf.format(Date())
        }
        currentDate.text = getCurrentDate()

      title = "KotlinApp"
      val isFlashAvailable = applicationContext.packageManager
      .hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
      if (!isFlashAvailable) {
         showNoFlashError()
      }
      cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
      try {
         cameraId = cameraManager.cameraIdList[0]
      } catch (e: CameraAccessException) {
         e.printStackTrace()
      }
      toggleButton = findViewById(R.id.flashlightButton)
      toggleButton.setOnCheckedChangeListener { _, isChecked -> switchFlashLight(isChecked) }

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
   private fun showNoFlashError() {
      val alert = AlertDialog.Builder(this)
      .create()
      alert.setTitle("Oops!")
      alert.setMessage("Flash not available in this device...")
      alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _, _ -> finish() }
      alert.show()
   }
   private fun switchFlashLight(status: Boolean) {
      try {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameraManager.setTorchMode(cameraId, status)
         }
      } catch (e: CameraAccessException) {
         e.printStackTrace()
      }
   }
}