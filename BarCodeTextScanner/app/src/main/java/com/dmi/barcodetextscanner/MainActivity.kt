package com.dmi.barcodetextscanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.dmi.barcodetextscanhelper.MainActivity

class MainActivity : AppCompatActivity() {

    private val RequestCameraPermission = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_launch)
    }

    override fun onResume() {
        super.onResume()

        if (ActivityCompat.checkSelfPermission( applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.CAMERA), RequestCameraPermission)
        } else {
            Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_LONG).show()

            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        when (requestCode) {
            RequestCameraPermission -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(applicationContext, "Permission not Granted", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}