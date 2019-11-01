package com.fangcang.hotel.sync.jt.manager.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fangcang.hotel.sync.data.dto.HttpResponse;
import com.fangcang.hotel.sync.data.util.HttpClientUtil;
import com.fangcang.hotel.sync.data.util.Md5Util;
import com.fangcang.hotel.sync.jt.api.request.*;
import com.fangcang.hotel.sync.jt.api.response.*;
import com.fangcang.hotel.sync.jt.api.response.hotel.DayCmsRmPriceListResponse;
import com.fangcang.hotel.sync.jt.config.JTConfig;
import com.fangcang.hotel.sync.jt.constants.JTConstants;
import com.fangcang.hotel.sync.jt.manager.JTManager;
import com.fangcang.hotel.sync.jt.service.JTExtendsConfigService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.*;

@Service("jtManager")
public class JTManagerImpl implements JTManager {

    private Log log = LogFactory.getLog(JTManagerImpl.class);

    @Autowired
    private JTExtendsConfigService jtExtendsConfigService;


    /**
     *酒店基础信息
     */
    @Override
    public Response<HotelListResponse> queryHotelList(QueryHotelListRequest request) throws Exception {
        String  url = JTConfig.getUrl() +JTConstants.getHotelList;
        Map<String, String> headMap = getSignature();
        HttpResponse httpResponse = HttpClientUtil.doPost(url, JSON.toJSONString(request), headMap, JTConfig.getContenttype(),5000, 15000);
        Response<HotelListResponse> response = JSONObject.parseObject(httpResponse.getContent().replace("\"Data\":[","\"Data\":{\"hotelResponses\": [")+"}", new TypeReference<Response<HotelListResponse>>() {});
        return response;
    }

    /**
     * 房型基础信息列表
     */
    @Override
    public Response<RoomTypeListResponse> queryRoomTypeList(QueryRoomTypeListRequest request) throws Exception {
        Response<RoomTypeListResponse> response = null;
        String  url = JTConfig.getUrl() +JTConstants.getCmsRoomTypeList;
        Map<String, String> headMap = getSignature();
        HttpResponse httpResponse = HttpClientUtil.doPost(url, JSON.toJSONString(request), headMap, JTConfig.getContenttype(),5000, 15000);
        response = JSONObject.parseObject(httpResponse.getContent().replace("\"Data\":[","\"Data\":{\"roomTypeResponsees\": [")+"}", new TypeReference<Response<RoomTypeListResponse>>() {});
        return response;
    }


    /**
     * 订单创建
     */
    @Override
    public Response<FitReserveResponse> createFitReserve(QueryFitReserveRequest request) throws Exception {
        Response<FitReserveResponse> response = null;
        String  url = JTConfig.getUrl() +JTConstants.createFitReserve;
        Map<String, String> headMap = getSignature();
        HttpResponse httpResponse = HttpClientUtil.doPost(url, JSON.toJSONString(request), headMap, JTConfig.getContenttype(),5000, 15000);
        response = JSONObject.parseObject(httpResponse.getContent(), new TypeReference<Response<FitReserveResponse>>() {});
        return response;
    }

    /**
     *订单取消接口
     */
    @Override
    public Response<FitReserveResponse> cancelFitReserve(CancelFitReserveRequest request) throws Exception {
        Response response = null;
        String  url = JTConfig.getUrl() +JTConstants.cancelFitReserve;
        Map<String, String> headMap = getSignature();
        HttpResponse httpResponse = HttpClientUtil.doPost(url, JSON.toJSONString(request), headMap, JTConfig.getContenttype(),5000, 15000);
        response = JSONObject.parseObject(httpResponse.getContent(), new TypeReference<Response>() {});
        return response;
    }

    /**
     * 订单状态查询接口
     */
    @Override
    public Response<ResRoomResourceLiteOutputResponse> batchRoomResourceList(RoomResourceListRequest request) throws Exception {
        Response<ResRoomResourceLiteOutputResponse> response = null;
        String  url = JTConfig.getUrl() +JTConstants.getBatchRoomResourceList;
        Map<String, String> headMap = getSignature();
        HttpResponse httpResponse = HttpClientUtil.doPost(url, JSON.toJSONString(request), headMap, JTConfig.getContenttype(),5000, 15000);
        response = JSONObject.parseObject(httpResponse.getContent(), new TypeReference<Response<ResRoomResourceLiteOutputResponse>>() {});
        return response;
    }


    /**
     * 预订试单
     */
    @Override
    public Response<RoomResourceSingleTrialOutputResponse> singleTrialFitReserve(SingleTrialFitReserveRequest request) throws Exception {
        Response<RoomResourceSingleTrialOutputResponse> response = null;
        String  url = JTConfig.getUrl() +JTConstants.singleTrialFitReserve;
        Map<String, String> headMap = getSignature();
        HttpResponse httpResponse = HttpClientUtil.doPost(url, JSON.toJSONString(request), headMap, JTConfig.getContenttype(),5000, 15000);
        response = JSONObject.parseObject(httpResponse.getContent(), new TypeReference<Response<RoomResourceSingleTrialOutputResponse>>() {});
        return response;
    }


