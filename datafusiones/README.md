<strong><center><font color=blue size=6 face=“黑体”>相关说明</font></center></strong>

###<font color=blue size=4 face=“黑体”>功能描述：</font>

####1. 创建分群：

选择实体---选择标签(标识)---输入对应的变量名和默认值---输入分群名称和分群描述---sql语句和相应的信息存入Mysql相应的表

####2. 查看分群：

界面选择分群，点击查看按钮---Mysql读出相应的分群信息和Sql语句---执行Sql语句获得ElasticSearch中的相应实体---分页展示所得实体列表

####3. 查看分群详情：

界面选择相应的实体ID---点击查看详情按钮---跳转至详情页，展示相应实体的标签和标识

####4. 编辑分群：

跳转至创建分群界面

####5. 删除分群：

删除Mysql中相应的记录

####6. 实体画像：

选择实体---输入完整标识或部分标识---显示查询模糊结果---点击某个实体的查看详情按钮---跳转至详情页面展示实体标签和标识

###<font color=blue size=4 face=“黑体”>综上所述：</font>

所涉及的ElasticSearch中的查询内容仅涉及实体ID、实体标签和实体标识，故设计ElasticSearch中的结构为：

<pre class=”brush: json; gutter: true;”>
{"entityID":"","identification":[{"iid":"","isource":["","",""]}],"label":[{"lid":"","lsource":["","",""]}]}
</pre>

详细示例见json文件

其中entityID为实体ID，identification为实体标识，包含iid(实体标识内容)和isource(实体标识来源)，label为实体标签，包含lid(实体标签内容)和lsource(实体标签来源)
因实体分群是以相同实体为基础进行的操作，故每个实体为ElasticSearch中的一个类型(Type)，共设一个索引(index)
###<font color=blue size=4 face=“黑体”>功能/方法映射：</font>
* 创建分群功能只涉及界面和Mysql
* 查看分群功能中的执行Sql语句-------ElasticSearchSql.getData()
* 查看分群详情中的依据实体ID查询实体标签和实体标识-------UserProfile.getOne()
* 编辑分群功能与创建分群相同
* 删除分群功能只涉及界面和Mysql
* 实体画像功能-------UserProfile.fuzzyQuery(),此处模糊查询若为中文，需转码

###<font color=blue size=4 face=“黑体”>ElasticSearch-sql插件在此处的应用说明：</font>
因此处的功能要求可以根据标签或标识搜索特定的人群，且ElasticSearch中的索引结构设计为嵌套，故设计基础sql语句如下，用户可根据基础查询语句编辑复杂查询语句：

* select ? from INDEX where nested ( ' identification ' , identification.iid = ' ? ' )-------根据标识查询
* select ? from INDEX where nested ( ' label ' , label.lid = ' ? ' )-------根据标签查询
