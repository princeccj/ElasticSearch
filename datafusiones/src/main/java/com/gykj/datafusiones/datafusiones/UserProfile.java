package com.gykj.datafusiones.datafusiones;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

/** 
* @author  刘博伟 
* @version 创建时间：2017年3月27日 下午5:41:31 
* 实体画像/查看分群详情
*/
public class UserProfile {

	/** 获取client*/
	private static Client client = InitTransportClient.initClient();
	
	/** 获取ES中的索引名*/
	private static String index=null;
	
	
	/*
	 * 根据用户选择的实体及输入框输入的标识模糊查询内容，对每个实体进行模糊查询
	 * @param: entity 实体名，对应ES中的type
	 * @param: identification 标识模糊查询内容
	 * @return:包含实体ID及其标识和标签的列表
	 * 
	 * */
	public static SearchHits fuzzyQuery(String entity,String identification){
		
		 QueryBuilder queryBuilder = QueryBuilders.nestedQuery("identification", 
	                QueryBuilders.regexpQuery("identification.iid", ".*"+identification+".*"),org.apache.lucene.search.join.ScoreMode.Avg);
		
		SearchResponse response = client.prepareSearch(index).setTypes(entity)
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(queryBuilder)
		        //用于分页查询，setFrom()用于设置起始位置，setSize()方法用于设置每次查询数量
		        .setFrom(0).setSize(200).setExplain(true)
		        .get();		
		 SearchHits searchHit = response.getHits();
		 System.out.println("共查到数据：" + searchHit.getHits().length);
	 		
		 for (SearchHit it : searchHit) {
	 			System.out.println(it.getSource());
	 	 }
		 
		return searchHit;
	}

	
	/*
	 * 在模糊查询之后，用户选择某个查询出的实体，进入详情页查看实体详情
	 * @param: entity 实体名，对应ES中的type
	 * @param: entityID 实体ID
	 * @return:包含实体ID及其标识和标签的列表
	 * 
	 * */
	public static SearchHits getOne(String entity,String entityID){
		
		SearchResponse response = client.prepareSearch(index).setTypes(entity)
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setQuery(QueryBuilders.matchQuery("entityID", entityID))
		        .get();		
		 SearchHits searchHit = response.getHits();
		 System.out.println("共查到数据：" + searchHit.getHits().length);
	 		
		 for (SearchHit it : searchHit) {
	 			System.out.println(it.getSource());
	 	 }
		 
		return searchHit;
	}
	
	
}
 