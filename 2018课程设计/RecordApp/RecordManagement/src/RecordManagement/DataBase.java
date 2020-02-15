package RecordManagement;

import java.sql.*;
import java.util.Vector;

import javax.swing.JOptionPane;

public class DataBase {
	private String driveName="com.mysql.jdbc.Driver";
	private String uri="jdbc:mysql://localhost:3306/design?user=root&password=22222&useSSL=true";
	/**
	 * ���ݿ������
	 * @return Connection
	 */
	public Connection getConn(){
		Connection conn=null;
		try{
			Class.forName(driveName);
			conn=DriverManager.getConnection(uri);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
class UserUtil{
	private static Connection conn=new DataBase().getConn();
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private static Vector rows=null;
	private static Vector columnHeads=null;
	
	/**
	 * ��ȡ��������
	 */
	public static Vector getRows(){
		try{
			pstmt=conn.prepareStatement("select * from users");
			rs=pstmt.executeQuery();
			if(rs.wasNull()){
				JOptionPane.showMessageDialog(null, "�޼�¼");
			}
			rows=new Vector();
			ResultSetMetaData rsmd=rs.getMetaData();//������ص���Ϣ
			while(rs.next()){
				rows.addElement(getNextRow(rs,rsmd));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return rows;
	}
	/**
	 * �����л�ȡ�˴�ÿ�е�����
	 * @param rs
	 * @param rsmd
	 * @return
	 */
	public static Vector getNextRow(ResultSet rs,ResultSetMetaData rsmd){
		Vector currentRow=new Vector();
		try{
			for(int i=1;i<=rsmd.getColumnCount();i++){
				currentRow.addElement(rs.getString(i));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return currentRow;
	}
	/**
	 * ��ȡ��ͷ����
	 */
	public static Vector getHead(){
		try{
			pstmt=conn.prepareStatement("select * from users");
			rs=pstmt.executeQuery();
			if(rs.wasNull()){
				JOptionPane.showMessageDialog(null, "�޼�¼");
			}
			columnHeads=new Vector();
			ResultSetMetaData rsmd=rs.getMetaData();
			for(int i=1;i<=rsmd.getColumnCount();i++){
				columnHeads.addElement(rsmd.getColumnName(i));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return columnHeads;
	}
}