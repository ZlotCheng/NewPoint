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
			 float mx = event.getX(1);//参数1代表是第二个手指
			  float my = event.getY(1);
			  Log.d("DrawActivity", ""+event.getX(1)+"  " +event.getY(1));
			  oldPath = (float) Math.sqrt((mx - event.getX(0)) * (mx - event.getX(0)) + (my - event.getY(0)) * (my - event.getY(0)));// 得到初始距离
			break;
		case MotionEvent.ACTION_POINTER_UP:
			  x = event.getX(0);// 参数0代表第一个按下的手指
			  y = event.getY(0);
			  mx = event.getX(1);//参数1代表第二个按下的手指
			  my = event.getY(1);
			  Log.d("DrawActivity", ""+event.getX(1)+"  " +event.getY(1));
			  float newPath = (float) Math.sqrt((mx - x) * (mx - x) + (my - y) * (my - y));// 计算得到新距离
			  
			  if (250 < newPath)// 判断新距离和旧距离
				  _scale*=1.1;// 放大棋盘
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
		// 判斷返回是否OK
		switch (requestCode) {
		case 1:

			if (resultCode == RESULT_OK) {
				Log.d("DrawActivity", "onActivityResult的a[pointindex].y=pointy");
				// 如果传来的参数是正确的，那么我需要去把正确的结果存进我的
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
						"onActivityResult的checkpoint(pointx,pointy);");
				drawUI();

				
			}
			break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		// 添加菜单项
		MenuItem add = menu.add(0, 1, 0, "定位");
		MenuItem del = menu.add(0, 2, 0, "另选出发点");
		MenuItem save = menu.add(0, 3, 0, "移除");
		MenuItem named = menu.add(0, 4, 0, "重命名台区");
		MenuItem change = menu.add(0, 5, 0, "修改");
		MenuItem parameter = menu.add(0, 6, 0, "参数");

		add.setOnMenuItemClickListener(this);
		del.setOnMenuItemClickListener(this);
		save.setOnMenuItemClickListener(this);
		named.setOnMenuItemClickListener(this);
		change.setOnMenuItemClickListener(this);
		parameter.setOnMenuItemClickListener(this);

		// 绑定到ActionBar
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
		//判断外部存储是否至少可以读
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
	

	// 进行绘图部分的Demo
	// 这些是坐标系中要用到的参数
	float _scale = 1, up_down = 0, left_right = 0, level = 1;
	float _pointx = 0, _pointy = 0;

	//

	public void drawUI() {

		// 将所有的点绘制出来。
		/*
		 * 这里的点暂时用一个圆圈表示，替换选择为
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
		Log.d("DrawActivity", "drawUI()for之后");

	//	icanvas.drawLine(10, 10, 5000, 5000, paint);
	//	icanvas.drawLine(800, 800, 5000, 5000, paint);
		icanvas.drawCircle(5000, 5000, 15, paint);
		icanvas.drawBitmap(ibitmap, 0, 0, paint);

		Log.d("DrawActivity", "drawBitmap之后");
		imageview.setImageBitmap(ibitmap);
		imageview.invalidate();

	}

	public void drawUI_tranfs() {
		Log.d("DrawActivity_scale", ""+_scale);
		// 将所有的点绘制出来。
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
		Log.d("DrawActivity", "drawUI()for之后");

	//	icanvas.drawLine(10, 10, 5000, 5000, paint);
	//	icanvas.drawLine(800, 800, 5000, 5000, paint);
		icanvas.drawCircle(5000, 5000, 15, paint);
		icanvas.drawBitmap(ibitmap, 0, 0, paint);

		Log.d("DrawActivity", "drawBitmap之后");
		imageview.setImageBitmap(ibitmap);
		imageview.invalidate();

	}
	public void tranfs() {
		// 将表里的数据给转化成视图中的坐标
	}

	// 这张图所有点的数目应该index
	int pointindex = 0;
	int check_pointindex = 0;
	float pointx, pointy;
	Point a[] = new Point[100];
	Point result[] = new Point[100];

	// 检查点是否应该显示在视图中
	public void checkpoint(float pointx, float pointy) {
		// 传入当前点的位置
		// 遍历所有的点，选出应该显示的点。
		// 所有点的数目
		Calculate_xy c = new Calculate_xy();
		left_right = -pointx;
		up_down = pointy;
		check_pointindex = 0;
		_scale=1;
		Log.d("DrawActivity", "进入checkpoint");
		for (int i = 0; i < pointindex; i++) {
			Log.d("DrawActivity", "进入checkpoint循环");
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
			// 重置ConnectThread
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
				// 连接建立之前的先配对
				if (mBluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE) {
					Method creMethod = BluetoothDevice.class
							.getMethod("createBond");
					Log.e("TAG", "开始配对");
					creMethod.invoke(mBluetoothDevice);
				} else {
				}
			} catch (Exception e) {
				// TODO: handle exception
				// DisplayMessage("无法配对！");
				e.printStackTrace();
			}
			mBluetoothAdapter.cancelDiscovery();
			try {
				socket.connect();
				// DisplayMessage("连接成功!");
				connetTime++;
				connected = true;
			} catch (IOException e) {
				// TODO: handle exception
				// DisplayMessage("连接失败！");
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

		// 蓝牙操作，首先判断是否有开启蓝牙，有的话进行连接通信
		public void blueoperation() {
			// 得到BluetoothAdapter对象
			blueadapter = BluetoothAdapter.getDefaultAdapter();
			// 判断本机是否有蓝牙模块，有的话进入是否开启的判断，没有打开的话，申请打开
			if (blueadapter != null) {
				if (blueadapter.isEnabled()) {
					// Toast.makeText(MainActivity.this, "蓝牙已经打开",
					// Toast.LENGTH_SHORT)
					// .show();
				} else {
					Intent intent = new Intent(
							BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivity(intent);
				}
				// 已经配对的信息

				if (blueadapter.isEnabled()) {

					getdevices();
				}
			}
		}

		// 连接好两个蓝牙之间的通信并进行接收数据
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

						// 能输出这句话证明连接上远程的蓝牙了
						Log.d("mytag", "我连接上了远程的蓝牙了");

						connect_result = true;
						// 表示连接上了远程的蓝牙
						if (connect_result == true) {
							// 加载第二个布局文件
							InputStream in = bluetoothSocket.getInputStream();
							String res = "";

							byte data[] = new byte[1024];

							in.read(data);
							res = new String(data, "UTF-8");
							Toast.makeText(DrawActivity.this,
									" 这次收到的数据是：" + res, Toast.LENGTH_SHORT)
									.show();

						} else {
							Log.d("mytag", "没有连接上蓝牙");
							Toast.makeText(DrawActivity.this, "没有取得与对方蓝牙的联系",
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

		// 扫描附近的蓝牙
		private void scanning() {
			IntentFilter intentFilter = new IntentFilter(
					BluetoothDevice.ACTION_FOUND);
			// 创建一个BluetoothReceiver对象
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

					// 只要BluetoothReceiver接收到来自系统的广播，
					// Intent代表刚刚发现远程蓝牙设备适配器的对象，可以从收到的Intent对象取出一些信息
					BluetoothDevice bluetoothDevice = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					Log.d("mytag", bluetoothDevice.getAddress());
					try {
						Method creMethod = BluetoothDevice.class
								.getMethod("createBond");
						Log.e("TAG", "开始配对");
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

