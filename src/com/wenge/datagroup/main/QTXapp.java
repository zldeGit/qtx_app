package com.wenge.datagroup.main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import jdk.nashorn.internal.objects.annotations.Where;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wenge.datagroup.utils.JdbcTemp;
import com.wenge.datagroup.utils.MD5;
import com.wenge.datagroup.utils.SystemRpcService;


public class QTXapp {
	private static final Logger logger = Logger.getLogger(QTXapp.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static MD5 md5 = new MD5();

	static {
		PropertyConfigurator.configure("config" + File.separator + "log4j.properties");
	}

	public static void main(String[] args) {
		JSONObject data = new JSONObject();

		int first_id = 93474;
		new Thread(() ->
		{
			syncCount();
		}
		,"互动量同步").start();
		List<String> list = queryData(first_id);
		while (true) {
			try {
				if (list != null && !list.isEmpty()) {
					for (String result : list) {
						String res = result;
						System.out.println(res);
						JSONObject json = JSONObject.parseObject(res);
						first_id = json.getIntValue("auto_id");
						String website = json.getString("website");
						data.put("site", website);
						data.put("website_name", website);
						String author = json.getString("author");
						data.put("author", author);
						String column_name = json.getString("column_name");
						data.put("channel", column_name);
						data.put("channel_name", column_name);
						data.put("crawler_no", "192.168.10.137");
						String title = json.getString("title");
						data.put("title", title);
						data.put("title_str", title);
						String url = json.getString("url");
						data.put("source_url", url);
						data.put("url", url);
						String uuid = md5.getMD5digest(url, "utf-8");
						data.put("uuid", uuid);
						String content = json.getString("content");
						data.put("content", content);
						Date p_time = json.getDate("pubtime");
						String pubtime = null;

						if (p_time != null) {
							pubtime = sdf.format(p_time);
						}
						data.put("pubtime", pubtime);
						data.put("S", "RMT");
						data.put("insert_time", sdf.format(new Date()));
						logger.info("result: " + data);
						long startTime = System.currentTimeMillis();
						boolean isSuccess = SystemRpcService.kafkaService.send("appdata", System.currentTimeMillis() + "",
								data.toJSONString());
						long endTime = System.currentTimeMillis();
						if (isSuccess) {
							logger.info("Sent QTX data to kafka is successful. uuid= " + uuid
									+ " spent time=" + (endTime - startTime) + "ms.");
						} else {
							logger.info("Sent QTX data to kafka is failed. uuid= " + uuid + " spent time="
									+ (endTime - startTime) + "ms.");
						}
					}
					logger.info("推送完毕，休眠半小时！");
					DoSleep();
				} else {
					logger.info("暂无数据更新，继续休眠！");
					DoSleep();
				}
				list = queryData(first_id);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("发生异常,休眠5分钟");
				DoSleep5minuets();
			}


		}
	}


	public static List<String> queryData(int first_id) {
		String sql = "select auto_id,url,title,website,pubtime,author,pictures,column_name,insert_time,content from sync_caibian_data where auto_id > " + first_id;
		List<Map<String, Object>> list = JdbcTemp.queryForList(sql);
//		System.out.println(list);
		String res = null;
		List<String> list2 = new ArrayList<String>();
		for (Map<String, Object> maps : list) {
			res = JSON.toJSONString(maps, false);
			list2.add(res);
		}
		return list2;
	}

	public static void DoSleep() {
		try {
			Thread.sleep(30 * 60 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void DoSleep5minuets() {
		try {
			Thread.sleep(30 * 60 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//同步互动量
	public static void syncCount() {
		while (true) {
			logger.info("互动量推送开始");
			int count=0;
			List<String> list = queryData();
			for (String result : list) {
				JSONObject data = new JSONObject();
				String res = result;
				System.out.println(res);
				JSONObject json = JSONObject.parseObject(res);
				//必传参数
				data.put("insert_time", sdf.format(new Date()));
				data.put("updateTime", sdf.format(new Date()));
				String url = json.getString("url");
				String uuid = md5.getMD5digest(url, "utf-8");
				data.put("uuid", uuid + new Date().getTime());
				data.put("md5", uuid);
				data.put("S", "RMT");
				data.put("url", url);
				//封装互动量
				JSONObject num = new JSONObject();

				long read_num = json.getLongValue("read_num");
				long share_num = json.getLongValue("share_num");
				long like_num = json.getLongValue("like_num");
				long comment_num = json.getLongValue("comment_num");
				num.put("share_count", read_num);
				num.put("read_count", share_num);
				num.put("up_count", like_num);
				num.put("cmt_count", comment_num);
				num.put("id", uuid);
				num.put("index", "appdata");
				num.put("type", "appdata");
				JSONArray objects = new JSONArray();
				objects.add(num);
				data.put("relation", objects.toJSONString());

				logger.info("result: " + data);
				long startTime = System.currentTimeMillis();
				boolean isSuccess = SystemRpcService.kafkaService.send("appdata_record", System.currentTimeMillis() + "",
						data.toJSONString());
				long endTime = System.currentTimeMillis();
				if (isSuccess) {
					logger.info("Sent QTX data to kafka is successful. uuid= " + uuid
							+ " spent time=" + (endTime - startTime) + "ms.");
					count++;
					logger.info("互动量推送条数"+count+"条:"+data.toJSONString());
				} else {
					logger.info("Sent QTX data to kafka is failed. uuid= " + uuid + " spent time="
							+ (endTime - startTime) + "ms.");
					logger.info("互动量推送失败"+data.toJSONString());
				}

			}
			logger.info("互动量推送完毕，休眠半小时！");
			DoSleep5minuets();
		}
	}

	//查询互动量数据
	public static List<String> queryData() {
		String sql = "select url,read_num,share_num,like_num,comment_num from sync_caibian_data_Interaction ";
		List<Map<String, Object>> list = JdbcTemp.queryForList(sql);
//		System.out.println(list);
		String res = null;
		List<String> list2 = new ArrayList<String>();
		for (Map<String, Object> maps : list) {
			res = JSON.toJSONString(maps, false);
			list2.add(res);
		}
		return list2;
	}

	public static String queryCount(int first_id) {
		String sql = "select count(*) from sync_caibian_data";
		List<Map<String, Object>> list = JdbcTemp.queryForList(sql);
//		System.out.println(list);
		String res = null;
		for (Map<String, Object> maps : list) {
			res = JSON.toJSONString(maps, false);
		}
		return res;
	}
}
