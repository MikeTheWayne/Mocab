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
    private Camera camera;

    public CameraPreview(Context context, Camera camera) {
        super(context);

        this.camera = camera;

        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);

        this.camera.setDisplayOrientation(90);

    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d("Camera", "Setting camera preview Error! " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (surfaceHolder.getSurface() == null){
            // preview surface - not exist
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e){ }

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e){
            Log.d("Camera", "Error starting camera preview: " + e.getMessage());
        }
    }
}