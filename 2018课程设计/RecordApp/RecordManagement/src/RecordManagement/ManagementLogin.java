package RecordManagement;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

public class ManagementLogin extends JPanel implements ActionListener {
	private JPanel messPanel, downPanel, tablePanel;// ��¼��Ϣ�ͱ����Ϣ
	private JLabel label;
	private CardLayout card;
	private JButton btnIn, btnReset, btnDel, btnModify;// ��¼�����ã��޸İ�ť
	private JTextField userText, passwordText;
	private JLabel promptLabel;// ��ʾ��Ϣ
	private Connection conn;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * ���췽������ʼ������Ա����
	 */
	public ManagementLogin(Connection c) {
		conn = c;
		promptLabel = new JLabel("��ӭ����Ա��¼��", JLabel.LEFT);
		promptLabel.setFont(new Font("����", Font.BOLD, 13));
		promptLabel.setForeground(Color.RED);
		/*
		 * promptLabel.setOpaque(true);//��͸�� promptLabel.setBackground(new
		 * Color(216,224,231));//���ñ�����ɫ
		 */
		initMessPanel();// ��ʼ����¼����
		initTablePanel();// ��ʼ��������
		initDownPanel();// ��ʼ���·�card����
		JSplitPane splitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT, messPanel, downPanel);
		add(promptLabel, BorderLayout.NORTH);
		add(splitH, BorderLayout.CENTER);
	}

	/**
	 * ��ʼ����½����
	 */
	public void initMessPanel() {
		/**
		 * Box ����Դ�������Ӱ�첼�ֵĲ��ɼ���� ��ˮ�������ܽ�����ֿ�
		 */
		JLabel lblUser = new JLabel("�û�����", JLabel.CENTER);
		userText = new JTextField(10);
		Box userBox = Box.createHorizontalBox();// ���ˮƽBox
		userBox.add(lblUser);
		userBox.add(userText);
		JLabel lblPassword = new JLabel(" ���룺", JLabel.CENTER);
		passwordText = new JTextField(10);
		Box passwordBox = Box.createHorizontalBox();// ���ˮƽBox
		passwordBox.add(lblPassword);
		passwordBox.add(passwordText);
		btnIn = new JButton("��¼");
		btnReset = new JButton("����");
		Box buttonBox = Box.createHorizontalBox();// ���ˮƽBox
		buttonBox.add(btnIn);
		buttonBox.add(btnReset);
		Box boxH = Box.createVerticalBox();
		boxH.add(userBox);
		boxH.add(passwordBox);
		boxH.add(buttonBox);
		boxH.add(Box.createVerticalGlue());// ��Ӵ�ֱ��ˮ
		btnIn.addActionListener(this);
		btnReset.addActionListener(this);
		messPanel = new JPanel();
		messPanel.add(boxH);
	}

	/**
	 * ��ʼ���·��Ľ���
	 */
	public void initDownPanel() {
		card = new CardLayout();
		downPanel = new JPanel();
		downPanel.setLayout(card);
		downPanel.add("δ��¼����", label);
		downPanel.add("�ѵ�¼����", tablePanel);
	}
	/**
	 * ������ݵĳ�ʼ��
	 */
	public void initTabelContent(){
		Vector rowData = UserUtil.getRows();
		Vector columnNames = UserUtil.getHead();
		tableModel.setDataVector(rowData, columnNames);
		table.setModel(tableModel);
	}
	/**
	 * ��ʼ��������
	 */
	public void initTablePanel() {
		label = new JLabel("   ", JLabel.CENTER);
		label.setIcon(new ImageIcon("design.jpg"));
		label.setFont(new Font("����", Font.BOLD, 36));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setForeground(Color.green);
		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		initTabelContent();
		btnDel = new JButton("ɾ��");
		btnModify = new JButton("�޸�");
		Box buttonBox = Box.createHorizontalBox();// ���ˮƽBox
		buttonBox.add(btnDel);
		buttonBox.add(btnModify);
		Box boxH = Box.createVerticalBox();
		boxH.add(new JScrollPane(table));
		boxH.add(buttonBox);
		boxH.add(Box.createVerticalGlue());// ��Ӵ�ֱ��ˮ
		btnDel.addActionListener(this);
		btnModify.addActionListener(this);
		tablePanel = new JPanel();
		tablePanel.add(boxH);
	}

	/**
	 * �����¼�����ã�ɾ�������水ťʱִ�еĲ���
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnIn) {
			String name = userText.getText().trim();
			String password = passwordText.getText();
			if (name.equals("") || password.equals("")) {
				JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��");
			} else {
				String sql = "select * from managers where id='" + name + "' and password='" + password + "'";
				try {
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					if (rs.next()) {
						if (rs.getString(1).equals(name) && rs.getString(2).equals(password)) {
							System.out.println("�ɹ���¼");
							/**
							 * ��¼�ɹ�����,�·���������ʾ
							 * 
							 */
							card.show(downPanel, "�ѵ�¼����");
						}
					} else {
						JOptionPane.showMessageDialog(null, "�û������������");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == btnReset) {
			userText.setText("");
			passwordText.setText("");
		} else if (e.getSource() == btnDel) {
			int k = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ�����ݿ���ɾ���˼�¼", "ɾ��", JOptionPane.YES_NO_CANCEL_OPTION,
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
			 * �޸ģ� 
			 */
			int column=table.getSelectedColumn();
			int row=table.getSelectedRow();
			String id=table.getValueAt(row, 0).toString();
			String code=table.getValueAt(row, 1).toString();
			String email=table.getValueAt(row, 2).toString();
			String tel=table.getValueAt(row, 3).toString();
			String file=table.getValueAt(row, 4).toString();
			switch(column){
			case 0:JOptionPane.showMessageDialog(null,"�û����޷�����");break;
			case 1:String codeStr=JOptionPane.showInputDialog("������������");
				if(codeStr==null||codeStr.equals("")){
					JOptionPane.showMessageDialog(null, "�޸�ʧ��");
				}else
					code=codeStr;break;
			case 2:String emailStr=JOptionPane.showInputDialog("������������",email);
			if(emailStr==null||emailStr.equals("")){
				JOptionPane.showMessageDialog(null, "�޸�ʧ��");
			}else
					email=emailStr;break;
			case 3:String telStr=JOptionPane.showInputDialog("�������µ绰����",tel);
			if(telStr==null||telStr.equals("")){
				JOptionPane.showMessageDialog(null, "�޸�ʧ��");
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
	 * ����ʾ����Ϣ���
	 */
	public void clearMessage() {
		userText.setText(null);
		passwordText.setText(null);
		card.show(downPanel, "δ��¼����");
	}
}