    /**
     * 订单预付
     */
    @Override
    public Response prepayFitReserve(PrepayFitReserveRequest request) throws Exception{
        Response response = null;
        String  url = JTConfig.getUrl() +JTConstants.prepayFitReserve;
        Map<String, String> headMap = getSignature();
        HttpResponse httpResponse = HttpClientUtil.doPost(url, JSON.toJSONString(request), headMap, JTConfig.getContenttype(),5000, 15000);
        response = JSONObject.parseObject(httpResponse.getContent(), new TypeReference<Response>() {});
        return response;
    }

    /**
     * 房量
     */
    @Override
    public Response<ResRoomStockOutputListResponse> getRoomStockList(RoomStockListRequest request) throws Exception{
        Response<ResRoomStockOutputListResponse> response = null;
        String  url = JTConfig.getUrl() +JTConstants.getRoomStockList;
        Map<String, String> headMap = getSignature();
        HttpResponse httpResponse = HttpClientUtil.doPost(url, JSON.toJSONString(request), headMap, JTConfig.getContenttype(),5000, 15000);
        response = JSONObject.parseObject(httpResponse.getContent().replace("\"Data\":[","\"Data\":{\"resRoomStockOutputResponses\": [")+"}", new TypeReference<Response<ResRoomStockOutputListResponse>>() {});
        return response;
    }

    /**
     *房价
     */
    @Override
    public Response<DayCmsRmPriceListResponse> getDailyRoomPriceList(DailyRoomPriceListRequest request) throws Exception{
        Response<DayCmsRmPriceListResponse> response = null;
        String  url = JTConfig.getUrl() +JTConstants.getDailyRoomPriceList;
        Map<String, String> headMap = getSignature();
        HttpResponse httpResponse = HttpClientUtil.doPost(url, JSON.toJSONString(request), headMap, JTConfig.getContenttype(),5000, 15000);
        response = JSONObject.parseObject(httpResponse.getContent().replace("\"Data\":[","\"Data\":{\"dayCmsRmPriceResponses\": [")+"}", new TypeReference<Response<DayCmsRmPriceListResponse>>() {});
        return response;
    }

//    /**
//     * 房价变化增量
//     */
//    @Override
//    public Response<DayCmsRmPriceResponse> getIncrementDailyRoomPriceList(IncrementDailyRoomPriceListRequest request) throws Exception{
//        Response<DayCmsRmPriceResponse> response = null;
//        String  url = JTConfig.getUrl() +JTConstants.getIncrementDailyRoomPriceList;
//        Map<String, String> headMap = getSignature();
//        HttpResponse httpResponse = HttpClientUtil.doPost(url, JSON.toJSONString(request), headMap, JTConfig.getContenttype(),5000, 15000);
//        response = JSONObject.parseObject(httpResponse.getContent(), new TypeReference<Response<DayCmsRmPriceResponse>>() {});
//        return response;
//    }

    /**
     * 酒店房价变化增量
     */
    @Override
    public Response<IncrementRmPriceInputResponse> getHotelIncrementRoomPriceList (HotelIncrementRoomPriceListRequest request) throws Exception{
        Response<IncrementRmPriceInputResponse> response = null;
        String  url = JTConfig.getUrl() +JTConstants.getHotelIncrementRoomPriceList;
        Map<String, String> headMap = getSignature();
        HttpResponse httpResponse = HttpClientUtil.doPost(url, JSON.toJSONString(request), headMap, JTConfig.getContenttype(),5000, 15000);
        response = JSONObject.parseObject(httpResponse.getContent(), new TypeReference<Response<IncrementRmPriceInputResponse>>() {});
        return response;
    }

    /**
     *封装header
     */
    public Map<String,String> getSignature() throws Exception {

        Response<TokenResponse> response = new Response();
        String url = JTConfig.getUrl() + JTConstants.getToken;
        String clientId = JTConfig.getClientId();
        clientId = URLEncoder.encode(clientId, "UTF-8");
        String clientSecret = JTConfig.getClientSecret();
        clientSecret = URLEncoder.encode(clientSecret, "UTF-8");
        HttpResponse httpResponse = HttpClientUtil.doGet(url+"?clientId="+ clientId+"&clientSecret="+clientSecret);
        response = JSONObject.parseObject(httpResponse.getContent(), new TypeReference<Response<TokenResponse>>() {});
        TokenResponse data = (TokenResponse)response.getData();
        /**
         * 生成token
         */
        String  token = data.getAccessToken();

        /**
         * 获取签名
         */
        String timestamp = new Date().getTime()+"";
        String signature =token+timestamp+JTConfig.getNonce()+JTConfig.getKey();
        char[] chars = signature.toCharArray();
        Arrays.sort(chars);
        String s = Arrays.toString(chars);
        signature = Md5Util.md5Encode(Md5Util.md5Encode(s));
        String authorization = JTConfig.getAuthorization()+ token;
        Map<String,String> map = new HashMap<>();
        map.put("Authorization",authorization);
        map.put("Content-Type","application/json");
        map.put("timestamp",timestamp);
        map.put("nonce",JTConfig.getNonce());
        map.put("signature",signature);
        return map;
    }

}
