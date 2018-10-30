/**************************************************************************
* 项目名称：数联软件 web开发框架                          
***************************************************************************/
package com.stock.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;



 /**
 * 对象操作工具类, 继承org.apache.commons.lang3.ObjectUtils类.
 * @author <a href="mailto:taofeng@zjport.gov.cn">taofeng</a>
 * @version $Id$   
 * @since 2.0
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {
	
	private static final String TEMP_ENCODING = "ISO-8859-1";  
    private static final String DEFAULT_ENCODING = "UTF-8";  
  
	
	/**
	 * 注解到对象复制，只复制能匹配上的方法。
	 * @param annotation
	 * @param object
	 */
	public static void annotationToObject(Object annotation, Object object){
		if (annotation != null){
			Class<?> annotationClass = annotation.getClass();
			Class<?> objectClass = object.getClass();
			for (Method m : objectClass.getMethods()){
				if (StringUtils.startsWith(m.getName(), "set")){
					try {
						String s = StringUtils.uncapitalize(StringUtils.substring(m.getName(), 3));
						Object obj = annotationClass.getMethod(s).invoke(annotation);
						if (obj != null && !"".equals(obj.toString())){
							if (object == null){
								object = objectClass.newInstance();
							}
							m.invoke(object, obj);
						}
					} catch (Exception e) {
						// 忽略所有设置失败方法
					}
				}
			}
		}
	}
	
	/**
	 * 序列化对象
	 * @param object
	 * @return byte[]
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			if (object != null){
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(object);
				return baos.toByteArray();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 反序列化对象
	 * @param bytes
	 * @return Object
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			if (bytes != null && bytes.length > 0){
				bais = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bais);
				return ois.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 对象序列化为字符串
	 * writeToStr:(这里用一句话描述这个方法的作用).
	 * TODO(这里描述这个方法适用条件 – 可选).
	 * 
	 * @author taofeng
	 * @param obj
	 * @return
	 * @throws IOException 
	 * @since JDK 1.7
	 */
	public static String writeToStr(Object obj) throws IOException {  
        // 此类实现了一个输出流，其中的数据被写入一个 byte 数组。  
        // 缓冲区会随着数据的不断写入而自动增长。可使用 toByteArray() 和 toString() 获取数据。  
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
        // 专用于java对象序列化，将对象进行序列化  
        ObjectOutputStream objectOutputStream = null;  
        String serStr = null;  
        try {  
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);  
            objectOutputStream.writeObject(obj);  
            serStr = byteArrayOutputStream.toString(TEMP_ENCODING);  
            serStr = java.net.URLEncoder.encode(serStr, DEFAULT_ENCODING);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            objectOutputStream.close();  
        }  
        return serStr;  
    }  
	
	/**
	 * 将字符串反序列化为对象
	 * deserializeFromStr:(这里用一句话描述这个方法的作用).
	 * TODO(这里描述这个方法适用条件 – 可选).
	 * 
	 * @author taofeng
	 * @param serStr
	 * @return
	 * @throws IOException 
	 * @since JDK 1.7
	 */
	 public static Object deserializeFromStr(String serStr) throws IOException {  
		 ByteArrayInputStream byteArrayInputStream = null;        
		 ObjectInputStream objectInputStream = null;        
		 try {             
			 String deserStr = java.net.URLDecoder.decode(serStr, DEFAULT_ENCODING);           
			 byteArrayInputStream = new ByteArrayInputStream(deserStr.getBytes(TEMP_ENCODING));             
			 objectInputStream = new ObjectInputStream(byteArrayInputStream);              
			 return objectInputStream.readObject();         
			 }
		 catch (IOException e) {              
			 e.printStackTrace();         
			 } catch (ClassNotFoundException e) { 
				 e.printStackTrace();          
				 } finally {           
					 objectInputStream.close();           
					 byteArrayInputStream.close();       
					 }          
		 return null;     
		 }
	
}
