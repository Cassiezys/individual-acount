package RecordManagement;

import java.io.*;

import javax.swing.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;

public class ControlDemo {
	/**
	 * 判断文件类型
	 */
	static String getFileType(String fileName) {
		// 以“.”分割 必须写成String.split("\\.");的方式
		String[] strName = fileName.split("\\.");
		int index = strName.length - 1;
		return strName[index];
	}

	/**
	 * 将数组存到文件中
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
			HSSFSheet sheet = hw.createSheet("我的记录单");
			HSSFRow row1 = sheet.createRow(0);
			row1.createCell(0).setCellValue("个人记账软件保存记录");
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
			HSSFRow row2 = sheet.createRow(1);
			row2.createCell(0).setCellValue("年");
			row2.createCell(1).setCellValue("月");
			row2.createCell(2).setCellValue("日");
			row2.createCell(3).setCellValue("事情");
			row2.createCell(4).setCellValue("收入/支出");
			row2.createCell(5).setCellValue("备注");
			row2.createCell(6).setCellValue("图片地址");
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
				JOptionPane.showMessageDialog(null, "Excel保存完毕");
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
				JOptionPane.showMessageDialog(null, "Txt保存完毕");
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
	 * 返回文件中保存的数组
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	static Note[] inputObject(String fileName) {
		File file = new File(fileName);
		int listNumber = 0;
		/*if (!file.exists()) {
			JOptionPane.showMessageDialog(null, "文件不存在");
			System.exit(0);
		}
		if (file.length() == 0) {
			JOptionPane.showMessageDialog(null, "文件为空");
			System.exit(0);
		}*/
		Note[] n = new Note[100];
		if (getFileType(fileName).equals("xls")) {
			try {
				FileInputStream fis = new FileInputStream(file);
				// 从指定的文件输入流导入Excel产生Workbook对象
				Workbook wb = new HSSFWorkbook(fis);
				// 获取Excel文档中第一个表单
				Sheet sht0 = wb.getSheetAt(0);
				// 对Sheet中的每一行进行迭代
				for (Row r : sht0) {
					// 从第二行开始
					if (r.getRowNum() <= 1)
						continue;
					// 创建实体类
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
			JOptionPane.showMessageDialog(null, "从Excel中读取完毕");
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
				JOptionPane.showMessageDialog(null, "从TXT中读取完毕");
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
	 * 返回文件中保存的数组的个数
	 * 
	 * @param fileName
	 * @return
	 */
	static int numberObject(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null, "文件不存在");
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
	 * 数组的更新
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
	 * 根据表格返回数组个数
	 */
	public static int tableUpd(JTable table) {
		return table.getRowCount();
	}

	/**
	 * 返回余额
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
	 * 返回总支出
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
	 * 返回总收入
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
	 * 测试用的ControlDemo所用的主函数
	 */
	/*public static void main(String[] args) throws Exception {
		Note[] list = new Note[100];
		list[0] = new Note(2018, 3, 2, "买衣服", "-300", "好看");
		list[1] = new Note(2018, 1, 8, "买零食", "-200", "感觉蛮好吃的");
		list[2] = new Note(2018, 6, 9, "还钱", "+100", "发财啦");
		Note[] copy = new Note[100];
		outputObject(list, "D:\\课程设计img\\workbook.xls");*/
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