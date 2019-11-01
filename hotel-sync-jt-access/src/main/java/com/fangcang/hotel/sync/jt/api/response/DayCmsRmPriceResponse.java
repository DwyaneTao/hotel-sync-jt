package com.fangcang.hotel.sync.jt.api.response;

/**
 * 房价返回参数
 * @author py
 * @date 2019/10/23 19:57
 **/
public class DayCmsRmPriceResponse extends BusinessResponse {

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 营业日期
     */
    private String businessDate;

    /**
     * 房类
     */
    private String roomType;

    /**
     * 房类别名
     */
    private String roomTypeAlias;

    /**
     * 房价码
     */
    private String rateCode;

    /**
     * 原门市价
     */
    private String roomRate;

    /**
     * 成交价
     */
    private Number actualRate;

    /**
     * 是否包含免费早餐
     */
    private Boolean isContainBreakfast;

    /**
     * 包价码
     */
    private String packeagesCode;

    /**
     * 早餐数量
     */
    private Integer packeagesQuantity;

    /**
     * 预定政策
     */
    private String subscribePolicy;

    /**
     * 退订政策
     */
    private String unsubscribePolicy;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 自增ID号
     */
    private Integer id;

    /**
     * 集团编号
     */
    private String groupCode;

    /**
     * 酒店编号
     */
    private String hotelCode;

    /**
     * 批量操作内容
     */
    private String batchContent;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomTypeAlias() {
        return roomTypeAlias;
    }

    public void setRoomTypeAlias(String roomTypeAlias) {
        this.roomTypeAlias = roomTypeAlias;
    }

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

    public Number getActualRate() {
        return actualRate;
    }

    public void setActualRate(Number actualRate) {
        this.actualRate = actualRate;
    }

    public Boolean getContainBreakfast() {
        return isContainBreakfast;
    }

    public void setContainBreakfast(Boolean containBreakfast) {
        isContainBreakfast = containBreakfast;
    }

    public String getPackeagesCode() {
        return packeagesCode;
    }

    public void setPackeagesCode(String packeagesCode) {
        this.packeagesCode = packeagesCode;
    }

    public Integer getPackeagesQuantity() {
        return packeagesQuantity;
    }

    public void setPackeagesQuantity(Integer packeagesQuantity) {
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getBatchContent() {
        return batchContent;
    }

    public void setBatchContent(String batchContent) {
        this.batchContent = batchContent;
    }
}
