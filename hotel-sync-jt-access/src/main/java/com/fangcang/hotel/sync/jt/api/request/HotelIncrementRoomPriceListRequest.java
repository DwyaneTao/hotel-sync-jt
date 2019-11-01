package com.fangcang.hotel.sync.jt.api.request;

/**
 * 酒店房价变化增量请求参数
 * @author py
 * @date 2019/10/23 20:13
 **/
public class HotelIncrementRoomPriceListRequest extends BusinessRequest  {

    /**
     * 集团编号
     */
    private String groupCode;

    /**
     * 酒店编号
     */
    private String hotelCode;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 房类
     */
    private String roomType;

    /**
     * 房价码
     */
    private String rateCode;

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

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }
}
