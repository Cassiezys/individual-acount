package RecordManagement;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class LoginWindow extends JFrame implements ActionListener{
	private JFrame frame=new JFrame("LoginQAQ");
	private ManagementLogin managementLogin;//管理员登录
	private UsersLogin usersLogin;//用户登录
	private JMenuBar bar;//菜单栏
	private JMenu fileMenu;
	private JMenuItem manageItem,userItem,welcomeItem;
	private CardLayout card=null;
	private JLabel label=null;
	private JPanel pCenter;
	private Connection conn;
	
	/**
	 * 构造方法，初始化界面
	 */
	public LoginWindow(){
		initFrame();
		setBounds(100,50,500,680);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				int n = JOptionPane.showConfirmDialog(null, "确认退出吗?", "确认对话框",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	setResizable(false);//调整大小
	//	validate();//不知道
	}
	/**
	 * 初始化主界面的各个组件
	 */
	public void initFrame(){
		manageItem=new JMenuItem("管理员登录");
		userItem=new JMenuItem("用户登录");
		welcomeItem=new JMenuItem("欢迎界面");
		bar=new JMenuBar();
		fileMenu=new JMenu("登录选项");
		fileMenu.add(manageItem);
		fileMenu.add(userItem);
		fileMenu.add(welcomeItem);
		bar.add(fileMenu);
		setJMenuBar(bar);
		label=new JLabel("个人           软件",JLabel.CENTER);
		label.setIcon(new ImageIcon("welcomeLogin2.jpg"));
		label.setFont(new Font("隶书",Font.BOLD,36));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setForeground(Color.RED);
		manageItem.addActionListener(this);
		userItem.addActionListener(this);
		welcomeItem.addActionListener(this);
		card=new CardLayout();
		pCenter=new JPanel();
		pCenter.setLayout(card);
		conn=new DataBase().getConn();
		managementLogin=new ManagementLogin(conn);
		usersLogin=new UsersLogin(frame,conn);
		pCenter.add("欢迎界面",label);
		pCenter.add("管理员登录界面",managementLogin);
		pCenter.add("用户登录界面",usersLogin);
		add(pCenter,BorderLayout.CENTER);
	}
	/**
	 * 当点击管理员登录，用户登录，欢迎界面时执行的操作
	 * @Override
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==manageItem){
			card.show(pCenter, "管理员登录界面");
			managementLogin.clearMessage();
		} else if(e.getSource()==userItem){
			card.show(pCenter, "用户登录界面");
			usersLogin.clearMessage();
		} else if(e.getSource()==welcomeItem){
			card.show(pCenter,"欢迎界面");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoginWindow();
	}
	

}
