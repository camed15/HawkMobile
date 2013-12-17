/**
 * Esta clase establece las conexiones con los servicios web
 * 
 * @author Pablo Glez
 */

package org.pyramid.hawkmobile;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Conexion implements Runnable {
	String respuesta;
	private static String NAMESPACE = "http://pyramid.org";
	private static String URL = "http://SOCKET/LoginHwkM/services/Login?wsdl";
	private static String SOAP_ACTION = "http://pyramid.org/authentication";
	private static String METHOD_NAME = "authentication";
	static String[] arre = new String[10];
	public static String filename = "UserData";
	String state;
	String control;
	String pin;

	public void loginAction(String no_control, String np) {

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// -------------------------- LOGIN INTERNET ------------------//
		// Pasa el valor de la variable numcontrol al servicio web
		PropertyInfo unameProp = new PropertyInfo();
		unameProp.setName("nocontrol");// Define el nombre de la variable en el
										// metodo del servicio web
		unameProp.setValue(no_control);// establece el valor de la variable
										// nocontrol
		unameProp.setType(String.class);// Define el tipo de variable
		request.addProperty(unameProp);// Pasa las propiedades ala variable

		// Pasa el valor de la variable NIP al servicio web
		PropertyInfo passwordProp = new PropertyInfo();
		passwordProp.setName("nip");
		passwordProp.setValue(np);
		passwordProp.setType(String.class);
		request.addProperty(passwordProp);

		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		final HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

			respuesta = response.toString();

		} catch (Exception e) {

		}

	}

	@Override
	public void run() {
		loginAction(control, pin);

	}

	public Mats boleta(int pos, String nocontrol) {
		Mats m;

		String s[] = consult(pos, nocontrol);

		m = new Mats(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9]);

		return m;
	}

	public String[] semestres(String nocontrol) {

		String URL = "http://SOCKET/LoginHwkM/services/Semestres?wsdl";
		String SOAP_ACTION = "http://pyramid.org/Kardex";
		String METHOD_NAME = "Kardex";

		String sem = "0";
		String arreglo[];
		try {

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			PropertyInfo unameProp = new PropertyInfo();
			unameProp.setName("nocontrol");// Define el nombre de la variable en
											// el metodo del servicio web
			unameProp.setValue(nocontrol);// establece el valor de la variable
											// nocontrol
			unameProp.setType(String.class);// Define el tipo de variable
			request.addProperty(unameProp);// Pasa las propiedades ala variable

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.bodyIn;

			arreglo = new String[response.getPropertyCount()];
			for (int y = 0; y < response.getPropertyCount(); y++) {
				arreglo[y] = response.getProperty(y).toString();

			}
			// sem = response.getProperty(pos).toString();

			return arreglo;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Mats horario(int pos, String nocontrol, String dia) {

		Mats m;
		String s[] = consultH(pos, nocontrol, dia);
		m = new Mats(s[0], s[1], s[2]);
		return m;

	}

	public Mats krdexsem(int pos, String nocontrol, String sem) {

		Mats m;
		String s[] = consultK(pos, nocontrol, sem);
		m = new Mats(s[0], s[1]);
		return m;

	}

	public String[] consultK(int pos, String nocontrol, String sem) {

		String[] arreglo = new String[10];

		String NAMESPACE = "http://pyramid.org";
		String URL = "http://SOCKET/LoginHwkM/services/SemestreInd?wsdl";
		String SOAP_ACTION = "http://pyramid.org/Kardex";
		String METHOD_NAME = "Kardex";

		try {

			// for(int x=0;x<3;x++){
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			PropertyInfo unameProp = new PropertyInfo();
			unameProp.setName("nocontrol");// Define el nombre de la variable en
											// el metodo del servicio web
			unameProp.setValue(nocontrol);// establece el valor de la variable
											// nocontrol
			unameProp.setType(String.class);// Define el tipo de variable
			request.addProperty(unameProp);// Pasa las propiedades ala variable

			PropertyInfo position = new PropertyInfo();
			position.setName("pos");
			position.setValue(pos);
			position.setType(Integer.class);
			request.addProperty(position);

			PropertyInfo sems = new PropertyInfo();
			sems.setName("sems");
			sems.setValue(sem);
			sems.setType(String.class);
			request.addProperty(sems);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.bodyIn;

			for (int y = 0; y < 11; y++) {
				arreglo[y] = response.getProperty(y).toString();

			}

			return arreglo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arreglo;

	}

	public String[] consultH(int posi, String no_control, String dia) {

		String NAMESPACE = "http://pyramid.org";
		String URL = "http://SOCKET/LoginHwkM/services/Horario?wsdl";
		String SOAP_ACTION = "http://pyramid.org/horas";
		String METHOD_NAME = "horas";

		String[] arreglo = new String[3];

		try {

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			PropertyInfo unameProp = new PropertyInfo();
			unameProp.setName("nocontrol");// Define el nombre de la variable en
											// el metodo del servicio web
			unameProp.setValue(no_control);// establece el valor de la variable
											// nocontrol
			unameProp.setType(String.class);// Define el tipo de variable
			request.addProperty(unameProp);// Pasa las propiedades ala variable

			PropertyInfo position = new PropertyInfo();
			position.setName("pos");
			position.setValue(posi);
			position.setType(Integer.class);
			request.addProperty(position);

			PropertyInfo day = new PropertyInfo();
			day.setName("dia");
			day.setValue(dia);
			day.setType(Integer.class);
			request.addProperty(day);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.bodyIn;

			for (int y = 0; y < 3; y++) {
				arreglo[y] = response.getProperty(y).toString();

			}

			return arreglo;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return arreglo;

	}

	public String[] consult(int posi, String no_control) {

		String[] arreglo = new String[10];

		String NAMESPACE = "http://pyramid.org";
		String URL = "http://SOCKET/LoginHwkM/services/boleta?wsdl";
		String SOAP_ACTION = "http://pyramid.org/consulta";
		String METHOD_NAME = "consulta";

		try {

			// for(int x=0;x<3;x++){
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			PropertyInfo unameProp = new PropertyInfo();
			unameProp.setName("nocontrol");// Define el nombre de la variable en
											// el metodo del servicio web
			unameProp.setValue(no_control);// establece el valor de la variable
											// nocontrol
			unameProp.setType(String.class);// Define el tipo de variable
			request.addProperty(unameProp);// Pasa las propiedades ala variable

			PropertyInfo position = new PropertyInfo();
			position.setName("pos");
			position.setValue(posi);
			position.setType(Integer.class);
			request.addProperty(position);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.bodyIn;

			for (int y = 0; y < 11; y++) {
				arreglo[y] = response.getProperty(y).toString();

			}

			return arreglo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arreglo;

	}

}
