package com.waynegames.mocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

	private SensorManager sensorManager;
	private Sensor rotationSensor;

	private SensorClass sensorClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

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