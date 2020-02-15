package RecordManagement;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

public class ManagementLogin extends JPanel implements ActionListener {
	private JPanel messPanel, downPanel, tablePanel;// 登录信息和表格信息
	private JLabel label;
	private CardLayout card;
	private JButton btnIn, btnReset, btnDel, btnModify;// 登录，重置，修改按钮
	private JTextField userText, passwordText;
	private JLabel promptLabel;// 提示信息
	private Connection conn;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * 构造方法：初始化管理员界面
	 */
	public ManagementLogin(Connection c) {
		conn = c;
		promptLabel = new JLabel("欢迎管理员登录：", JLabel.LEFT);
		promptLabel.setFont(new Font("宋体", Font.BOLD, 13));
		promptLabel.setForeground(Color.RED);
		/*
		 * promptLabel.setOpaque(true);//不透明 promptLabel.setBackground(new
		 * Color(216,224,231));//设置背景颜色
		 */
		initMessPanel();// 初始化登录界面
		initTablePanel();// 初始化表格界面
		initDownPanel();// 初始化下方card界面
		JSplitPane splitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT, messPanel, downPanel);
		add(promptLabel, BorderLayout.NORTH);
		add(splitH, BorderLayout.CENTER);
	}

	/**
	 * 初始化登陆界面
	 */
	public void initMessPanel() {
		/**
		 * Box 类可以创建几种影响布局的不可见组件 胶水；经可能将组件分开
		 */
		JLabel lblUser = new JLabel("用户名：", JLabel.CENTER);
		userText = new JTextField(10);
		Box userBox = Box.createHorizontalBox();// 添加水平Box
		userBox.add(lblUser);
		userBox.add(userText);
		JLabel lblPassword = new JLabel(" 密码：", JLabel.CENTER);
		passwordText = new JTextField(10);
		Box passwordBox = Box.createHorizontalBox();// 添加水平Box
		passwordBox.add(lblPassword);
		passwordBox.add(passwordText);
		btnIn = new JButton("登录");
		btnReset = new JButton("重置");
		Box buttonBox = Box.createHorizontalBox();// 添加水平Box
		buttonBox.add(btnIn);
		buttonBox.add(btnReset);
		Box boxH = Box.createVerticalBox();
		boxH.add(userBox);
		boxH.add(passwordBox);
		boxH.add(buttonBox);
		boxH.add(Box.createVerticalGlue());// 添加垂直胶水
		btnIn.addActionListener(this);
		btnReset.addActionListener(this);
		messPanel = new JPanel();
		messPanel.add(boxH);
	}

	/**
	 * 初始化下方的界面
	 */
	public void initDownPanel() {
		card = new CardLayout();
		downPanel = new JPanel();
		downPanel.setLayout(card);
		downPanel.add("未登录界面", label);
		downPanel.add("已登录界面", tablePanel);
	}
	/**
	 * 表格内容的初始化
	 */
	public void initTabelContent(){
		Vector rowData = UserUtil.getRows();
		Vector columnNames = UserUtil.getHead();
		tableModel.setDataVector(rowData, columnNames);
		table.setModel(tableModel);
	}
	/**
	 * 初始化表格界面
	 */
	public void initTablePanel() {
		label = new JLabel("   ", JLabel.CENTER);
		label.setIcon(new ImageIcon("design.jpg"));
		label.setFont(new Font("隶书", Font.BOLD, 36));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setForeground(Color.green);
		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		initTabelContent();
		btnDel = new JButton("删除");
		btnModify = new JButton("修改");
		Box buttonBox = Box.createHorizontalBox();// 添加水平Box
		buttonBox.add(btnDel);
		buttonBox.add(btnModify);
		Box boxH = Box.createVerticalBox();
		boxH.add(new JScrollPane(table));
		boxH.add(buttonBox);
		boxH.add(Box.createVerticalGlue());// 添加垂直胶水
		btnDel.addActionListener(this);
		btnModify.addActionListener(this);
		tablePanel = new JPanel();
		tablePanel.add(boxH);
	}

	/**
	 * 点击登录，重置，删除，保存按钮时执行的操作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnIn) {
			String name = userText.getText().trim();
			String password = passwordText.getText();
			if (name.equals("") || password.equals("")) {
				JOptionPane.showMessageDialog(null, "用户名和密码不能为空");
			} else {
				String sql = "select * from managers where id='" + name + "' and password='" + password + "'";
				try {
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					if (rs.next()) {
						if (rs.getString(1).equals(name) && rs.getString(2).equals(password)) {
							System.out.println("成功登录");
							/**
							 * 登录成功界面,下方表格可以显示
							 * 
							 */
							card.show(downPanel, "已登录界面");
						}
					} else {
						JOptionPane.showMessageDialog(null, "用户名或密码错误");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == btnReset) {
			userText.setText("");
			passwordText.setText("");
		} else if (e.getSource() == btnDel) {
			int k = JOptionPane.showConfirmDialog(null, "请确定要从数据库中删除此记录", "删除", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (k == JOptionPane.YES_OPTION) {
				int row = table.getSelectedRow();
				if (row >= 0) {
					String name = table.getValueAt(row, 0).toString();
					String password = table.getValueAt(row, 1).toString();
					tableModel.removeRow(row);
					try {
						String sql = "delete from users where id='" + name + "' and code='" + password + "'";
						PreparedStatement pstmt = conn.prepareStatement(sql);
					    pstmt.executeUpdate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		} else if (e.getSource() == btnModify) {
			/**
			 * 修改： 
			 */
			int column=table.getSelectedColumn();
			int row=table.getSelectedRow();
			String id=table.getValueAt(row, 0).toString();
			String code=table.getValueAt(row, 1).toString();
			String email=table.getValueAt(row, 2).toString();
			String tel=table.getValueAt(row, 3).toString();
			String file=table.getValueAt(row, 4).toString();
			switch(column){
			case 0:JOptionPane.showMessageDialog(null,"用户名无法更改");break;
			case 1:String codeStr=JOptionPane.showInputDialog("请输入新密码");
				if(codeStr==null||codeStr.equals("")){
					JOptionPane.showMessageDialog(null, "修改失败");
				}else
					code=codeStr;break;
			case 2:String emailStr=JOptionPane.showInputDialog("请输入新邮箱",email);
			if(emailStr==null||emailStr.equals("")){
				JOptionPane.showMessageDialog(null, "修改失败");
			}else
					email=emailStr;break;
			case 3:String telStr=JOptionPane.showInputDialog("请输入新电话号码",tel);
			if(telStr==null||telStr.equals("")){
				JOptionPane.showMessageDialog(null, "修改失败");
			}else
					tel=telStr;break;
			case 4:JFileChooser jfc=new JFileChooser();
			FileNameExtensionFilter filter=new FileNameExtensionFilter("Excel & xls & txt & obj","xls","txt","obj");
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setFileFilter(filter);
			int value=jfc.showSaveDialog(this);
			if(value==JFileChooser.APPROVE_OPTION){
				file=new String(jfc.getSelectedFile().getAbsolutePath());
			}break;
			}
			try {
				PreparedStatement pstmt=conn.prepareStatement("update users set code='"+code+"', email='"+email+"',tele='"+tel+"' where id='"+id+"'");
				pstmt.executeUpdate();
				initTabelContent();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
	/**
	 * 将显示的信息清空
	 */
	public void clearMessage() {
		userText.setText(null);
		passwordText.setText(null);
		card.show(downPanel, "未登录界面");
	}
}
