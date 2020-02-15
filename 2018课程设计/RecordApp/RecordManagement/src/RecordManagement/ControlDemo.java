package RecordManagement;

import java.io.*;

import javax.swing.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;

public class ControlDemo {
	/**
	 * �ж��ļ�����
	 */
	static String getFileType(String fileName) {
		// �ԡ�.���ָ� ����д��String.split("\\.");�ķ�ʽ
		String[] strName = fileName.split("\\.");
		int index = strName.length - 1;
		return strName[index];
	}

	/**
	 * ������浽�ļ���
	 * 
	 * @param n
	 * @param fileName
	 */
	static void outputObject(Note[] n, String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (getFileType(fileName).equals("xls")) {
			HSSFWorkbook hw = new HSSFWorkbook();
			HSSFSheet sheet = hw.createSheet("�ҵļ�¼��");
			HSSFRow row1 = sheet.createRow(0);
			row1.createCell(0).setCellValue("���˼�����������¼");
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
			HSSFRow row2 = sheet.createRow(1);
			row2.createCell(0).setCellValue("��");
			row2.createCell(1).setCellValue("��");
			row2.createCell(2).setCellValue("��");
			row2.createCell(3).setCellValue("����");
			row2.createCell(4).setCellValue("����/֧��");
			row2.createCell(5).setCellValue("��ע");
			row2.createCell(6).setCellValue("ͼƬ��ַ");
			for (int i = 0; i < n.length; i++) {
				if (n[i] == null) {
					break;
				}
				HSSFRow row3 = sheet.createRow(i + 2);
				for (int j = 0; j < 7; j++) {
					HSSFCell cell = row3.createCell(j);
					switch (j) {
					case 0:
						cell.setCellValue(n[i].getYear());
						break;
					case 1:
						cell.setCellValue(n[i].getMonth());
						break;
					case 2:
						cell.setCellValue(n[i].getDay());
						break;
					case 3:
						cell.setCellValue(n[i].getThing());
						break;
					case 4:
						cell.setCellValue(n[i].getCost());
						;
						break;
					case 5:
						cell.setCellValue(n[i].getComment());
						break;
					case 6:
						cell.setCellValue(String.valueOf(n[i].getImagePic()));
						break;
					}
				}
			}
			try {
				FileOutputStream output = new FileOutputStream(fileName);
				hw.write(output);
				output.flush();
				JOptionPane.showMessageDialog(null, "Excel�������");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				ObjectOutputStream oops = new ObjectOutputStream(new FileOutputStream(file));
				for (int i = 0; i < n.length; i++) {
					if (n[i] == null)
						break;
					oops.writeObject(n[i]);
				}
				oops.close();
				JOptionPane.showMessageDialog(null, "Txt�������");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * �����ļ��б��������
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	static Note[] inputObject(String fileName) {
		File file = new File(fileName);
		int listNumber = 0;
		/*if (!file.exists()) {
			JOptionPane.showMessageDialog(null, "�ļ�������");
			System.exit(0);
		}
		if (file.length() == 0) {
			JOptionPane.showMessageDialog(null, "�ļ�Ϊ��");
			System.exit(0);
		}*/
		Note[] n = new Note[100];
		if (getFileType(fileName).equals("xls")) {
			try {
				FileInputStream fis = new FileInputStream(file);
				// ��ָ�����ļ�����������Excel����Workbook����
				Workbook wb = new HSSFWorkbook(fis);
				// ��ȡExcel�ĵ��е�һ����
				Sheet sht0 = wb.getSheetAt(0);
				// ��Sheet�е�ÿһ�н��е���
				for (Row r : sht0) {
					// �ӵڶ��п�ʼ
					if (r.getRowNum() <= 1)
						continue;
					// ����ʵ����
					Note temp = new Note();
					temp.setYear((int) r.getCell(0).getNumericCellValue());
					temp.setMonth((int) r.getCell(1).getNumericCellValue());
					temp.setDay((int) r.getCell(2).getNumericCellValue());
					temp.setThing(r.getCell(3).getStringCellValue());
					temp.setCost(r.getCell(4).getStringCellValue());
					temp.setComment(r.getCell(5).getStringCellValue());
					temp.setImagePic(new File(r.getCell(6).getStringCellValue()));
					if (temp == null)
						break;
					n[listNumber++] = temp;
					fis.close();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "��Excel�ж�ȡ���");
		} else {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
				while (true) {
					n[listNumber++] = (Note) ois.readObject();
					/*
					 * n[listNumber] = (Note) ois.readObject();
					 * if(n[listNumber]==null) break;
					 */
				}
			} catch (EOFException e) {
				JOptionPane.showMessageDialog(null, "��TXT�ж�ȡ���");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return n;
	}

	/**
	 * �����ļ��б��������ĸ���
	 * 
	 * @param fileName
	 * @return
	 */
	static int numberObject(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null, "�ļ�������");
		}
		Note[] n = new Note[100];
		int noteNumber = 0;
		if (getFileType(fileName).equals("xls")) {
			try {
				FileInputStream fis = new FileInputStream(fileName);
				Workbook wb = new HSSFWorkbook(fis);
				Sheet sheet0 = wb.getSheetAt(0);
				noteNumber = sheet0.getPhysicalNumberOfRows() - 2;
				fis.close();
				wb.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				while (true) {
					n[noteNumber++] = (Note) ois.readObject();
				}
			} catch (EOFException e) {
				noteNumber--;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return noteNumber;
	}

	/**
	 * ����ĸ���
	 */
	public static Note[] noteUpd(Note[] oldNote, JTable table, File picFile) {
		Note[] list = new Note[100];
		int listNumber = 0;
		int row = table.getRowCount();
		int line = table.getSelectedRow();
		for (int i = 0; i < row; i++) {
			Note note = new Note();
			note.setYear(Integer.valueOf(table.getValueAt(i, 0).toString()));
			note.setMonth(Integer.valueOf(table.getValueAt(i, 1).toString()));
			note.setDay(Integer.valueOf(table.getValueAt(i, 2).toString()));
			note.setThing(table.getValueAt(i, 3).toString());
			note.setCost(table.getValueAt(i, 4).toString());
			note.setComment(table.getValueAt(i, 5).toString());
			if (i == line) {
				note.setImagePic(picFile);
			}else{
				if(oldNote[i]==null){
					note.setImagePic(null);
				}else
				note.setImagePic(oldNote[i].getImagePic());
			}
				
			list[listNumber] = note;
			listNumber++;
		}
		Note[] realList=new Note[listNumber];
		for(int i=0;i<listNumber;i++){
			realList[i]=list[i];
		}
		return realList;
	}

	/**
	 * ���ݱ�񷵻��������
	 */
	public static int tableUpd(JTable table) {
		return table.getRowCount();
	}

	/**
	 * �������
	 */
	static double balance(Note[] n) {
		int cost = 0;
		for (int i = 0; i < n.length; i++) {
			if (n[i] == null)
				break;
			else {
				cost += n[i].getRealCost();
			}
		}
		return cost;
	}

	/**
	 * ������֧��
	 */
	static double costAll(Note[] n) {
		int cost = 0;
		for (int i = 0; i < n.length; i++) {
			if (n[i] == null)
				break;
			else if (n[i].getRealCost() < 0) {
				cost += n[i].getRealCost();
			}
		}
		return cost;
	}

	/**
	 * ����������
	 */
	static double inAll(Note[] n) {
		int cost = 0;
		for (int i = 0; i < n.length; i++) {
			if (n[i] == null)
				break;
			else if (n[i].getRealCost() > 0) {
				cost += n[i].getRealCost();
			}
		}
		return cost;
	}
	/**
	 * �����õ�ControlDemo���õ�������
	 */
	/*public static void main(String[] args) throws Exception {
		Note[] list = new Note[100];
		list[0] = new Note(2018, 3, 2, "���·�", "-300", "�ÿ�");
		list[1] = new Note(2018, 1, 8, "����ʳ", "-200", "�о����óԵ�");
		list[2] = new Note(2018, 6, 9, "��Ǯ", "+100", "������");
		Note[] copy = new Note[100];
		outputObject(list, "D:\\�γ����img\\workbook.xls");*/
		/*copy = inputObject("d:\\workbook.xls");
		for (int i = 0; i < copy.length; i++) {
			if (copy[i] != null) {
				System.out.println(copy[i].toString());
				System.out.println(copy[i].getImagePic());
			}
		}
		System.out.println(numberObject("d:\\workbook.xls"));
		outputObject(list, "D:\\123\\DataInputStream.txt");
		copy = inputObject("D:\\123\\DataInputStream.txt");
		for (int i = 0; i < copy.length; i++) {
			if (copy[i] != null) {
				System.out.println(copy[i].toString());
			}
		}
		System.out.println(numberObject("D:\\123\\DataInputStream.txt"));*/
//	}
}