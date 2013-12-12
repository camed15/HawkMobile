package org.pyramid.hawkmobile;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SemAdapter extends ArrayAdapter<Mats> {

	// arraylist donde se guardaran los objetos materias
	private ArrayList<Mats> objects;

	/*
	 * se sobreescribe el constructor del ArrayAdapter la unica variable que
	 * cambia es ArrayList<Mats> por que es la lista de lo que queremos mostrar
	 */
	public SemAdapter(Context context, int textViewResourceId,
			ArrayList<Mats> objects) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
	}

	/*
	 * se sobreescribe el metodo getview - este define como cada objeto de la
	 * lista se vera
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		// asignamos el view que estamos trabajando a una variable
		View v = convertView;

		// si el view es null, se infla(muestra)

		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.sem_row, null);
		}

		Mats m = objects.get(position);

		if (m != null) {

			// se asigna cada textview a una variable java

			TextView mat = (TextView) v.findViewById(R.id.nom_mat);
			TextView prom = (TextView) v.findViewById(R.id.prom_mat);

			// checa si cada textview es null, si no se le asigna texto.

			if (mat != null) {
				mat.setText(m.getNombre());
			}

			if (prom != null) {
				prom.setText(m.getPromedio());
			}

		}

		// el view se regresa ala actividad principal
		return v;

	}

}