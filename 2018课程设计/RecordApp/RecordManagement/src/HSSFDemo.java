
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class HSSFDemo {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//����HSSFWorkbook����		
		HSSFWorkbook wb=new HSSFWorkbook();
		//����HSSFSheet ������
		HSSFSheet sheet=wb.createSheet("sheet0");
		//����HSSFRow �ж���
		HSSFRow row=sheet.createRow(1);
		//����HSSFCell ���ӵ�Ԫ����
		HSSFCell cell=row.createCell(2);
		//���õ�Ԫ���ֵ
		cell.setCellValue("��Ԫ���е�����");
		//���Excel�ļ�
		FileOutputStream output=new FileOutputStream("d:\\workbook.xlsx");
		wb.write(output);
		output.flush();
	}

}
