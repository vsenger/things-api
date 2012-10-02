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

import android.util.Log;


public class CentralWS {
	public static final String CONTEXT = "/jhome-console";
	private CentralWS() {
	}

	public static String discovery(String serverAddress, int serverPort) {
		String responseString = null;

		URI uri = null;
		try {
			uri = URIUtils.createURI("http", serverAddress, serverPort,
					CONTEXT + "/Devices", "command=discovery", null);

			Log.d("jHome", uri.toString());
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
