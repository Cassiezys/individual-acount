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
		System.out.println("�ͻ��˿�ʼ����");
	}
	
	public void run(){
		while(this.isAlive()){
			try{
				Socket client=server.accept();
				//ȡ�ÿͻ��˵�������
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
				//��ͻ��������Ϣ
				PrintStream ps=new PrintStream(client.getOutputStream());
				ps.println("���ǵ�"+(++num)+"������run����");
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
