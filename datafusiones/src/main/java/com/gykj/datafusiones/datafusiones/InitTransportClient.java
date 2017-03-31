package com.gykj.datafusiones.datafusiones;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/** 
* @author  刘博伟 
* @version 创建时间：2017年3月27日 下午4:39:48 
* 初始化TransportClient 
*/
public class InitTransportClient {
	
	public static Client initClient(){	
		
		InputStream inStream = InitTransportClient.class.getClassLoader().getResourceAsStream("cluster.properties");
		Properties prop = new Properties();  
		try {
			prop.load(inStream);	
		Settings settings = Settings.builder().put("cluster.name", prop.getProperty("clusterName")).build();
		TransportClient client;
			client = new PreBuiltTransportClient(settings)
			         .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(prop.getProperty("host1")), 9300));
//可添加多台
//			         .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(prop.getProperty("host2")), 9300));
			return client;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
	}
	
}
 