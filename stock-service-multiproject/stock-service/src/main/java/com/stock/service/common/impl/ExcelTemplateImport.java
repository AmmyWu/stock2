package com.stock.service.common.impl;

//import com.mz3co.fleet.dataaccess.dao.ContactMapper;
//import com.mz3co.fleet.dataaccess.dao.VehicleMapper;
//import com.mz3co.fleet.dataaccess.model.Contact;
//import com.mz3co.fleet.dataaccess.model.ContactExample;
//import com.mz3co.fleet.dataaccess.model.Vehicle;
//import com.mz3co.fleet.dataaccess.model.VehicleExample;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("excelTemplateImport")
public class ExcelTemplateImport {

	@Autowired
	private ExcelOperation excelOperation;

	@Autowired
	private ServletContext sc;

	@Autowired
	private DataSource dataSource;



	public String importExcel(MultipartFile clientFile, String templateFileName)
			throws IOException {

		String excleFileName = clientFile.getOriginalFilename();
	
		InputStream in = clientFile.getInputStream();
		

		return this.importExcelByTemplate(in, excleFileName, templateFileName);

	}

	public String importExcelByTemplate(InputStream in, String excelFileName,
			String templateFileName) {

		try {
			Sheet sheet = excelOperation.readExcelFile(in, excelFileName);

			Element tableElement = this.readExcelTemplate(templateFileName);

			if (!this.isCorrectTemplate(sheet.getRow(0), tableElement))
				return "你所导入的Excel文件与模板不符，如有疑问，请联系系统管理员。";

			List<Element> eList = this.sheetToListElement(sheet, tableElement);

			return this.insertExcelDataToTable(eList);

		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return "读取Excel文件失败!";
		} catch (DocumentException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return "XML模板文件解析失败!";
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		// catch (InvocationTargetException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// return "数据库执行插入时异常!" + e.toString();
		// }

		return "导入成功！";

	}

	/**
	 * 将生存成 element list数据插入到数据库中
	 * 
	 * @param eList
	 * @throws SQLException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	// todo add transcation
	@SuppressWarnings("unchecked")
	private String insertExcelDataToTable(List<Element> eList)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Element mtElement = null;
		List<Element> subeList = null;

		Connection connection = null;
		int transactionLevel = 0;
		Statement stmt = null;

		try {
			connection = dataSource.getConnection();

			connection.setAutoCommit(false);

			// 获得当前jdbc的transaction level
			transactionLevel = connection.getTransactionIsolation();

			// 对当前导入的数据表进行锁定
			connection
					.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			stmt = connection.createStatement();

			// BigDecimal currentBusinessId = null;

			for (Element e : eList) {

				subeList = e.elements("subtable");

				mtElement = e.element("maintable");

				// if (currentBusinessId == null)
				// currentBusinessId = this.getBussinessId(mtElement.valueOf(
				// "@name").trim());
				// else
				// currentBusinessId = currentBusinessId.add(new BigDecimal(1));

				if (subeList.size() == 0) {

					this.insertMainTable(stmt, mtElement);
					continue;
				}

				this.insertMainTable(stmt, mtElement);

				String mainTableName = mtElement.valueOf("@name");

				// 依次插入副表数据
				for (Element sube : subeList) {

					this.insertSubTable(stmt, sube, mainTableName);

				}

			}

			connection.commit();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

			try {
				connection.rollback();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				return "数据库执行回滚操作时异常!" + e.toString();
			}

			return "数据库执行插入时异常!" + e1.toString();
		} finally {
			try {
				stmt.close();

				connection.setAutoCommit(true);
				// 恢复jdbc transaction leve 级别
				connection.setTransactionIsolation(transactionLevel);

				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				return "数据库执行关闭数据库链接操作时异常！" + e.toString();
			}

		}

		return "总共导入"+eList.size()+"记录";
	}

	/*
	 * private BigDecimal getMainTableId(Statement stmt,String tablename) throws
	 * NoSuchMethodException, SecurityException, IllegalAccessException,
	 * IllegalArgumentException, InvocationTargetException, SQLException {
	 * BigDecimal id = null;
	 * 
	 * // ResultSet rs = stmt.getGeneratedKeys(); ResultSet rs =
	 * stmt.executeQuery
	 * ("select  last_number from ( select * from user_sequences )" +
	 * "where sequence_name='SEQ_"+tablename+"'"); while (rs.next()){ id =
	 * rs.getBigDecimal("last_number"); } rs.close(); return id; }
	 */

	/**
	 * 根据表名找到相应的service，调用该service的getBussinessId方法获得当前的bussinessId
	 * 
	 * @param tablename
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
//	private BigDecimal getBussinessId(String tablename)
//			throws NoSuchMethodException, SecurityException,
//			IllegalAccessException, IllegalArgumentException,
//			InvocationTargetException {
//
//		ApplicationContext ac1 = WebApplicationContextUtils
//				.getRequiredWebApplicationContext(sc);
//
//		Object serviceObject = ac1.getBean(this
//				.tablenameToServicename(tablename));
//		Class serviceClass = serviceObject.getClass();
//
//		Method m = serviceClass.getMethod("getNextBusinessId", null);
//
//		return (BigDecimal) m.invoke(serviceObject, null);
//	}

	/**
	 * 将表明转为相应的servicename
	 * 
	 * @param tablename
	 * @return
	 */
//	private String tablenameToServicename(String tablename) {
//
//		String[] tablenameWords = tablename.split("_");
//
//		if (tablenameWords.length == 1)
//			return tablenameWords[0].toLowerCase() + "Service";
//
//		String servicename = "";
//
//		for (int i = 0; i < tablenameWords.length; i++) {
//
//			if (i == 0)
//
//				servicename += tablenameWords[0].toLowerCase();
//
//			else
//				servicename += tablenameWords[i].substring(0, 1).toUpperCase()
//						+ tablenameWords[i].substring(1,
//								tablenameWords[i].length()).toLowerCase();
//
//		}
//		return servicename + "Service";
//	}

	private void insertMainTable(Statement stmt, Element mainTable)
			throws SQLException {

		String tablename = mainTable.valueOf("@name");

		StringBuilder sbSQL = new StringBuilder("insert into " + tablename);

		StringBuilder fieldSQL = new StringBuilder("");
		StringBuilder valueSQL = new StringBuilder("");

		this.setSQLbyElement(fieldSQL, valueSQL, mainTable);
		

		sbSQL.append(" (" + fieldSQL + ") values (" + valueSQL + ")");
		System.out.println(sbSQL.toString());
		stmt.execute(sbSQL.toString());

	}

	private void insertSubTable(Statement stmt, Element subTable,
			String mainTableName) throws SQLException {

		StringBuilder sbSQL = new StringBuilder("insert into "
				+ subTable.valueOf("@name"));

		StringBuilder fieldSQL = new StringBuilder("business_id,");
		StringBuilder valueSQL = new StringBuilder("SEQ_" + mainTableName
				+ ".CURRVAL" + ",");// businessId

		this.setSQLbyElement(fieldSQL, valueSQL, subTable);

		sbSQL.append(" (" + fieldSQL + ") values (" + valueSQL + ")");

		stmt.execute(sbSQL.toString());
	}

	/**
	 * 根据xml element生成相应的SQL
	 * 
	 * @param fieldSQL
	 * @param valueSQL
	 * @param tableElement
	 */
	private void setSQLbyElement(StringBuilder fieldSQL, StringBuilder valueSQL,
			Element tableElement) {

		List<Element> columnElement = tableElement.elements("column");

		//
	
		for (Element e : columnElement) {
		

			// todo 根据e.valueOf("jdbcType") 构造不同的valueSQL 字符串
			if ("".equals(e.valueOf("@jdbcType").trim()))
				continue;

			else if ("VARCHAR2".equals(e.valueOf("@jdbcType")))
				valueSQL.append("'" + e.valueOf("@value") + "',");

			else if ("DATE".equals(e.valueOf("@jdbcType"))){
				System.out.println(e.valueOf("@value")+"------------------------");
				valueSQL.append("to_date('" + e.valueOf("@value")
						+ "','yyyy-MM-dd HH24:mi:ss'),");
				}

			else
				valueSQL.append(e.valueOf("@value") + ",");

			fieldSQL.append(e.valueOf("@name") + ',');

		}

		// 删除最后一个逗号
		fieldSQL.deleteCharAt(fieldSQL.length() - 1);
		valueSQL.deleteCharAt(valueSQL.length() - 1);

		System.out.println(fieldSQL);

		System.out.println(valueSQL);
	}

	/**
	 * 根据Excel 第一行title 与 template.xml文件的 column元素判断模板文件是否符合规范
	 * 
	 * @return
	 */
	private boolean isCorrectTemplate(Row titleRow, Element tableElement) {

		for (int j = 0; j < titleRow.getPhysicalNumberOfCells(); j++) {

			String titleName = String.valueOf(titleRow.getCell(j)).trim();
//			System.out.println(titleName);

			if (!this.isExistTitleName(titleName, tableElement))
				return false;

		}

		return true;
	}

	private boolean isExistTitleName(String titleName, Element tableElement) {

		@SuppressWarnings("unchecked")
		List<Node> l = tableElement.selectNodes("//column[@field='" + titleName.trim()
				+ "']");

		if (l == null || l.size() < 1)
			return false;

		return true;
	}

	// 返回excle模板的table element
	public Element readExcelTemplate(String fileName) throws DocumentException {

		SAXReader sax = new SAXReader();
		Document xmlDoc = sax.read(new File(fileName));
		Element root = xmlDoc.getRootElement();// 根节点

		// Element te = root.element("table");

		return root.element("table");
	}

	/**
	 * 过滤运输公司、车牌号等列名，过滤后返回其id，否则返回原value
	 * 
	 * @param column
	 *            列名
	 * @param value
	 *            值
	 * @return 返回的id或者是原value
	 */
	private String filterColumnValues(String column, String value) {
//		if (column.equals("运输公司")) {
//			ContactExample contactExample = new ContactExample();
//			contactExample.createCriteria().andCnameEqualTo(value);
//			List<Contact> l = contactMapper.selectByExample(contactExample);
//			return l != null && l.size() > 0 ? l.get(0).getId() + "" : "";
//		} else if (column.equals("车牌号")) {
//			VehicleExample vehicleExample = new VehicleExample();
//			vehicleExample.createCriteria().andVehicleNoEqualTo(value);
//			List<Vehicle> l = vehicleMapper.selectByExample(vehicleExample);
//			return l != null && l.size() > 0 ? l.get(0).getId() + "" : "";
//		}
		return value;
	}

	/**
	 * 将excel 的sheet(单元表)数据 转为模板 table 的list
	 * 
	 * @param sheet
	 * @param tableElement
	 * @return
	 */
	private List<Element> sheetToListElement(Sheet sheet, Element tableElement) {

		List<Element> eList = new ArrayList<Element>();
		String titleName = "";
		String cellValue = "";

		int rowNum = sheet.getLastRowNum();
		Row titleRow = sheet.getRow(0);

		int colNum = titleRow.getPhysicalNumberOfCells();

		// 依次取数据行,从第二行开始导入，第一行为标题
		for (int i = 1; i <=rowNum; i++) {

			Row dataRow = sheet.getRow(i);

			// 取每行的列
			String  flag= "";//空行判断的标志
			for (int j = 0; j < colNum; j++) {

				if (excelOperation.isMergedRegion(sheet, i, j))

					cellValue = excelOperation
							.getMergedRegionValue(sheet, i, j);
				else
					cellValue = excelOperation.getCellValue(dataRow.getCell(j));// String.valueOf(dataRow.getCell(j)).trim();

				titleName = String.valueOf(titleRow.getCell(j)).trim();

				cellValue = filterColumnValues(titleName, cellValue);

				
				// System.out.println(titleName + "=" + cellValue);
				if(!StringUtils.isEmpty(cellValue.trim())){
					flag +=cellValue.trim() ;
				}
				
				this.addExcelColumnValueToElement(titleName, cellValue,
						tableElement);
			}

			// 将excel数据转为xml element
			// this.setRowElement(titleRow, dataRow, tableElement);

			// 复制tableElement为dataElement，并添加到List中，并对maintable数据相同的table
			// element进行合并
			if("".equalsIgnoreCase(flag)) break;
			
			this.addAndMerginMaintable(eList, tableElement);

		}

		return eList;

	}

	/**
	 * 如果List中dataElement中的maintable 数据有相同的，则进行合并
	 */
	private void addAndMerginMaintable(List<Element> eList, Element tableElement) {

		Element subtable = tableElement.element("subtable");

		if (subtable == null) {
			Element dataElement = tableElement.createCopy("table");

			eList.add(dataElement);
			return;
		}

		boolean isEqualOfMaintable = true;

		for (Element el : eList) {

			isEqualOfMaintable = true;

			List<Element> maintableCloumnList = el.element("maintable")
					.elements("column"); // .selectNodes("//maintable/cloumn");

			for (Element maintableCloumn : maintableCloumnList) {

				String columnValue = maintableCloumn.valueOf("@value");

				@SuppressWarnings("unchecked")
				List<Element> nList = tableElement
						.selectNodes("//column[@value='" + columnValue + "']");

				// 只要在数据Element中未能找到value相等的node/element，则说明改data element不存在
				if (nList == null || nList.size() < 1) {

					isEqualOfMaintable = false;
					break;
				}

			}

			// 此时说明当前el与dataElement的maintable相等，则将dataElement的subtable添加到List
			// 当前的el中
			if (isEqualOfMaintable) {
				el.add(tableElement.element("subtable").createCopy());
				return;
			} else
				continue;

		}

		// 最后仍未找到
		Element dataElement = tableElement.createCopy("table");

		eList.add(dataElement);

	}

	private void addExcelColumnValueToElement(String title, String value,
			Element tableElement) {

		List<Element> element = tableElement.selectNodes("//column[@field='"
				+ title + "']");

		Element columnElement = (Element) element.get(0);

		columnElement.addAttribute("value", value);

	}

}
