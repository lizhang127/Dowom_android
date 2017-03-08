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

import org.xutils.x;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 *
 *
 */
public class MainActivity extends Activity {
	private EditText threadnum;
	private EditText urladdress;
	private int runthread;
	private String path;
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
		path = new String(("http://192.168.1.102:8089/err.avi"));
		urladdress.setText(path);
		threadnum.setText("3");
		 
	}

	public void butt2click(View v) {
		File filedelete = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/1.avi");
		filedelete.delete();
	}

	public void buttclick(View v) {
		   

	}

}
