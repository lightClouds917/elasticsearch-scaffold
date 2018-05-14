package com.java4all.service.impl;

import com.java4all.base.PolicyFileEso;
import com.java4all.config.EsConfig;
import com.java4all.service.PolicyFileService;
import com.java4all.util.EsRestClient;
import com.java4all.util.MD5Util;
import com.java4all.util.MapJsonUtil;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Author: momo
 * Date: 2018/5/12
 * Description:
 */
@Service
public class PolicyFileServiceImpl implements PolicyFileService{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final Integer DEFAULT_START = 0;

    /**
     * 根据id删除文档
     * 若根据其他条件删除，需要先根据条件查询出文档id，然后再根据id删除
     * @param policyFileEso
     * @throws Exception
     */
    @Override
    public String deleteByParams(PolicyFileEso policyFileEso) throws Exception {
        //先查询是否存在
        PolicyFileEso poFind = this.getById(policyFileEso.getId());
        if(poFind == null || poFind.getId() == null){
            return "数据不存在！";
        }
        RestClient restClient = new EsRestClient().getRestClient();
        logger.info("=========>删除条件："+EsConfig.POLICYFILE_ES_ENDPOINT+policyFileEso.getId());
        restClient.performRequest("DELETE",EsConfig.POLICYFILE_ES_ENDPOINT+policyFileEso.getId());
        return "删除成功";
    }

    /**
     * 局部更新方法
     * 比如：修改name的值，或者age的值,或者新增一个字段type1
     * 但是：如果此类型为一个嵌套类型，此方法会直接把嵌套类型的值替换掉，而不会追加或者减少
     * @param policyFileEso
     * @throws Exception
     */
    @Override
    public void updateOrAddSomeField(PolicyFileEso policyFileEso)throws Exception{
        //以修改某指定文档的name和age属性值为例
        Map updateMap = new HashedMap();
        Random random = new Random();
        updateMap.put("name","火男"+random.nextInt(50));
        updateMap.put("age",random.nextInt(50));
        updateMap.put("type1",random.nextInt(10));
        String updateJson = MapJsonUtil.toJSONString(Collections.singletonMap("doc",updateMap),true);

        HttpEntity entity = new NStringEntity(updateJson,ContentType.APPLICATION_JSON);
        RestClient restClient = new EsRestClient().getRestClient();
        logger.info("========>修改条件："+EsConfig.POLICYFILE_ES_ENDPOINT+policyFileEso.getId()+"/_update"+updateJson);
        restClient.performRequest("POST",EsConfig.POLICYFILE_ES_ENDPOINT+policyFileEso.getId()+"/_update",Collections.EMPTY_MAP,entity);
    }


    /**
     * 此接口可作为新增接口
     * 也可以作为更新接口，此更新为整个文档的更新：新文档会覆盖原来的文档，原来的文档会被删除，二者除了id一致，没有任何关系
     * @param policyFileEso
     * @throws Exception
     */
    @Override
    public void save(PolicyFileEso policyFileEso) throws Exception {
        //假数据测试，生产环境请删除
        policyFileEso = this.create();

        String opString = MapJsonUtil.toJSONString(policyFileEso, true);
        HttpEntity entity = new NStringEntity(opString,ContentType.APPLICATION_JSON);
        RestClient restClient = new EsRestClient().getRestClient();
        logger.info("======>保存条件："+EsConfig.POLICYFILE_ES_ENDPOINT+policyFileEso.getId()+opString);
        //此处拼接id非常重要，否则，新增的document中元属性：_id会为空，建议同步
        Response response =restClient.performRequest("POST", EsConfig.POLICYFILE_ES_ENDPOINT + policyFileEso.getId(), Collections.EMPTY_MAP, entity);

    }

    /**
     * 根据id查找文档
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public PolicyFileEso getById(String id) throws Exception{
        PolicyFileEso policyFileEso = new PolicyFileEso();
        //构造查询条件
        Map queryMap = new HashMap();
        queryMap.put("query", Collections.singletonMap("term",Collections.singletonMap("id",id)));
        String opString = MapJsonUtil.toJSONString(queryMap, true);

        //es查询
        HttpEntity entity = new NStringEntity(opString, ContentType.APPLICATION_JSON);
        RestClient restClient = new EsRestClient().getRestClient();
        logger.info("======>查询条件："+EsConfig.POLICYFILE_ES_ENDPOINT+"_search"+opString);
        Response response =
                restClient.performRequest("GET", EsConfig.POLICYFILE_ES_ENDPOINT + "_search", Collections.EMPTY_MAP, entity);

        //解析查询结果
        String entityStr = EntityUtils.toString(response.getEntity());
        Map<String, Object> mapres = MapJsonUtil.json2Map(entityStr);
        Map hitsMap = (Map) mapres.get("hits");
        JSONArray jsonArray = (JSONArray) hitsMap.get("hits");
        if(CollectionUtils.isNotEmpty(jsonArray)) {
            Map map = (Map) jsonArray.get(0);
            Map sourceMap = (Map)map.get("_source");
            policyFileEso = this.map2PolicyFileEso(sourceMap);
        }

        return policyFileEso;
    }


    /**
     * map to PolicyFileEso
     * @param map
     * @return
     */
    public PolicyFileEso map2PolicyFileEso(Map map){
        PolicyFileEso policyFileEso = new PolicyFileEso();
        policyFileEso.setId(map.get("id") == null ? null : map.get("id").toString());
        policyFileEso.setName(map.get("name") == null ? null : map.get("name").toString());
        policyFileEso.setAge(map.get("age") == null ? null : Integer.parseInt(map.get("age").toString()));
        String provinceJson = map.get("provinceNumInfo") == null ? null : map.get("provinceNumInfo").toString();
        List<Map<String, Object>> list = StringUtils.isBlank(provinceJson) ? null : MapJsonUtil.jsonToMapList(provinceJson);
        policyFileEso.setProvinceNumInfo(list);

        return policyFileEso;
    }

    /**造数据*/
    public PolicyFileEso create(){
        PolicyFileEso policyFileEso = new PolicyFileEso();
        Random random = new Random();
        Map<String,Object> map1 = new HashedMap();
        map1.put("province","陕西"+random.nextInt(50));
        map1.put("number",random.nextInt(10000));

        Map<String,Object> map2 = new HashedMap();
        map2.put("province","浙江"+random.nextInt(20));
        map2.put("number",random.nextInt(10000));

        List<Map<String,Object>> list = new ArrayList<>();
        list.add(map1);
        list.add(map2);

        policyFileEso.setName("Musk");
        policyFileEso.setAge(35);
        policyFileEso.setId(MD5Util.sign(policyFileEso.getName()+policyFileEso.getAge().toString()));
        policyFileEso.setProvinceNumInfo(list);

        return policyFileEso;
    }
}
