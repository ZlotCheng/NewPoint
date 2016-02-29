package com.example.newpoint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

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
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.*;
import android.graphics.Bitmap.Config;

public class DrawActivity extends Activity implements OnMenuItemClickListener,
		OnTouchListener {
	private MyApplication app;
	public final static int NONE=1,DRAG=2,ZOOM=3;
	int mode=NONE;
	String name;
	int screenw = 0;
	int screenh = 0;
	Bitmap ibitmap;
	Canvas icanvas;
	Paint ipaint;
	ImageView imageview;
	float startx;
	float starty;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		screenw = getWindowManager().getDefaultDisplay().getWidth();
		screenh = getWindowManager().getDefaultDisplay().getHeight();
		imageview = (ImageView) findViewById(R.id.imageview);
		imageview.setOnTouchListener(this);
		Log.d("DrawActivity", "" + screenw);
        
		app=(MyApplication)getApplication();
		name=app.getValue();

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		/*
		Point startp=new Point();
		// TODO Auto-generated method stub
		switch(event.getAction()&MotionEvent.ACTION_MASK){
		
		case MotionEvent.ACTION_DOWN:
			startp.x=event.getX();
			startp.y=event.getY();
			Log.d("DrawActivity", "" + event.getX() + " " + event.getY());
			mode=DRAG;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mode=ZOOM;
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mode=NONE;
			break;
			
		case MotionEvent.ACTION_MOVE:
			if(mode==DRAG){
				left_right+=(event.getX()-startp.x);
				up_down+=(event.getY()-startp.y);
				startp.x=event.getX();
				startp.y=event.getY();
				Log.d("DrawActivity-move_drag", "" + event.getX() + " " + event.getY());
				drawUI_tranfs();
			}
			else if(mode==ZOOM){
				Log.d("DrawActivity-move_zoom", "" + event.getX() + " " + event.getY());
			}
			break;
			
		}
		
		*/
		
		float oldPath=0;
		float x,y;
		float stopx;
		float stopy;
		int i=0;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startx = event.getX();
			starty = event.getY();
			Log.d("DrawActivity", "" + event.getX() + " " + event.getY());
			break;
		case MotionEvent.ACTION_UP:
			if(0==i){
			stopx = event.getX();
			stopy = event.getY();
			Log.d("DrawActivity", "" + event.getX() + " " + event.getY());
			left_right+=(stopx-startx);
			up_down+=(stopy-starty);
			drawUI_tranfs();
			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			  i=1;
			 float mx = event.getX(1);//����1�����ǵڶ�����ָ
			  float my = event.getY(1);
			  Log.d("DrawActivity", ""+event.getX(1)+"  " +event.getY(1));
			  oldPath = (float) Math.sqrt((mx - event.getX(0)) * (mx - event.getX(0)) + (my - event.getY(0)) * (my - event.getY(0)));// �õ���ʼ����
			break;
		case MotionEvent.ACTION_POINTER_UP:
			  x = event.getX(0);// ����0�����һ�����µ���ָ
			  y = event.getY(0);
			  mx = event.getX(1);//����1����ڶ������µ���ָ
			  my = event.getY(1);
			  Log.d("DrawActivity", ""+event.getX(1)+"  " +event.getY(1));
			  float newPath = (float) Math.sqrt((mx - x) * (mx - x) + (my - y) * (my - y));// ����õ��¾���
			  
			  if (250 < newPath)// �ж��¾���;ɾ���
				  _scale*=1.1;// �Ŵ�����
			  else {
				  _scale*=0.9;
			  }
			  drawUI_tranfs();
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		
		
		

		
		Log.d("DrawActivity_scale", ""+_scale);
		
		return true;
	}
	
	
	
	

	public static float distance(MotionEvent event)
    {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
         
        return (float) Math.sqrt(dx*dx + dy*dy);
    }
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// �Д෵���Ƿ�OK
		switch (requestCode) {
		case 1:

			if (resultCode == RESULT_OK) {
				Log.d("DrawActivity", "onActivityResult��a[pointindex].y=pointy");
				// ��������Ĳ�������ȷ�ģ���ô����Ҫȥ����ȷ�Ľ������ҵ�
				pointx = Float.parseFloat(data.getStringExtra("datax"));
				pointy = Float.parseFloat(data.getStringExtra("datay"));
				
				a[pointindex] = new Point();
				a[pointindex].x = pointx;
				a[pointindex].y = pointy;
				
				a[pointindex].i = startpointi;//Integer.parseInt(data.getStringExtra("datai"));
				if(pointindex!=0){
					startpointi=startpointi+1;//Integer.parseInt(data.getStringExtra("datai"))+1;
				}
				
				pointindex++;
				Log.d("DrawActivity", "" + pointx);
				Log.d("DrawActivity", "" + pointy);
				Log.d("DrawActivity", "" + a[pointindex-1].i);
				checkpoint(pointx, pointy);
				Log.d("DrawActivity",
						"onActivityResult��checkpoint(pointx,pointy);");
				drawUI();

				
			}
			break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		// ��Ӳ˵���
		MenuItem add = menu.add(0, 1, 0, "��λ");
		MenuItem del = menu.add(0, 2, 0, "��ѡ������");
		MenuItem save = menu.add(0, 3, 0, "�Ƴ�");
		MenuItem named = menu.add(0, 4, 0, "������̨��");
		MenuItem change = menu.add(0, 5, 0, "�޸�");
		MenuItem parameter = menu.add(0, 6, 0, "����");

		add.setOnMenuItemClickListener(this);
		del.setOnMenuItemClickListener(this);
		save.setOnMenuItemClickListener(this);
		named.setOnMenuItemClickListener(this);
		change.setOnMenuItemClickListener(this);
		parameter.setOnMenuItemClickListener(this);

		// �󶨵�ActionBar
		add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		del.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		save.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		named.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		change.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		parameter.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}

	int startpointi=0;
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:

			Intent intent = new Intent(DrawActivity.this, DataActivity.class);
			intent.putExtra("pointi", startpointi);
			startActivityForResult(intent, 1);
			break;

		case 2:
			PairingBlue p = new PairingBlue();
			p.blueoperation();
			break;
		case 3:
			
			GraphHelper gh=new GraphHelper();
			gh.init("mnt/sdcard/"+app.getValue()+".xls");
			break;
		case 4:
			Intent intent1 = new Intent();
			intent1.setClass(DrawActivity.this, MainActivity.class);
			startActivity(intent1);
			break;
		case 5:
			break;
		case 6:
			drawUI();
			break;
		}
		return false;
	}
	
	
		public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			
			Log.d("DrawActivity", "isExternalStorageWritable");
		return true;
		}
		Log.d("DrawActivity", "is not"
				+ " ExternalStorageWritable");
		return false;
		}
		//�ж��ⲿ�洢�Ƿ����ٿ��Զ�
		public boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) ||
		Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			Log.d("DrawActivity", "MEDIA_MOUNTED_READ_ONLY");
		return true;
		}
		Log.d("DrawActivity", " is not MEDIA_MOUNTED_READ_ONLY");
		return false;
		}
	

	// ���л�ͼ���ֵ�Demo
	// ��Щ������ϵ��Ҫ�õ��Ĳ���
	float _scale = 1, up_down = 0, left_right = 0, level = 1;
	float _pointx = 0, _pointy = 0;

	//

	public void drawUI() {

		// �����еĵ���Ƴ�����
		/*
		 * ����ĵ���ʱ��һ��ԲȦ��ʾ���滻ѡ��Ϊ
		 */
		ibitmap = Bitmap.createBitmap(screenw, screenh, Config.ARGB_8888);
	
		icanvas = new Canvas();
		icanvas.setBitmap(ibitmap);
		ipaint = new Paint();
		Paint paint = new Paint();
		ipaint.setColor(Color.RED);
		Calculate_xy c = new Calculate_xy();
		Bitmap bmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.icons_dingwei);
		bmp = Bitmap.createBitmap(bmp, 0, 0, 50,
				50, null, true);
		Bitmap bmp1=BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_zuobiao);
		bmp1 = Bitmap.createBitmap(bmp1, 0, 0, 50,
				50, null, true);
		Log.d("DrawActivity", "drawUI()");
		icanvas.drawBitmap(bmp1,50,screenh-100,ipaint);
		for (int i = 0; i < pointindex; i++) {

			
			icanvas.drawLine(c.XlstoScreen_x(a[i].x, _scale, up_down,
					left_right, screenw), c.XlstoScreen_y(a[i].y, _scale,
					up_down, left_right, screenh), c.XlstoScreen_x(a[a[i].i].x,
					_scale, up_down, left_right, screenw), c.XlstoScreen_y(
					a[a[i].i].y, _scale, up_down, left_right, screenh), paint);
			icanvas.drawBitmap(bmp, c.XlstoScreen_x(a[i].x, _scale, up_down,
								left_right, screenw)-25, c.XlstoScreen_y(a[i].y, _scale,
													up_down, left_right, screenh)-25, ipaint);
		//	icanvas.drawCircle(c.XlstoScreen_x(a[i].x, _scale, up_down,
		//			left_right, screenw), c.XlstoScreen_y(a[i].y, _scale,
		//			up_down, left_right, screenh), 15, ipaint);
			icanvas.drawText("p"+i, c.XlstoScreen_x(a[i].x, _scale, up_down,
					left_right, screenw),  c.XlstoScreen_y(a[i].y, _scale,
					up_down, left_right, screenh)-15, paint);
			
		}
		ipaint.setColor(Color.BLUE);
		icanvas.drawCircle(screenw / 2, screenh / 2, 15, ipaint);
		Log.d("DrawActivity", "drawUI()for֮��");

	//	icanvas.drawLine(10, 10, 5000, 5000, paint);
	//	icanvas.drawLine(800, 800, 5000, 5000, paint);
		icanvas.drawCircle(5000, 5000, 15, paint);
		icanvas.drawBitmap(ibitmap, 0, 0, paint);

		Log.d("DrawActivity", "drawBitmap֮��");
		imageview.setImageBitmap(ibitmap);
		imageview.invalidate();

	}

	public void drawUI_tranfs() {
		Log.d("DrawActivity_scale", ""+_scale);
		// �����еĵ���Ƴ�����
		ibitmap = Bitmap.createBitmap(screenw, screenh, Config.ARGB_8888);
		icanvas = new Canvas();
		icanvas.setBitmap(ibitmap);
		ipaint = new Paint();
		Paint paint = new Paint();
		ipaint.setColor(Color.RED);
		Calculate_xy c = new Calculate_xy();
		Bitmap bmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.icons_dingwei);
		bmp = Bitmap.createBitmap(bmp, 0, 0, 50,
				50, null, true);
		
		Bitmap bmp1=BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_zuobiao);
		bmp1 = Bitmap.createBitmap(bmp1, 0, 0, 50,
				50, null, true);
	
		icanvas.drawBitmap(bmp1,50,screenh-100,ipaint);
		bmp1.recycle();
		for (int i = 0; i < pointindex; i++) {

			icanvas.drawLine(c.XlstoScreen_x(a[i].x, _scale, up_down,
					left_right, screenw), c.XlstoScreen_y(a[i].y, _scale,
					up_down, left_right, screenh), c.XlstoScreen_x(a[a[i].i].x,
					_scale, up_down, left_right, screenw), c.XlstoScreen_y(
					a[a[i].i].y, _scale, up_down, left_right, screenh), paint);
			icanvas.drawBitmap(bmp, c.XlstoScreen_x(a[i].x, _scale, up_down,
								left_right, screenw)-25, c.XlstoScreen_y(a[i].y, _scale,
													up_down, left_right, screenh)-25, ipaint);
		//	icanvas.drawCircle(c.XlstoScreen_x(a[i].x, _scale, up_down,
		//			left_right, screenw), c.XlstoScreen_y(a[i].y, _scale,
		//			up_down, left_right, screenh), 15, ipaint);
			icanvas.drawText("p"+i, c.XlstoScreen_x(a[i].x, _scale, up_down,
					left_right, screenw),  c.XlstoScreen_y(a[i].y, _scale,
					up_down, left_right, screenh)-15, paint);
		}
		Log.d("DrawActivity", "drawUI()for֮��");

	//	icanvas.drawLine(10, 10, 5000, 5000, paint);
	//	icanvas.drawLine(800, 800, 5000, 5000, paint);
		icanvas.drawCircle(5000, 5000, 15, paint);
		icanvas.drawBitmap(ibitmap, 0, 0, paint);

		Log.d("DrawActivity", "drawBitmap֮��");
		imageview.setImageBitmap(ibitmap);
		imageview.invalidate();

	}
	public void tranfs() {
		// ����������ݸ�ת������ͼ�е�����
	}

	// ����ͼ���е����ĿӦ��index
	int pointindex = 0;
	int check_pointindex = 0;
	float pointx, pointy;
	Point a[] = new Point[100];
	Point result[] = new Point[100];

	// �����Ƿ�Ӧ����ʾ����ͼ��
	public void checkpoint(float pointx, float pointy) {
		// ���뵱ǰ���λ��
		// �������еĵ㣬ѡ��Ӧ����ʾ�ĵ㡣
		// ���е����Ŀ
		Calculate_xy c = new Calculate_xy();
		left_right = -pointx;
		up_down = pointy;
		check_pointindex = 0;
		_scale=1;
		Log.d("DrawActivity", "����checkpoint");
		for (int i = 0; i < pointindex; i++) {
			Log.d("DrawActivity", "����checkpointѭ��");
			Log.d("DrawActivity", "" + a[i].x + "  " + a[i].y);
			if ((a[i].x > (pointx - screenw / 2))
					&& (a[i].x < (pointx + screenw / 2))
					&& (a[i].y > (pointy - screenh / 2))
					&& (a[i].y < (pointy + screenh / 2))) {
				Log.d("DrawActivity",
						""
								+ c.XlstoScreen_x(a[i].x, _scale, up_down,
										left_right, screenw));
				Log.d("DrawActivity",
						""
								+ c.XlstoScreen_y(a[i].y, _scale, up_down,
										left_right, screenh));
				result[check_pointindex] = new Point();
				result[check_pointindex].x = c.XlstoScreen_x(a[i].x, _scale,
						up_down, left_right, screenw);
				result[check_pointindex].y = c.XlstoScreen_y(a[i].y, _scale,
						up_down, left_right, screenh);
				check_pointindex++;
			}
		}

	}

	public void check() {
		//
	}

	private class ConnectThread extends Thread {
		String macAddress = "";

		boolean connecting, connected;
		BluetoothAdapter mBluetoothAdapter = null;
		BluetoothDevice mBluetoothDevice = null;
		BluetoothSocket socket = null;
		int connetTime = 0;

		public ConnectThread(String mac) {
			macAddress = mac;
		}

		public void run() {
			connecting = true;
			connected = false;
			if (mBluetoothAdapter == null) {
				mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			}
			mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(macAddress);
			mBluetoothAdapter.cancelDiscovery();
			UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
			try {
				socket = mBluetoothDevice
						.createRfcommSocketToServiceRecord(uuid);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Log.e("TAG", "Socket", e);
			}
			// adapter.cancelDiscovery();
			while (!connected && connetTime <= 10) {
				connectDevice();
			}
			// ����ConnectThread
			// synchronized (BluetoothService.this) {
			// ConnectThread = null;
			// }
		}

		public void cancel() {
			try {
				socket.close();
				socket = null;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				connecting = false;
			}
		}

		protected void connectDevice() {
			try {
				// ���ӽ���֮ǰ�������
				if (mBluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE) {
					Method creMethod = BluetoothDevice.class
							.getMethod("createBond");
					Log.e("TAG", "��ʼ���");
					creMethod.invoke(mBluetoothDevice);
				} else {
				}
			} catch (Exception e) {
				// TODO: handle exception
				// DisplayMessage("�޷���ԣ�");
				e.printStackTrace();
			}
			mBluetoothAdapter.cancelDiscovery();
			try {
				socket.connect();
				// DisplayMessage("���ӳɹ�!");
				connetTime++;
				connected = true;
			} catch (IOException e) {
				// TODO: handle exception
				// DisplayMessage("����ʧ�ܣ�");
				connetTime++;
				connected = false;
				try {
					socket.close();
					socket = null;
				} catch (IOException e2) {
					// TODO: handle exception
					Log.e("TAG",
							"Cannot close connection when connection failed");
				}
			} finally {
				connecting = false;
			}
		}

	}

	public class PairingBlue {
		private BluetoothAdapter blueadapter = null;
		private BluetoothReceiver bluetoothReceiver = null;
		private BluetoothSocket bluetoothSocket = null;
		private Boolean connect_result = false;

		// ���������������ж��Ƿ��п����������еĻ���������ͨ��
		public void blueoperation() {
			// �õ�BluetoothAdapter����
			blueadapter = BluetoothAdapter.getDefaultAdapter();
			// �жϱ����Ƿ�������ģ�飬�еĻ������Ƿ������жϣ�û�д򿪵Ļ��������
			if (blueadapter != null) {
				if (blueadapter.isEnabled()) {
					// Toast.makeText(MainActivity.this, "�����Ѿ���",
					// Toast.LENGTH_SHORT)
					// .show();
				} else {
					Intent intent = new Intent(
							BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivity(intent);
				}
				// �Ѿ���Ե���Ϣ

				if (blueadapter.isEnabled()) {

					getdevices();
				}
			}
		}

		// ���Ӻ���������֮���ͨ�Ų����н�������
		private void getdevices() {
			Set<BluetoothDevice> devices = blueadapter.getBondedDevices();
			if (devices.size() > 0) {
				for (Iterator iterator = devices.iterator(); iterator.hasNext();) {
					BluetoothDevice device = (BluetoothDevice) iterator.next();

					Log.d("mytag", device.getAddress());
					Log.d("mytag", device.getName());
					UUID uuid = UUID
							.fromString("00001101-0000-1000-8000-00805F9B34FB");
					try {
						bluetoothSocket = device
								.createRfcommSocketToServiceRecord(uuid);

						bluetoothSocket.connect();

						// �������仰֤��������Զ�̵�������
						Log.d("mytag", "����������Զ�̵�������");

						connect_result = true;
						// ��ʾ��������Զ�̵�����
						if (connect_result == true) {
							// ���صڶ��������ļ�
							InputStream in = bluetoothSocket.getInputStream();
							String res = "";

							byte data[] = new byte[1024];

							in.read(data);
							res = new String(data, "UTF-8");
							Toast.makeText(DrawActivity.this,
									" ����յ��������ǣ�" + res, Toast.LENGTH_SHORT)
									.show();

						} else {
							Log.d("mytag", "û������������");
							Toast.makeText(DrawActivity.this, "û��ȡ����Է���������ϵ",
									Toast.LENGTH_SHORT).show();
						}

					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			} else {
				scanning();
			}
		}

		// ɨ�踽��������
		private void scanning() {
			IntentFilter intentFilter = new IntentFilter(
					BluetoothDevice.ACTION_FOUND);
			// ����һ��BluetoothReceiver����
			bluetoothReceiver = new BluetoothReceiver();
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
	}


}

