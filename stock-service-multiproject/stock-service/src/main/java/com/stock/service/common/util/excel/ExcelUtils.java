package com.stock.service.common.util.excel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtils {
	public static <T> HSSFWorkbook excelTpls(List<T> list, String propertyPath)
			throws Exception {
		//为0 返回
		if(list.size()==0) return null;
		Class<?> clz = list.get(0).getClass();
		ResourceBundle resource = ResourceBundle.getBundle(propertyPath);
		String sheetName = (String) resource.getString("sheetName");
		String[] cols = ((String) resource.getString("colName")).split(",");
		String[] colWidth = resource.getString("colWidth").split(",");
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		//样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCellStyle dateStyle = wb.createCellStyle();
		HSSFDataFormat format = wb.createDataFormat();
		dateStyle.setDataFormat(format.getFormat("yyyy年MM月dd日"));
		Font font=wb.createFont();
		font.setColor(HSSFColor.RED.index);
		HSSFCellStyle redFont= wb.createCellStyle();
		redFont.setFont(font);
		
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		int rowNum = 0;// 第一行为标题,所以从第二行开始
		int colNum = 0;
		sheet.autoSizeColumn(rowNum);// 列宽自适应

		// 表头head特殊处理
		HSSFRow head = sheet.createRow(rowNum++);
		HSSFCell headCell=head.createCell(colNum++);
		headCell.setCellStyle(redFont);
		headCell.setCellValue((String) clz.getField("head").get(list.get(0)));

		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow title = sheet.createRow(rowNum++);
		

		// 设置列宽
		colNum = 0;
		for (String c : colWidth) {
			sheet.setColumnWidth(colNum++, Integer.valueOf(c.trim()) * 2 * 256);
		}

		// 设置标题
		colNum = 0;
		for (String c : cols) {
			title.createCell(colNum++).setCellValue(c.trim());
		}

		for (T t : list) {
			if (t == null)
				continue;
			HSSFRow row = sheet.createRow(rowNum++);
			colNum = 0;
			Field[] fields = clz.getDeclaredFields();
			for (Field f : fields) {
				if (Modifier.isStatic(f.getModifiers())) {// 静态属性sum特殊处理
					continue;
				}
				String name = f.getName();
				String type = f.getType().getName();
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				Method method = clz.getMethod("get" + name);

				switch (type) {
				case "java.lang.String":
					String vString = (String) method.invoke(t);
					row.createCell(colNum++).setCellValue(vString);
					break;
				case "java.util.Date":
					Date vDate = (Date) method.invoke(t);
					HSSFCell cell = row.createCell(colNum++);
					cell.setCellStyle(dateStyle);
					cell.setCellValue(vDate);
					break;
				case "java.math.BigDecimal":
					BigDecimal v = (BigDecimal) method.invoke(t);
					double vBigDecimal = v.doubleValue();
					row.createCell(colNum++).setCellValue(vBigDecimal);
					break;
				case "int":
					int vInt = (int) method.invoke(t);
					row.createCell(colNum++).setCellValue(vInt);
					break;

				}
			}
		}

		// 特殊 合计
		BigDecimal sum = new BigDecimal(0);
		sum = (BigDecimal) clz.getField("sum").get(list.get(0));

		HSSFRow sumRow = sheet.createRow(rowNum++);
		colNum = 0;
		sumRow.createCell(colNum++).setCellValue("合计");
		sumRow.createCell(colNum++).setCellValue(sum.doubleValue());
		return wb;
	}
	
	//跟1基本相似，先不抽取公共部分
	public static <T> HSSFWorkbook excelTpls2(List<T> list, String propertyPath)
			throws Exception {
		//为0 返回
		if(list.size()==0) return null;
		Class<?> clz = list.get(0).getClass();
		ResourceBundle resource = ResourceBundle.getBundle(propertyPath);
		String sheetName = (String) resource.getString("sheetName");
		String[] cols = ((String) resource.getString("colName")).split(",");
		String[] colWidth = resource.getString("colWidth").split(",");
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		//样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCellStyle dateStyle = wb.createCellStyle();
		HSSFDataFormat format = wb.createDataFormat();
		dateStyle.setDataFormat(format.getFormat("yyyy年MM月dd日"));
		Font font=wb.createFont();
		font.setColor(HSSFColor.RED.index);
		HSSFCellStyle redFont= wb.createCellStyle();
		redFont.setFont(font);
		
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		int rowNum = 0;
		int colNum = 0;
		sheet.autoSizeColumn(rowNum);// 列宽自适应

		// 表头head特殊处理
		HSSFRow head = sheet.createRow(rowNum++);
		head.setHeight((short) 512);
		HSSFCell headCell=head.createCell(colNum++);
		HSSFCellStyle headStyle = wb.createCellStyle();
		Font headFont=wb.createFont();
		headFont.setFontHeight((short)350);
		headFont.setBold(true);
		headStyle.setFont(headFont);
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		headCell.setCellStyle(headStyle);
		headCell.setCellValue((String) clz.getField("head").get(list.get(0)));
		
		//增加一行 总收入 总支出.....
		HSSFRow secondHead=sheet.createRow(rowNum++);
		CellRangeAddress cra0=new CellRangeAddress(0, 0, 0, 13);        
		CellRangeAddress cra1=new CellRangeAddress(1, 1, 1, 2);        
		CellRangeAddress cra2=new CellRangeAddress(1, 1, 3, 10);        
		CellRangeAddress cra3=new CellRangeAddress(1, 1, 11, 13);  
		
		sheet.addMergedRegion(cra0); 
		sheet.addMergedRegion(cra1); 
		sheet.addMergedRegion(cra2); 
		sheet.addMergedRegion(cra3); 
		HSSFCell cell1 = secondHead.createCell(1);
		cell1.setCellStyle(style);
		cell1.setCellValue("收入");
		HSSFCell cell2 = secondHead.createCell(3);
		cell2.setCellStyle(style);
		cell2.setCellValue("支出");
		HSSFCell cell3 = secondHead.createCell(11);
		cell3.setCellStyle(style);
		cell3.setCellValue("总支出");
		     
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow title = sheet.createRow(rowNum++);

		// 设置列宽
		colNum = 0;
		for (String c : colWidth) {
			sheet.setColumnWidth(colNum++, Integer.valueOf(c.trim()) * 2 * 256);
		}

		// 设置标题
		colNum = 0;
		for (String c : cols) {
			title.createCell(colNum++).setCellValue(c.trim());
		}

		for (T t : list) {
			if (t == null)
				continue;
			HSSFRow row = sheet.createRow(rowNum++);
			colNum = 0;
			Field[] fields = clz.getDeclaredFields();
			for (Field f : fields) {
				if (Modifier.isStatic(f.getModifiers())) {// 静态属性sum特殊处理
					continue;
				}
				String name = f.getName();
				String type = f.getType().getName();
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				Method method = clz.getMethod("get" + name);

				switch (type) {
				case "java.lang.String":
					String vString = (String) method.invoke(t);
					row.createCell(colNum++).setCellValue(vString);
					break;
				case "java.util.Date":
					Date vDate = (Date) method.invoke(t);
					HSSFCell cell = row.createCell(colNum++);
					cell.setCellStyle(dateStyle);
					cell.setCellValue(vDate);
					break;
				case "java.math.BigDecimal":
					BigDecimal v = (BigDecimal) method.invoke(t);
					double vBigDecimal = v.doubleValue();
					row.createCell(colNum++).setCellValue(vBigDecimal);
					break;
				case "java.lang.Double":
					Double value = (Double) method.invoke(t);
					if(value == null) value = 0.0;
					row.createCell(colNum++).setCellValue(value);
					break;
				case "int":
					int vInt = (int) method.invoke(t);
					row.createCell(colNum++).setCellValue(vInt);
					break;

				}
			}
		}

		// 特殊 合计
		BigDecimal sum = new BigDecimal(0);
		sum = (BigDecimal) clz.getField("sum").get(list.get(0));

		HSSFRow sumRow = sheet.createRow(rowNum++);
		colNum = 0;
		sumRow.createCell(colNum++).setCellValue("合计");
		sumRow.createCell(colNum++).setCellValue(sum.doubleValue());
		return wb;
	}
}
