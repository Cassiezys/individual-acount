package RecordManagement;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class UsersLogin extends JPanel implements ActionListener{
	private JFrame frame;
	private JPanel messPanel,downPanel,createPanel,panel;
	private JLabel promptLabel;//提示信息
	private JButton btnIn,btnReset,btnCreate,btnNew,btnRe,btnFile;
	private JTextField userText,passwordText,createUserText,
	createPasswordText,createRepasswordText,emailText,telText,saveText;
	private Connection conn;
	private CardLayout card;
	/**
	 * 构造方法：初始化用户登录界面
	 * @param frame
	 * @param c
	 */
	public UsersLogin(JFrame frame,Connection c){
		this.frame=frame;
		conn=c;
		promptLabel=new JLabel("欢迎用户登录：",JLabel.LEFT);
		promptLabel.setFont(new Font("宋体",Font.BOLD,13));
		promptLabel.setForeground(Color.green);
		/*promptLabel.setOpaque(true);//不透明
		promptLabel.setBackground(new Color(216,224,231));//设置背景颜色 */	
		initCreatePanel();//创建界面
		initMessPanel();//登录界面
		initDownPanel();//下方card布局
		JSplitPane splitH = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				messPanel, downPanel);
		add(promptLabel, BorderLayout.NORTH);
		add(splitH, BorderLayout.CENTER);
	}
	/**
	 * 初始化注册界面
	 */
	public void initCreatePanel() {
		JLabel label = new JLabel("欢迎使用个人记账软件", JLabel.CENTER);
		JLabel label2= new JLabel();
		label2.setIcon(new ImageIcon("D:\\123\\user.jpg"));
		label.setFont(new Font("幼圆", Font.BOLD, 36));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setForeground(Color.PINK);
		Box labelBox=Box.createVerticalBox();
		labelBox.add(label2);
		labelBox.add(label);
		panel=new JPanel();
		panel.add(labelBox);
		JLabel lblUser=new JLabel("用户名：",JLabel.CENTER);
		createUserText=new JTextField(10);
		Box userBox=Box.createHorizontalBox();//添加水平Box
		userBox.add(lblUser);
		userBox.add(createUserText);
		JLabel lblPassword=new JLabel("密码：",JLabel.CENTER);
		createPasswordText=new JTextField(10);
		Box passwordBox=Box.createHorizontalBox();//添加水平Box
		passwordBox.add(lblPassword);
		passwordBox.add(createPasswordText);
		JLabel lblRepassword=new JLabel("确认密码：",JLabel.CENTER);
		createRepasswordText=new JTextField(10);
		Box rePasswordBox=Box.createHorizontalBox();//添加水平Box
		rePasswordBox.add(lblRepassword);
		rePasswordBox.add(createRepasswordText);
		JLabel lblEmail=new JLabel("邮箱：",JLabel.CENTER);
		emailText=new JTextField(10);
		Box emailBox=Box.createHorizontalBox();//添加水平Box
		emailBox.add(lblEmail);
		emailBox.add(emailText);
		JLabel lblTel=new JLabel("电话：",JLabel.CENTER);
		telText=new JTextField(10);
		Box telBox=Box.createHorizontalBox();//添加水平Box
		telBox.add(lblTel);
		telBox.add(telText);
		JLabel lblSave=new JLabel("保存位置：",JLabel.CENTER);
		saveText = new JTextField(10);
		btnFile =new JButton("选择");
		btnFile.addActionListener(this);
		Box saveBox=Box.createHorizontalBox();//添加水平Box
		saveBox.add(lblSave);
		saveBox.add(saveText);
		saveBox.add(btnFile);
		btnNew=new JButton("新建");
		btnNew.addActionListener(this);
		btnRe=new JButton("重置");
		btnRe.addActionListener(this);
		Box buttonBox=Box.createHorizontalBox();//添加水平Box
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
		boxH.add(Box.createVerticalGlue());//添加垂直胶水
		createPanel=new JPanel();
		createPanel.add(boxH);
	}
	/**
	 * 初始化显示用户登录的部分界面
	 */
	public void initMessPanel() {
		JLabel lblUser=new JLabel("用户名：",JLabel.CENTER);
		userText=new JTextField(10);
		Box userBox=Box.createHorizontalBox();//添加水平Box
		userBox.add(lblUser);
		userBox.add(userText);
		JLabel lblPassword=new JLabel("密码：",JLabel.CENTER);
		passwordText=new JTextField(10);
		Box passwordBox=Box.createHorizontalBox();//添加水平Box
		passwordBox.add(lblPassword);
		passwordBox.add(passwordText);
		btnIn=new JButton("确定");
		btnIn.addActionListener(this);
		btnReset=new JButton("重置");
		btnReset.addActionListener(this);
		btnCreate=new JButton("注册");
		btnCreate.addActionListener(this);
		Box buttonBox=Box.createHorizontalBox();//添加水平Box
		buttonBox.add(btnIn);
		buttonBox.add(btnReset);
		buttonBox.add(btnCreate);
		Box boxH=Box.createVerticalBox();
		boxH.add(userBox);
		boxH.add(passwordBox);
		boxH.add(buttonBox);
		buttonBox.add(Box.createVerticalGlue());//添加垂直胶水
		messPanel=new JPanel();
		messPanel.add(boxH);
	}
	/**
	 * card布局的初始化
	 */
	public void initDownPanel() {
		card = new CardLayout();
		downPanel = new JPanel();
		downPanel.setLayout(card);
		downPanel.add("非注册界面", panel);
		downPanel.add("注册界面", createPanel);
	}
	/**
	 * 点击按钮时产生的动作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnIn){
			String name = userText.getText().trim();
			String password = passwordText.getText();
			if (name.equals("")|| password.equals("")) {
				JOptionPane.showMessageDialog(null, "用户名和密码不能为空");
			} else {
				String sql = "select * from users where id='" + name + "' and code='" + password + "'";
				try {
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					if (rs.next()) {
						if (rs.getString(1).equals(name) && rs.getString(2).equals(password)) {
							JOptionPane.showMessageDialog(null, "登录成功");
							String email=rs.getString(3).toString();
							String tel=rs.getString(4).toString();
							String fileName=rs.getString(5).toString();
							new RecordWindow(frame,name,email,tel,fileName);
						}
					} else {
						JOptionPane.showMessageDialog(null, "用户名或密码错误");
						int r = JOptionPane.showConfirmDialog(null, "需要创建新用户吗？", "提示", JOptionPane.YES_NO_OPTION);
						if (r == JOptionPane.YES_OPTION) {
								System.out.println("注册");
								card.show(downPanel, "注册界面");
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
			System.out.println("注册");
			/**
			 * 注册窗口
			 */
			card.show(downPanel, "注册界面");
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
				JOptionPane.showMessageDialog(null, "用户名和密码不能为空QAQ");
			} else if(inputFile.equals("")){
				JOptionPane.showMessageDialog(null, "不选择文件是无法被保存哒QAQ");
			} else{
				String sql = "select * from users where id='" + inputUser + "'";
				try{
					PreparedStatement pstmt=conn.prepareStatement(sql);
					ResultSet rs=pstmt.executeQuery();
					if(rs.next()){
						if(rs.getString(1).equals(inputUser)){
							JOptionPane.showMessageDialog(null, "用户名已存在，请登录");
							createUserText.setText("");
							createPasswordText.setText("");
							createRepasswordText.setText("");
							clearMessage();
						}
					}else{
						if(inputPassword.equals(inputRepassword)){
							JOptionPane.showMessageDialog(null, "创建成功,请登陆");
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
							JOptionPane.showMessageDialog(null, "创建失败，两次密码不一致");
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
			 * 选择保存路径 只显示“xls”"txt""obj"后缀
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
	 * 将显示的信息清空
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
		card.show(downPanel, "非注册界面");
	}
}
