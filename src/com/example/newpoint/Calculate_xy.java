package com.example.newpoint;

//这个类负责将实际的数据和视图上的数据进行坐标的转换
public class Calculate_xy {

	// 将采集的数据转换为在屏幕坐标系的的相对应的值
	// 并返回其屏幕的x值
	public float XlstoScreen_x(float x, float scale, float up_down,
			float left_right,float dw) {
         return (x*scale+(dw/2+left_right));
	}
	//将测绘采集的数据转换为屏幕坐标系的相对应的值
	//并返回其屏幕的y值
	public float XlstoScreen_y(float y, float scale, float up_down,
			float left_right,float dh) {
         return ((dh/2+up_down)-y*scale);
	}
	//将屏幕上的值转换为实际的x值，便于判断其值是否应该在屏幕上显示
	public float ScreentoXls_x(float x,float scale,float up_down,
			float left_right,float dw)
	{
		return x-(dw/2+left_right);
	}
	
	public float ScreentoXls_y(float y,float scale,float up_down,
			float left_right,float dh)
	{
		return y-(dh/2+up_down);
	}
}
