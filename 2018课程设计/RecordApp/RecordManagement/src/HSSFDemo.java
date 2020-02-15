
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class HSSFDemo {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//创建HSSFWorkbook对象		
		HSSFWorkbook wb=new HSSFWorkbook();
		//创建HSSFSheet 表单对象
		HSSFSheet sheet=wb.createSheet("sheet0");
		//创建HSSFRow 行对象
		HSSFRow row=sheet.createRow(1);
		//创建HSSFCell 格子单元对象
		HSSFCell cell=row.createCell(2);
		//设置单元格的值
		cell.setCellValue("单元格中的中文");
		//输出Excel文件
		FileOutputStream output=new FileOutputStream("d:\\workbook.xlsx");
		wb.write(output);
		output.flush();
	}

}
