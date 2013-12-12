package org.pyramid.hawkmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public class Mapa extends View {
	
	Bitmap user;
	int x, y; 
	public Mapa(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		user = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		x = 0;
		y = 0;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Bitmap m = BitmapFactory.decodeResource(getResources(), R.drawable.mapa);
		Rect map = new Rect();
		map.set(0, 0, canvas.getWidth(), canvas.getHeight());
		
		Paint fondo = new Paint();
		fondo.setColor(Color.BLACK);
		fondo.setStyle(Paint.Style.FILL);
	
		canvas.drawBitmap(m, 0, 0, null);
		
		if(x< canvas.getWidth()){
		x += 10;}else{
			x =0;
		}
		if(y < canvas.getHeight()){
		y += 10;}else{
			y =0;
		}
		canvas.drawBitmap(user, x, y, new Paint());
		invalidate();
	}

}
