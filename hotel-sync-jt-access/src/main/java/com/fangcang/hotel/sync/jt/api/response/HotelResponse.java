package com.fangcang.hotel.sync.jt.api.response;

public class HotelResponse{


    /**
     * 酒店全称
     */
    private String hotelFullName;

    /**
     * 酒店地址
     */
    private String hotelAddress;

    /**
     * 最低房价
     */
    private String minRoomRate;

    /**
     * 酒店集团
     */
    private String groupCode;

    /**
     * 酒店编号
     */
    private String hotelCode;

    public String getHotelFullName() {
        return hotelFullName;
    }

    public void setHotelFullName(String hotelFullName) {
        this.hotelFullName = hotelFullName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getMinRoomRate() {
        return minRoomRate;
    }

    public void setMinRoomRate(String minRoomRate) {
        this.minRoomRate = minRoomRate;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 操作员名称
     */
    private String userName;

}
