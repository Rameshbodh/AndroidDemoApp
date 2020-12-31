package com.dmi.scannerhelper

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dmi.scanhelper.barcode.BarcodeActivity
import com.dmi.scanhelper.ocr.OCRActivity


class MainActivity : AppCompatActivity() {

    var mScanText: Button? = null
    var mScanBarCode: Button? = null

    val LAUNCH_TEXTSCAN_ACTIVITY = 1111
    val LAUNCH_BARCODE_ACTIVITY = 2222

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mScanText = findViewById(R.id.scanText)
        mScanBarCode = findViewById(R.id.scanBarcode)
    }

    override fun onResume() {
        super.onResume()

        mScanText?.setOnClickListener {
            startActivityForResult((Intent(applicationContext, OCRActivity::class.java)), LAUNCH_TEXTSCAN_ACTIVITY)
        }

        mScanBarCode?.setOnClickListener {
            startActivityForResult((Intent(applicationContext, BarcodeActivity::class.java)), LAUNCH_BARCODE_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_TEXTSCAN_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                val result = data?.getStringExtra("result")
                Toast.makeText(applicationContext, "TEXT SCAN: " + result, Toast.LENGTH_SHORT).show()
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(applicationContext, "TEXT FAILED", Toast.LENGTH_SHORT).show()//Write your code if there's no result
            }
        }
        if (requestCode == LAUNCH_BARCODE_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                val result = data?.getStringExtra("result")
                Toast.makeText(applicationContext, "BARCODE SCAN: " + result, Toast.LENGTH_SHORT).show()
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(applicationContext, "BARCODE FAILED", Toast.LENGTH_SHORT).show()//Write your code if there's no result
            }
        }
    } //onActivityResult

    override fun onPause() {
        super.onPause()

        mScanText?.setOnClickListener(null)
        mScanBarCode?.setOnClickListener(null)
    }

    override fun onDestroy() {
        super.onDestroy()

        mScanText = null
        mScanBarCode = null
    }
}