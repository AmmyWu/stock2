/**
  * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.service.sys.httpmodel
 * 类/接口名	: HttpJson
 * 版本信息	: 1.00
 * 新建日期	: 2017年5月7日下午5:50:19
 * 作者		: chengxl
 * 修改历史	: 2017年5月7日下午5:50:19
 * Copyright (c) zjport Co., Ltd,2017.All rights reserved.
 */
package com.stock.common.tool.http;

@SuppressWarnings("serial")
public class RequestResultVO implements java.io.Serializable {


		
		/**
		 * code:TODO
		 * @since JDK 1.7
		 */
		private int code = 0;//表示成功
		/**
		 * message:TODO
		 * @since JDK 1.7
		 */
		private String message = "";
		/**
		 * obj:TODO
		 * @since JDK 1.7
		 */
		private Object data = null;
		
//		private boolean success =true;
		
		


		/**
		 * Create a new instance of RequestResult
		 */
		public RequestResultVO() {
			// TODO Auto-generated constructor stub
		}



		/**
		 * code. 
		 * 
		 * @return  the code 
		 * @since   JDK 1.7 
		 */
		public int getCode() {
			return code;
		}


		/**
		 * code. 
		 * 
		 * @param   code    the code to set 
		 * @since   JDK 1.7 
		 */
		public void setCode(int code) {
			this.code = code;
		}


		/**
		 * message. 
		 * 
		 * @return  the message 
		 * @since   JDK 1.7 
		 */
		public String getMessage() {
			return message;
		}


		/**
		 * message. 
		 * 
		 * @param   message    the message to set 
		 * @since   JDK 1.7 
		 */
		public void setMessage(String message) {
			this.message = message;
		}




		/**
		 * data. 
		 * @return  the data 
		 * @since   JDK 1.7 
		 */
		public Object getData() {
			return data;
		}




		/**
		 * data. 
		 * @param   data    the data to set 
		 * @since   JDK 1.7 
		 */
		public void setData(Object data) {
			this.data = data;
		}


		/**
		 * content. 
		 * @param   setContent    the setContent to set 
		 * @since   JDK 1.7 
		 */
		public void setContent(int code,String message,Object data){
			this.code = code;
			this.message = message;
			this.data = data;
		}


	}

