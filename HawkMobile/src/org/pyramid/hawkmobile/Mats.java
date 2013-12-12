package org.pyramid.hawkmobile;



public class Mats {

	static String[] arreglo = new String[10];
	private String nombre;
	private String un1;
	private String un2;
	private String un3;
	private String un4;
	private String un5;
	private String un6;
	private String un7;
	private String un8;
	private String un9;
	private String promedio;
	private String creditos;
	private String horaDia;
	private String Salon;
	private String Sem;

	public String getSem() {
		return Sem;
	}

	public void setSem(String sem) {
		Sem = sem;
	}

	public String getPromedio() {
		return promedio;
	}

	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	public String getSalon() {
		return Salon;
	}

	public String getHoraDia() {
		return horaDia;
	}

	public void setHoraDia(String horaDia) {
		this.horaDia = horaDia;
	}

	public void setSalon(String salon) {
		Salon = salon;
	}

	public String getUn3() {
		return un3;
	}

	public void setUn3(String un3) {
		this.un3 = un3;
	}

	public String getUn4() {
		return un4;
	}

	public void setUn4(String un4) {
		this.un4 = un4;
	}

	public String getUn5() {
		return un5;
	}

	public void setUn5(String un5) {
		this.un5 = un5;
	}

	public String getUn6() {
		return un6;
	}

	public void setUn6(String un6) {
		this.un6 = un6;
	}

	public String getUn7() {
		return un7;
	}

	public void setUn7(String un7) {
		this.un7 = un7;
	}

	public String getUn8() {
		return un8;
	}

	public void setUn8(String un8) {
		this.un8 = un8;
	}

	public String getUn9() {
		return un9;
	}

	public void setUn9(String un9) {
		this.un9 = un9;
	}

	public Mats(String nombre, String promedio) {
		this.nombre = nombre;
		this.promedio = promedio;

	}

	public Mats(String a, String b, String c, String d, String e, String f,
			String g, String h, String i, String j) {
		this.nombre = a;
		this.un1 = b;
		this.un2 = c;
		this.un3 = d;
		this.un4 = e;
		this.un5 = f;
		this.un6 = g;
		this.un7 = h;
		this.un8 = i;
		this.un9 = j;

	}

	public Mats(String nombre, String hora, String salon) {
		this.nombre = nombre;
		this.horaDia = hora;
		this.Salon = salon;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUn1() {
		return un1;
	}

	public void setUn1(String un1) {
		this.un1 = un1;
	}

	public String getUn2() {
		return un2;
	}

	public void setUn2(String un2) {
		this.un2 = un2;
	}

}
