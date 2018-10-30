/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.common.util
 * 类/接口名: SortChineseName
 * 版本信息	: 1.00
 * 新建日期	: 2017年6月10日下午4:40:51
 * 作者		: taofeng
 * 修改历史	: 2017年6月10日下午4:40:51
 */
package com.stock.common.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.Map.Entry;

/**
 * ClassName	: SortChineseName
 * Function		: 实现map的value按中文字母顺序进行排序，支持英文排序
 * date 		: 2017年6月10日下午4:40:51
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
public class SortChineseName implements Comparator<Entry<Integer,String>> {

	/**
	 * Create a new instance of SortChineseName
	 */
	public SortChineseName() {
		// TODO Auto-generated constructor stub
	}

	
	Collator cmp = Collator.getInstance(java.util.Locale.CHINA);  
	


	/** 
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
	
		// TODO Auto-generated method stub
	    if (cmp.compare(o1.getValue(), o2.getValue())>0){  
            return 1;  
        }else if (cmp.compare(o1.getValue(), o2.getValue())<0){  
            return -1;  
        }  
        return 0;  
	}
	


}
