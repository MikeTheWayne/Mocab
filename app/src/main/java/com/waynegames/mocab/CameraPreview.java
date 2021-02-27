package com.waynegames.mocab;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Camera Camera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        Camera = camera;

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            Camera.setPreviewDisplay(holder);
            Camera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Setting camera preview Error! " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (surfaceHolder.getSurface() == null){
            // preview surface - not exist
            return;
        }

        try {
            Camera.stopPreview();
        } catch (Exception e){
        }

        try {
            Camera.setPreviewDisplay(surfaceHolder);
            Camera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}