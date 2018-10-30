package com.stock.service.common.util.excel;

import org.apache.poi.xssf.usermodel.XSSFCell;





public class ExcelReader {
	/**    
     * 获取单元格数据内容为字符串类型的数据    
     * @param xssfCell Excel单元格    
     * @return String 单元格数据内容，若为字符串的要加单引号    
     */     
    public String getStringCellValue(XSSFCell xssfCell) {      
        String strCell = "";      
        switch (xssfCell.getCellType()) {      
        case XSSFCell.CELL_TYPE_STRING:    
            strCell =  xssfCell.getStringCellValue() ;      
            break;      
        case XSSFCell.CELL_TYPE_NUMERIC:      
               
               strCell = String.valueOf(xssfCell.getNumericCellValue());         
            break;      
        case XSSFCell.CELL_TYPE_BOOLEAN:      
            strCell = String.valueOf(xssfCell.getBooleanCellValue());      
            break;      
        case XSSFCell.CELL_TYPE_BLANK:      
            strCell = "";      
            break;     
        default:      
            strCell = "";      
            break;      
        }      
        if (strCell.equals("") || strCell == null) {      
            return "";      
        }      
        return strCell;      
    }  
      
    /*public String getTitleValue(HSSFCell cell) {      
        String strCell =  cell.getStringCellValue();      
        return strCell;      
    }

    public String exchangeExcelTitle( String  a){
       String t=a.trim();
    	if(t.equals ("车队"))
    	  t="FLEET_NAME";
    	if(t.equals("车号"))
    		t="VEHICLE_NO" ;
    	if(t.equals("驾驶员电话"))
    		t="DRIVER_TEL";
    	if(t.equals("提单号"))
    		t="BILL_NO";
    	if(t.equals("箱号"))
    	    t="BOX_NO";
    	if(t.equals("封号"))
    		t="SEAL_NO";
    	if(t.equals("箱型"))
    		t="BOX_TYPE";
    	if(t.equals("装柜地点"))
    		t="LOAD_ADDR";
    	if(t.equals("装柜时间"))
    		t="LOAD_TIME";
    	if(t.equals("业务员"))
    		t="CLERK_NAME";
    	if(t.equals("备注"))
    		t="REMARK";
    	if(t.equals("截关日"))
    		t="CLOSING_DATE";
    	if(t.equals("到厂时间"))
    		t="REACH_TIME";
    	if(t.equals("出厂时间"))
    		t="LEAVE_TIME";
    	if(t.equals("停空费"))
    		t="TK_FEE";
    	if(t.equals("进港时间"))
    		t="ENTRY_TIME";
    	if(t.equals("审核签字"))
    		t="CHECK_SIGN";
    	if(t.equals("工厂联系电话"))
    		t="FACTORY_TEL";
    	if(t.equals("托卡费"))
    		t="WAYBILL_COST";
    	if(t.equals("其他费用"))
    		t="OTHER_COST";
    	 return t;
    	
    	
    	}*/
    			
    
	     

  }
