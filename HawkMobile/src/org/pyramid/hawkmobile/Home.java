/**
 * Llama a cada nueva actividad y le da la bienvenida al nuevo usuario
 * 
 * @author Pavlo Glez
 */

package org.pyramid.hawkmobile;

import android.os.Bundle;


import android.app.Activity;

import android.app.ProgressDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;


public class Home extends Activity {
	
	 
	  //  TextView myTextView = (TextView)findViewById(R.id.myTextView);
	  //  myTextView.setTypeface(myTypeface);
	public static String filename = "UserData";
	ProgressDialog mProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_home);
		loadprefs();
		buttonFormat();
		Typeface titleFont = Typeface.createFromAsset(this.getAssets(),"Roboto.ttf");
		

		//tema(R.id.tituloH, titleFont);
		tema(R.id.noprueba, titleFont);

		tema(R.id.locatxt, titleFont);
		tema(R.id.locatxt2, titleFont);
		
		tema(R.id.nottxt, titleFont);
		tema(R.id.nottxt2, titleFont);
		
		tema(R.id.horatxt, titleFont);
		tema(R.id.horatxt2, titleFont);
		
		tema(R.id.kardextxt, titleFont);
		tema(R.id.kardextxt2, titleFont);
		
		tema(R.id.califtxt, titleFont);
		tema(R.id.califtxt2, titleFont);

	}

	

	
	private void buttonFormat() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int y = metrics.heightPixels;
		int x = metrics.widthPixels;
		
		View v = (View)findViewById(R.id.btnnoticias);
		View b = (View)findViewById(R.id.horaMat);
		View k = (View)findViewById(R.id.btnKardex);
		View c = (View)findViewById(R.id.califbtn);
		View l = (View)findViewById(R.id.btnloca);
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams((x/2)-10, (y/4)+20);
		RelativeLayout.LayoutParams p2 = new RelativeLayout.LayoutParams((x/2)-10, (y/4)+20);
		RelativeLayout.LayoutParams p3 = new RelativeLayout.LayoutParams(x, y/5+10);
		RelativeLayout.LayoutParams p4 = new RelativeLayout.LayoutParams(x, y/5+10);
		RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(x, y/5+15);
		p.addRule(RelativeLayout.BELOW, R.id.btnloca);
		p2.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.btnnoticias );
		p2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, R.id.btnnoticias);
		p3.addRule(RelativeLayout.BELOW, R.id.btnnoticias);
		p4.addRule(RelativeLayout.BELOW, R.id.btnKardex);
		p1.addRule(RelativeLayout.BELOW, R.id.noprueba);
		v.setLayoutParams(p);
		b.setLayoutParams(p2);
		k.setLayoutParams(p3);
		c.setLayoutParams(p4);
		l.setLayoutParams(p1);
		
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	// Metodo llamado cuando le dan click al boton noticias
	public void abrenoticias(View view) {
	
		// lo que se va a hacer cuando le den click
		Intent intent = new Intent(this, Lasnoticias.class);
		startActivity(intent);

	}
	
	private void tema(int v, Typeface titleFont){
		TextView txtvw = (TextView) findViewById(v);
		txtvw.setTypeface(titleFont,1);
		
	}
	
	public void cierra(View view){
		this.finish();
		
		
	}

	public void abrehorario(View view) {
		// lo que se va a hacer cuando le den click
		Intent intent = new Intent(this, Horario.class);
		startActivity(intent);
	}

	public void abrecalificaciones(View view) {
		// lo que se va a hacer cuando le den click

		Intent intent = new Intent(this, Calificaciones.class);

		startActivity(intent);

	}

	public void abreloca(View view) {
		// lo que se va a hacer cuando le den click
		Intent intent = new Intent(this, Loca.class);
		startActivity(intent);
	}

	public void abrekardex(View view) {
		// lo que se va a hacer cuando le den click
		Intent intent = new Intent(this, SemButtons.class);
		startActivity(intent);
	}

	public void loadprefs() {
		Typeface titleFont = Typeface.createFromAsset(this.getAssets(),"Roboto.ttf");
		//Typeface myTypeface = Typeface.createFromAsset(getAssets(), "D3Modulism.ttf");
		SharedPreferences sp = getSharedPreferences(filename,
				MODE_MULTI_PROCESS);
		// SharedPreferences sp =
		// PreferenceManager.getDefaultSharedPreferences(this);

		String name = sp.getString("nombre", "");
		TextView txtv;
		txtv = (TextView) findViewById(R.id.noprueba);
		//txtv.setTypeface(titleFont);
		txtv.setText("Bienvenid@: " + name);
		

	}

}
