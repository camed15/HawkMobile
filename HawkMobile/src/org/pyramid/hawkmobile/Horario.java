package org.pyramid.hawkmobile;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Horario extends ListActivity implements OnClickListener {

	// se declara la lista de objetos materias, el runnable y el adaptador de
	// lista
	private ArrayList<Mats> m_parts;
	private Runnable viewParts;
	private SemaAdapter m_adapter;
	public static Integer d = 0;

	Button lunes, martes, miercoles, jueves, viernes;
	ProgressDialog mProgress;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dia);
		mProgress = ProgressDialog.show(this, "Cargando",
				"Espera un momento...");
		lunes = (Button) findViewById(R.id.btnlunes);
		martes = (Button) findViewById(R.id.btnmartes);
		miercoles = (Button) findViewById(R.id.btnmiercoles);
		jueves = (Button) findViewById(R.id.btnjueves);
		viernes = (Button) findViewById(R.id.btnviernes);

		lunes.setOnClickListener(this);
		martes.setOnClickListener(this);
		miercoles.setOnClickListener(this);
		jueves.setOnClickListener(this);
		viernes.setOnClickListener(this);
		color(R.id.btnlunes);
		cargaDia("Lunes");

	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			m_adapter = new SemaAdapter(Horario.this, R.layout.activity_dia,
					m_parts);

			// se muestra la lista
			setListAdapter(m_adapter);

		}
	};

	public void cargaDia(final String cual) {

		//Toast.makeText(getApplicationContext(), cual, Toast.LENGTH_LONG).show();
		setListAdapter(m_adapter);

		SharedPreferences sp = getSharedPreferences("UserData",
				MODE_MULTI_PROCESS);
		String server = sp.getString("Server", "");

		if (Integer.parseInt(server) != 0) {
			m_parts = new ArrayList<Mats>();
			// instanciar la clase MyAdapter y se le asigna al adaptador de la
			// lista
			m_adapter = new SemaAdapter(this, R.layout.dia_row, m_parts);
			viewParts = new Runnable() {
				public void run() {
					SharedPreferences sp = getSharedPreferences("UserData",
							MODE_MULTI_PROCESS);
					String nocontrol = sp.getString("nocontrol", "");
					int x = 0;
					Conexion c = new Conexion();
					do {
						Mats y = c.horario(x, nocontrol, cual);
						if (y.getNombre() != null) {
							m_parts.add(y);
							guarda(y, cual);
						}
						x++;
					} while (c.horario(x, nocontrol, cual).getNombre() != (null));
					if (mProgress.isShowing()) {
						mProgress.dismiss();
					}

					handler.sendEmptyMessage(0);

				}
			};

		} else {
			m_parts = new ArrayList<Mats>();
			// instanciar la clase MyAdapter y se le asigna al adaptador de la
			// lista
			m_adapter = new SemaAdapter(this, R.layout.dia_row, m_parts);

			viewParts = new Runnable() {
				public void run() {

					Bd consulta = new Bd(Horario.this);

					consulta.open();
					consulta.getLastH(cual);
					int last = consulta.c;

					for (int x = 0; x < last; x++) {
						Mats y = consulta.getHor(x, cual);
						if (y.getNombre() != null) {
							m_parts.add(y);

						}
					}

					consulta.close();
					if (mProgress.isShowing()) {
						mProgress.dismiss();
					}

					handler.sendEmptyMessage(0);

				}
			};

		}

		// definiendo el nuevo hilo

		// llamamos al hilo -es enviado al handler de abajo
		Thread thread = new Thread(null, viewParts);

		thread.start();
		/*try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	@Override
	public void onClick(View v) {

		mProgress = ProgressDialog.show(this, "Cargando",
				"Espera un momento...");
		switch (v.getId()) {
		case R.id.btnlunes:

			color(R.id.btnlunes);
			cargaDia("Lunes");
			this.d = 1;
			break;
		case R.id.btnmartes:

			color(R.id.btnmartes);
			cargaDia("Martes");
			this.d = 2;
			break;
		case R.id.btnmiercoles:

			color(R.id.btnmiercoles);
			cargaDia("Miercoles");
			this.d = 3;
			break;
		case R.id.btnjueves:

			color(R.id.btnjueves);
			this.d = 4;
			cargaDia("Jueves");
			break;
		case R.id.btnviernes:

			color(R.id.btnviernes);
			this.d = 5;
			cargaDia("Viernes");
			break;
		default:
			break;
		}

	}

	public void color(int r) {
		Button diap = (Button) findViewById(r);

		lunes.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.diaselect));
		martes.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.diaselect));
		miercoles.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.diaselect));
		jueves.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.diaselect));
		viernes.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.diaselect));

		diap.setBackgroundDrawable(getResources()
				.getDrawable(R.drawable.pushed));

	}

	public void guarda(Mats a, String cual) {

		boolean jalo = true;
		try {
			String nombre = a.getNombre();
			String dia = cual;
			String salon = a.getSalon();
			String hora = a.getHoraDia();

			Bd entry = new Bd(Horario.this);
			entry.open();
			entry.horaEntry(nombre, salon, hora, dia);
			entry.close();
		} catch (Exception e) {
			jalo = false;
		}

	}
	
	public void cierra(View view){
		this.finish();
		
		
	}

}