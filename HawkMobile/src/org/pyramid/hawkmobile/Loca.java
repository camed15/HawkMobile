package org.pyramid.hawkmobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class Loca extends Activity implements OnTouchListener {
	public boolean dentro;
	double plon;
	double plat;
	double anchopant;
	double largopant;

	Maps v;
	Bitmap hal;
	float x, y;
	Bitmap mapa;
	Bitmap help;
	Boolean click = false;
	static float xf=0;
	static float yf=0;

	float xc =0;
	float yc = 0;
	LocationManager lm;
	LocationListener ll;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//--- NUEVO
		
		
		
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		ll = new myLocationListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);

		v = new Maps(this);
		// v.setOnTouchListener(this);
		hal = BitmapFactory.decodeResource(getResources(),
				R.drawable.user_loc);
		mapa = BitmapFactory.decodeResource(getResources(), R.drawable.mapa);
		help = BitmapFactory.decodeResource(getResources(), R.drawable.infomapa);

		x = y = 0;

		v.setOnTouchListener(this);
		setContentView(v);
		Toast.makeText(getApplicationContext(),
				"Tu eres el punto rojo",
				Toast.LENGTH_SHORT).show();

	}
	//---- METODO PARA ESCALAR
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth)
	{
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // create a matrix for the manipulation
	    Matrix matrix = new Matrix();
	    // resize the bit map
	    matrix.postScale(scaleWidth, scaleHeight);
	    // recreate the new Bitmap
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}

	@Override
	protected void onPause() {

		super.onPause();
		lm.removeUpdates(ll);
		v.pause();
	}

	@Override
	protected void onResume() {

		super.onResume();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
		v.resume();
	}

	public class Maps extends SurfaceView implements Runnable {

		Thread t;
		SurfaceHolder holder;
		boolean isItOk = false;

		public Maps(Context context) {
			super(context);

			holder = getHolder();
		}

		@Override
		public void run() {

			while (isItOk) {

				if (!holder.getSurface().isValid()) {
					continue;
				}

				Paint circulo = new Paint();
				circulo.setColor(Color.RED);
				circulo.setStyle(Paint.Style.FILL);
				Canvas c = holder.lockCanvas();
				xc = mapa.getWidth();
				yc = mapa.getHeight();
				//int tam = c.getWidth()/10;
				c.drawARGB(255, 150, 150, 10);
				c.drawBitmap(
				(Bitmap.createScaledBitmap(mapa, c.getWidth(),
								c.getHeight(), false)), 0, 0, null);
			//	c.drawBitmap(mapa, xf, yf,null);
				c.drawCircle(x, y, 4, circulo);
				
				if(x>c.getWidth() || y>c.getHeight()){
					
					Toast.makeText(getApplicationContext(),
							"Dispositivo fuera de la institución",
							Toast.LENGTH_SHORT).show();
					
				}
				
			//	c.drawBitmap(hal, x - (hal.getWidth() / 2),
				//		y - (hal.getHeight()), null);
				if(click){
					c.drawBitmap(
							(Bitmap.createScaledBitmap(help, c.getWidth(),
									c.getHeight(), false)), 0, 0, null);
				}
//				anchopant = c.getWidth();
//				largopant = c.getHeight();
				
				anchopant = mapa.getWidth();
				largopant = mapa.getHeight();
				holder.unlockCanvasAndPost(c);
			}
		}

		public void pause() {
			isItOk = false;
			while (true) {
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;

			}
			t = null;

		}

		public void resume() {
			isItOk = true;
			t = new Thread(this);
			t.start();

		}

	}

	private class myLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {

			if (location != null) {
				// plon = location.getLongitude();
				// plat = location.getLatitude();

				plon = (float) Math.abs(location.getLongitude());
				plat = (float) Math.abs(location.getLatitude());


				
			
				//double difpgx = ((105.430042 - plon) * anchopant) /0.003357;
				//double difpgy = ((28.213686 - plat) * largopant) / 0.001953;
				
				double difpgx = ((105.43014526 - plon) * anchopant) /0.00308984;
				double difpgy = ((28.21332283 - plat) * largopant)  /0.00223140;
				
				x = Math.abs((float) difpgx);
				y = Math.abs((float) difpgy);

				/*Toast.makeText(getApplicationContext(),
						"Longitud: " + plon + " Latitud: " + plat,
						Toast.LENGTH_SHORT).show();*/
				
				
			}

		}

		@Override
		public void onProviderDisabled(String provider) {

			Toast.makeText(getApplicationContext(), "EL SENSOR GPS ESTA APAGADO",
					Toast.LENGTH_LONG).show();

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent me) {

		// x = me.getX();
		// y = me.getY();

		try {
			Thread.sleep(50);
		} catch (Exception e) {
			e.printStackTrace();
		}

		switch (me.getAction()) {

//		case MotionEvent.ACTION_DOWN:
//		//	x = me.getX();
//		//	y = me.getY();
//			if(click){
//				click = false;
//			}else{
//			click = true;}
//			break;

		case MotionEvent.ACTION_UP:
			//x = me.getX();
			//y = me.getY();
			break;

		case MotionEvent.ACTION_MOVE:
//			xf =  me.getX() - (xc/2) ;
//			yf =  me.getY() - (yc/2);
//			
//			System.out.println("X = "+xf+" Y = "+yf);
			break;
			
		

		}

		return true;
	}

}