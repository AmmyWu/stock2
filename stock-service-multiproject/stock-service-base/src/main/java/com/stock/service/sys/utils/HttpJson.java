package com.stock.service.sys.utils;

@SuppressWarnings("serial")
public class HttpJson implements java.io.Serializable {

	private boolean success = false;// �Ƿ�ɹ�?
	private String msg = "";// ��ʾ��Ϣ
	private Object obj = null;// ������Ϣ

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}