package RecordManagement;
import java.util.Vector;

import javax.swing.*;

public class Notes {
	private static Note[] list;
	private String fileName;
	private int listNumber;
	private Vector rowData = null;
	private Vector columnNames = null;
	private double balance = 0;
	/**
	 * 构造方法：初始化从文件中保存来的Note数组
	 * @param n
	 * @param fileName
	 */
	public Notes(Note[] n,String fileName){
		this.list=n;
		this.fileName=fileName;
		listNumber=ControlDemo.numberObject(fileName);
		balance=0;
	}
	/**
	 * 构造方法： 初始化当前的Note数组
	 * @param n
	 * @param table
	 */
	public Notes(Note[] n,JTable table){
		this.list=n;
		listNumber=ControlDemo.tableUpd(table);
		balance=0;
	}
	/**
	 * 获得总的行的开头
	 * 
	 * @return
	 */
	public Vector getRows() {
		Vector rows = new Vector();
		for (int i = 0; i < listNumber; i++) {
			rows.addElement(getNextRow(i));
		}
		// rows.addElement("pic");
		return rows;
	}

	/**
	 * 获得每行的数据
	 * 
	 * @param j
	 * @return
	 */
	private Vector getNextRow(int j) {
		Vector currentRow = new Vector();
		currentRow.addElement(list[j].getYear());
		currentRow.addElement(list[j].getMonth());
		currentRow.addElement(list[j].getDay());
		currentRow.addElement(list[j].getThing());
		currentRow.addElement(list[j].getCost());
		currentRow.addElement(list[j].getComment());
		balance += list[j].getRealCost();
		currentRow.addElement(balance);
		// currentRow.addElement(list[j].getPic());
		return currentRow;
	}

	/**
	 * 获得列的属性
	 * 
	 * @return
	 */
	public Vector getHead() {
		Vector columnHeads = new Vector();
		columnHeads.addElement("年");
		columnHeads.addElement("月");
		columnHeads.addElement("日");
		columnHeads.addElement("用途");
		columnHeads.addElement("收入/支出");
		columnHeads.addElement("备注");
		columnHeads.addElement("余额");
		// columnHeads.addElement("图片");
		return columnHeads;
	}
	
}
