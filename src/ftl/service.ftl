package ${packageName};

<#if !implflag>
import java.util.List;
import java.util.Map;

import ${modelpackageName}.${className};
import ${parentPackageName}.pojo.vo.RequestResultVO;

public interface ${className}Service {

	public RequestResultVO insert(${className}  ${modelName});

	public RequestResultVO update(${className}  ${modelName});

	public RequestResultVO delete(List<Integer> ${id}s);

    public Map<String,Object> getByPage(String keys, Integer pageSize,Integer pageNow);

    }


<#else>


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${parentPackageName}.common.exceptions.BizException;
import ${parentPackageName}.common.tool.log.ErrorLoggers;
import ${parentPackageName}.common.tool.log.LogUtil;
import ${parentPackageName}.dao.page.Page;
import ${parentPackageName}.service.common.util.DateJsonValueProcessor;
import ${parentPackageName}.service.sys.utils.HttpResponseConstants.Public;
import ${parentPackageName}.pojo.vo.RequestResultVO;
import ${parentPackageName}.service.common.DataAuthorizeService;
import ${parentPackageName}.service.sys.utils.ResultBuilder;
import ${parentPackageName}.service.sys.CommonService;
import ${parentPackageName}.dao.mapper.stock.${className}Mapper;
import ${parentPackageName}.dao.model.stock.${className};
import ${parentPackageName}.dao.model.stock.${className}Example;

import ${parentPackageName}.pojo.vo.stock.${className}VO;
import ${parentPackageName}.service.stock.${className}Service;

@Service
public class ${className}ServiceImpl implements ${className}Service {

	@Autowired
	private ${className}Mapper ${modelName}Mapper;

	@Autowired
	private DataAuthorizeService dataAuthorizeService;

	private CommonService<${className}, ${className}Mapper, ${className}Example> commonService;
	//注入commonService
	@Resource(name = "commonService")
	public void setCommonService(CommonService<${className}, ${className}Mapper, ${className}Example> commonService) {
		this.commonService = commonService;
	}

	@Override
	public RequestResultVO insert(${className} ${modelName}) {
		if(${modelName} == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(${modelName}, "insert");
    ${modelName}Mapper.insert(${modelName});
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_200, "");
	}

	@Override
	public RequestResultVO update(${className} ${modelName}) {
		if(${modelName} == null || ${modelName}.get${upperId}() == null){
			throw new BizException(Public.ERROR_700);
		}
		dataAuthorizeService.addDataAuthorizeInfo(${modelName}, "update");
    ${modelName}Mapper.updateByPrimaryKeySelective(${modelName});
		return ResultBuilder.buildSuccessResult(Public.SUCCESS_300, "");
	}

	@Override
	public RequestResultVO delete(List<Integer> ${id}s) {
    if(${id}s == null || ${id}s.size() == 0){
    throw new BizException(Public.ERROR_700);
    }
    ${className}Example ${modelName}Example = new ${className}Example();
    ${modelName}Example.createCriteria().and${upperId}In(${id}s);
    ${modelName}Mapper.deleteByExample(${modelName}Example);
    return ResultBuilder.buildSuccessResult(Public.SUCCESS_400, "");
    }

    @Override
    public Map<String, Object> getByPage(String keys, Integer pageSize,
    Integer pageNow) {
    ${className}Example ${modelName}Example = new ${className}Example();
    this.setCriteria(keys, ${modelName}Example);
    int totalrecords = ${modelName}Mapper.countByExample(${modelName}Example);

    Page page = new Page();
    page.setBegin(pageNow);
    page.setLength(pageSize);
    ${modelName}Example.setOrderByClause("${primaryKey} desc");
    ${modelName}Example.setPage(page);
    List<${className}> ${modelName}s = ${modelName}Mapper.selectByExample(${modelName}Example);

    Map<String, Object> map = new HashMap<String, Object>();
    JsonConfig config = new JsonConfig();
    config.setIgnoreDefaultExcludes(false);
    config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
    try {
    map.put("aaData", JSONArray.fromObject(this.creatVos(${modelName}s), config));
    } catch (Exception e) {
    LogUtil.error(ErrorLoggers.ERROR_LOGGER, e.getMessage());
    throw new BizException(Public.ERROR_100);
    }
    map.put("recordsTotal", totalrecords);
    map.put("recordsFiltered", totalrecords);

    return map;
    }
    private void setCriteria(String keys, ${className}Example ${modelName}Example) {
    if (keys == null || "{}".equals(keys))
    return;
    //JSONObject jKeys = JSONObject.fromObject(keys);
    //Criteria criteria = ${modelName}Example.createCriteria();

    }
    private List<${className}VO> creatVos(List<${className}> ${modelName}s) throws Exception{
        List<${className}VO> ${modelName}VOs = new ArrayList<${className}VO>();
            for(${className} ${modelName} : ${modelName}s){
            ${className}VO ${modelName}VO = new ${className}VO();
            BeanUtils.copyProperties(${modelName}, ${modelName}VO);
            commonService.addBaseModel(${modelName}, ${modelName}VO);
            ${modelName}VOs.add(${modelName}VO);
            }
            return ${modelName}VOs;
            }
            }




</#if>