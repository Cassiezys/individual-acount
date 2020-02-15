package RecordManagement;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerDemo extends Thread{
	public List<Note> list=new ArrayList<>();
	ServerSocket server=null;
	File file;
	int num=0;
	public ServerDemo(String fileName){
		try{
			server=new ServerSocket(8000);
		} catch(IOException e){
			e.printStackTrace();
		}
		this.start();
		this.file=new File(fileName);
		System.out.println("客户端开始运行");
	}
	
	public void run(){
		while(this.isAlive()){
			try{
				Socket client=server.accept();
				//取得客户端的输入流
				ObjectInputStream ois=new ObjectInputStream(client.getInputStream());
				Note note=(Note) ois.readObject();
				
				list.add(note);
				ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
				for(Note n:list){
					System.out.println(n.toString());
					oos.writeObject(n);
				}

				/*System.out.println(note.toString());
				ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
				oos.writeObject(note);*/
				//向客户端输出信息
				PrintStream ps=new PrintStream(client.getOutputStream());
				ps.println("这是第"+(++num)+"次运行run方法");
				ois.close();
				oos.close();
				ps.close();
				client.close();
			}catch(IOException e){
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
