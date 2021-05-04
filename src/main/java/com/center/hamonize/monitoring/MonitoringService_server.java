package com.center.hamonize.monitoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.influxdb.dto.BoundParameterQuery.QueryBuilder;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import com.mapper.IMonitoringMapper;
import com.mapper.IMonitoringMapper_server;
import com.model.PcDataVo;
import com.model.PcMemoryDataVo;

@Service
public class MonitoringService_server {

	@Autowired
	private IMonitoringMapper_server mMpper;

	@Autowired
	private InfluxDBTemplate<Point> influxDBTemplate;

	public List<Map<String, Object>> pcListInfo(Map<String, Object> params) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = mMpper.pcListInfo(params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<PcDataVo> influxInfo() {
		JSONArray jsonArray = new JSONArray();
		Object jObj = null;

		Query cpu_query = QueryBuilder.newQuery(
				"SELECT value, host FROM (SELECT TOP(value, 1) AS value, host from cpu_value WHERE time > now() -1m GROUP BY host) tz('Asia/Seoul')")
				.forDatabase("collectd").create();

		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		long start = System.currentTimeMillis();
		System.out.println("startTime===="+start);
		QueryResult queryResult = influxDBTemplate.query(cpu_query);

		List<PcDataVo> memoryPointList = resultMapper.toPOJO(queryResult, PcDataVo.class);
		long end = System.currentTimeMillis();
		System.out.println("spendtime===="+(end-start));

		return memoryPointList;
	}


	public List<PcMemoryDataVo> getMemory(String host) {

		JSONArray jsonArray = new JSONArray();
		Object jObj = null;

		Query mem_query = QueryBuilder
				.newQuery("SELECT value, host , type_instance FROM memory_value where type='percent' and time > now() -20s and host='" + host
						+ "' order by time desc limit 6")
				.forDatabase("collectd").create();
		QueryResult results = influxDBTemplate.query(mem_query);
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();

		QueryResult queryResult = influxDBTemplate.query(mem_query);
		int i = 0;
		for (QueryResult.Result result : queryResult.getResults()) {

			i++;
		}

		List<PcMemoryDataVo> memoryPointList = resultMapper.toPOJO(queryResult, PcMemoryDataVo.class);

		return memoryPointList;
	}
	
	
	public List<PcDataVo> getCpu(String host) {

		JSONArray jsonArray = new JSONArray();
		Object jObj = null;

		Query cpu_query = QueryBuilder
				.newQuery("SELECT ROUND(mean(value)) as value FROM cpu_value WHERE type_instance = 'user' AND type = 'percent' and time > now() -20s and host='"+host+ "'"
						+ "order by time desc")
				.forDatabase("collectd").create();
		QueryResult results = influxDBTemplate.query(cpu_query);
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();

		QueryResult queryResult = influxDBTemplate.query(cpu_query);
		int i = 0;
		for (QueryResult.Result result : queryResult.getResults()) {

			i++;
		}

		List<PcDataVo> cpuPointList = resultMapper.toPOJO(queryResult, PcDataVo.class);

		return cpuPointList;
	}

}
