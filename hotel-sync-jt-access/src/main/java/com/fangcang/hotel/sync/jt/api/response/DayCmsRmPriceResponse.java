package com.fangcang.hotel.sync.jt.api.response;


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
     * 早餐等
     */
    private String breakfast1;

    private String breakfast2;

    private String breakfast3;

    private String breakfast4;

    private String breakfastN;

    /**
     * ticket
     */
    private String tickets1;

    private String tickets2;

    private String tickets3;

    private String tickets4;

    /**
     *
     */
    private String packeagesList;

    private String priceRemarks;

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

    public String getBreakfast1() {
        return breakfast1;
    }

    public void setBreakfast1(String breakfast1) {
        this.breakfast1 = breakfast1;
    }

    public String getBreakfast2() {
        return breakfast2;
    }

    public void setBreakfast2(String breakfast2) {
        this.breakfast2 = breakfast2;
    }

    public String getBreakfast3() {
        return breakfast3;
    }

    public void setBreakfast3(String breakfast3) {
        this.breakfast3 = breakfast3;
    }

    public String getBreakfast4() {
        return breakfast4;
    }

    public void setBreakfast4(String breakfast4) {
        this.breakfast4 = breakfast4;
    }

    public String getBreakfastN() {
        return breakfastN;
    }

    public void setBreakfastN(String breakfastN) {
        this.breakfastN = breakfastN;
    }

    public String getTickets1() {
        return tickets1;
    }

    public void setTickets1(String tickets1) {
        this.tickets1 = tickets1;
    }

    public String getTickets2() {
        return tickets2;
    }

    public void setTickets2(String tickets2) {
        this.tickets2 = tickets2;
    }

    public String getTickets3() {
        return tickets3;
    }

    public void setTickets3(String tickets3) {
        this.tickets3 = tickets3;
    }

    public String getTickets4() {
        return tickets4;
    }

    public void setTickets4(String tickets4) {
        this.tickets4 = tickets4;
    }

    public String getPackeagesList() {
        return packeagesList;
    }

    public void setPackeagesList(String packeagesList) {
        this.packeagesList = packeagesList;
    }

    public String getPriceRemarks() {
        return priceRemarks;
    }

    public void setPriceRemarks(String priceRemarks) {
        this.priceRemarks = priceRemarks;
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
