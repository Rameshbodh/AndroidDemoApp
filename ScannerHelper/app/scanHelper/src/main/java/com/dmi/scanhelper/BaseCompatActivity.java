package com.dmi.scanhelper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseCompatActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback {

    public static final String TAG = BaseCompatActivity.class.getSimpleName();
    protected boolean isFlashAvailable = false;
    protected SurfaceView surfaceView;
    protected CameraSource cameraSource;
    protected final int REQUEST_CAMERA_PERMISSION = 1001;
    protected TextRecognizer textRecognizer;
    protected Map<String, Integer> mAggregateResultsMap;

    protected CircularImageView mCircularImageView = null;
    private boolean isFlashON = false;
    private Camera mCamera;
    private Camera.Parameters parameters;

    private CameraManager mCameraManager;
    private String mCameraId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_scanner);

        mCircularImageView = findViewById(R.id.flashControl);
        surfaceView = findViewById(R.id.surface_view);
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);

        isFlashON = false;


        mAggregateResultsMap = new HashMap<>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCircularImageView.setEnabled(isFlashAvailable);
        if (isFlashAvailable) {
            mCircularImageView.setOnClickListener(this);
        }

        isFlashON = false;
        mCircularImageView.setImageDrawable(getDrawable(R.drawable.ic_baseline_flash_off_24));
        getSupportActionBar().hide();
        initialiseDetectorsAndSources();
        mAggregateResultsMap.clear();
    }


    @Override
    protected void onPause() {
        super.onPause();

        isFlashON = false;
        switchFlashLight(false);
        mCircularImageView.setImageDrawable(getDrawable(R.drawable.ic_baseline_flash_off_24));

        getSupportActionBar().hide();
        cameraSource.release();
        mAggregateResultsMap.clear();
    }

    protected void initialiseDetectorsAndSources() {
        surfaceView.getHolder().addCallback(this);
    }

    public void switchFlashLight(boolean status) {
        //            mCameraManager.setTorchMode(mCameraId, status);
        if (status) {
            turnFlashlightOn();
        } else {
            turnFlashlightOff();
        }
        if (!status) {
            mCircularImageView.setImageDrawable(getDrawable(R.drawable.ic_baseline_flash_off_24));
            isFlashON = false;
        } else {
            mCircularImageView.setImageDrawable(getDrawable(R.drawable.ic_baseline_flash_on_24));
            isFlashON = true;
        }
    }

    private void turnFlashlightOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                String cameraId = null;
                if (mCameraManager != null) {
                    cameraId = mCameraManager.getCameraIdList()[0];
                    mCameraManager.setTorchMode(cameraId, true);
                }
            } catch (CameraAccessException e) {
                Log.e(TAG, e.toString());
            }
        } else {
            mCamera = Camera.open();
            parameters = mCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        }
    }

    private void turnFlashlightOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                String cameraId;
                mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                if (mCameraManager != null) {
                    cameraId = mCameraManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
                    mCameraManager.setTorchMode(cameraId, false);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            mCamera = Camera.open();
            parameters = mCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            mCamera.stopPreview();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switchFlashLight(!isFlashON);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                cameraSource.start(surfaceView.getHolder());
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        cameraSource.stop();

        surfaceView.getHolder().removeCallback(this);
        textRecognizer.setProcessor(null);
        textRecognizer.release();
        mAggregateResultsMap = null;
        surfaceView = null;
        cameraSource = null;
        textRecognizer = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void pileUpReferenceTexts(String newDetection) {
        if (newDetection != null && !newDetection.isEmpty()) {
            if (mAggregateResultsMap == null || mAggregateResultsMap.isEmpty()) {
                mAggregateResultsMap = new HashMap<>();
            }

            if (mAggregateResultsMap.containsKey(newDetection)) {
                mAggregateResultsMap.put(newDetection, mAggregateResultsMap.get(newDetection) + 1);

                if (mAggregateResultsMap.get(newDetection) > 20) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", (newDetection));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                    mAggregateResultsMap.clear();
                }
            } else {
                mAggregateResultsMap.put(newDetection, 1);
            }
        }
    }
}
