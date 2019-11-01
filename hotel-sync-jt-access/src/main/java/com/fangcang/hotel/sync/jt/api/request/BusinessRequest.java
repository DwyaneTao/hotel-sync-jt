package com.fangcang.hotel.sync.jt.api.request;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class BusinessRequest {

    @JSONField(serialize=false)
    private String supplyCode;

    public String getSupplyCode() {
        return supplyCode;
    }

    public void setSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode;
    }

}