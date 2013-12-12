package org.pyramid.hawkmobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * @author Pavlo
 */
public class SemButtons extends Activity implements OnClickListener {

	public static String filename = "UserData";
	private Runnable cargaSems;
	static String s[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sem_buttons);

		SharedPreferences sp = getSharedPreferences(filename,
				MODE_MULTI_PROCESS);
		String server = sp.getString("Server", "");

		if (Integer.parseInt(server) != 0) {

			cargaSems = new Runnable() {
				public void run() {
					SharedPreferences sp = getSharedPreferences(filename,
							MODE_MULTI_PROCESS);
					String nocontrol = sp.getString("nocontrol", "");

					Conexion c = new Conexion();
					s = c.semestres(nocontrol);

				}
			};

			// llamamos al hilo -es enviado al handler de abajo
			Thread thread = new Thread(null, cargaSems);

			thread.start();

			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LinearLayout layout = (LinearLayout) findViewById(R.id.sembtns);
			for (int i = 0; i < s.length; i++) {
				Button btnTag = new Button(SemButtons.this);
				btnTag.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				btnTag.setText("Semestre " + s[i]);
				btnTag.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.diaselect));
				int num = Integer.parseInt(s[i]);
				btnTag.setId(num);
				layout.addView(btnTag);
				btnTag.setOnClickListener(SemButtons.this);

			}

		} else {
			Bd consulta = new Bd(SemButtons.this);

			consulta.open();
			// GETLAST

			String[] b = consulta.getSem();
			
			LinearLayout layout = (LinearLayout) findViewById(R.id.sembtns);
			for (int i = 0; i < b.length; i++) {
				Button btnTag = new Button(SemButtons.this);
				btnTag.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				btnTag.setText("Semestre " + b[i]);
				btnTag.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.diaselect));
				int num = Integer.parseInt(b[i]);
				btnTag.setId(num);
				layout.addView(btnTag);
				btnTag.setOnClickListener(SemButtons.this);

			}

			consulta.close();

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		SharedPreferences sp = getSharedPreferences(filename,
				MODE_MULTI_PROCESS);
		SharedPreferences.Editor edit = sp.edit();
		edit.putLong("Semestre", v.getId());
		edit.commit();
		Intent intent = new Intent(SemButtons.this, Kardex.class);
		startActivity(intent);

	}
	
	public void cierra(View view){
		this.finish();
		
		
	}

}
