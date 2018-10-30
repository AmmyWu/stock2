package ${packageName};

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ${parentPackageName}.dao.model.stock.${className};
import ${parentPackageName}.service.stock.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ${parentPackageName}.common.exceptions.BizException;
import ${parentPackageName}.common.util.CommonUtils;
import ${parentPackageName}.common.util.JsonFastUtil;
import ${parentPackageName}.pojo.vo.RequestResultVO;
import ${parentPackageName}.service.sys.utils.HttpResponseConstants.Public;

@Controller
@RequestMapping(value="/${modelName}")
public class ${className}Controller {

	@Autowired
	private ${className}Service ${modelName}Service;
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insert.do")
	public @ResponseBody RequestResultVO insert(HttpServletRequest request){
		String ${modelName}String = request.getParameter("${modelName}");
${className} ${modelName} = null;
		try{
${modelName} = JsonFastUtil.parseObject(${modelName}String, ${className}.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return ${modelName}Service.insert(${modelName});
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update.do")
	public @ResponseBody RequestResultVO update(HttpServletRequest request){
		String ${modelName}String = request.getParameter("${modelName}");
${className} ${modelName} = null;
		try{
${modelName} = JsonFastUtil.parseObject(${modelName}String, ${className}.class);
		}catch(Exception e){
			throw new BizException(Public.ERROR_700);
		}
		return ${modelName}Service.update(${modelName});
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	public @ResponseBody RequestResultVO delete(HttpServletRequest request){
		String ${id}sString = request.getParameter("${id}s");
		List<Integer> ${id}s;
    try{
${id}s = CommonUtils.idsArrayToList(${id}sString);
    }catch(Exception e){
    throw new BizException(Public.ERROR_700);
    }
    return ${modelName}Service.delete(${id}s);
    }
    /**
    * 分页查询
    * @param request
    * @return
    */
    @RequestMapping(value="/getByPage.do")
    public @ResponseBody Object getByPage(HttpServletRequest request){
    String keys = request.getParameter("keys");
    Integer length = Integer.parseInt(request.getParameter("length"));
    Integer start = Integer.parseInt(request.getParameter("start"));
    return ${modelName}Service.getByPage(keys, length, start);
    }
    }
