package com.waynegames.mocab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class WordView extends SurfaceView {

	private Paint paint;

	private float camX;
	private float camY;

	private float incorrectTime;

	public WordView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

		this.setWillNotDraw(false);
		// Setup transparent background
		this.setBackgroundColor(Color.TRANSPARENT);
		this.setZOrderOnTop(true);

		this.paint = new Paint();
		this.camX = 0;
		this.camY = 0;
		this.incorrectTime = 0;

	}

	public void onDraw(Canvas canvas) {

		// Set word drawing style
		paint.setTextSize(100);
		paint.setARGB(255, 255, 255, 255);

		// Draw words
		for(Word w : MainActivity.getWords()) {

			if(w.isShow()) {

				if (w.isEngSelect()) {
					paint.setARGB(255, 225, 192, 55);
				} else {
					paint.setARGB(255, 255, 255, 255);
				}
				canvas.drawText(w.getEnglish(), w.getEngX(getWidth()) - camX - paint.measureText(w.getEnglish()) / 2, -w.getEngY(getHeight()) + camY - 200, paint);

				if (w.isTrnSelect()) {
					paint.setARGB(255, 225, 192, 55);
				} else {
					paint.setARGB(255, 255, 255, 255);
				}
				canvas.drawText(w.getTranslation(), w.getTrnX(getWidth()) - camX - paint.measureText(w.getEnglish()) / 2, -w.getTrnY(getHeight()) + camY - 200, paint);
			}
		}

		// Draw incorrect overlay
		if(incorrectTime > 0) {
			paint.setTextSize(150);
			paint.setARGB(255, (int) (incorrectTime * 255), 0, 0);
			canvas.drawText("INCORRECT", 200, 200, paint);

			incorrectTime -= 0.01f;
		}

	}

	public void setCam(float degX, float degY) {
		this.camX = 6 * getWidth() * (degX / 360f);
		this.camY = 2 * getHeight() * (degY / 180f);
	}

	public float getCamX() {
		return camX;
	}

	public float getCamY() {
		return camY;
	}

	public void setIncorrectTime(float incorrectTime) {
		this.incorrectTime = incorrectTime;
	}
}
