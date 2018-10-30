package com.stock.common.util;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Http链接共同类
 * Created by JD on 2017/04/25.
 */
public class HttpConnectUtil {
	private static Logger log = LogManager.getLogger("File");
	private static int m_HttpTimeOut = 30000;

	/**
	 * GET
	 */
	public static String doHttpGet(String sUrl) {
		String sResult = "";

		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			//创建连接
			URL url = new URL(sUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setConnectTimeout(m_HttpTimeOut);
			connection.setReadTimeout(m_HttpTimeOut);

			//读取响应
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

			if (sb.length() > 0) sResult = sb.substring(0, sb.length() - 1);
			log.debug("GET URL:" + sUrl + "\r\nRESULT:" + sResult);
		} catch (Exception e) {
			log.error("GET URL:" + sUrl + "\r\ne:" + e.getMessage());
		} finally {
			try {
				if (reader != null) reader.close();
				// 断开连接
				if (connection != null) connection.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sResult;
	}

	/**
	 * POST
	 */
	public static String doHttpPost(String sUrl, String sParam) {
		String sResult = "";

		HttpURLConnection connection = null;
		DataOutputStream out = null;
		BufferedReader reader = null;
		try {
			//创建连接
			URL url = new URL(sUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestMethod("POST");
			connection.setInstanceFollowRedirects(true);
			connection.setConnectTimeout(m_HttpTimeOut);
			connection.setReadTimeout(m_HttpTimeOut);

			//POST请求
			out = new DataOutputStream(connection.getOutputStream());
			out.write(sParam.getBytes("UTF-8"));//这样可以处理中文乱码问题
			out.flush();

			//读取响应
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

			if (sb.length() > 0) sResult = sb.substring(0, sb.length() - 1);
			log.debug("POST URL:" + sUrl + "\r\nPARAM:" + sParam + "\r\nRESULT:" + sResult);
		} catch (Exception e) {
			log.error("POST URL:" + sUrl + "\r\nPARAM:" + sParam + "\r\ne:" + e.getMessage());
		} finally {
			try {
				if (out != null) out.close();
				if (reader != null) reader.close();
				// 断开连接
				if (connection != null) connection.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sResult;
	}

	/**
	 * 发送GET请求
	 */
	public static String sendGet(String sUrl) {
		String sResult = "";
		BufferedReader reader = null;
		try {
			URL url = new URL(sUrl);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.connect();

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

			if (sb.length() > 0) sResult = sb.substring(0, sb.length() - 1);
			log.debug("GET URL:" + sUrl + "\r\nRESULT:" + sResult);
		} catch (Exception e) {
			log.error("GET URL:" + sUrl + "\r\ne:" + e.getMessage());
		} finally {
			try {
				if (reader != null) reader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return sResult;
	}

	/**
	 * 发送POST请求
	 */
	public static String sendPost(String sUrl, String sParam) {
		String sResult = "";
		PrintWriter out = null;
		BufferedReader reader = null;
		try {
			URL realUrl = new URL(sUrl);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=utf-8");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			//POST请求必填
			conn.setDoOutput(true);
			conn.setDoInput(true);

			//设置参数
			out = new PrintWriter(conn.getOutputStream());
			out.print(sParam);
			out.flush();

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

			if (sb.length() > 0) sResult = sb.substring(0, sb.length() - 1);
			//log.debug("POST URL:" + sUrl + "\r\nPARAM:" + sParam + "\r\nRESULT:" + sResult);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("POST URL:" + sUrl + "\r\nPARAM:" + sParam + "\r\ne:" + e.getMessage());
		} finally {
			try {
				if (out != null) out.close();
				if (reader != null) reader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sResult;
	}

	/**
	 * 发送POST请求 参数为UTF-8
	 */
	public static String sendPostUTF8(String sUrl, String sParam) {
		String sResult = "";
		DataOutputStream out = null;
		BufferedReader in = null;
		try {
			URL realUrl = new URL(sUrl);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=utf-8");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new DataOutputStream(conn.getOutputStream());
			out.write(sParam.getBytes("UTF-8"));//这样可以处理中文乱码问题
			out.flush();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

			if (sb.length() > 0) sResult = sb.substring(0, sb.length() - 1);
			log.debug("POST URL:" + sUrl + "\r\nPARAM:" + sParam + "\r\nRESULT:" + sResult);
		} catch (Exception e) {
			log.error("POST URL:" + sUrl + "\r\nPARAM:" + sParam + "\r\ne:" + e.getMessage());
		} finally {
			try {
				if (out != null) out.close();
				if (in != null) in.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sResult;
	}

	/**
	 * URL参数转换
	 */
	public static String getUrlParam(String sParam) {
		String sResult = "";
		try {
			sResult = URLEncoder.encode(sParam, "UTF-8");
		} catch (Exception e) {
			log.error("URL参数转换失败。" + e.getMessage());
		}
		return sResult;
	}

	/**
	 * URL参数转换
	 */
	public static String getUrlParam(Map<String, String> oParam) {
		String sResult = "";
		try {
			for (Map.Entry<String, String> entry : oParam.entrySet()) {
				sResult += "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
			}
			if (StringUtils.isNotEmpty(sResult)) sResult = sResult.substring(1);
		} catch (Exception e) {
			log.error("URL参数转换失败。" + e.getMessage());
		}
		return sResult;
	}

}
