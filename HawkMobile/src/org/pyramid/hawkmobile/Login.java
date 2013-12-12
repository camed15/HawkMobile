/** 
 * author Pavlo Glez
 */
package org.pyramid.hawkmobile;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.IntentFilter.AuthorityEntry;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class Login extends Activity {

	public static String filename = "UserData";
	public SharedPreferences sp;
	int op;
	static Boolean isServerOn;
	String no_control;
	String password;
	ChecarCon cd;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int y = metrics.heightPixels;
		int x = metrics.widthPixels;
		
		View v = (View)findViewById(R.id.logoin);
		
		
		
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(x,y/3);
		v.setLayoutParams(p);
		Button login = (Button) findViewById(R.id.conecta);
		login.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				new ProgressTask().execute();

			}
		});

	}

	// Estos Metodos Guardan La info En SharedPrefs por default de
	// android------------
	private void SavePrefs(String Key, String value) {
		sp = getSharedPreferences(filename, 0);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString(Key, value);
		edit.commit();

	}

	private void sinInternet(String nocontrol, String nip) {
		sp = getSharedPreferences(filename, MODE_MULTI_PROCESS);
		String control = sp.getString("nocontrol", "");
		String n_p = sp.getString("nip", "");

		if (control.equals(nocontrol) && n_p.equals(nip)) {
			Intent intent = new Intent(Login.this, Home.class);
			startActivity(intent);

			Toast.makeText(getApplicationContext(), "Autenticacion Correcta",
					Toast.LENGTH_LONG).show();
			finish();

		} else {
			Toast.makeText(getApplicationContext(), "Credenciales Incorrectas",
					Toast.LENGTH_LONG).show();
		}


	}

	private void conInternet(String nocontrol, String nip) {

		Conexion conecta = new Conexion();
		conecta.control = nocontrol;
		conecta.pin = nip;

		Thread net = new Thread(conecta);
		net.start();
		// String respuesta = conecta.loginAction(nocontrol, nip);
		try {
			net.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String respuesta = conecta.respuesta;
		if (respuesta != null) {
			// abre la actividad home

			Intent intent = new Intent(Login.this, Home.class);
			startActivity(intent);
			Toast.makeText(getApplicationContext(), "Autenticacion Correcta",
					Toast.LENGTH_LONG).show();
			finish();

			// -----------------------GUARDAR DATOS ----------------//

			SavePrefs("nocontrol", nocontrol.toString());
			SavePrefs("nip", nip.toString());
			SavePrefs("nombre", respuesta);

			// --------------------------------------------//
		} else {

			Toast.makeText(getApplicationContext(), "Credenciales Incorrectas",
					Toast.LENGTH_LONG).show();
		}


	}

	
	private class ProgressTask extends AsyncTask<String, Void, Boolean> {

		private ProgressDialog dialog = new ProgressDialog(Login.this);

		/** progress dialog to show user that the backup is processing. */
		/** application context. */

		protected void onPreExecute() {
			this.dialog.setTitle("Conectando");
			this.dialog.setMessage("Por favor espera");
			this.dialog.show();
		}

		protected Boolean doInBackground(final String... args) {
			try {
				

				EditText nocontrol = (EditText) findViewById(R.id.nocontroltext);
				no_control = nocontrol.getText().toString();
				EditText nip = (EditText) findViewById(R.id.niptext);
				password = nip.getText().toString();
				cd = new ChecarCon(getApplicationContext());
				Boolean isInternetPresent = cd.isConnectingToInternet(); // true
																			// or
																			// false

				if (isInternetPresent) {

					Runnable r = new Runnable() {

						@Override
						public void run() {
							isServerOn = cd.ping();

						}
					};

					Thread t = new Thread(r);
					t.start();
					try {
						t.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (isServerOn) {
						SavePrefs("Server", "1");


					} else {
	
						SavePrefs("Server", "0");


					}

				} else {

					SavePrefs("Server", "0");


				}



				return true;
			} catch (Exception e) {
				Log.e("tag", "error", e);
				return false;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			
			SharedPreferences sp = getSharedPreferences(filename,
					MODE_MULTI_PROCESS);

			Integer serv = Integer.parseInt(sp.getString("Server", ""));
			
			switch (serv) {
			case 0:
				sinInternet(no_control, password);
				break;

			case 1:
				conInternet(no_control, password);
				break;
			}



			if (success) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}

			}
		}
	}
	
}