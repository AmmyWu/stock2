package com.stock.common.util;

import java.util.ResourceBundle;

public class PropertyReader {

	/**
	 * 读取properties文件中key对应的value
	 * @param baseName
	 * @param key
	 * @return
	 */
	public static String read(String baseName,String key){
		ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName);
		String value = resourceBundle.getString(key);
		return value;
	}
}
