package br.com.globalcode.jhome.android.ws;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class DeviceWS {

	private DeviceWS() {
	}

	public static String send(String serverAddress, int serverPort,
			String deviceName, String commandValue) {
		String responseString = null;

		URI uri = null;
		try {
			//uri = URIUtils.createURI("http", serverAddress, serverPort, "/"
			//		+ deviceName + "?" + commandValue, null, null);
			uri = URIUtils.createURI("http", serverAddress, serverPort, 
					"/jhome-console/Devices?component=" + deviceName + "&command=" + commandValue, null, null);

			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(uri);
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			responseString = EntityUtils.toString(entity);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseString;
	}
	
	public static String read(String serverAddress, int serverPort, String deviceName) {
		String responseString = null;

		URI uri = null;
		try {
			/*uri = URIUtils.createURI("http", serverAddress, serverPort, "/"
					+ deviceName, null, null);*/
			uri = URIUtils.createURI("http", serverAddress, serverPort, 
					"/jhome-console/Devices?component=" + deviceName, null, null);

			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(uri);
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			responseString = EntityUtils.toString(entity);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseString;
	}
}