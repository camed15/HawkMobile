package org.pyramid.hawkmobile;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Mats> {

	// arraylist donde se guardaran los objetos materias
	private ArrayList<Mats> objects;

	/*
	 * se sobreescribe el constructor del ArrayAdapter la unica variable que
	 * cambia es ArrayList<Mats> por que es la lista de lo que queremos mostrar
	 */
	public MyAdapter(Context context, int textViewResourceId,
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
			v = inflater.inflate(R.layout.list_row, null);
		}

		/*
		 * Recall that the variable position is sent in as an argument to this
		 * method. The variable simply refers to the position of the current
		 * object in the list. (The ArrayAdapter iterates through the list we
		 * sent it)
		 * 
		 * i es una materia (objeto Mats)
		 */
		Mats m = objects.get(position);

		if (m != null) {

			// se asigna cada textview a una variable java

			TextView nom = (TextView) v.findViewById(R.id.materia);

			TextView un1 = (TextView) v.findViewById(R.id.un1);

			TextView un2 = (TextView) v.findViewById(R.id.un2);

			TextView un3 = (TextView) v.findViewById(R.id.un3);

			TextView un4 = (TextView) v.findViewById(R.id.un4);

			TextView un5 = (TextView) v.findViewById(R.id.un5);

			TextView un6 = (TextView) v.findViewById(R.id.un6);

			TextView un7 = (TextView) v.findViewById(R.id.un7);

			TextView un8 = (TextView) v.findViewById(R.id.un8);

			TextView un9 = (TextView) v.findViewById(R.id.un9);

			// checa si cada textview es null, si no se le asigna texto.

			if (nom != null) {
				nom.setText(m.getNombre());
			}

			if (un1 != null) {
				un1.setText(m.getUn1());
			}

			if (un2 != null) {
				un2.setText(m.getUn2());
			}

			if (un3 != null) {
				un3.setText(m.getUn3());
			}

			if (un4 != null) {
				un4.setText(m.getUn4());
			}

			if (un5 != null) {
				un5.setText(m.getUn5());
			}

			if (un6 != null) {
				un6.setText(m.getUn6());
			}

			if (un7 != null) {
				un7.setText(m.getUn7());
			}

			if (un8 != null) {
				un8.setText(m.getUn8());
			}
			if (un9 != null) {
				un9.setText(m.getUn9());
			}
		}

		// el view se regresa ala actividad principal
		return v;

	}

}
