package com.stock.common.constants;


 /**
 * 常量类.
 * @author <a href="mailto:taofeng@zjport.gov.cn">taofeng</a>
 * @version $Id$   
 * @since 2.0
 */
public class Constants {
	
	/** 成功字符 */
	public static final String SUCCESS = "success";
	
	/** 失败字符 */
	public static final String FAILURE = "failure";
	
	/** 异常字符 */
	public static final String EXCEPTION = "exception";
	
	/** 字符0 */
	public static final String ZERO = "0";
	
	/** 字符1 */
	public static final String ONE = "1";
	
	/** 字符2 */
	public static final String TWO = "2";
	
	/** Redis缓存前缀 */
	public static final String CACHE_FLAG = "SGN:";
	
	/** Redis缓存间隔符号 */
	public static final String CACHE_SPLIT = "_";
	
	/** 短信发送URL */
	public static final String SMS_SENDURL ="http://www.tosms.cn/Api/SendSms.ashx";
	
	/** 短信发送用户名 */
	public static final String SMS_USERNAME ="zy87302728"; 
	
	/** 短信发送密码 */
	public static final String SMS_PASSWORD ="9389796395346BE90FD6D4E57DC4D479"; 
	
    /** 报销费code*/
    public static final String REIMBURSEMENT_CODE="BXF";
    /** 员工*/
    public static final String EMPLOYEE="员工";
    /** 客户*/
    public static final String CUSTOMER="客户";
    /** 登录组织架构*/
    public static final String LOGINING_ACCESS_GROUP="loginingAccessgroup";
    /** 登录的accessgroup*/
    public static final String LOGINING_TRUE_ACCESS_GROUP="loginingTrueAccessgroup";
    /** 登录公司名字*/
    public static final String LOGINING_COMPANY_NAME="loginingCompanyName";
    /** 登录公司id*/
    public static final String LOGINING_COMPANY_ID="loginingCompanyId";
    /** 登录公司code*/
    public static final String LOGINING_COMPANY_CODE="loginingCompanyCode";
    /** 登录职位*/
    public static final String LOGINING_DUTIES="duties";
    /** 登录员工id*/
    public static final String LOGINING_EMPLOYEE_ID="employeeId";
    /** 登录员工name*/
    public static final String LOGINING_EMPLOYEE_NAME="employeeName";
    /** 登录预约者id*/
    public static final String LOGINING_RESERVE_ID="reserveId";
    /** 登录预约者name*/
    public static final String LOGINING_RESERVE_NAME="reserveName";
    /** 登录用户信息*/
    public static final String LOGINING_USER="loginingUser";
    /** 登录用户角色ID（只存一个角色ID）*/
    public static final String LOGINING_ROLE_ID="loginingRoleId";
    /** 微信端登录用户信息*/
    public static final String LOGINING_USER_WECHAT="user";
    /** 默认件数单位*/
    public static final String DEFAULT_NUMBER_UNIT="CASES";
    /** 默认件数单位简称*/
    public static final String DEFAULT_NUMBER_UNIT_ABBRE="CAS";
    /** 默认体积单位*/
    public static final String DEFAULT_VOLUME_UNIT="CBM";
    /** 默认重量单位*/
    public static final String DEFAULT_WEIGHT_UNIT="KGS";
    /** 人民币*/
    public static final String RMB="RMB";
    /** 美元 */
    public static final String USD="USD";
    /** update */
    public static final String UPDATE="update";
    /** insert */
    public static final String INSERT="insert";
    /**进货编号**/
    public static final String JHBH = "zyjh_code";
    /**提货编号**/
    public static final String THBH = "zyth_code";
    /**提现编号**/
    public static final String TXBH = "zytx_code";
    /**订单**/
    public static final String ORDER_TYPE_ONE = "订单";
    /**提货**/
    public static final String ORDER_TYPE_TWO = "提货";
    /**通过**/
    public static final String CHECK_STATUS_PASS = "通过";
    /**不通过**/
    public static final String CHECK_STATUS_REFUSE = "不通过";
    /**待审核**/
    public static final String CHECK_STATUS_WAIT = "待审核";
    /**未审核**/
    public static final String CHECK_STATUS_NOT = "未审核";
    /**审核:宣教员**/
    public static final String CHECK_STATUS_TEACHER = "宣教员";
     /**审核:课程**/
     public static final String CHECK_STATUS_COURSE = "课程";
     /**审核:线路模版**/
     public static final String CHECK_STATUS_SOURCE = "线路模版";
     /**审核:线路**/
     public static final String CHECK_STATUS_ROUTELAUNCHER = "线路";
     /**审核:线路**/
     public static final String CHECK_STATUS_BASE = "基地";
//     /**审核:宣教员**/
//     public static final String CHECK_STATUS_TEACHER = "宣教员";
    /**普通会员**/
    public static final String IDENTITY_ORDINARY_MEMBER = "普通会员";
    /**
	 * 七牛云接口参数
	 */
	public static final String QINIU_ACCESS_KEY = "Ga0hcTdMRgmToFRm-n91BdiHrs9HPkm1K8wZTC_A";

	public static final String QINIU_SECRET_KEY = "6RVUr25ykVmqjfENvBnMA_52YK-gX2Z8U6enfy7M";
	
	public static final String QINIU_IMG_BUCKET = "slianclass";
	
	public static final String QINIU_HOST = "http://p5gqt1b4h.bkt.clouddn.com/";
    /**主题基础数据ID**/
    public static final int THEME_PARENT_ID = 31;
    /**学院管理员角色名**/
    public static final String COLLEGE_ADMIN_ROLE_NAME = "学院管理员";
    /**宣教员角色名**/
    public static final String TEACHER_ROLE_NAME = "宣教员";
    /**学院管理员角色ID**/
    public static final int COLLEGE_ADMIN_ROLE_ID = 5;
    /**宣教员角色ID**/
    public static final int TEACHER_ROLE_ID = 6;
    /**超级管理员角色ID**/
    public static final Integer ADMIN_ROLE_ID = 1;
    /**启用**/
    public static final String STATUS_ENABLE = "启用";
    /**禁用**/
    public static final String STATUS_DISABLE = "禁用";
    /**审核状态_通过**/
    public static final String CHECK_STATUS_READY="已审核";
    
    
}
