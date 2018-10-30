/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.utils
 * 类/接口名	: Encode
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午6:08:06
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午6:08:06
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.service.common.util;

import java.io.File;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
/**
 * 
 * ClassName	: Encode
 * Function		: TODO
 * date 		: 2017年5月7日下午6:11:38
 * @author chengxl
 * @version		: 
 * @since   JDK 1.7
 */
public class Encode {
	// 图片宽度的一�?
		private static final int IMAGE_WIDTH = 300;
		private static final int IMAGE_HEIGHT = 300;
		private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
		private static final int FRAME_WIDTH = 2;

		// 二维码写码器
		private static MultiFormatWriter mutiWriter = new MultiFormatWriter();
		
	    /**
	     * 二维码编�?
	     * @param contents
	     * @param width
	     * @param height
	     * @param imgPath
	     */
	    public static void encode(String contents, int width, int height, String imgPath) {
	    	
	    	Hashtable hints= new Hashtable();  
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
	        try {
	            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,BarcodeFormat.QR_CODE, width, height);

	            MatrixToImageWriter.writeToFile(bitMatrix, "png", new File(imgPath));

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    /**
	     * 二维码编�?
	     * @param contents
	     * @param width
	     * @param height
	     * @param imgPath
	     */
	    public static void createQRCode(String contents,String savePath,String fileName,int width, int height) {
	    	
	    	Hashtable hints= new Hashtable();  
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
	        
	        try {
	        	
	        	File filePath = new File(savePath);
	    		
	    		if(!filePath.exists()){
	    			filePath.mkdirs();
	    		}
	        	
	            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,BarcodeFormat.QR_CODE, IMAGE_WIDTH, IMAGE_HEIGHT);

	            MatrixToImageWriter.writeToFile(bitMatrix, "png", new File(savePath +fileName));

	        } catch (Exception e) {
	            e.printStackTrace();
	            
	        }
	    }
}
