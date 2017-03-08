package com.itcast.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.itcast.unit_test.R;

public class Unit  {
	
	public static String intoStr(InputStream in) throws IOException {

		ByteArrayOutputStream bytOutput = new ByteArrayOutputStream();
		byte[] byt = new byte[1024];
		int len = -1;

		while ((len = in.read(byt)) != -1) {
			bytOutput.write(byt, 0, len);
		}
		String str = new String(bytOutput.toByteArray());
		return str;
	}
	public  static String getRedirectCode(String path) throws MalformedURLException, IOException, ProtocolException {

		URL url = new URL(path);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setConnectTimeout(6000);
		int code = urlConnection.getResponseCode();
		String location = urlConnection.getHeaderField("Location");
		System.out.println("getResponseCode=" + code + "");
		return location;
	}
	
}
