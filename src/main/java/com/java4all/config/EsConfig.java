package com.java4all.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author: momo
 * Date: 2018/5/11
 * Description:elasticsearch配置文件
 */
@Component
public class EsConfig implements InitializingBean {

    @Value("${policyFile.es.host}")
    private String policyFileEsHost;
    @Value("${policyFile.es.port}")
    private String policyFileEsPort;
    @Value("${policyFile.es.hostname}")
    private String policyFileEsHostName;
    @Value("${policyFile.es.password}")
    private String policyFileEsPassword;
    @Value("${policyFile.es.endpoint}")
    private String policyFileEsEndpoint;

    public static String POLICYFILE_ES_HOST;
    public static String POLICYFILE_ES_PORT;
    public static String POLICYFILE_ES_HOST_NAME;
    public static String POLICYFILE_ES_PASSWORD;
    public static String POLICYFILE_ES_ENDPOINT;

    @Override
    public void afterPropertiesSet() throws Exception{
        POLICYFILE_ES_HOST=policyFileEsHost;
        POLICYFILE_ES_PORT=policyFileEsPort;
        POLICYFILE_ES_HOST_NAME=policyFileEsHostName;
        POLICYFILE_ES_PASSWORD=policyFileEsPassword;
        POLICYFILE_ES_ENDPOINT=policyFileEsEndpoint;
    }
}
