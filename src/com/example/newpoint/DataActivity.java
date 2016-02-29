package com.example.newpoint;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DataActivity extends Activity {

	private Button button_ok, button_cancle;
	private EditText edittext1,edittext2;
	private TitleLayout tl;

	public String s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.data);
		Intent drawintent=getIntent();
		s=drawintent.getStringExtra("pointi");
		
		findView();
		setListener();
		edittext1.setText("0");
		edittext2.setText("0");
		
	}

	public void findView() {
		button_ok = (Button) findViewById(R.id.title_edit);
		button_cancle = (Button) findViewById(R.id.title_back);
		edittext1 = (EditText) findViewById(R.id.first);
		edittext2 = (EditText) findViewById(R.id.second);
		tl=(TitleLayout)findViewById(R.id.title);
	}

	public void setListener() {
		button_ok.setOnClickListener(save_data);
		button_cancle.setOnClickListener(save_data);
	}

	private Button.OnClickListener save_data = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_edit:
				//serverpoint ser=new serverpoint();
				//ser.start();
				Intent returni=new Intent(DataActivity.this,DrawActivity.class);
			    returni.putExtra("datax", edittext1.getText().toString());
			    returni.putExtra("datay", edittext2.getText().toString());
			    returni.putExtra("datai", s);
			    setResult(RESULT_OK,returni);
			    Log.d("DataActivity", "保存");
			    finish();
			    Log.d("DataActivity", " finish();");
				break;
			case R.id.title_back:
				Intent i = new Intent(DataActivity.this, DrawActivity.class);
				DataActivity.this.setResult(3, i);
				finish();
				break;

			}
		}
	};

	public class serverpoint extends Thread{
		
	
		public void run() {
			// 注册蓝牙Server
			
			while(true){
				BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
			BluetoothServerSocket serverSocket = null;
			try {
				serverSocket = bluetooth
						.listenUsingRfcommWithServiceRecord(
								bluetooth.getName(),
								UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BluetoothSocket socket=null;
		
				try {
					socket = serverSocket.accept();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		

			// 处理请求内容

			if (socket != null) {
				OutputStream out=null;
				try {
					out = socket.getOutputStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String str = "问题在于太多问题，试凑够很多个字，因为之前测试的只是很少的数据，如果是一长串数字会怎么办呢，等下再试一下16进制的";
				byte[] data = null;
				try {
					data = str.getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					out.write(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//	edittext1.setText(String.valueOf(data));

			}

			// 关闭蓝牙Server

			try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	}
}

