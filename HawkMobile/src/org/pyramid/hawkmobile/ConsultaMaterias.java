package org.pyramid.hawkmobile;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class ConsultaMaterias {

	public String[] consult(int posi) {

		String no_control = "08540323";
		String[] arreglo = new String[10];

		String NAMESPACE = "http://pyramid.org";
		String URL = "http://192.168.1.90:8080/LoginHwkM/services/boleta?wsdl";
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

			for (int y = 0; y < 10; y++) {
				arreglo[y] = response.getProperty(y).toString();

			}

			return arreglo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arreglo;

	}

}
