package com.java4all.util;

import com.java4all.config.EsConfig;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.stereotype.Component;

/**
 * Author: momo
 * Date: 2018/5/12
 * Description:
 */
@Component
public class EsRestClient {

    /**获取RestClient*/
    public RestClient getRestClient(){
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(EsConfig.POLICYFILE_ES_HOST_NAME, EsConfig.POLICYFILE_ES_PASSWORD));

        RestClient restClient = RestClient.builder(new HttpHost(EsConfig.POLICYFILE_ES_HOST, Integer.parseInt(EsConfig.POLICYFILE_ES_PORT)))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        // disable preemptive authentication
                        httpClientBuilder.disableAuthCaching();
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                })
                .build();
        return restClient;
    }

    public static void wocao(){
        ;
    }
}
