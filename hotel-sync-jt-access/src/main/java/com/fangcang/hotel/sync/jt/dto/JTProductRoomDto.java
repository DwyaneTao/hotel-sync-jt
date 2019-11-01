package com.fangcang.hotel.sync.jt.dto;

public class JTProductRoomDto {

    private String rateCode;

    private String roomRate;

    private String actualRate;

    private Boolean containBreakfast;

    private String packeagesQuantity;

    private String subscribePolicy;

    private String unsubscribePolicy;

    private String batchContent;

    private String remarks;

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public String getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(String roomRate) {
        this.roomRate = roomRate;
    }

    public String getActualRate() {
        return actualRate;
    }

    public void setActualRate(String actualRate) {
        this.actualRate = actualRate;
    }

    public Boolean getContainBreakfast() {
        return containBreakfast;
    }

    public void setContainBreakfast(Boolean containBreakfast) {
        this.containBreakfast = containBreakfast;
    }

    public String getPackeagesQuantity() {
        return packeagesQuantity;
    }

    public void setPackeagesQuantity(String packeagesQuantity) {
        this.packeagesQuantity = packeagesQuantity;
    }

    public String getSubscribePolicy() {
        return subscribePolicy;
    }

    public void setSubscribePolicy(String subscribePolicy) {
        this.subscribePolicy = subscribePolicy;
    }

    public String getUnsubscribePolicy() {
        return unsubscribePolicy;
    }

    public void setUnsubscribePolicy(String unsubscribePolicy) {
        this.unsubscribePolicy = unsubscribePolicy;
    }

    public String getBatchContent() {
        return batchContent;
    }

    public void setBatchContent(String batchContent) {
        this.batchContent = batchContent;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
