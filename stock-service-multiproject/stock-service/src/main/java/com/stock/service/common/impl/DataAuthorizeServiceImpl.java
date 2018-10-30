/**
 * 崇新OA系统
 */
package com.stock.service.common.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.common.util.ReflectionUtil;
import com.stock.dao.util.DataAuthorityRegister;
import com.stock.service.common.DataAuthorizeService;


/**
 * @author stocksoft.com 2017年5月8日 下午5:35:44
 * 
 */
@Service("dataAuthorizeService")
public class DataAuthorizeServiceImpl  implements DataAuthorizeService{


	@Autowired
	private HttpSession session;
	
	
	/**
	 * 
	 * setMappExByAtuhorization:(这里用一句话描述这个方法的作用). 
	 * 通过设置扩展Mapper中的参数，进行数据访问授权控制。
	 * 
	 * @author chengxl
	 * @param para：包括accessGroup字段，用于标识记录属于哪个部门
	 * @param dataBelongFieldSetMethod :标识记录属于哪个员工的字段的set方法，应属于para类的属性
	 * @since JDK 1.7
	 */
	public int setMappExParaByAtuhorization(Object paraOjbect,String dataBelongFieldSetMethod){
		
		
		Thread currentTh = Thread.currentThread();

		// 不需要数据授权
		if (!DataAuthorityRegister.isDataUnderControl(currentTh.getId()))
			return 0;

		Set<Integer> orgList = DataAuthorityRegister.getOrgList(currentTh
				.getId());

		List<Integer> orgStringList = new ArrayList<Integer>();

		if (orgList.size() ==0 || (orgList.size() == 1 && orgList.contains(new Integer(-1)))) {
			
			Integer employeeId = ((Integer) session.getAttribute("employeeId"));
		
			try {
						
				
				Method setMethod = paraOjbect.getClass().getMethod(
								dataBelongFieldSetMethod, new Class[] { Integer.class });
						
						

				Object[] arguments = new Object[] { employeeId };
				
				// 执行方法
				setMethod.invoke(paraOjbect, arguments);

			} catch (NoSuchMethodException | SecurityException
						| IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
		} else {

			for (Integer org : orgList) {
				orgStringList.add(org);
			}
		
			try {
					
					Method setMethod = paraOjbect.getClass().getMethod(
							"setAccessGroupList", new Class[] { List.class });
					
		
					Object[] arguments = new Object[] { orgStringList };
					// 执行方法
					setMethod.invoke(paraOjbect, arguments);

				} catch (NoSuchMethodException | SecurityException
						| IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}

		return 0;

		
	}

	
	/**
	 * 根据角色对应的组织架构进行授权
	 * @see com.seawin.service.common.DataAuthorizeService#setExampleByAuthorization(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public  void setExampleByAuthorization(Object example) {

		// List<Object> criteriaList = new ArrayList<Object>();

		try {
			Method getOredCriteria = example.getClass().getMethod(
					"getOredCriteria");

			// 执行方法，活动example已创建的criteria
			Object eListObject = getOredCriteria.invoke(example);

			
			//该list指向已创建的 oredCriteria
			List<Object> tCriteriaList = (ArrayList<Object>) eListObject;

			if (tCriteriaList.size() == 0) {

				// criteriaList.add(example.createCriteria());
				Method createCriteria = example.getClass().getMethod(
						"createCriteria");

				// 执行方法，创建一个新的criteria，并在tCriteriaList存在
				createCriteria.invoke(example);

			}

			setCriteriasByAuthorization(tCriteriaList);

		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 为每个criteria添加数据权限
	private  int setCriteriasByAuthorization(List<Object> tCriteriaList) {

//		DataAuthorityRegister dataAuthorityRegister = (DataAuthorityRegister) session
//				.getAttribute("dataAuthorityRegister");
//
//		if (dataAuthorityRegister == null)
//			return 0;

		Thread currentTh = Thread.currentThread();

		// 不需要数据授权
		if (!DataAuthorityRegister.isDataUnderControl(currentTh.getId()))
			return 0;

		Set<Integer> orgList = DataAuthorityRegister.getOrgList(currentTh
				.getId());

		List<Integer> orgStringList = new ArrayList<Integer>();

		if (orgList.size() ==0 || (orgList.size() == 1 && orgList.contains(new Integer(-1)))) {
			
			Integer employeeId = ((Integer) session.getAttribute("employeeId"));

			for (Object criteria : tCriteriaList) { // example.getOredCriteria()

					try {
					Method andCreatorEqualTo = criteria.getClass().getMethod(
							"andCreatorEqualTo", new Class[] { Integer.class });

					Object[] arguments = new Object[] { employeeId };
					// 执行方法
					andCreatorEqualTo.invoke(criteria, arguments);

				} catch (NoSuchMethodException | SecurityException
						| IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {

			for (Integer org : orgList) {
				orgStringList.add(org);
			}
			for (Object criteria : tCriteriaList) { // example.getOredCriteria()
				try {
					Method andAccessGroupIn = criteria.getClass().getMethod(
							"andAccessGroupIn", new Class[] { List.class });

					Object[] arguments = new Object[] { orgStringList };
					// 执行方法
					andAccessGroupIn.invoke(criteria, arguments);

				} catch (NoSuchMethodException | SecurityException
						| IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
	
		}

		return 0;

	}
	
	


	/**
	 * 插入修改是添加记录创建人，创建时间，访问组等信息
	 * 
	 * @author taofeng
	 * @param model
	 * @param type 
	 * @since JDK 1.7
	 */
	@Override
	public void addDataAuthorizeInfo(Object model,String type) {

		if("insert".equals(type))
			this.setValueForInsert(model);
		
		if("update".equals(type))
			this.setValueForUpdate(model);
		
	}
	
	
	// 插入时，设置creator 和 accessGroup
	private void setValueForInsert(Object model) {


		ReflectionUtil.setObjectValues(model, "setCreator", "class java.lang.Integer",
				 session.getAttribute("employeeId") == null?-1:(Integer) session.getAttribute("employeeId") );

		ReflectionUtil.setObjectValues(model, "setCreateTime", "class java.util.Date",
				new Date());
		
		ReflectionUtil.setObjectValues(model, "setAmender", "class java.lang.Integer",

				 session.getAttribute("employeeId") == null?-1:(Integer) session.getAttribute("employeeId"));

		ReflectionUtil.setObjectValues(model, "setAmendTime", "class java.util.Date",
				new Date());
		
		ReflectionUtil.setObjectValues(model, "setAccessGroup",
				"class java.lang.Integer", session.getAttribute("loginingAccessgroup") == null?-1:(Integer) session.getAttribute("loginingAccessgroup") );

		this.setValueForUpdate(model);
	}

	// 修改时，设置amender 和 amendTime
	private void setValueForUpdate(Object model) {

		ReflectionUtil.setObjectValues(model, "setAmender", "class java.lang.Integer",

				 session.getAttribute("employeeId") == null?-1:(Integer) session.getAttribute("employeeId"));

		ReflectionUtil.setObjectValues(model, "setAmendTime", "class java.util.Date",
				new Date());

	}
	
	

	
}
