package RecordManagement;

import java.io.*;
import java.io.*;
import java.net.*;

public class ClientDemo {
/*	File file;
	public ClientDemo(String fileName){
		this.file=new File(fileName);
	}*/
	public static void Send(Note n){
		try {
			Socket client=new Socket("localhost",8000);
			//向服务端输入信息
			ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());
			oos.writeObject(n);
			//接受客户端信息
			BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
			System.out.println(br.readLine());
			br.close();
			oos.close();
			client.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
