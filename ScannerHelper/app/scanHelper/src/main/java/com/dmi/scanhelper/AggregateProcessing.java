package com.dmi.scanhelper;

import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.text.TextBlock;

public class AggregateProcessing {

    public static String aggregateResultsOCR(Detector.Detections<TextBlock> detections) {
        final SparseArray<TextBlock> items = detections.getDetectedItems();

        if (items != null && items.size() != 0) {
            if (items.size() > 0) {
                if (items.size() == 1) {
                    StringBuilder stringBuilder = new StringBuilder();
                    TextBlock item = items.valueAt(0);
                    stringBuilder.append(item.getValue());
                    Log.e("Searched Word", stringBuilder.toString());
                    return (stringBuilder.toString());
                } else if (items.size() == 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    TextBlock item = items.valueAt(1);
                    stringBuilder.append(item.getValue());
                    Log.e("Searched Word", stringBuilder.toString());
                    return (stringBuilder.toString());
                } else if (items.size() > 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    TextBlock item = items.valueAt(items.size() / 2);
                    stringBuilder.append(item.getValue());
                    Log.e("Searched Word", stringBuilder.toString());
                    return (stringBuilder.toString());
                } else if (items.size() > 5) {
                    StringBuilder stringBuilder = new StringBuilder();
                    TextBlock item = items.valueAt((items.size() * 4) / 5);
                    stringBuilder.append(item.getValue());
                    Log.e("Searched Word", stringBuilder.toString());
                    return (stringBuilder.toString());
                }
            }
        }
        return null;
    }


    public static String aggregateResultsBarCode(Detector.Detections<Barcode> detections) {
        final SparseArray<Barcode> items = detections.getDetectedItems();

        if (items != null && items.size() != 0) {
            if (items.size() > 0) {
                if (items.size() == 1) {
                    StringBuilder stringBuilder = new StringBuilder();
                    Barcode item = items.valueAt(0);
                    stringBuilder.append(item.displayValue);
                    Log.e("Scanned Barcode", stringBuilder.toString());
                    return (stringBuilder.toString());
                } else if (items.size() == 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    Barcode item = items.valueAt(1);
                    stringBuilder.append(item.displayValue);
                    Log.e("Scanned Barcode", stringBuilder.toString());
                    return (stringBuilder.toString());
                } else if (items.size() > 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    Barcode item = items.valueAt(items.size() / 2);
                    stringBuilder.append(item.displayValue);
                    Log.e("Scanned Barcode", stringBuilder.toString());
                    return (stringBuilder.toString());
                }
            }
        }
        return null;
    }
}