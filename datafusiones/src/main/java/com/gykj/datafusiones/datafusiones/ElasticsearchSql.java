package com.gykj.datafusiones.datafusiones;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;

/** 
* @author  刘博伟 
* @version 创建时间：2017年3月28日 上午10:08:23 
* 创建分群/查看分群 
*/
public class ElasticsearchSql {
	
	static InputStream inStream = InitTransportClient.class.getClassLoader().getResourceAsStream("cluster.properties");
	static Properties prop = new Properties();  
	
	
	/*
	 * 用于创建分群页面Elastiasearch的sql插件sql语句运行的方法，在查看分群处使用此方法执行Sql语句，获得结果
	 * 选择实体后选择级联标签或标识，拼写sql语句传入方法运行
	 * 详细用法见 https://github.com/NLPchina/elasticsearch-sql以及README.md
	 *
	 **/
	public static String getData(String sql){

		try {
			prop.load(inStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Properties properties = new Properties();
		properties.put("url", "jdbc:elasticsearch://"+prop.getProperty("host1")+":9300");
		DruidDataSource dds;
		try {
			dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory
			        .createDataSource(properties);	
		dds.setInitialSize(1);
		Connection connection = dds.getConnection();
//      普通sql语句
//		String sql = "select * FROM test";
//		嵌套查询
//		String sql1="select * FROM test3 where nested('identification', identification.iid='女') ";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
		    System.out.println("标识："+resultSet.getObject("identification")+"\t"+"标签："+resultSet.getObject("label"));
		}
		ps.close();
		connection.close();
		dds.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	

}
 