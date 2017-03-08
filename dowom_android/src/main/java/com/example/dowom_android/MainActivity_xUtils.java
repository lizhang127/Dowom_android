package com.example.dowom_android;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MainActivity_xUtils extends Activity {
	private EditText threadnum;
	private EditText urladdress;
	private int runthread;
	private  String path;
	private LinearLayout layout;
	private ProgressBar progressBar;

	private List<ProgressBar> pbList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		threadnum = (EditText) findViewById(R.id.editText1);
		urladdress = (EditText) findViewById(R.id.editText2);
		layout = (LinearLayout) findViewById(R.id.line);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		 path =new String(( "http://192.168.1.102:8089/err.avi"));
		urladdress.setText(path);threadnum.setText("3");
	}
	public void  butt2click(View v) {
		File filedelete = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/1.avi");
		filedelete.delete();
	}
	public void buttclick(View v) {
		 
		Editable text = threadnum.getText();
		runthread = Integer.parseInt(text.toString());
		layout.removeAllViews();
		pbList = new ArrayList<ProgressBar>();
		for (int i = 0; i < Integer.parseInt(text.toString()); i++) {
			ProgressBar pbView = (ProgressBar) View.inflate(getApplicationContext(), R.layout.item, null);
			layout.addView(pbView);
			pbList.add(pbView);
		}
		new Thread() {
			public void run() {
				try {
					parameter();
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}
		}.start();
	}
	public  class ThreadData extends Thread {
		private int star;
		private int end;
		private int id;
		private int andlast;
		private int max;
		public ThreadData(int start, int end, int id) {
			super();
			this.star = start;
			this.end = end;
			this.id = id;
		}
		@Override
		public void run() {
			try {
				max=end-star;
				String filePath=Environment.getExternalStorageDirectory().getAbsolutePath();
				File file = new File(filePath+"/" + id + ".text");
				fileisExists(file);
				HttpURLConnection urlConnection = urlConnection();
				urlConnection.setRequestProperty("Range", "bytes=" + star + "-" + end);
				String fileName = path.substring(path.lastIndexOf("/")+1,path.length());
				if (urlConnection.getResponseCode() == 206) {
					InputStream in = urlConnection.getInputStream();
					readOrwrite(filePath, in);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}
		private void readOrwrite(String filePath, InputStream in) throws FileNotFoundException, IOException {
			RandomAccessFile raf = new RandomAccessFile
					(filePath+"/1.avi", "rwd");
			RandomAccessFile raflast = new RandomAccessFile
					(filePath+"/"+ id + ".text", "rwd");
			byte[] byt = new byte[1024];
			int len = 0;int tobyt = 0;	int last = 0;
			raf.seek(star);
			while ((len = in.read(byt)) != -1) {
				raf.write(byt, 0, len);
				tobyt += len;
				last = tobyt + star;
				raflast.seek(0);
				andlast=andlast+len;
				raflast.write(("" + last).getBytes());
				pbList.get(id).setMax(max);
				pbList.get(id).setProgress(andlast);
				
			}System.out.println("���سɹ�");
			raflast.close();
			in.close();
			raf.close();
			filedelete(filePath);
		}
		private void filedelete(String filePath) {
			synchronized (this) {
				runthread--;
				if (runthread==0) {
					for (int i = 0; i <Integer.parseInt(threadnum.getText().toString()); i++) {
						File deletefile =new File
								(filePath+"/"+i + ".text");
						deletefile.delete();
					}
				}
			}
		}
		private void fileisExists(File file) throws FileNotFoundException, IOException {
			if (file.exists() && file.length() > 0) {
				BufferedReader bufr = new BufferedReader(new FileReader(file));
				String filesize = bufr.readLine();
				int lastpoit = Integer.parseInt(filesize);
				andlast=lastpoit-star;
				star = lastpoit;
				bufr.close();
			}
		}

	}
	private HttpURLConnection urlConnection() throws MalformedURLException, IOException, ProtocolException {
		URL url = new URL(path);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setConnectTimeout(5000);
	
		return urlConnection;
	}
	private void parameter() throws MalformedURLException, IOException, ProtocolException {
		HttpURLConnection urlConnection = urlConnection();
		if (urlConnection.getResponseCode() == 200) {
			InputStream input = urlConnection.getInputStream();
			int totalsize = urlConnection.getContentLength();
			int start = 0;
			int end = 0;
			int threadsize = totalsize / 3;
			for (int i = 0; i < Integer.parseInt(threadnum.getText().toString()); i++) {
				start = i * threadsize;
				end = (i + 1) * threadsize - 1;
				if (i == 2) {
					end = totalsize;
				}
				 new ThreadData(start, end, i).start();
			}
		}
	}
	
}
