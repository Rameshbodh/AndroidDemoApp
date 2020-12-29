package com.dmi.barcodetextscanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.dmi.barcodetextscanhelper.barcode.BarcodeActivity
import com.dmi.barcodetextscanhelper.ocr.OCRActivity

class MainActivity : AppCompatActivity() {

    private val RequestCameraPermission = 1001
    private var LaunchBarCode: Button? = null
    private var LaunchTextScan: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_launch)

        LaunchBarCode = findViewById(R.id.scanBarcode)
        LaunchTextScan = findViewById(R.id.scanText)
    }

    override fun onResume() {
        super.onResume()

        if (ActivityCompat.checkSelfPermission( applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.CAMERA), RequestCameraPermission)
        } else {
            Toast.makeText(applicationContext, "Permission ALREADY Granted", Toast.LENGTH_LONG).show()
            setupClicks()
        }
    }

    private fun setupClicks() {
        LaunchBarCode?.apply {
            setOnClickListener {
                startActivity(Intent(context, BarcodeActivity::class.java))
            }
        }

        LaunchTextScan?.apply {
            setOnClickListener {
                startActivity(Intent(context, OCRActivity::class.java))
            }
        }
    }

    override fun onPause() {
        super.onPause()

        LaunchBarCode?.setOnClickListener(null)
        LaunchTextScan?.setOnClickListener(null)
    }

    override fun onDestroy() {
        super.onDestroy()

        LaunchBarCode = null
        LaunchTextScan = null
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == RequestCameraPermission) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(applicationContext, "Permission Not Granted", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_LONG).show()
                        setupClicks()
                    }
                } else {
                    Toast.makeText(applicationContext, "Permission Not Granted", Toast.LENGTH_LONG).show()
                }
        }
    }
}