package com.example.newpoint;

//����ฺ��ʵ�ʵ����ݺ���ͼ�ϵ����ݽ��������ת��
public class Calculate_xy {

	// ���ɼ�������ת��Ϊ����Ļ����ϵ�ĵ����Ӧ��ֵ
	// ����������Ļ��xֵ
	public float XlstoScreen_x(float x, float scale, float up_down,
			float left_right,float dw) {
         return (x*scale+(dw/2+left_right));
	}
	//�����ɼ�������ת��Ϊ��Ļ����ϵ�����Ӧ��ֵ
	//����������Ļ��yֵ
	public float XlstoScreen_y(float y, float scale, float up_down,
			float left_right,float dh) {
         return ((dh/2+up_down)-y*scale);
	}
	//����Ļ�ϵ�ֵת��Ϊʵ�ʵ�xֵ�������ж���ֵ�Ƿ�Ӧ������Ļ����ʾ
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
