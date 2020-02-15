package RecordManagement;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class LoginWindow extends JFrame implements ActionListener{
	private JFrame frame=new JFrame("LoginQAQ");
	private ManagementLogin managementLogin;//����Ա��¼
	private UsersLogin usersLogin;//�û���¼
	private JMenuBar bar;//�˵���
	private JMenu fileMenu;
	private JMenuItem manageItem,userItem,welcomeItem;
	private CardLayout card=null;
	private JLabel label=null;
	private JPanel pCenter;
	private Connection conn;
	
	/**
	 * ���췽������ʼ������
	 */
	public LoginWindow(){
		initFrame();
		setBounds(100,50,500,680);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				int n = JOptionPane.showConfirmDialog(null, "ȷ���˳���?", "ȷ�϶Ի���",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	setResizable(false);//������С
	//	validate();//��֪��
	}
	/**
	 * ��ʼ��������ĸ������
	 */
	public void initFrame(){
		manageItem=new JMenuItem("����Ա��¼");
		userItem=new JMenuItem("�û���¼");
		welcomeItem=new JMenuItem("��ӭ����");
		bar=new JMenuBar();
		fileMenu=new JMenu("��¼ѡ��");
		fileMenu.add(manageItem);
		fileMenu.add(userItem);
		fileMenu.add(welcomeItem);
		bar.add(fileMenu);
		setJMenuBar(bar);
		label=new JLabel("����           ���",JLabel.CENTER);
		label.setIcon(new ImageIcon("welcomeLogin2.jpg"));
		label.setFont(new Font("����",Font.BOLD,36));
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
		pCenter.add("��ӭ����",label);
		pCenter.add("����Ա��¼����",managementLogin);
		pCenter.add("�û���¼����",usersLogin);
		add(pCenter,BorderLayout.CENTER);
	}
	/**
	 * ���������Ա��¼���û���¼����ӭ����ʱִ�еĲ���
	 * @Override
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==manageItem){
			card.show(pCenter, "����Ա��¼����");
			managementLogin.clearMessage();
		} else if(e.getSource()==userItem){
			card.show(pCenter, "�û���¼����");
			usersLogin.clearMessage();
		} else if(e.getSource()==welcomeItem){
			card.show(pCenter,"��ӭ����");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoginWindow();
	}
	

}
