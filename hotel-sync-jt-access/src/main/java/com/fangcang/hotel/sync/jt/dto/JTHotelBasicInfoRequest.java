package com.fangcang.hotel.sync.jt.dto;

import com.alibaba.fastjson.JSON;
import com.fangcang.hotel.sync.data.dto.BaseSyncRequest;
import com.fangcang.hotel.sync.jt.constants.JTRedisKey;

import java.util.List;

public class JTHotelBasicInfoRequest extends BaseSyncRequest {

    private String requestJson;

    private List<Integer> spCityIds;

    private List<Integer> spHotelIds;

    private Integer totalPage;

    private Integer pageSize;

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public String toJsonString() {
        return JSON.toJSONString(this);
    }

    @Override
    public String obtainRedisKey() {
        return JTRedisKey.HOTEL_BASIC_INFO_KEY;
    }

    @Override
    public String obtainSyncHotelIds() {
        return null;
    }

    @Override
    public String obtainExceptionRedisKey() {
        return null;
    }

    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }

    public List<Integer> getSpCityIds() {
        return spCityIds;
    }

    public void setSpCityIds(List<Integer> spCityIds) {
        this.spCityIds = spCityIds;
    }

    public List<Integer> getSpHotelIds() {
        return spHotelIds;
    }

    public void setSpHotelIds(List<Integer> spHotelIds) {
        this.spHotelIds = spHotelIds;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
