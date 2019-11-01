package com.fangcang.hotel.sync.jt.api.response;

public class HotelDetailResponse extends BusinessResponse{

    /**
     * 酒店全称
     */
    private String HotelFullName;

    /**
     * 酒店地址
     */
    private String HotelAddress;

    /**
     * 最低放假
     */
    private String MinRoomRate;

    /**
     * 酒店集团
     */
    private String GroupCode;

    /**
     * 酒店编号
     */
    private String HotelCode;

    /**
     * 操作员名称
     */
    private String UserName;

    public String getHotelFullName() {
        return HotelFullName;
    }

    public void setHotelFullName(String hotelFullName) {
        HotelFullName = hotelFullName;
    }

    public String getHotelAddress() {
        return HotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        HotelAddress = hotelAddress;
    }

    public String getMinRoomRate() {
        return MinRoomRate;
    }

    public void setMinRoomRate(String minRoomRate) {
        MinRoomRate = minRoomRate;
    }

    public String getGroupCode() {
        return GroupCode;
    }

    public void setGroupCode(String groupCode) {
        GroupCode = groupCode;
    }

    public String getHotelCode() {
        return HotelCode;
    }

    public void setHotelCode(String hotelCode) {
        HotelCode = hotelCode;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
