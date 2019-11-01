package com.fangcang.hotel.sync.jt.util;

import com.fangcang.hotel.sync.jt.service.impl.JTFetchServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpUtil {

    private static Log log = LogFactory.getLog(HttpUtil.class);

    public static String post(String url, String params) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept","application/json");
        httpPost.setHeader("Content-Type","application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {

            response = httpClient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if(state == HttpStatus.SC_OK){
                HttpEntity responseEntity = response.getEntity();

                String jsonString = EntityUtils.toString(responseEntity);

                return jsonString;
            }else {
                log.error("请求返回"+state+"("+url+")");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(response != null){
                try{
                    response.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            try {
                httpClient.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
