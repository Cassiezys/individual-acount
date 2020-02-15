package RecordManagement;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class UsersLogin extends JPanel implements ActionListener{
	private JFrame frame;
	private JPanel messPanel,downPanel,createPanel,panel;
	private JLabel promptLabel;//��ʾ��Ϣ
	private JButton btnIn,btnReset,btnCreate,btnNew,btnRe,btnFile;
	private JTextField userText,passwordText,createUserText,
	createPasswordText,createRepasswordText,emailText,telText,saveText;
	private Connection conn;
	private CardLayout card;
	/**
	 * ���췽������ʼ���û���¼����
	 * @param frame
	 * @param c
	 */
	public UsersLogin(JFrame frame,Connection c){
		this.frame=frame;
		conn=c;
		promptLabel=new JLabel("��ӭ�û���¼��",JLabel.LEFT);
		promptLabel.setFont(new Font("����",Font.BOLD,13));
		promptLabel.setForeground(Color.green);
		/*promptLabel.setOpaque(true);//��͸��
		promptLabel.setBackground(new Color(216,224,231));//���ñ�����ɫ */	
		initCreatePanel();//��������
		initMessPanel();//��¼����
		initDownPanel();//�·�card����
		JSplitPane splitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				messPanel, downPanel);
		add(promptLabel, BorderLayout.NORTH);
		add(splitH, BorderLayout.CENTER);
	}
	/**
	 * ��ʼ��ע�����
	 */
	public void initCreatePanel() {
		JLabel label = new JLabel("��ӭʹ�ø��˼������", JLabel.CENTER);
		JLabel label2= new JLabel();
		label2.setIcon(new ImageIcon("D:\\123\\user.jpg"));
		label.setFont(new Font("��Բ", Font.BOLD, 36));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setForeground(Color.PINK);
		Box labelBox=Box.createVerticalBox();
		labelBox.add(label2);
		labelBox.add(label);
		panel=new JPanel();
		panel.add(labelBox);
		JLabel lblUser=new JLabel("�û�����",JLabel.CENTER);
		createUserText=new JTextField(10);
		Box userBox=Box.createHorizontalBox();//���ˮƽBox
		userBox.add(lblUser);
		userBox.add(createUserText);
		JLabel lblPassword=new JLabel("���룺",JLabel.CENTER);
		createPasswordText=new JTextField(10);
		Box passwordBox=Box.createHorizontalBox();//���ˮƽBox
		passwordBox.add(lblPassword);
		passwordBox.add(createPasswordText);
		JLabel lblRepassword=new JLabel("ȷ�����룺",JLabel.CENTER);
		createRepasswordText=new JTextField(10);
		Box rePasswordBox=Box.createHorizontalBox();//���ˮƽBox
		rePasswordBox.add(lblRepassword);
		rePasswordBox.add(createRepasswordText);
		JLabel lblEmail=new JLabel("���䣺",JLabel.CENTER);
		emailText=new JTextField(10);
		Box emailBox=Box.createHorizontalBox();//���ˮƽBox
		emailBox.add(lblEmail);
		emailBox.add(emailText);
		JLabel lblTel=new JLabel("�绰��",JLabel.CENTER);
		telText=new JTextField(10);
		Box telBox=Box.createHorizontalBox();//���ˮƽBox
		telBox.add(lblTel);
		telBox.add(telText);
		JLabel lblSave=new JLabel("����λ�ã�",JLabel.CENTER);
		saveText = new JTextField(10);
		btnFile =new JButton("ѡ��");
		btnFile.addActionListener(this);
		Box saveBox=Box.createHorizontalBox();//���ˮƽBox
		saveBox.add(lblSave);
		saveBox.add(saveText);
		saveBox.add(btnFile);
		btnNew=new JButton("�½�");
		btnNew.addActionListener(this);
		btnRe=new JButton("����");
		btnRe.addActionListener(this);
		Box buttonBox=Box.createHorizontalBox();//���ˮƽBox
		buttonBox.add(btnNew);
		buttonBox.add(btnRe);
		Box boxH=Box.createVerticalBox();
		boxH.add(userBox);
		boxH.add(passwordBox);
		boxH.add(rePasswordBox);
		boxH.add(emailBox);
		boxH.add(telBox);
		boxH.add(saveBox);
		boxH.add(buttonBox);
		boxH.add(Box.createVerticalGlue());//��Ӵ�ֱ��ˮ
		createPanel=new JPanel();
		createPanel.add(boxH);
	}
	/**
	 * ��ʼ����ʾ�û���¼�Ĳ��ֽ���
	 */
	public void initMessPanel() {
		JLabel lblUser=new JLabel("�û�����",JLabel.CENTER);
		userText=new JTextField(10);
		Box userBox=Box.createHorizontalBox();//���ˮƽBox
		userBox.add(lblUser);
		userBox.add(userText);
		JLabel lblPassword=new JLabel("���룺",JLabel.CENTER);
		passwordText=new JTextField(10);
		Box passwordBox=Box.createHorizontalBox();//���ˮƽBox
		passwordBox.add(lblPassword);
		passwordBox.add(passwordText);
		btnIn=new JButton("ȷ��");
		btnIn.addActionListener(this);
		btnReset=new JButton("����");
		btnReset.addActionListener(this);
		btnCreate=new JButton("ע��");
		btnCreate.addActionListener(this);
		Box buttonBox=Box.createHorizontalBox();//���ˮƽBox
		buttonBox.add(btnIn);
		buttonBox.add(btnReset);
		buttonBox.add(btnCreate);
		Box boxH=Box.createVerticalBox();
		boxH.add(userBox);
		boxH.add(passwordBox);
		boxH.add(buttonBox);
		buttonBox.add(Box.createVerticalGlue());//��Ӵ�ֱ��ˮ
		messPanel=new JPanel();
		messPanel.add(boxH);
	}
	/**
	 * card���ֵĳ�ʼ��
	 */
	public void initDownPanel() {
		card = new CardLayout();
		downPanel = new JPanel();
		downPanel.setLayout(card);
		downPanel.add("��ע�����", panel);
		downPanel.add("ע�����", createPanel);
	}
	/**
	 * �����ťʱ�����Ķ���
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnIn){
			String name = userText.getText().trim();
			String password = passwordText.getText();
			if (name.equals("")|| password.equals("")) {
				JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��");
			} else {
				String sql = "select * from users where id='" + name + "' and code='" + password + "'";
				try {
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					if (rs.next()) {
						if (rs.getString(1).equals(name) && rs.getString(2).equals(password)) {
							JOptionPane.showMessageDialog(null, "��¼�ɹ�");
							String email=rs.getString(3).toString();
							String tel=rs.getString(4).toString();
							String fileName=rs.getString(5).toString();
							new RecordWindow(frame,name,email,tel,fileName);
						}
					} else {
						JOptionPane.showMessageDialog(null, "�û������������");
						int r = JOptionPane.showConfirmDialog(null, "��Ҫ�������û���", "��ʾ", JOptionPane.YES_NO_OPTION);
						if (r == JOptionPane.YES_OPTION) {
								System.out.println("ע��");
								card.show(downPanel, "ע�����");
						} else {
							userText.setText("");
							passwordText.setText("");
						}

					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} else if(e.getSource()==btnReset){
			userText.setText("");
			passwordText.setText("");
		} else if(e.getSource()==btnCreate){
			System.out.println("ע��");
			/**
			 * ע�ᴰ��
			 */
			card.show(downPanel, "ע�����");
		} else if(e.getSource()==btnRe){
			createUserText.setText("");
			createPasswordText.setText("");
			createRepasswordText.setText("");
		} else if(e.getSource()==btnNew){
			String inputUser = createUserText.getText();
			String inputPassword = createPasswordText.getText();
			String inputRepassword = createRepasswordText.getText();
			String inputEmail = emailText.getText();
			String inputPhone = telText.getText();
			String inputFile = saveText.getText();
			if (inputUser.equals("") || inputPassword.equals("")|| inputRepassword.equals("")) {
				JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��QAQ");
			} else if(inputFile.equals("")){
				JOptionPane.showMessageDialog(null, "��ѡ���ļ����޷���������QAQ");
			} else{
				String sql = "select * from users where id='" + inputUser + "'";
				try{
					PreparedStatement pstmt=conn.prepareStatement(sql);
					ResultSet rs=pstmt.executeQuery();
					if(rs.next()){
						if(rs.getString(1).equals(inputUser)){
							JOptionPane.showMessageDialog(null, "�û����Ѵ��ڣ����¼");
							createUserText.setText("");
							createPasswordText.setText("");
							createRepasswordText.setText("");
							clearMessage();
						}
					}else{
						if(inputPassword.equals(inputRepassword)){
							JOptionPane.showMessageDialog(null, "�����ɹ�,���½");
							sql="insert into users(id,code,email,tele,file) values(?,?,?,?,?)";
							pstmt=conn.prepareStatement(sql);
							pstmt.setString(1, inputUser);
							pstmt.setString(2, inputPassword);
							pstmt.setString(3, inputEmail);
							pstmt.setString(4, inputPhone);
							pstmt.setString(5, inputFile);
							pstmt.executeUpdate();
							clearMessage();
						}else{
							JOptionPane.showMessageDialog(null, "����ʧ�ܣ��������벻һ��");
							createPasswordText.setText("");
							createRepasswordText.setText("");
						}
					}
				}catch(SQLException e1){
					e1.printStackTrace();
				}
			}
		} else if(e.getSource()==btnFile){
			/**
			 * ѡ�񱣴�·�� ֻ��ʾ��xls��"txt""obj"��׺
			 */
			JFileChooser jfc=new JFileChooser();
			FileNameExtensionFilter filter=new FileNameExtensionFilter("Excel & xls & txt & obj","xls","txt","obj");
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setFileFilter(filter);
			int value=jfc.showSaveDialog(this);
			if(value==JFileChooser.APPROVE_OPTION){
				System.out.println(jfc.getSelectedFile().getAbsolutePath());
				System.out.println(jfc.getSelectedFile().getPath());
				saveText.setText(jfc.getSelectedFile().getAbsolutePath());
			}
		}
	}
	/**
	 * ����ʾ����Ϣ���
	 */
	public void clearMessage() {
		userText.setText(null);
		passwordText.setText(null);
		createUserText.setText(null);
		createPasswordText.setText(null);
		createRepasswordText.setText(null);
		emailText.setText(null);
		telText.setText(null);
		saveText.setText(null);
		card.show(downPanel, "��ע�����");
	}
}
