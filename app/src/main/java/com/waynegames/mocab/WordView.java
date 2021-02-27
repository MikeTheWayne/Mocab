package com.waynegames.mocab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class WordView extends SurfaceView {

	private Paint paint;

	public WordView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

		this.setWillNotDraw(false);
		this.setBackgroundColor(Color.TRANSPARENT);
		this.setZOrderOnTop(true);

		this.paint = new Paint();

	}

	public void onDraw(Canvas canvas) {

		paint.setTextSize(100);
		paint.setARGB(255, 255, 255, 255);
		canvas.drawText("Hola", 100, 100, paint);

	}

}
