package com.stock.service.common.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.stock.common.util.convert.DateConvert;



@Service("excelOperation")
public class ExcelOperation {


	


	/**
	 * 根据文件名读取excel文件，并生存对应的sheet
	 * reference http://www.tuicool.com/articles/6jMb2i
	 * 
	 * @param fileName
	 * @throws IOException
	 */

	public Sheet readExcelFile(InputStream in,String fileName) throws IOException {

		
		if (StringUtils.isEmpty(fileName)) 
			return null;
		if(in == null ){
			
			File file = new File(fileName);
			in = new FileInputStream(file);
		}
			
	
		Workbook hwb = null;

		if (fileName.endsWith("xls") || fileName.endsWith("XLS")) {
		

			hwb = new HSSFWorkbook(in);
			

			in.close();
		} 
		else if(fileName.endsWith("xlsx") || fileName.endsWith("XLSX")) {
			
			hwb = new XSSFWorkbook(in);

			in.close();

		}else{

			in.close();
			return null;
		}
			
		
		Sheet sheet = hwb.getSheetAt(0);
		
		//((FileInputStream) hwb).close();
		
		return sheet;
		
	}


	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {

				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell);
				}
			}
		}

		return null;
	}

	/**
	 * 判断合并了行
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public  boolean isMergedRow(Sheet sheet, int row, int column) {
		
		int sheetMergeCount = sheet.getNumMergedRegions();
		
		for (int i = 0; i < sheetMergeCount; i++) {
			
			CellRangeAddress range = sheet.getMergedRegion(i);
			
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			
			if (row == firstRow && row == lastRow) {
				
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row  行下标
	 * @param column 列下标
	 * @return
	 */
	public boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断sheet页中是否含有合并单元格
	 * 
	 * @param sheet
	 * @return
	 */
	private boolean hasMerged(Sheet sheet) {
		return sheet.getNumMergedRegions() > 0 ? true : false;
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param firstRow  开始行
	 * @param lastRow 结束行
	 * @param firstCol 开始列
	 * @param lastCol 结束列
	 */
	private void mergeRegion(Sheet sheet, int firstRow, int lastRow,
			int firstCol, int lastCol) {
		
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol,
				lastCol));
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public String getCellValue(Cell cell) {

		if (cell == null)
			return "";

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

			return cell.getStringCellValue();

		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

			return String.valueOf(cell.getBooleanCellValue());

		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

			return cell.getCellFormula();

		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

		
			return this.processCELL_TYPE_NUMERIC(cell);

		} 
		
		return "";
	}
	
	private String processCELL_TYPE_NUMERIC(Cell cell){
		
		if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  

            Date date = cell.getDateCellValue();

            if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
                    .getBuiltinFormat("h:mm")) {  

              return   DateConvert.dateToStr(date, "HH:mm")  ;

            } else {// 日期  
            	 return   DateConvert.dateToStr(date,"yyyy-MM-dd") ; 

            }  

        } else if (cell.getCellStyle().getDataFormat() == 58) {  
            // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  

            double value = cell.getNumericCellValue();  
            Date date = org.apache.poi.ss.usermodel.DateUtil  
                    .getJavaDate(value);  

            return   DateConvert.dateToStr(date,"yyyy-MM-dd") ;
        } else {  
            double value = cell.getNumericCellValue();  
            CellStyle style = cell.getCellStyle();  
            DecimalFormat format = new DecimalFormat();  
            String temp = style.getDataFormatString();  
            // 单元格设置成常规  
            if (temp.equals("General")) {  
                format.applyPattern("#");  
            }  
            return format.format(value);  
        }  

//		//1、判断是否是数值格式  
//		if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
//		    short format = cell.getCellStyle().getDataFormat();  
//		    SimpleDateFormat sdf = null;  
//		    if(format == 14 || format == 31 || format == 57 || format == 58){  
//		        //日期  
//		        sdf = new SimpleDateFormat("yyyy-MM-dd");  
//		    }else if (format == 20 || format == 32) {  
//		        //时间  
//		        sdf = new SimpleDateFormat("HH:mm");  
//		    }  
//		    double value = cell.getNumericCellValue();  
//		    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);  
//		    result = sdf.format(date);  
//		} 

		
	}
}
