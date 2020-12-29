package com.dmi.scannerhelper

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.vision.barcode.Barcode
import info.androidhive.barcode.BarcodeReader


class BaseScannerActivity: AppCompatActivity(), BarcodeReader.BarcodeReaderListener {
    val TAG: String = BaseScannerActivity::class.java.getSimpleName()

    var barcodeReader: BarcodeReader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bases_scanner_activity_main)

        // getting barcode instance
        barcodeReader = getSupportFragmentManager().findFragmentById(R.id.barcode_fragment) as BarcodeReader?
        /***
         * Providing beep sound. The sound file has to be placed in
         * `assets` folder
         */
        // barcodeReader.setBeepSoundFile("shutter.mp3");
        /**
         * Pausing / resuming barcode reader. This will be useful when you want to
         * do some foreground user interaction while leaving the barcode
         * reader in background
         */
        // barcodeReader.pauseScanning();
        // barcodeReader.resumeScanning();
    }

    override fun onScanned(barcode: Barcode) {
        Log.e(TAG, "onScanned: " + barcode.displayValue)
        barcodeReader!!.playBeep()
        runOnUiThread(Runnable {
            Toast.makeText(
                getApplicationContext(),
                "Barcode: " + barcode.displayValue,
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    override fun onScannedMultiple(barcodes: List<Barcode>) {
        Log.e(TAG, "onScannedMultiple: " + barcodes.size)
        var codes = ""
        for (barcode in barcodes) {
            codes += barcode.displayValue + ", "
        }
        val finalCodes = codes
        runOnUiThread(Runnable {
            Toast.makeText(
                getApplicationContext(),
                "Barcodes: $finalCodes",
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    override fun onBitmapScanned(sparseArray: SparseArray<Barcode?>?) {}

    override fun onScanError(errorMessage: String?) {}

    override fun onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG)
            .show()
        finish()
    }
}