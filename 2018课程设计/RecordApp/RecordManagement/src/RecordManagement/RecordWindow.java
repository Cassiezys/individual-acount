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
	private JPanel ButtonPanel;// 我和记录以及首页按钮的容器
	private JPanel bPanel, picPanel;// 装按钮的容器，详情图片的容器
	private JPanel DownPanel, myPanel, tablePanel, welcomePanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private Notes notes;
	private static Vector rowData;
	private static Vector columnNames;
	private CardLayout card;
	private String name, email, tel;

	/**
	 * 初始化RecordWindow
	 * 
	 * @param frame
	 *            指明父窗口
	 * @param name
	 *            email tel 登录账户的信息
	 * @param fileName
	 *            账户所保存的文件名
	 */
	public RecordWindow(JFrame frame, String name, String email, String tel, String fileName) {
		super(frame);// 1. 指明父窗口
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
				int n = JOptionPane.showConfirmDialog(frame, "需要保存暂未保存的信息吗?", "确认对话框", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					list = ControlDemo.noteUpd(list, table, picFile);
					ControlDemo.outputObject(list, fileName);
				}
			}
		});

		this.setModal(true);// 2. 模态窗口
		this.setVisible(true);// 3. 最后显示JDialog setVisible(true);

	}

	/**
	 * 初始化主界面
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
		infoLabel = new JLabel("<html><br><span style=\"color:pink\">记录手册</span>QAQ：<br>"
				+ "点击<span style=\"color:blue\">记一笔</span>添加记录,点击<span style=\"color:blue\">完成</span>计算余额<br>"
				+ "输入好备注后别忘记敲击<span style=\"color:blue\">回车</span>  ヾ(≧O≦)〃嗷~ )</html>", JLabel.CENTER);
		infoLabel.setFont(new Font("幼圆", Font.BOLD, 19));
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
	 * 初始化照片部分的界面
	 */
	private void initpicPanel() {
		// TODO Auto-generated method stub
		JLabel picLabel = new JLabel("图片：", JLabel.LEFT);
		picButton = new JButton("<html>选择<br>照片<br>QAQ</html>");
		picButton.addActionListener(this);
		picPanel = new JPanel();
		picPanel.setLayout(new BorderLayout());
		// picPanel.add(picLabel,BorderLayout.WEST);
		picPanel.add(notePicture, BorderLayout.CENTER);
		picPanel.add(picButton, BorderLayout.EAST);
	}

	/**
	 * 初始化“我”的界面
	 */
	private void initMyPanel() {
		// TODO Auto-generated method stub
		// System.out.println("进入initMyPanel（）方法");
		JLabel[] label = new JLabel[18];
		label[0] = new JLabel("余额：");
		label[1] = new JLabel(String.valueOf(ControlDemo.balance(list)), JLabel.CENTER);
		// label[1].repaint();
		// System.out.println(ControlDemo.balance(list));
		label[2] = new JLabel("元");
		Box balanceBox = Box.createHorizontalBox();
		balanceBox.add(label[0]);
		balanceBox.add(label[1]);
		balanceBox.add(label[2]);
		// balanceBox.repaint();
		label[3] = new JLabel("总支出：");
		label[4] = new JLabel(String.valueOf(ControlDemo.costAll(list)), JLabel.CENTER);
		label[5] = new JLabel("元");
		Box costBox = Box.createHorizontalBox();
		costBox.add(label[3]);
		costBox.add(label[4]);
		costBox.add(label[5]);
		label[6] = new JLabel("总收入：");
		label[7] = new JLabel(String.valueOf(ControlDemo.inAll(list)), JLabel.CENTER);
		label[8] = new JLabel("元");
		Box inBox = Box.createHorizontalBox();
		inBox.add(label[6]);
		inBox.add(label[7]);
		inBox.add(label[8]);
		label[9] = new JLabel("姓名：");
		label[10] = new JLabel(this.name, JLabel.CENTER);
		Box nameBox = Box.createHorizontalBox();
		nameBox.add(label[9]);
		nameBox.add(label[10]);
		label[11] = new JLabel("邮箱：");
		label[12] = new JLabel(this.email, JLabel.CENTER);
		Box emailBox = Box.createHorizontalBox();
		emailBox.add(label[11]);
		emailBox.add(label[12]);
		label[13] = new JLabel("电话号码：");
		label[14] = new JLabel(this.tel, JLabel.CENTER);
		Box telBox = Box.createHorizontalBox();
		telBox.add(label[13]);
		telBox.add(label[14]);
		for (int i = 0; i < 15; i++) {
			label[i].setFont(new Font("幼圆", Font.BOLD, 28));
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
		boxH.add(Box.createVerticalGlue());// 添加垂直胶水
		boxH.repaint();
		JSplitPane splitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT, boxH, label[15]);
		// splitH.repaint();
		myPanel.add(splitH);
		// myPanel.repaint();
	}

	/**
	 * 初始化card布局界面
	 */
	private void initDownPanel() {
		// TODO Auto-generated method stub
		card = new CardLayout();
		DownPanel = new JPanel();
		DownPanel.setLayout(card);
		DownPanel.add("首界面", welcomePanel);
		DownPanel.add("我的界面", myPanel);
		DownPanel.add("账本", tablePanel);
	}

	/**
	 * 初始化按钮界面
	 */
	private void initButton() {
		// TODO Auto-generated method stub
		btnMe = new JButton("我");
		btnMe.addActionListener(this);
		btnRecords = new JButton("记录");
		btnRecords.addActionListener(this);
		btnWelcome = new JButton("首页");
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
	 * 表格的更新
	 */
	private void calculateTotal() {
		rowData = notes.getRows();
		columnNames = notes.getHead();
		tableModel.setDataVector(rowData, columnNames);
		table.setModel(tableModel);
	}

	/**
	 * 初始化表格界面的布局
	 */
	private void initTable() {
		// TODO Auto-generated method stub
		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		notes = new Notes(list, fileName);
		calculateTotal();
		btnAdd = new JButton("<html><center>记</center><br>---<br><br>笔</html>");
		btnAdd.addActionListener(this);
		btnModify = new JButton("完成");
		btnModify.addActionListener(this);
		btnDel = new JButton("删除");
		btnDel.addActionListener(this);
		btnSave = new JButton("保存");
		btnSave.addActionListener(this);
		btnShow = new JButton("详情");
		btnShow.addActionListener(this);
		btnSort = new JButton("排序");
		btnSort.addActionListener(this);
		bPanel = new JPanel(new GridLayout(2, 2));
		bPanel.add(btnShow);
		bPanel.add(btnModify);
		bPanel.add(btnDel);
		bPanel.add(btnSave);
		JPanel sortPanel = new JPanel(new GridLayout(2, 1));
		sortPanel.add(bPanel);
		sortPanel.add(btnSort);
		Box buttonBox = Box.createHorizontalBox();// 添加水平Box
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
		boxH.add(Box.createVerticalGlue());// 添加垂直胶水
		tablePanel = new JPanel();
		tablePanel.add(boxH);
	}

	/**
	 * 所有按钮所产生的动作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnMe) {
			// initMyPanel();
			// DownPanel.add("我的界面",myPanel);
			card.show(DownPanel, "我的界面");
		} else if (e.getSource() == btnRecords) {
			card.show(DownPanel, "账本");
		} else if (e.getSource() == btnWelcome) {
			card.show(DownPanel, "首界面");
		} else if (e.getSource() == btnAdd) {
			tableModel.addRow(new Vector());
			clearMessage();
		} else if (e.getSource() == btnSave) {
			list = ControlDemo.noteUpd(list, table, picFile);
			notes = new Notes(list, table);
			ControlDemo.outputObject(list, fileName);
			calculateTotal();
		} else if (e.getSource() == btnDel) {
			int k = JOptionPane.showConfirmDialog(null, "确定要删除此记录吗？", "删除", JOptionPane.YES_NO_CANCEL_OPTION,
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
				 * 自动保存删除操作的语句
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
				picButton.setText("<html>再次<br>选择<br>QAQ</html>");
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
			this.list = ControlDemo.noteUpd(list, table, picFile);// 现在的准确的list
			Arrays.sort(this.list, new NoteComparator());
			notes = new Notes(list, table);
			calculateTotal();
			table.repaint();
		}
	}

	public void clearMessage() {
		picButton.setText("选择照片");
		picFile = null;
		notePicture.setImage(picFile);
		notePicture.repaint();
	}
	
	  public static void main(String[] args) { new RecordWindow(new JFrame(),
	  "zys", "457920700@qq.com", "15576870646", "d:\\workbook.xls"); }
	 

}
