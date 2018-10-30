package ${packageName};

import ${modelPackageName}.${className};
import com.stock.pojo.vo.BaseModelVO;

public class ${className}VO extends ${className} {

	private BaseModelVO baseModel;

	public BaseModelVO getBaseModel() {
		return baseModel;
	}
	public void setBaseModel(BaseModelVO baseModel) {
		this.baseModel = baseModel;
	}
}