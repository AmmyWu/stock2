/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.seawin.common.util.excel
 * 类/接口名: ExcelImporter
 * 版本信息	: 1.00
 * 新建日期	: 2017年9月28日上午9:21:19
 * 作者		: taofeng
 * 修改历史	: 2017年9月28日上午9:21:19
 */
package com.stock.common.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stock.common.tool.string.StringUtil;
import com.stock.common.util.ReflectionUtil;


/**
 * ClassName	: ExcelImporter
 * Function		: excel导入，支持xls和xlsx
 * date 		: 2017年9月28日上午9:21:19
 * @author 		: zhangsy
 * @version		: 1.0
 * @since   JDK 1.7
 */
public class ExcelImporter {
	private static Logger LOGGER = LoggerFactory.getLogger(ExcelImporter.class);
	public static SimpleDateFormat sdf =   new SimpleDateFormat("yyyy/MM/dd"); 

	/**
	 * 
	 * importExcel:(excel导入).
	 * 
	 * @author zhangsy
	 * @param inputStream excel文件流
	 * @param excelType excel类型（xls/xlsx）
	 * @param beanClass excel转换的bean的class
	 * @param beans 导入后的结果
	 * @param methodMap 如果全部导入String属性：methodMap.put("用户名", new String[]{"setUserName","class java.lang.String"});
	 *                  否则：methodMap.put("年龄", new String[]{"setAge","class java.lang.Number","getNumericCellValue"});
	 * @param isAllString 是否全部导入String属性，是=true（建议！！）         
	 * @return 成功返回true
	 * @since JDK 1.7
	 */
	public static <T> boolean importExcel(InputStream inputStream,String excelType,Class<T> beanClass,List<T> beans,Map<String, String[]> methodMap,boolean isAllString){
		try {
			Workbook workbook = null;
			if("xlsx".equals(excelType)){
				workbook = new XSSFWorkbook(inputStream); 
			}else{
				if("xls".equals(excelType)){
					workbook = new HSSFWorkbook(inputStream);
				}else{
					return false;
				}
			}

			//循环工作表sheet
			for(int sheetNum=0;sheetNum<workbook.getNumberOfSheets();sheetNum++){
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if(sheet == null){
					continue;
				}
				//第一行是列名
				Row firstRow = sheet.getRow(0);
				if(firstRow == null){
					continue;
				}
				//列数
				Integer totalCells = (int) firstRow.getLastCellNum();
				//存放每列的名称
				Map<Integer, String> cellNameMap = new HashMap<Integer,String>();
				for(int cellNum=0;cellNum<totalCells;cellNum++){
					cellNameMap.put(cellNum, firstRow.getCell(cellNum).getStringCellValue());
				}
				//循环row,从第2行开始
				//List<UserVo> userVos2 = new ArrayList<UserVo>();
				//System.out.println("行数："+sheet.getLastRowNum());
				for(int rowNum=1;rowNum<=sheet.getLastRowNum();rowNum++){
					Row row = sheet.getRow(rowNum);
					if(row == null){
						continue;
					}
					//新建一个bean
					T bean = beanClass.newInstance();
					//是否为空行
					boolean isEmpty = true;
					//读取列，从第一列开始
					for(int cellNum=0;cellNum<totalCells;cellNum++){
						//如果此列没有在模板中定义则不用读取
						if(methodMap.get(cellNameMap.get(cellNum)) == null)
							continue;
						Cell cell = row.getCell(cellNum);
						if(isAllString){
							//列的值
							String cellValue = ExcelImporter.getValue(cell);
							if(!StringUtil.isBlank(cellValue)){
								isEmpty = false;
							}
							ReflectionUtil.setObjectValues(bean, methodMap.get(cellNameMap.get(cellNum))[0], methodMap.get(cellNameMap.get(cellNum))[1],cellValue);
						}
						else{
							String cellValue = (String) cell.getClass().getMethod(methodMap.get(cellNameMap.get(cellNum))[2]).invoke(cell);
							if(!StringUtil.isBlank(cellValue)){
								isEmpty = false;
							}
						    ReflectionUtil.setObjectValues(bean, methodMap.get(cellNameMap.get(cellNum))[0], methodMap.get(cellNameMap.get(cellNum))[1],cell.getClass().getMethod(methodMap.get(cellNameMap.get(cellNum))[2]).invoke(cell));
						}
					}
					if(!isEmpty){
						//不是空行
						beans.add(bean);
					}					
				}
				/*for(T bean:beans){
					System.out.println(bean.toString());
				}*/
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("EXCEL转换失败:"+ExceptionUtils.getFullStackTrace(e));
			return false;
		}finally{
			try {
				if(inputStream!=null){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static String getValue(Cell cell){  
		if(cell == null){
			return "";
		}
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {  
            return String.valueOf(cell.getBooleanCellValue());  
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {  
            String cellValue = "";  
            if(DateUtil.isCellDateFormatted(cell)){                  
                Date date = DateUtil.getJavaDate(cell.getNumericCellValue());  
                cellValue = sdf.format(date);  
            }else{  
                DecimalFormat df = new DecimalFormat("#.##");  
                cellValue = df.format(cell.getNumericCellValue());  
                String strArr = cellValue.substring(cellValue.lastIndexOf(".")+1,cellValue.length());  
                if(strArr.equals("00")){  
                    cellValue = cellValue.substring(0, cellValue.lastIndexOf("."));  
                }    
            }  
            return cellValue;  
        } else {  
           return String.valueOf(cell.getStringCellValue());  
        }  
   } 
}
