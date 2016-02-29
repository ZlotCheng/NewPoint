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
	//�������ݽṹ���е�����
	public void save_all(){
		
	}
	
	
	public void delete_someone(){
		
	}

}
