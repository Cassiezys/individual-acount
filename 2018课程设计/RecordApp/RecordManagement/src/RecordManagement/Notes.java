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
	 * ���췽������ʼ�����ļ��б�������Note����
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
	 * ���췽���� ��ʼ����ǰ��Note����
	 * @param n
	 * @param table
	 */
	public Notes(Note[] n,JTable table){
		this.list=n;
		listNumber=ControlDemo.tableUpd(table);
		balance=0;
	}
	/**
	 * ����ܵ��еĿ�ͷ
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
	 * ���ÿ�е�����
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
	 * ����е�����
	 * 
	 * @return
	 */
	public Vector getHead() {
		Vector columnHeads = new Vector();
		columnHeads.addElement("��");
		columnHeads.addElement("��");
		columnHeads.addElement("��");
		columnHeads.addElement("��;");
		columnHeads.addElement("����/֧��");
		columnHeads.addElement("��ע");
		columnHeads.addElement("���");
		// columnHeads.addElement("ͼƬ");
		return columnHeads;
	}
	
}
