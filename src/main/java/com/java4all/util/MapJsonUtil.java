package com.java4all.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Author: momo
 * Date: 2018/5/7
 * Description:map和json互转工具类
 */
public class MapJsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(MapJsonUtil.class);
    //public static void main(String[] args){
    //    Map<String,Object> map = new LinkedMap();
    //    map.put("title","国务院2号文件");
    //    map.put("attach","根据中华人民共和国");
    //    String s = mapToJson(map);
    //
    //    Map<String,Object> map2 = new LinkedMap();
    //    map2.put("title","省委三号文件");
    //    map2.put("attach","本次大会研究决定");
    //    System.out.println(map2);
    //    String s2 = mapToJson(map2);
    //
    //    List<Map<String,Object>> list = new LinkedList<>();
    //    list.add(map);
    //    list.add(map2);
    //    String s1 = mapListToJson(list);
    //    System.out.println(s1);
    //    List<Map<String, Object>> list1 = jsonToMapList(s1);
    //
    //    System.out.println(list1.get(0).get("title"));
    //    System.out.println(list1.get(0).get("attach"));
    //
    //
    //}


    /**
     * map转json
     * @param map        {title=国务院2号文件, attach=根据中华人民共和国}
     * @return  json     {"title":"国务院2号文件","attach":"根据中华人民共和国"}
     */
    public static String mapToJson(Map<String,Object> map){
        ObjectMapper obm = new ObjectMapper();
        String param = null;
        try {
            param = obm.writeValueAsString(map);
            return param;
        }catch (Exception ex){
            throw new RuntimeException("map转json出错",ex);
        }
    }

    /**
     * json转map
     * @param jsonStr   {"title":"国务院2号文件","attach":"根据中华人民共和国"}
     * @return  map     {title=国务院2号文件, attach=根据中华人民共和国}
     */
    public static Map<String,Object> jsonToMap(String jsonStr){
        try {
            Map<String,Object> map2= JSON.parseObject(jsonStr,LinkedHashMap.class);
            return map2;
        }catch (Exception ex){
            throw new RuntimeException("json转map出错",ex);
        }
    }

    /**
     * json转List<Map<String, Object>>
     * @param jsonStr
     * @return
     */
    public static List<Map<String,Object>> jsonToMapList(String jsonStr){
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            List<Object> list2 = JSON.parseArray(jsonStr);
            for(int i = 0,length = list2.size();i < length;i++){
                Map<String,Object> map = (Map<String,Object>)list2.get(i);
                list.add(map);
            }
            return list;
        }catch (Exception ex){
            throw new RuntimeException("json转List<Map<String,Object>>出错",ex);
        }
    }

    /**
     * List<Map<String,Object>>转json
     * @param mapList       [{title=国务院2号文件, attach=根据中华人民共和国}, {title=省委三号文件, attach=本次大会研究决定}]
     * @return String       [{"title":"国务院2号文件","attach":"根据中华人民共和国"}, {"title":"省委三号文件","attach":"本次大会研究决定"}]
     */
    public static String mapListToJson(List<Map<String,Object>> mapList){
        System.out.println(mapList.toString());
        List<Object> list = new ArrayList<>();
        ObjectMapper obm = new ObjectMapper();
        try {
            if(mapList != null && mapList.size() > 0){
                for (int i = 0,length = mapList.size();i < length;i++){
                    list.add(obm.writeValueAsString(mapList.get(i)));
                }
            }
            System.out.println(list.toString());
            return list.toString();
        }catch (Exception ex){
            throw new RuntimeException("List<Map<String,Object>>转json出错",ex);
        }
    }

    /**
     * 将json字符串转为Map结构
     * 如果json复杂，结果可能是map嵌套map
     * @param jsonStr 入参，json格式字符串
     * @return 返回一个map
     */
    public static Map<String, Object> json2Map(String jsonStr) {
        Map<String, Object> map = new HashMap<>();
        if(jsonStr != null && !"".equals(jsonStr)){
            //最外层解析
            JSONObject json = JSONObject.fromObject(jsonStr);
            for (Object k : json.keySet()) {
                Object v = json.get(k);
                //如果内层还是数组的话，继续解析
                if (v instanceof JSONArray) {
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    Iterator<JSONObject> it = ((JSONArray) v).iterator();
                    while (it.hasNext()) {
                        JSONObject json2 = it.next();
                        list.add(json2Map(json2.toString()));
                    }
                    map.put(k.toString(), list);
                } else {
                    map.put(k.toString(), v);
                }
            }
            return map;
        }else{
            return null;
        }
    }

    /**
     * object to json
     * @param object
     * @param prettyFormat
     * @return
     */
    public static final String toJSONString(Object object, boolean prettyFormat) {
        return JSON.toJSONString(object, prettyFormat);
    }

    /**
     * json to Object
     * @param jsontext
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> T getObject(String jsontext, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(jsontext, clazz);
        } catch (Exception e) {
            logger.error("json字符串转换失败！" + jsontext, e);
        }
        return t;
    }
}
