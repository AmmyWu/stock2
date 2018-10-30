
package com.stock.webapp.base.controller.pcweb;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.stock.service.common.impl.ExcelTemplateImport;

@Controller
@RequestMapping(value = "/commonImport")
public class CommonImportController {

    @Autowired
    private ExcelTemplateImport excelTemplateImport;
//
//    @Autowired
//    private ExcelTemplateService excelTemplateService;
//    
//    @Autowired
//	private EmployeeService employeeService;

//    @Autowired
//    private DataSource dataSource;
//
//    @RequestMapping(value = "/listTableName.do")
//    public @ResponseBody
//    JSONArray  listTableName(HttpServletRequest request) {
//        JSONArray jsonArray= new JSONArray();
//
//
//          String fileName=  request.getRealPath("/")+"/WEB-INF/classes/Import.properties";
//
//          Properties templateNameProp = vanService.converse(fileName);
//          
//          if(templateNameProp == null) {
//        	  
//        	  return null;
//          }
//          
//          Iterator it = templateNameProp.keySet().iterator();
//          
//          while(it.hasNext()){
//        	  Map<String,String> map=new HashMap<String,String>();
//        	  String key = (String)it.next();
//        	  map.put("code",key);
//              map.put("name",  templateNameProp.getProperty(key) );//vanService.converse(tableName, fileName)
//              
//              jsonArray.add(map);
//          }
//          
//  
//        return jsonArray;
//
//    }


   
//    @RequestMapping(value = "/listTableColumnName.do")
//    public @ResponseBody
//    JSONObject  listTableColumnName(HttpServletRequest request) throws SQLException {
//    	
//    	String tableName=request.getParameter("tableName");
//    
//    	
//        JSONArray jsonArray= new JSONArray();
//        //JSONObject json = new JSONObject();
//        Map<String,String> map=new HashMap<String,String>();
//        
//        Connection  conn = null;
//        try{
//            Properties props = new Properties();
//            props.put("remarksReporting","true");
//
////            //获得数据库的登录号
//            String filePath=  request.getServletContext().getRealPath("/")+"/WEB-INF/classes/db.properties";
//
//            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
//            props.load(in);
//            String user = props.getProperty("db.username");
//            String url = props.getProperty("db.url");
//            String password = props.getProperty("db.password");
//            String driver = props.getProperty("db.driverClassName");
//
//            props.put("user", user);
//            props.put("password", password);
//            Class.forName(driver);
//              conn=DriverManager.getConnection(url, props);
//        	
//
//            DatabaseMetaData dmd=(DatabaseMetaData) conn.getMetaData();
////            ResultSet rs=dmd.getTables(null, "CD", "%", new String[]{"TABLE"});
//            StringBuffer field = new StringBuffer();
//            StringBuffer text = new StringBuffer();
////            while(rs.next()){
////                String tableSchem = rs.getString("TABLE_SCHEM");
////                String tableName= rs.getString("TABLE_NAME");
//                
////                String tableShowName = templateNameProp.getProperty(tableName);
//                
////                if(StringUtils.isEmpty(tableShowName))
////                	continue;
//                
////                user.equalsIgnoreCase(tableSchem)&& tableName, 
////                if( !tableName.startsWith("BIN$")){
////                    map.put("code",tableName);
////                    map.put("name", tableShowName );//vanService.converse(tableName, fileName)
//
////                    Long start = System.currentTimeMillis();
//                    ResultSet rst=dmd.getColumns(null, null, tableName, "%");
////                    Long end = System.currentTimeMillis();
////                    System.out.println("1 took:" + (end - start) / 1000.0 + "s");
//
////                    start = System.currentTimeMillis();
//                    int cnt = 0;
//                    while(rst.next()){
//                        String columnname=rst.getString("COLUMN_NAME");
//                        String remark=rst.getString("REMARKS");
//                        field.append(columnname);
//                        field.append(",");
//                        text.append(remark);
//                        text.append(",");
////                        cnt++;
//                    }
//////                    end = System.currentTimeMillis();
//////                    System.out.println("2 took:" + (end - start) / 1000.0 + "s," + cnt);
//                    map.put("field", field.toString().substring(0, field.toString().length()-1>=0?field.toString().length()-1:0));
//                    map.put("text", text.toString().substring(0,text.toString().length()-1));
////                    jsonArray.add(map);
////                    field.delete(0, field.toString().length()-1);
////                    text.delete(0, text.toString().length()-1);
////                    map.clear();
////                }
//
////            }
//          
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }finally{
//        	
//        	 if(conn != null) conn.close();
//        	
//        }
//        System.out.println(jsonArray);
//
//        return JSONObject.fromObject(map);
//
//    }
//
//
//
//
//    @RequestMapping(value = "/listExcelName.do", method = RequestMethod.POST)
//    public
//    void listExcelName(
//            @RequestParam("clientFile") MultipartFile clientFile,
//            HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//
//        response.setContentType("application/json;charset=utf-8");
//        //String filePath=  request.getRealPath("/")+"/WEB-INF/classes/Import.properties";
//        PrintWriter out = response.getWriter();
//        out = response.getWriter();
//
//        if(clientFile.isEmpty()) {
//
//            out.println("文件为空，提交失败");
//            out.flush();
//            out.close()	;
//            
//            return;
//        }
//        
//        String fileName = new String( clientFile.getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8"); 
////        System.out.print(new String( clientFile.getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8")); 
//        
//        
//        //保存上传模板文件
////        String templateFilePath = request.getServletContext().getRealPath("/")+"/uploads/exceltemplates/";
//        
//        String templateFilePath = request.getSession().getServletContext().getRealPath("/").replace("fleet-admin","fleet-uploads")+"/exceltemplates/";
//        
//
//        OutputStream ops =  new FileOutputStream(templateFilePath + fileName);
//        ops.write(clientFile.getBytes());
//        ops.close();
//        
//        request.getSession().setAttribute("excelTemplateFileName", fileName);
//        
//        // System.out.println(vanService.listExcelName(clientFile,request.getParameter("type"),filePath));
//        //return vanService.listExcelName(clientFile,request.getParameter("type"),filePath);
//        out.println(vanService.listExcelName(clientFile).toString());
//        out.flush();
//        out.close()	;
//    }

    
    /**
     * importExcel:导入excel. <br/> 
     * TODO(这里描述这个方法适用条件 – 可选).<br/> 
     * 
     * @author chengxl 
     * @param clientFile
     * @param request
     * @param response
     * @throws Exception 
     * @since JDK 1.7 
     */
    @RequestMapping(value = "/importExcel.do", method = RequestMethod.POST)
    public void importExcel(
            @RequestParam("clientFile") MultipartFile clientFile,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	if (request.getCharacterEncoding() == null) {
    		request.setCharacterEncoding("UTF-8");//你的编码格式
    		}

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out = response.getWriter();
        //模板名字类型
        //String type=request.getParameter("type");
        String type = "document_attachment";
        if(clientFile.isEmpty()) {

            out.println("文件为空，提交失败");
            out.flush();
            out.close()	;
            return;
        }
        
        String templateFile = request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/classes/exceltemplates/"+type.toLowerCase()+"-template.xml";
        
//        String templateFile = request.getSession().getServletContext().getRealPath("/").replace("bspheis-admin","bspheis-web")+"/exceltemplates/"+type.toLowerCase()+"-template.xml";

        String result = excelTemplateImport.importExcel(clientFile, templateFile);

        out.println(result);
        out.flush();
        out.close();

    }

//    @RequestMapping(value = "/savexml.do")
//    public @ResponseBody
//    String saveXml(HttpServletRequest request,ExcelTemplate excelTemplate){
//        String results=request.getParameter("table");
//        String templatename=null;
//        String filePath=  null;
//        JSONArray jsonArray=null;
//        String tmpId = "";
//        if (results != null)
//            jsonArray = JSONArray.fromObject(results);
//        if (jsonArray == null || jsonArray.size() == 0)
//            return "";
//        for(int i=0;i<jsonArray.size();i++){
//            JSONObject jo=(JSONObject) jsonArray.get(i);
//            JSONArray maintable=(JSONArray) jo.get("maintable");
//            tmpId=jo.get("tmpId").toString();
//            JSONObject main=(JSONObject) maintable.get(0);
//            String table=(String) main.get("name");
//            String tableName=(String) main.get("description");
//
//            String code=(String) main.get("code");
//            String excelName=(String) main.get("excelName");
//
//            JSONArray subtable=(JSONArray) jo.get("subtable");
//            String tablesub=null;
//            String codesub=null;
//            String tableNamesub=null;
//            String excelNamesub=null;
//
//            if(subtable!=null) {
//                for(int j=0;j<subtable.size();j++){
//                    JSONObject sub=(JSONObject) subtable.get(j);
//                    tablesub=(String) sub.get("name");
//                    tableNamesub=(String) sub.get("description");
//
//                    codesub=(String) sub.get("code");
//                    excelNamesub=(String) sub.get("excelName");
//
//                }
//            }
//           
//            
////            filePath = request.getServletContext().getRealPath("/")+"/WEB-INF/classes/exceltemplates/"+table.toLowerCase()+"-template.xml";
//            
//            filePath =  request.getSession().getServletContext().getRealPath("/").replace("fleet-admin","fleet-uploads")+"/exceltemplates/"+table.toLowerCase()+"-template.xml";
//
//            File f = new File(filePath);//table.toLowerCase()+"-template.xml"
//            if(f.exists()){ //备份之前定义的模板文件
//            	 File bakFile= new File(filePath +".bak");
//            	 f.renameTo(bakFile);
//            	
//            }
//            
//            xmlService.createXml(filePath);
//            
//            xmlService.addDataToXml(filePath,table, tableName,code,excelName,tablesub, tableNamesub,codesub,excelNamesub);
//            excelTemplate.setCode(table);
//            excelTemplate.setName(tableName);
//            
////            excelTemplate.setExcelDetail(tableName+".xlxs");
//            
//            String excelDetail = request.getSession().getAttribute("excelTemplateFileName") ==null ? "":
//            		(String)request.getSession().getAttribute("excelTemplateFileName");
//            if(!"".equals(excelDetail))
//            	excelTemplate.setExcelDetail(excelDetail);
//            
//            excelTemplate.setDelStatus((short)0);
//            //	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//            excelTemplate.setCreateTime(DateHandler.strToDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()),"yyyy-MM-dd"));//// new Date()为获取当前系统时间
//
//            Employee employee = UserLoginSessionUtil.getLoginEmployee(request, employeeService);
//            String employeeName = employee == null?"":employee.geteCname();
//            excelTemplate.setCreator(employeeName);
//            if(!"".equals(tmpId))
//            {
//                excelTemplate.setId(Long.parseLong(tmpId));
//                excelTemplateService.update(excelTemplate);
//            }
//            else
//                excelTemplateService.insert(excelTemplate);
//        }
//        return "success";
//    }
//
//    @RequestMapping(value = "/listxml.do")
//    public @ResponseBody
//    JSONArray listXml(HttpServletRequest request){
//        String type=request.getParameter("type");
////        String filePath=request.getRealPath("/").substring(0,request.getRealPath("/").lastIndexOf("\\"));
////        filePath=request.getRealPath("/").substring(0, filePath.lastIndexOf("\\"))+ "\\template\\"+type+"_Templates.xml";
//
////       String filePath1 = request.getSession().getServletContext().getRealPath("/")+"/WEB-INF/classes/exceltemplates/"+type.toLowerCase()+"-template.xml";
//        String filePath = request.getSession().getServletContext().getRealPath("/").replace("fleet-admin","fleet-uploads")+"/exceltemplates/"+type.toLowerCase()+"-template.xml";
//        System.out.println(request.getSession().getServletContext().getRealPath("/"));
//        return xmlService.parserXml(filePath);
//    }

}

