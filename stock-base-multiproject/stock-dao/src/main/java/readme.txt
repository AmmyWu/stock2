Dao相关知识点：
Example类实现了查询条件的对象化。
"_"分隔的字段名会自动转化为驼峰形式。
oracle nvarchar/nvarchar2类型会转化成Object类型。
oracle指定精度的数值型字段默认转为BigDecimal，不指定精度的数值型默认转为Long。
targetProject属性可以设置为MAVEN，代码将生成在target/generatored-source目录下。
generatorConfiguration节点下添加 <properties resource="jdbc.properties" />，可以在配置中的${...}表示的占位符。获取文件的方式为：Thread.currentThread().getContextClassLoader().getResource(resource)
context节点下添加<plugin type="org.mybatis.generator.plugins.SerializablePlugin"></plugin> 生成的pojo类implements Serializable。
通过enableCountByExample, enableUpdateByExample, enableDeleteByExample, enableSelectByExample, selectByExampleQueryId等属性可以控制是否生成和使用xxxExample类在查询中替代模型类。


Criterion
　　Criterion是最基本，最底层的Where条件，用于字段级的筛选。主要有以下这些：
		field IS NULL
		field IS NOT
		field > value
		field >= value
		field =value
		field <> value
		field <= value
		field < value
		field LIKE value
		field NOT LIKE value
		field BETWEEN value1 AND value2
		field IN (item1, item2,...)
		field NOT IN (item1, item2, ...)
　　LIKE模糊查询的%,?字符只能在构造查询条件是手动指定。
　　Mybatis Generator会为每个字段生成如上所示的Criterion，理论上可以构造任何筛选条件，如果字段较多生成的Example类会很大。
Criteria
　　Criteria包含了Cretiron的集合，同一个Criteria中的各个Cretiron之间为逻辑与(AND)关系。
oredCriteria
　　Example内有一个成员叫oredCriteria，是Criteria的集合，集合中的各个Criteria直接为逻辑或(OR)的关系。