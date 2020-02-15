package RecordManagement;

import java.sql.*;
import java.util.Vector;

import javax.swing.JOptionPane;

public class DataBase {
	private String driveName="com.mysql.jdbc.Driver";
	private String uri="jdbc:mysql://localhost:3306/design?user=root&password=22222&useSSL=true";
	/**
	 * 数据库的连接
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
	 * 获取各行数据
	 */
	public static Vector getRows(){
		try{
			pstmt=conn.prepareStatement("select * from users");
			rs=pstmt.executeQuery();
			if(rs.wasNull()){
				JOptionPane.showMessageDialog(null, "无记录");
			}
			rows=new Vector();
			ResultSetMetaData rsmd=rs.getMetaData();//与列相关的信息
			while(rs.next()){
				rows.addElement(getNextRow(rs,rsmd));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return rows;
	}
	/**
	 * 根据行获取此处每列的数据
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
	 * 获取表头数据
	 */
	public static Vector getHead(){
		try{
			pstmt=conn.prepareStatement("select * from users");
			rs=pstmt.executeQuery();
			if(rs.wasNull()){
				JOptionPane.showMessageDialog(null, "无记录");
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