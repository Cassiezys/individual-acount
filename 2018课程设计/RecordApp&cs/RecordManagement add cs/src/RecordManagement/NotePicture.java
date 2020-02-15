package RecordManagement;

import java.awt.*;
import java.io.*;

import javax.swing.*;

public class NotePicture extends JPanel{
	private File imageFile; //���ͼ���ļ�����
	private Toolkit tool; //����Image����
	/**
	 * ���췽������ʼ������
	 */
	public NotePicture(){
		tool=getToolkit();
		//���ñ߿�İ���ȥ��Ч����ɫ
		setBorder(BorderFactory.createLineBorder(Color.red));
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
	/**
	 * ����imageFile����
	 */
	public void setImage(File imageFile){
		this.imageFile=imageFile;
	}
	/**
	 * ��ʾ��Ƭ
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//��дpaintComponent()��Ϊ��ȷ������JPanel�ܱ��������е�ԭ�����
		int w=getBounds().width;
		int h=getBounds().height;
		if(imageFile!=null){
			Image image=tool.getImage(imageFile.getAbsolutePath());//���ͼ��
			g.drawImage(image, 0, 0, w,h,this);// ����ͼ��
		}else
			g.drawString("û��ѡ����Ƭͼ��", 20, 30);
	}
}
