package RecordManagement;

import java.awt.*;
import java.io.*;

import javax.swing.*;

public class NotePicture extends JPanel{
	private File imageFile; //存放图像文件引用
	private Toolkit tool; //创建Image对象
	/**
	 * 构造方法，初始化对象
	 */
	public NotePicture(){
		tool=getToolkit();
		//设置边框的凹下去的效果颜色
		setBorder(BorderFactory.createLineBorder(Color.red));
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
	/**
	 * 设置imageFile对象
	 */
	public void setImage(File imageFile){
		this.imageFile=imageFile;
	}
	/**
	 * 显示照片
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//重写paintComponent()，为了确保父类JPanel能保留容器中的原本组件
		int w=getBounds().width;
		int h=getBounds().height;
		if(imageFile!=null){
			Image image=tool.getImage(imageFile.getAbsolutePath());//获得图像
			g.drawImage(image, 0, 0, w,h,this);// 绘制图像
		}else
			g.drawString("没有选择照片图像", 20, 30);
	}
}
