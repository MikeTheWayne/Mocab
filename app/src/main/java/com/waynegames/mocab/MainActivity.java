package com.waynegames.mocab;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

	private Camera camera;
	private CameraPreview preview;

	private SensorManager sensorManager;
	private Sensor rotationSensor;

	private SensorClass sensorClass;
	private WordView wordView;

	private static Word[] words;
	private WordClass wordClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

		// Read External Storage permission check
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
		}

		// Camera permission check
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 11);
		}

		// Setup camera background
		if(checkCameraHardware(this)) {

			// Create an instance of Camera
			camera = getCameraInstance();

			// Create our Preview view and set it as the content of our activity.
			preview = new CameraPreview(this, camera);

			FrameLayout frameLayout = findViewById(R.id.camera_preview);
			frameLayout.addView(preview);

		}

		// Sensor setup
		this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		this.sensorClass = new SensorClass();
		this.sensorClass.start();

		// Word generation
		this.wordClass = new WordClass("English_Spanish_words.txt", "Spanish_word.txt", this);
		words = this.wordClass.getTenWords();

		// Word surfaceview
		this.wordView = findViewById(R.id.word_view);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float tX = event.getX();
		float tY = event.getY();

		if(event.getAction() == MotionEvent.ACTION_DOWN) {

			for(Word w : words) {

				if(Math.abs(tX - (w.getEngX(this.wordView.getWidth()) - wordView.getCamX())) < 250 && Math.abs(tY - (-w.getEngY(this.wordView.getHeight()) + wordView.getCamY())) < 75) {

					// Check if another word is selected, then check if it is the correct translation
					if(isWordSelected()) {
						checkWords(w, true);
						// Deselect all other words
						deselectAllWords();
					} else{
						// Deselect all other words
						deselectAllWords();
						// Select word
						w.setEngSelect(true);
					}

					break;

				} else if(Math.abs(tX - (w.getTrnX(this.wordView.getWidth()) - wordView.getCamX())) < 250 && Math.abs(tY - (-w.getTrnY(this.wordView.getHeight()) + wordView.getCamY())) < 75) {

					// Check if another word is selected, then check if it is the correct translation
					if(isWordSelected()) {
						checkWords(w, false);
						// Deselect all other words
						deselectAllWords();
					} else{
						// Deselect all other words
						deselectAllWords();
						// Select word
						w.setTrnSelect(true);
					}

					break;

				}

			}

		}

		return super.onTouchEvent(event);
	}

	/**
	 * Deselects all words
	 */
	private void deselectAllWords() {
		for(Word w : words) {
			w.setEngSelect(false);
			w.setTrnSelect(false);
		}
	}

	/**
	 * Checks if a word has already been selected
	 *
	 * @return
	 * 		True if a word is selected
	 */
	private boolean isWordSelected() {
		for(Word w : words) {
			if(w.isTrnSelect() || w.isEngSelect()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check the correct word pair has been selected by the user, then perform an action if it is correct or not
	 *
	 * @param compWord
	 * 		Last selected word, to be compared
	 */
	private void checkWords(Word compWord, boolean eng) {
		for(Word w : words) {
			if((w.isEngSelect() && !eng) || (w.isTrnSelect() && eng)) {
				if(compWord == w) {
					// Correct, hide
					w.setShow(false);
				} else{
					// Incorrect
					wordView.setIncorrectTime(1.0f);
				}
			}
		}
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

	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			// This device has a camera
			return true;
		} else {
			// No camera on this device
			return false;
		}
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
		Camera c = null;

		try {
			// Attempt to get a Camera instance
			c = android.hardware.Camera.open();
		} catch (Exception e){
			// Camera is not available (in use or does not exist)
		}

		// Return null if camera is unavailable
		return c;
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
			float yDeg = (float) (57.2598 * Math.acos(rotMatrix[10]));

			// Update camera position and redraw words
			wordView.setCam(xDeg, yDeg);
			wordView.invalidate();
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int i) { }
	}

	public static Word[] getWords() {
		return words;
	}
}