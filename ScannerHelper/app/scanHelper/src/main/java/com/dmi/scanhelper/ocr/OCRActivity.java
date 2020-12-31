package com.dmi.scanhelper.ocr;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dmi.scanhelper.AggregateProcessing;
import com.dmi.scanhelper.BaseCompatActivity;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.HashMap;

public class OCRActivity extends BaseCompatActivity implements Detector.Processor<TextBlock> {

    @Override
    protected void initialiseDetectorsAndSources() {
        super.initialiseDetectorsAndSources();

        textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detected dependence are not found ");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer).setFacing(CameraSource.CAMERA_FACING_BACK).setRequestedPreviewSize(1280, 1024).setRequestedFps(40.0f).setAutoFocusEnabled(true).build();
            textRecognizer.setProcessor(this);
        }
    }

    @Override
    public void release() {
    }

    @Override
    public void receiveDetections(@NonNull Detector.Detections<TextBlock> detections) {
        surfaceView.post(() -> pileUpReferenceTexts(AggregateProcessing.aggregateResultsOCR(detections)));
    }
}