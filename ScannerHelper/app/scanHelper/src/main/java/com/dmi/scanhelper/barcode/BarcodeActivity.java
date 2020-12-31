package com.dmi.scanhelper.barcode;

import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import com.dmi.scanhelper.AggregateProcessing;
import com.dmi.scanhelper.BaseCompatActivity;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class BarcodeActivity extends BaseCompatActivity implements Detector.Processor<Barcode> {

    private BarcodeDetector barcodeDetector;

    @Override
    protected void initialiseDetectorsAndSources() {
        super.initialiseDetectorsAndSources();

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(1920, 1080).setRequestedFps(40.0f).setAutoFocusEnabled(true).build();
        barcodeDetector.setProcessor(this);
    }

    @Override
    public void release() {
    }

    @Override
    public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
        surfaceView.post(() -> pileUpReferenceTexts(AggregateProcessing.aggregateResultsBarCode(detections)));
    }
}
