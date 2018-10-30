/**************************************************************************
* 项目名称：数联软件 web开发框架                          
***************************************************************************/
package com.stock.dao.page;

import java.util.List;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

 /**
 * TODO 请在此处添加注释
 * @author dijia.tang
 * @version $Id$   
 * @since 2.0
 */
public class MySQLPaginationPlugin_stock extends PluginAdapter {
	
	/**
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return boolean
	 * @see org.mybatis.generator.api.PluginAdapter#modelExampleClassGenerated(org.mybatis.generator.api.dom.java.TopLevelClass, org.mybatis.generator.api.IntrospectedTable)
	 */
	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		addPage(topLevelClass, introspectedTable, "page");
		return super.modelExampleClassGenerated(topLevelClass,introspectedTable);
	}

	/**
	 * @param element
	 * @param introspectedTable
	 * @return boolean
	 * @see org.mybatis.generator.api.PluginAdapter#sqlMapSelectByExampleWithoutBLOBsElementGenerated(org.mybatis.generator.api.dom.xml.XmlElement, org.mybatis.generator.api.IntrospectedTable)
	 */
	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		XmlElement page = new XmlElement("if");
		page.addAttribute(new Attribute("test", "page != null"));
		page.addElement(new TextElement("limit #{page.begin} , #{page.length}"));
		element.addElement(page);
		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element,introspectedTable);
	}

	/**
	 * @param topLevelClass
	 * @param introspectedTable
	 * @param name
	 */
	private void addPage(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable, String name) {
		topLevelClass.addImportedType(new FullyQualifiedJavaType("Page"));
		CommentGenerator commentGenerator = context.getCommentGenerator();
		Field field = new Field();
		field.setVisibility(JavaVisibility.PROTECTED);
		field.setType(new FullyQualifiedJavaType("Page"));
		field.setName(name);
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		char c = name.charAt(0);
		String camel = Character.toUpperCase(c) + name.substring(1);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + camel);
		method.addParameter(new Parameter(new FullyQualifiedJavaType("Page"), name));
		method.addBodyLine("this." + name + "=" + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType("Page"));
		method.setName("get" + camel);
		method.addBodyLine("return " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}

	/**
	 * This plugin is always valid - no properties are required
	 * @param warnings
	 * @return boolean
	 * @see org.mybatis.generator.api.Plugin#validate(java.util.List)
	 */
	public boolean validate(List<String> warnings) {
		return true;
	}

	/**  */
	public static void generate(String resourceXML) {
		String config = MySQLPaginationPlugin_stock.class.getClassLoader()
				.getResource(resourceXML).getFile();
		String[] arg = { "-configfile", config, "-overwrite" };
		ShellRunner.main(arg);
	}

	/**  */
	public static void main(String[] args) {
		
//		String resourceXML = "generatorConfig_sys.xml";
		
		String resourceXML = "generatorConfig_stock.xml";
		
		generate(resourceXML);
	}
}
