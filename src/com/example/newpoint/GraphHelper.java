package com.example.newpoint;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class GraphHelper {
	
	
	public void init(String name){
		WritableWorkbook wwb = null;
		try {
			wwb=Workbook.createWorkbook(new File(name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		if (wwb != null) {
			// 第一个参数是工作表的名称，第二个是工作表在工作簿中的位置
			WritableSheet ws = wwb.createSheet("顶点信息", 0);
			// 在指定单元格插入数据
			Label lbl1 = new Label(0, 0, "顶点");
			Label lbl2 = new Label(1, 0, "编号");
			Label lbl3 = new Label(2, 0, "经度");
			Label lbl4 = new Label(3, 0, "纬度");
			Label lbl5 = new Label(4, 0, "坐标x");
			Label lbl6 = new Label(5, 0, "坐标y");
			Label lbl7 = new Label(6, 0, "邻接点");
			Label lbl8 = new Label(7, 0, "绝对坐标x");
			Label lbl9 = new Label(8, 0, "绝对坐标y");

			// 第二张表，存储边的一些系数
			WritableSheet wsa = wwb.createSheet("边的信息", 1);
			Label lbla1 = new Label(0, 0, "边");
			Label lbla2 = new Label(1, 0, "编号");
			Label lbla3 = new Label(2, 0, "线长");
			Label lbla4 = new Label(3, 0, "线径");
			Label lbla5 = new Label(4, 0, "线材");
			Label lbla6 = new Label(5, 0, "相位");
			Label lbla7 = new Label(6, 0, "起始点");
			Label lbla8 = new Label(7, 0, "终点");

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
				// 从内存中写入文件
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
	//保存数据结构所有的数据
	public void save_all(){
		
	}
	
	
	public void delete_someone(){
		
	}

}
