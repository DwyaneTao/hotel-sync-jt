package com.fangcang.hotel.sync.jt.api.request;

import java.util.List;

public class RoomResourceListRequest extends  BusinessRequest {

    /**
     * 中央预订号(第三方订单号)
     */
    private String parentReserveNo;

    /**
     *中央订单号列表
     */
    private List<String> serialList;

    /**
     * 集团编号
     */
    private String groupCode;

    /**
     *酒店编号
     */
    private String hotelCode;

    public String getParentReserveNo() {
        return parentReserveNo;
    }

    public void setParentReserveNo(String parentReserveNo) {
        this.parentReserveNo = parentReserveNo;
    }

    public List<String> getSerialList() {
        return serialList;
    }

    public void setSerialList(List<String> serialList) {
        this.serialList = serialList;
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
}
