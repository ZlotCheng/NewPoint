package com.example.newpoint;

import android.app.Application;

public class MyApplication extends Application{
	
	  private static final String VALUE = "Newpoint";
	    
	    private String value;
	    
	    @Override
	    public void onCreate()
	    {
	        super.onCreate();
	        setValue(VALUE); // ��ʼ��ȫ�ֱ���
	    }
	    
	    public void setValue(String value)
	    {
	        this.value = value;
	    }
	    
	    public String getValue()
	    {
	        return value;
	    }
}
