package com.example.newpoint;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
 
	private MyApplication app;
	private Button button_build, button_exit;
	private EditText edittext1,edittext2,edittext3,edittext4,edittext5;
	@Override
	protected void onCreate(Bundle builddInstanceState) {
		super.onCreate(builddInstanceState);
		setContentView(R.layout.activity_main);
		findview();
		initdata();
		setListener();
	}

	void findview() {
		button_build = (Button) findViewById(R.id.build);
		button_exit = (Button) findViewById(R.id.exit);
		edittext1 = (EditText) findViewById(R.id.first);
		edittext2=(EditText)findViewById(R.id.second);
		edittext3 = (EditText) findViewById(R.id.third);
		edittext4=(EditText)findViewById(R.id.forth);
	}
	
	void initdata(){
		edittext2.setText(Environment.getDownloadCacheDirectory().toString());
		edittext3.setText(Environment.getDataDirectory().toString());

	}


	// ����������ť
	private void setListener() {
		button_build.setOnClickListener(build_map);
		button_exit.setOnClickListener(build_map);
	}

	private Button.OnClickListener build_map = new OnClickListener() {
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.build:
				
				app=(MyApplication)getApplication();
				app.setValue(edittext1.getText().toString());
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DrawActivity.class);
				startActivity(intent);
				break;
			case R.id.exit:
				 Log.d("MainActivity", "R.id.exit");
				 String path = "mnt/sdcard/" + "9547" + ".xls";
	             writeExcel(path);
	             fileIsExists();
				break;

			}
		}
	};
	//����ļ��Ƿ����
	public boolean fileIsExists(){
        try{
                File f=new File("mnt/sdcard/" + "9547" + ".xls");
                if(!f.exists()){
                	 Log.d("MainActivity", "fileIs not Exists");
                        return false;
                }
                
        }catch (Exception e) {
                // TODO: handle exception
                return false;
        }
        
        Log.d("MainActivity", "file Is Exists");
        return true;
}
	    private BluetoothAdapter blueadapter = null;
		private BluetoothReceiver bluetoothReceiver = null;
		private BluetoothSocket bluetoothSocket = null;
		private Boolean connect_result = false;

	public void blueoperation() {
		// �õ�BluetoothAdapter����
		blueadapter = BluetoothAdapter.getDefaultAdapter();
		// �жϱ����Ƿ�������ģ�飬�еĻ������Ƿ������жϣ�û�д򿪵Ļ��������
		if (blueadapter != null) {
			if (blueadapter.isEnabled()) {
				//Toast.makeText(MainActivity.this, "�����Ѿ���", Toast.LENGTH_SHORT)
				//		.show();
			} else {
				Intent intent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivity(intent);
			}
			// �Ѿ���Ե���Ϣ

			if (blueadapter.isEnabled()) {
				Log.d("mytag", "scanning()");
				scanning();
			}
		}
	}

	
	//ɨ�踽��������
	private void scanning() {
		IntentFilter intentFilter = new IntentFilter(
				BluetoothDevice.ACTION_FOUND);
		// ����һ��BluetoothReceiver����
		bluetoothReceiver = new BluetoothReceiver();
		Log.d("mytag", "registerReceiver");
		registerReceiver(bluetoothReceiver, intentFilter);
		blueadapter.startDiscovery();
	}
	private class BluetoothReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {

				// ֻҪBluetoothReceiver���յ�����ϵͳ�Ĺ㲥��
				// Intent����ոշ���Զ�������豸�������Ķ��󣬿��Դ��յ���Intent����ȡ��һЩ��Ϣ
				BluetoothDevice bluetoothDevice = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				Log.d("mytag", bluetoothDevice.getAddress());
				try {
					Method creMethod = BluetoothDevice.class    
					        .getMethod("createBond");    
					Log.e("TAG", "��ʼ���");    
					creMethod.invoke(bluetoothDevice);
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}    
			}
		}
	
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void writeExcel(String fileName) {
		WritableWorkbook wwb = null;

		try {
			// ����һ����д��Ĺ���������
			wwb = Workbook.createWorkbook(new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (wwb != null) {
			// ��һ�������ǹ���������ƣ��ڶ����ǹ������ڹ������е�λ��
			WritableSheet ws = wwb.createSheet("������Ϣ", 0);
			// ��ָ����Ԫ���������
			Label lbl1 = new Label(0, 0, "����");
			Label lbl2 = new Label(1, 0, "���");
			Label lbl3 = new Label(2, 0, "����");
			Label lbl4 = new Label(3, 0, "γ��");
			Label lbl5 = new Label(4, 0, "����x");
			Label lbl6 = new Label(5, 0, "����y");
			Label lbl7 = new Label(6, 0, "�ڽӵ�");
			Label lbl8 = new Label(7, 0, "��������x");
			Label lbl9 = new Label(8, 0, "��������y");

			// �ڶ��ű��洢�ߵ�һЩϵ��
			WritableSheet wsa = wwb.createSheet("�ߵ���Ϣ", 1);
			Label lbla1 = new Label(0, 0, "��");
			Label lbla2 = new Label(1, 0, "���");
			Label lbla3 = new Label(2, 0, "�߳�");
			Label lbla4 = new Label(3, 0, "�߾�");
			Label lbla5 = new Label(4, 0, "�߲�");
			Label lbla6 = new Label(5, 0, "��λ");
			Label lbla7 = new Label(6, 0, "��ʼ��");
			Label lbla8 = new Label(7, 0, "�յ�");

			try {
				ws.addCell(lbl1);
				ws.addCell(lbl2);
				ws.addCell(lbl3);
				ws.addCell(lbl4);
				ws.addCell(lbl5);
				ws.addCell(lbl6);
				ws.addCell(lbl7);
				ws.addCell(lbl8);
				ws.addCell(lbl9);
				wsa.addCell(lbla1);
				wsa.addCell(lbla2);
				wsa.addCell(lbla3);
				wsa.addCell(lbla4);
				wsa.addCell(lbla5);
				wsa.addCell(lbla6);
				wsa.addCell(lbla7);
				wsa.addCell(lbla8);
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				// ���ڴ���д���ļ�
				wwb.write();
				wwb.close();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}

