package RecordManagement;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

public class RecordWindow extends JDialog implements ActionListener {
	private String fileName;
	private Note[] list;
	private NotePicture notePicture;
	private File picFile;
	private JButton btnMe, btnRecords, btnWelcome, btnAdd, btnModify, btnDel, btnSave, btnShow, picButton, btnSort;
	private JPanel ButtonPanel;// �Һͼ�¼�Լ���ҳ��ť������
	private JPanel bPanel, picPanel;// װ��ť������������ͼƬ������
	private JPanel DownPanel, myPanel, tablePanel, welcomePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private Notes notes;
	private static Vector rowData;
	private static Vector columnNames;
	private CardLayout card;
	private String name, email, tel;

	/**
	 * ��ʼ��RecordWindow
	 * 
	 * @param frame
	 *            ָ��������
	 * @param name
	 *            email tel ��¼�˻�����Ϣ
	 * @param fileName
	 *            �˻���������ļ���
	 */
	public RecordWindow(JFrame frame, String name, String email, String tel, String fileName) {
		super(frame);// 1. ָ��������
		this.name = name;
		this.email = email;
		this.tel = tel;
		this.fileName = fileName;
		notePicture = new NotePicture();
		list = ControlDemo.inputObject(fileName);
		ControlDemo.numberObject(fileName);
		notes = new Notes(list, fileName);
		initDialog();

		this.setBounds(100, 50, 500, 680);
		this.add(ButtonPanel, BorderLayout.NORTH);
		this.add(DownPanel, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				int n = JOptionPane.showConfirmDialog(frame, "��Ҫ������δ�������Ϣ��?", "ȷ�϶Ի���", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					list = ControlDemo.noteUpd(list, table, picFile);
					ControlDemo.outputObject(list, fileName);
				}
			}
		});

		this.setModal(true);// 2. ģ̬����
		this.setVisible(true);// 3. �����ʾJDialog setVisible(true);

	}

	/**
	 * ��ʼ��������
	 */
	private void initDialog() {
		initButton();
		myPanel = new JPanel();
		welcomePanel = new JPanel();
		JLabel label2;
		label2 = new JLabel();
		label2.setIcon(new ImageIcon("design.jpg"));
		JLabel label3;
		label3 = new JLabel();
		label3.setIcon(new ImageIcon("design2.jpg"));
		JLabel infoLabel;
		infoLabel = new JLabel("<html><br><span style=\"color:pink\">��¼�ֲ�</span>QAQ��<br>"
				+ "���<span style=\"color:blue\">��һ��</span>��Ӽ�¼,���<span style=\"color:blue\">���</span>�������<br>"
				+ "����ñ�ע��������û�<span style=\"color:blue\">�س�</span>  �d(�RO�Q)���~ )</html>", JLabel.CENTER);
		infoLabel.setFont(new Font("��Բ", Font.BOLD, 19));
		infoLabel.setForeground(Color.magenta);
		infoLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		Box boxH = Box.createVerticalBox();
		boxH.add(label2);
		boxH.add(label3);
		boxH.add(infoLabel);
		welcomePanel.add(boxH);
		initTable();
		initMyPanel();
		initDownPanel();
	}

	/**
	 * ��ʼ����Ƭ���ֵĽ���
	 */
	private void initpicPanel() {
		// TODO Auto-generated method stub
		JLabel picLabel = new JLabel("ͼƬ��", JLabel.LEFT);
		picButton = new JButton("<html>ѡ��<br>��Ƭ<br>QAQ</html>");
		picButton.addActionListener(this);
		picPanel = new JPanel();
		picPanel.setLayout(new BorderLayout());
		// picPanel.add(picLabel,BorderLayout.WEST);
		picPanel.add(notePicture, BorderLayout.CENTER);
		picPanel.add(picButton, BorderLayout.EAST);
	}

	/**
	 * ��ʼ�����ҡ��Ľ���
	 */
	private void initMyPanel() {
		// TODO Auto-generated method stub
		// System.out.println("����initMyPanel��������");
		JLabel[] label = new JLabel[18];
		label[0] = new JLabel("��");
		label[1] = new JLabel(String.valueOf(ControlDemo.balance(list)), JLabel.CENTER);
		// label[1].repaint();
		// System.out.println(ControlDemo.balance(list));
		label[2] = new JLabel("Ԫ");
		Box balanceBox = Box.createHorizontalBox();
		balanceBox.add(label[0]);
		balanceBox.add(label[1]);
		balanceBox.add(label[2]);
		// balanceBox.repaint();
		label[3] = new JLabel("��֧����");
		label[4] = new JLabel(String.valueOf(ControlDemo.costAll(list)), JLabel.CENTER);
		label[5] = new JLabel("Ԫ");
		Box costBox = Box.createHorizontalBox();
		costBox.add(label[3]);
		costBox.add(label[4]);
		costBox.add(label[5]);
		label[6] = new JLabel("�����룺");
		label[7] = new JLabel(String.valueOf(ControlDemo.inAll(list)), JLabel.CENTER);
		label[8] = new JLabel("Ԫ");
		Box inBox = Box.createHorizontalBox();
		inBox.add(label[6]);
		inBox.add(label[7]);
		inBox.add(label[8]);
		label[9] = new JLabel("������");
		label[10] = new JLabel(this.name, JLabel.CENTER);
		Box nameBox = Box.createHorizontalBox();
		nameBox.add(label[9]);
		nameBox.add(label[10]);
		label[11] = new JLabel("���䣺");
		label[12] = new JLabel(this.email, JLabel.CENTER);
		Box emailBox = Box.createHorizontalBox();
		emailBox.add(label[11]);
		emailBox.add(label[12]);
		label[13] = new JLabel("�绰���룺");
		label[14] = new JLabel(this.tel, JLabel.CENTER);
		Box telBox = Box.createHorizontalBox();
		telBox.add(label[13]);
		telBox.add(label[14]);
		for (int i = 0; i < 15; i++) {
			label[i].setFont(new Font("��Բ", Font.BOLD, 28));
			label[i].setHorizontalTextPosition(SwingConstants.CENTER);
			label[i].setForeground(Color.magenta);
		}
		label[15] = new JLabel("", JLabel.CENTER);
		label[15].setIcon(new ImageIcon("label15.jpg"));
		label[15].setHorizontalTextPosition(SwingConstants.CENTER);
		Box boxH = Box.createVerticalBox();
		boxH.add(nameBox);
		boxH.add(emailBox);
		boxH.add(telBox);
		boxH.add(inBox);
		boxH.add(costBox);
		boxH.add(balanceBox);
		boxH.add(Box.createVerticalGlue());// ��Ӵ�ֱ��ˮ
		boxH.repaint();
		JSplitPane splitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT, boxH, label[15]);
		// splitH.repaint();
		myPanel.add(splitH);
		// myPanel.repaint();
	}

	/**
	 * ��ʼ��card���ֽ���
	 */
	private void initDownPanel() {
		// TODO Auto-generated method stub
		card = new CardLayout();
		DownPanel = new JPanel();
		DownPanel.setLayout(card);
		DownPanel.add("�׽���", welcomePanel);
		DownPanel.add("�ҵĽ���", myPanel);
		DownPanel.add("�˱�", tablePanel);
	}

	/**
	 * ��ʼ����ť����
	 */
	private void initButton() {
		// TODO Auto-generated method stub
		btnMe = new JButton("��");
		btnMe.addActionListener(this);
		btnRecords = new JButton("��¼");
		btnRecords.addActionListener(this);
		btnWelcome = new JButton("��ҳ");
		btnWelcome.addActionListener(this);
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(btnWelcome);
		buttonBox.add(btnRecords);
		buttonBox.add(btnMe);
		ButtonPanel = new JPanel();
		ButtonPanel.setBackground(new Color(216, 224, 231));
		ButtonPanel.add(buttonBox);
	}

	/**
	 * ���ĸ���
	 */
	private void calculateTotal() {
		rowData = notes.getRows();
		columnNames = notes.getHead();
		tableModel.setDataVector(rowData, columnNames);
		table.setModel(tableModel);
	}

	/**
	 * ��ʼ��������Ĳ���
	 */
	private void initTable() {
		// TODO Auto-generated method stub
		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		notes = new Notes(list, fileName);
		calculateTotal();
		btnAdd = new JButton("<html><center>��</center><br>---<br><br>��</html>");
		btnAdd.addActionListener(this);
		btnModify = new JButton("���");
		btnModify.addActionListener(this);
		btnDel = new JButton("ɾ��");
		btnDel.addActionListener(this);
		btnSave = new JButton("����");
		btnSave.addActionListener(this);
		btnShow = new JButton("����");
		btnShow.addActionListener(this);
		btnSort = new JButton("����");
		btnSort.addActionListener(this);
		bPanel = new JPanel(new GridLayout(2, 2));
		bPanel.add(btnShow);
		bPanel.add(btnModify);
		bPanel.add(btnDel);
		bPanel.add(btnSave);
		JPanel sortPanel = new JPanel(new GridLayout(2, 1));
		sortPanel.add(bPanel);
		sortPanel.add(btnSort);
		Box buttonBox = Box.createHorizontalBox();// ���ˮƽBox
		buttonBox.add(btnAdd);
		buttonBox.add(sortPanel);
		buttonBox.add(Box.createHorizontalGlue());
		initpicPanel();
		new JPanel();
		Box bbBox = Box.createHorizontalBox();
		bbBox.add(buttonBox);
		bbBox.add(picPanel);
		/*
		 * Box pbBox=Box.createHorizontalBox(); pbBox.add(bbBox);
		 * pbBox.add(picPanel);
		 */
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, bbBox, picPanel);
		Box boxH = Box.createVerticalBox();
		boxH.add(new JScrollPane(table));
		boxH.add(split);
		boxH.add(Box.createVerticalGlue());// ��Ӵ�ֱ��ˮ
		tablePanel = new JPanel();
		tablePanel.add(boxH);
	}

	/**
	 * ���а�ť�������Ķ���
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnMe) {
			// initMyPanel();
			// DownPanel.add("�ҵĽ���",myPanel);
			card.show(DownPanel, "�ҵĽ���");
		} else if (e.getSource() == btnRecords) {
			card.show(DownPanel, "�˱�");
		} else if (e.getSource() == btnWelcome) {
			card.show(DownPanel, "�׽���");
		} else if (e.getSource() == btnAdd) {
			tableModel.addRow(new Vector());
			clearMessage();
		} else if (e.getSource() == btnSave) {
			list = ControlDemo.noteUpd(list, table, picFile);
			notes = new Notes(list, table);
			ControlDemo.outputObject(list, fileName);
			calculateTotal();
		} else if (e.getSource() == btnDel) {
			int k = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ���˼�¼��", "ɾ��", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (k == JOptionPane.YES_OPTION) {
				int row = table.getSelectedRow();
				if (row >= 0) {
					tableModel.removeRow(row);
					table.setModel(tableModel);
				}
				list = ControlDemo.noteUpd(list, table, picFile);
				/*
				 * ControlDemo.outputObject(list, fileName); notes=new
				 * Notes(list,fileName);-------------------------------------
				 * �Զ�����ɾ�����������
				 */
				notes = new Notes(list, table);
				calculateTotal();
			}
		} else if (e.getSource() == btnModify) {
			list = ControlDemo.noteUpd(list, table, picFile);
			notes = new Notes(list, table);
			calculateTotal();
			table.repaint();
		} else if (e.getSource() == picButton) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG&GIF Images", "jpg", "gif");
			jfc.setFileFilter(filter);
			int value = jfc.showOpenDialog(null);
			if (value == JFileChooser.APPROVE_OPTION) {
				picButton.setText("<html>�ٴ�<br>ѡ��<br>QAQ</html>");
				picFile = jfc.getSelectedFile();
				notePicture.setImage(picFile);
				notePicture.repaint();
			}
		} else if (e.getSource() == btnShow) {
			int row = table.getSelectedRow();
			picFile = list[row].getImagePic();
			notePicture.setImage(picFile);
			notePicture.repaint();
		} else if (e.getSource() == btnSort) {
			this.list = ControlDemo.noteUpd(list, table, picFile);// ���ڵ�׼ȷ��list
			Arrays.sort(this.list, new NoteComparator());
			notes = new Notes(list, table);
			calculateTotal();
			table.repaint();
		}
	}

	public void clearMessage() {
		picButton.setText("ѡ����Ƭ");
		picFile = null;
		notePicture.setImage(picFile);
		notePicture.repaint();
	}
	
	  public static void main(String[] args) { new RecordWindow(new JFrame(),
	  "zys", "457920700@qq.com", "15576870646", "d:\\workbook.xls"); }
	 

}
