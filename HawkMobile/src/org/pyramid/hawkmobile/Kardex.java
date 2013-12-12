package org.pyramid.hawkmobile;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Kardex extends ListActivity {
	/*
	 * se declara la lista de objetos materias, el runnable y el adaptador de
	 * lista
	 */
	private ArrayList<Mats> m_parts;
	private Runnable viewParts;
	private SemAdapter m_adapter;
	public static String filename = "UserData";
	public static String filename2 = "Kardex";
	static Float prom = 0f;
    static Float proms;
	String btn;
	String sem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sem);
		m_parts = new ArrayList<Mats>();
		m_adapter = new SemAdapter(Kardex.this, R.layout.sem_row, m_parts);

		SharedPreferences sp = getSharedPreferences(filename,
				MODE_MULTI_PROCESS);
		Long semestre = sp.getLong("Semestre", 0);
	/*	Toast.makeText(getApplicationContext(), "Semestre" + semestre,
				Toast.LENGTH_LONG).show();*/

		String s = semestre.toString();
		this.sem = s;
		TextView txtv = (TextView) findViewById(R.id.semnum);
		txtv.setText(s);

		String server = sp.getString("Server", "");

		if (Integer.parseInt(server) != 0) {

			new ProgressTask().execute();

		} else {
			m_parts = new ArrayList<Mats>();
			Bd consulta = new Bd(Kardex.this);
			proms = 0f;
			consulta.open();
			consulta.getLastK(sem);
			Integer last = consulta.c;

			for (int x = 0; x < last; x++) {
				Mats y = consulta.getProm(x, sem);
				datosInt(y);
				proms = proms + Integer.parseInt(y.getPromedio());
			//	Toast.makeText(getApplicationContext(), y.getNombre(),
			//			Toast.LENGTH_SHORT).show();
			}
			prom(proms);
			consulta.close();
			TextView promtxt = (TextView) findViewById(R.id.promsem);
			promtxt.setText(prom.toString());
		}
		
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			m_adapter = new SemAdapter(Kardex.this, R.layout.sem_row, m_parts);

			// se muestra la lista
			setListAdapter(m_adapter);

		}
	};

	private class ProgressTask extends AsyncTask<String, Void, Boolean> {

		private ProgressDialog dialog = new ProgressDialog(Kardex.this);

		/** progress dialog to show user that the backup is processing. */
		/** application context. */

		protected void onPreExecute() {
			this.dialog.setTitle("Cargando");
			this.dialog.setMessage("Por favor espera");
			this.dialog.show();
		}

		protected Boolean doInBackground(final String... args) {
			try {
				proms = 0f;
				viewParts = new Runnable() {
					public void run() {
						SharedPreferences sp = getSharedPreferences(filename,
								MODE_MULTI_PROCESS);

						String nocontrol = sp.getString("nocontrol", "");

						Conexion c = new Conexion();
						int x = 0;
						do {
							Mats y = c.krdexsem(x, nocontrol, sem);

							if (y.getNombre() != null) {
								m_parts.add(y);
								
								guarda(y, sem);
								proms = proms + Integer.parseInt(y.getPromedio());
							}
							x++;
						} while (c.krdexsem(x, nocontrol, sem).getNombre() != (null));
						prom(proms);
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
			TextView promtxt = (TextView) findViewById(R.id.promsem);
			promtxt.setText(prom.toString());
			if (success) {
				// display UI

			}
		}
	}

	protected void onStart() {
		super.onStart();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kardex, menu);
		return true;
	}

	public void prom(Float m) {
		this.prom = m /m_parts.size();


	}

	public void guarda(Mats a, String sem) {
		boolean jalo = true;
		try {
			String nombre = a.getNombre();
			String prom = a.getPromedio();

			Bd entry = new Bd(Kardex.this);
			entry.open();
			entry.promEntry(nombre, prom, sem);
			entry.close();
		} catch (Exception e) {
			jalo = false;
		}
	}

	public void datosInt(final Mats y) {
		
	//	m_adapter = new SemAdapter(Kardex.this, R.layout.sem_row, m_parts);
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
