/**
 * Activity de Calificaciones parciales.
 * 
 * @author Pablo Gonzalez
 */

package org.pyramid.hawkmobile;

import java.util.ArrayList;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Calificaciones extends ListActivity {

	// se declara la lista de objetos materias, el runnable y el adaptador de
	// lista
	private ArrayList<Mats> m_parts = new ArrayList<Mats>();
	private Runnable viewParts;
	private MyAdapter m_adapter;
	public static String filename = "UserData";

	// String nocontrol = "08540323";

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.califs);
		m_adapter = new MyAdapter(this, R.layout.list_row, m_parts);

		setListAdapter(m_adapter);
		SharedPreferences sp = getSharedPreferences(filename,
				MODE_MULTI_PROCESS);
		String server = sp.getString("Server", "");
		if (Integer.parseInt(server) != 0) {
			// instanciar la clase MyAdapter y se le asigna al adaptador de la
			// lista

			new ProgressTask().execute();
			// definiendo el nuevo hilo

		} else {

			Bd consulta = new Bd(Calificaciones.this);

			consulta.open();
			consulta.getLast();
			int last = consulta.c;

			for (int x = 0; x < last; x++) {
				Mats y = consulta.getBol(x);
				datosInt(y);
			}

			consulta.close();

		}

	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			m_adapter = new MyAdapter(Calificaciones.this, R.layout.list_row,
					m_parts);

			// se muestra la lista
			setListAdapter(m_adapter);

		}
	};

	private class ProgressTask extends AsyncTask<String, Void, Boolean> {

		private ProgressDialog dialog = new ProgressDialog(Calificaciones.this);

		/** progress dialog to show user that the backup is processing. */
		/** application context. */

		protected void onPreExecute() {
			this.dialog.setTitle("Cargando");
			this.dialog.setMessage("Por favor espera");
			this.dialog.show();
		}

		protected Boolean doInBackground(final String... args) {
			try {

				viewParts = new Runnable() {
					public void run() {

						SharedPreferences sp = getSharedPreferences(filename,
								MODE_MULTI_PROCESS);

						String nocontrol = sp.getString("nocontrol", "");

						Conexion c = new Conexion();
						int x = 0;
						do {
							Mats y = c.boleta(x, nocontrol);
							if (y.getNombre() != null) {
								m_parts.add(y);
								guarda(y);
							}
							x++;
						} while (c.boleta(x, nocontrol).getNombre() != (null));
						handler.sendEmptyMessage(0);

					}
				};

				// llamamos al hilo -es enviado al handler de abajo
				Thread thread = new Thread(null, viewParts);

				thread.start();
				try {
					thread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return true;
			} catch (Exception e) {
				Log.e("tag", "error", e);
				return false;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success) {

			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			if (success) {
				// display UI

			}
		}
	}

	public void guarda(Mats a) {
		boolean jalo = true;
		try {
			String nombre = a.getNombre();
			String un1 = a.getUn1();
			String un2 = a.getUn2();
			String un3 = a.getUn3();
			String un4 = a.getUn4();
			String un5 = a.getUn5();
			String un6 = a.getUn6();
			String un7 = a.getUn7();
			String un8 = a.getUn8();
			String un9 = a.getUn9();

			Bd entry = new Bd(Calificaciones.this);
			entry.open();
			entry.califsEntry(nombre, un1, un2, un3, un4, un5, un6, un7, un8,
					un9);
			entry.close();
		} catch (Exception e) {
			jalo = false;
		}
	}

	public void datosInt(final Mats y) {

		viewParts = new Runnable() {
			public void run() {

				if (y.getNombre() != null) {
					m_parts.add(y);

				}

				handler.sendEmptyMessage(0);

			}
		};

		Thread thread = new Thread(null, viewParts);

		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void cierra(View view){
		this.finish();
		
		
	}

}
