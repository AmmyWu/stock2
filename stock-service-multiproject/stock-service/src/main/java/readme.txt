slian-service 包的命名规范及含义

总体考虑：由于系统庞大，设计的业务非常多，所以采用按模块进行package的定义

具体含义
一、基础服务 service 接口与实现
	com.slian.service.common
		用于定义通过的服务接口，暂时没有模块归属的接口
	com.slian.service.common.impl
		用于定义通过的服务接口实现，暂时没有模块归属的接口实现
		
	特别说明：
		CommonService.java 接口，采用泛型进行定义，
		用于规范所有业务模块service接口定义 增(insert),删(deleteById,deleteByIds),改(update),查(getById,getByKey,getByPage)方法

二、通用工具类包
	com.slian.service.utils
		用于统一系统通用的工具类型，包括service层所有的模块，controller也能够调用该工具类

三、分模块工具类包，所以模块均有下列包结构
	com.slian.service.模块名
		定义所有模块的service接口，所有的业务模块接口均需要继续基础模块的BaseService接口 ;
		
		例如设备管理模块接口
			public interface EquipmentService extends BaseService<Equipment> 
	
	com.slian.service.模块名.impl
		定义具体模块的service实现，注入模块对应的Dao（*Mapper.java）及其他模块的service对象。
		
		特别说明：
			所有service实现，仅仅能够注入其相关的Dao对象，不允许注入其他功能模块的Dao，如需要访问其他模块的数据，需通过其对应的serivce来调用。
			
			比如：在设备管理的service实现类中，需要访问员工数据，需要将*EmployeeServiceImpl对象注册到该类中，而不是将其EmployeeMapper对象注入进来

	com.slian.ervice.模块名.utils
		模块相关的工具类
	
	

	

	
  


