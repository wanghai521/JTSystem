package com.jt.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 	需要将json串与对象实现互转
 * @author Administrator
 *
 */
public class ObjectMapperUtil {
	// json转换的工具类
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 将java对象转换成json字符串
	 * @param object
	 * @return
	 */
	public static String objectToJson(Object object){
		String json = "";
		try {
			// 将java对象转换成json
			json = MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return json;
	}

	/**
	 * 将json字符串转换成指定类型的对象
	 * @param json
	 * @param targetClass
	 * @param <T>
	 * @return
	 */
	public static <T> T jsonToObject(String json,Class<T> targetClass){
		T resultObj;
		try {
			resultObj = MAPPER.readValue(json, targetClass);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return resultObj;
	}
	
	
	
}
