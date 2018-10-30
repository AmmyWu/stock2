package com.stock.common.constants;

/**
 * ClassName	: HttpResponseConstants
 * Function		: TODO
 * date 		: 2017年9月22日下午3:51:36
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
public interface HttpResponseConstants {
	/**
	 * 公共
	 */
	public static class Public {
		//response code
		/**0**/
		public static final Integer SUCCESS_CODE = 0;
		/**1**/
		public static final Integer ERROR_CODE = 1;
		/**
		 * session过期code
		 */
		public static final Integer SESSION_ERROR_CODE = -1;
		/**success**/
		public static final String SUCCESS = "success";
		/**保存成功**/
		public static final String SUCCESS_100 = "保存成功";
		/**新增成功**/
		public static final String SUCCESS_200 = "新增成功";
		/**修改成功**/
		public static final String SUCCESS_300 = "修改成功";
		/**删除成功**/
		public static final String SUCCESS_400 = "删除成功";
		/**操作成功**/
		public static final String SUCCESS_500 = "操作成功";
		/**error**/
		public static final String ERROR = "error";
		/**查询失败 **/
		public static final String ERROR_100 = "查询失败";
		/**新增失败 **/
		public static final String ERROR_200 = "新增失败";
		/**修改失败**/
		public static final String ERROR_300 = "修改失败"; 
		/**删除失败 **/
		public static final String ERROR_400 = "删除失败";
		/**导入失败**/
		public static final String ERROR_500 = "文件格式错误，导入失败";	
		/**操作失败**/
		public static final String ERROR_600 = "操作失败";
		/**参数错误**/
		public static final String ERROR_700 = "参数错误";
		/**未知异常**/
		public static final String ERROR_800 = "未知异常";
		/**账号或密码错误**/
		public static final String ERROR_900 = "账号或密码错误";
		/**编号重复 **/
		public static final String ERROR_1000 = "编号重复";
		/**审核数据已被删除 **/
		public static final String ERROR_1100 = "数据不存在或已被删除";
		/**所请求的数据未经审核，无法查看 **/
		public static final String ERROR_1200 = "所请求的数据未经审核，无法查看";
		public static final Integer ERROR_1200_CODE = 1200;
	}

	/**
	 * app
	 */
	public static enum APPEnum {

	    UID_REQUIRE(100, "uid缺失"),
	    UID_INVALID(101, "uid无效"),
	    TOKEN_REQUIRE(110, "token缺失"),
	    TOKEN_OVERDUE(111, "token过期"),
	    SIGN_REQUIRE(120, "sign缺失"),
	    SIGN_INVALID(121, "sign无效"),
	    ACCOUNT_REQUIRE(130,"手机号缺失"),
	    ACCOUNT_INVALID(131,"手机号已存在"),
	    PASSWORD_REQUIRE(132,"密码缺失"),
	    USERNAME_REQUIRE(133,"用户名缺失"),
	    USERNAME_INVALID(134,"用户名已存在"),
	    NEWS_NO_EXISTENCE(200,"公告不存在或已被删除"),
	    MSG_NO_EXISTENCE(210,"暂无任何通知");
	    private Integer status;
	    private String description;

	    public String getDescription() {
	        return description;
	    }

	    public Integer getStatus() {
	        return status;
	    }

	    private APPEnum(Integer status, String description) {
	        this.status = status;
	        this.description = description;
	    }
	}
	/**
	 * 客户
	 */
	public static class Customer{
		/**姓名不能为空**/
		public static final String ERROR_100 = "姓名不能为空";
		/**手机号不能为空**/
		public static final String ERROR_200 = "手机号不能为空";
	}
	/**
	 * 订单
	 */
	public static class Order{
		
	}

	public static class Course{
		/**课程与线路有关联**/
		public static final String ERROR_100 = "课程与线路有关联，不能删除";
		public static final String ERROR_200 = "课程正在审核，不能删除";
	}


	public static class RouteLauncher{
		public static final String ERROR_100="线路已被预约者预约，不能够删除";

		public static final String ERROR_200="线路正在审核，不能够删除";
	}

	public static class Route{
		public static final String ERROR_100="模板线路已被推出线路使用，不能够删除";

		public static final String ERROR_200="线路正在审核，不能够删除";
	}
	/**
	 * 预约者
	 */
	public static class Reserve{
		/**学号/工号/手机号已存在**/
		public static final String ERROR_100 = "学号/工号/手机号已存在";
		/**手机号对应的账号已存在**/
		public static final String ERROR_200 = "手机号对应的账号已存在";
	}

	public static class Portal{

		public static final String ERROR_100 = "才可再次预约";

		public static final String ERROR_200 = "已取消预约4次，不可再次预约";

		public static final String ERROR_300 = "已预约过课程，不可再次预约";

		public static final String SUCCESS_100 = "满足预约条件，可以进行预约";

		public static final String SUCCESS_200 = "取消预约成功";

	}
	public static class Message{

		/**邮件提醒发送异常 **/
		public static final String ERROR_100 = "邮件提醒发送异常";
		/**短信提醒发送异常 **/
		public static final String ERROR_200 = "短信提醒发送异常";
	}

	class ssoClient{
		public static final String CLIENT_ID = "还没有";
	}
}
