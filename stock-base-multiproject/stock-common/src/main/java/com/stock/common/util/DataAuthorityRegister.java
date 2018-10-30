package com.stock.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataAuthorityRegister {

	private static Map<Long,AuthorityRegisterUnit> dataAuthorityMap = new HashMap <Long,AuthorityRegisterUnit> ();//<threadId,单元>
	
	//构造
	public DataAuthorityRegister(){
		dataAuthorityMap=new HashMap <Long,AuthorityRegisterUnit> ();
	}
	//添加一条授权
	public synchronized static boolean  addNewAuthority(Long threadId,Set<Integer> orgList,boolean underControl){
		try{
			Set<Long> threadIdSet= dataAuthorityMap.keySet();
			Date dateNow = new Date();
			AuthorityRegisterUnit authorityRegisterUnit=new AuthorityRegisterUnit(dateNow,orgList,underControl);
			//AuthorityRegisterUnit authorityRegisterUnit=new AuthorityRegisterUnit(dateNow,orgList,underControl,employeeList);
			//清除超时数据授权
			for(Long cthreadId:threadIdSet){
				if(dateNow.getTime()- dataAuthorityMap.get(cthreadId).getRegisterTime().getTime()>30000){
					//时长超过30秒
					dataAuthorityMap.remove(cthreadId);
				}
			}
			dataAuthorityMap.put(threadId, authorityRegisterUnit);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	//删除一条授权
	public synchronized static boolean delAuthority(Long threadId){
		try{
			dataAuthorityMap.remove(threadId);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public synchronized static Set<Integer>  getOrgList(Long thId){
		return dataAuthorityMap.get(thId).getOrgSet();
	}
	public synchronized static Set<Integer> getEmlpoyee(Long thId){
		return dataAuthorityMap.get(thId).getEmployeeSet();
	}
	
	public synchronized static boolean isDataUnderControl(Long thId){
		if(dataAuthorityMap==null || thId==null || dataAuthorityMap.get(thId)==null){
			return false;
		}
		return dataAuthorityMap.get(thId).isDataUnderControl();
	}
	
	public synchronized void show(){
		for(Long thId:dataAuthorityMap.keySet()){
			System.out.println("thread:"+thId);
			AuthorityRegisterUnit ARU=dataAuthorityMap.get(thId);
			System.out.println("    time:"+ARU.getRegisterTime().toString());
			if(ARU.isDataUnderControl()){
				for (Integer org:ARU.getOrgSet()){
					System.out.println("        org:"+org);
				}
			}
		}
	}
	
}

class AuthorityRegisterUnit{
	private Set<Integer> orgSet;
	private Set<Integer> employeeSet;
	private Date registerTime;
	private boolean dataUnderControl;
	
	

	AuthorityRegisterUnit(Date date,Set<Integer> set,boolean underControl,Set<Integer> eSet){
		registerTime=date;
		orgSet=set;
		dataUnderControl=underControl;
		employeeSet=eSet;
	}
	AuthorityRegisterUnit(Date date,Set<Integer> set,boolean underControl){
		registerTime=date;
		orgSet=set;
		dataUnderControl=underControl;
		employeeSet=null;
	}
	
	public boolean isDataUnderControl() {
		return dataUnderControl;
	}
	public Set<Integer> getOrgSet() {
		return orgSet;
	}
	public Set<Integer> getEmployeeSet() {
		return employeeSet;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	
}
