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

	public WordView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

		this.setWillNotDraw(false);
		// Setup transparent background
		this.setBackgroundColor(Color.TRANSPARENT);
		this.setZOrderOnTop(true);

		this.paint = new Paint();
		this.camX = 0;
		this.camY = 0;

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
				canvas.drawText(w.getEnglish(), w.getEngX(getWidth()) - camX, -w.getEngY(getHeight()) + camY, paint);

				if (w.isTrnSelect()) {
					paint.setARGB(255, 225, 192, 55);
				} else {
					paint.setARGB(255, 255, 255, 255);
				}
				canvas.drawText(w.getTranslation(), w.getTrnX(getWidth()) - camX, -w.getTrnY(getHeight()) + camY, paint);
			}
		}

	}

	public void setCam(float degX, float degY) {
		this.camX = 4 * getWidth() * (degX / 360f);
		this.camY = 2 * getHeight() * (degY / 180f);
	}

	public float getCamX() {
		return camX;
	}

	public float getCamY() {
		return camY;
	}
}
