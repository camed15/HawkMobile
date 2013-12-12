package org.pyramid.hawkmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ChecarCon {

	private Context _context;

	public boolean isServerOn() {
		boolean state = false;
		String resp;
		String SOAP_ACTION = "http://pyramid.org/state";
		String METHOD_NAME = "state";
		String NAMESPACE = "http://pyramid.org";
		String URL = "http://189.244.8.252:4400/LoginHwkM/services/Login?wsdl";
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		final HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

			resp = response.toString();

			if (resp != (null))
				state = true;

		} catch (Exception e) {

		}
		return state;
	}

	public ChecarCon(Context context) {
		this._context = context;
	}

	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	public boolean isOn() {

		InetAddress addr = null;
		try {
			addr = InetAddress.getByName("189.244.8.252".toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (addr.isReachable(5000)) {
				return true;
			} else {

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}

		return false;
	}

	public boolean ping() {
		String host = "189.244.8.252";
		try {

			String pingCmd = "ping -c 4 " + host;
			String pingResult = "";
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(pingCmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				// System.out.println(inputLine);

				// Toast.makeText(_context, inputLine + "\n\n",
				// Toast.LENGTH_SHORT).show();
				pingResult += inputLine;

				// Toast.makeText(_context, pingResult,
				// Toast.LENGTH_SHORT).show();
			}
			// BUSCAR EN EL PINGRESULT POR "UNREACHABLE"
			System.out.println(pingResult);
			boolean esta;
			String unr = "Unreachable";
			esta = pingResult.contains(unr);
			System.out.println("ESTA: " + !esta);
			if (esta) {
				in.close();
				return false;

			} else {
				in.close();
				return true;
			}

		}

		catch (IOException e) {
			System.out.println(e);
			return false;
		}

	}
}