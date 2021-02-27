package com.waynegames.mocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {


	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}
	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
		Camera c = null;
		try {
			c = android.hardware.Camera.open(); // attempt to get a Camera instance
		}
		catch (Exception e){
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}

	private Camera mCamera;
	private CameraPreview mPreview;


	private SensorManager sensorManager;
	private Sensor rotationSensor;

	private SensorClass sensorClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

		Boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
				== PackageManager.PERMISSION_GRANTED);
		if (!hasPermission) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 11);
		}

		checkCameraHardware(this);
		// Create an instance of Camera
		mCamera = getCameraInstance();

		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);

		this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		this.sensorClass = new SensorClass();
		this.sensorClass.start();

	}

	@Override
	protected void onResume() {
		super.onResume();

		this.sensorClass.start();
	}

	@Override
	protected void onPause() {
		super.onPause();

		this.sensorClass.stop();
	}

	class SensorClass implements SensorEventListener {

		private final float[] rotMatrix;

		public SensorClass() {
			rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
			rotMatrix = new float[16];
		}

		public void start() {
			// Register sensor listener, sample every 10ms
			sensorManager.registerListener(this, rotationSensor, 10000);
		}

		public void stop() {
			sensorManager.unregisterListener(this);
		}

		@Override
		public void onSensorChanged(SensorEvent sensorEvent) {

			// Read sensor values
			SensorManager.getRotationMatrixFromVector(rotMatrix, sensorEvent.values);

			// Convert sensor values to x(0 - 360) y(0 - 180) degree values
			float xDeg = (float) (57.2598 * Math.acos(rotMatrix[0]) * ((rotMatrix[2] < 0) ? 1 : -1)) + 180;
			float yDeg = (float) (57.2598 * Math.acos(rotMatrix[5]));

			System.out.println(xDeg + " " + yDeg);

		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int i) { }
	}
}