package com.fangcang.hotel.sync.jt.api.response;

import java.util.List;

public class FitReserveResponse extends BusinessResponse {

    private String unionSerial;

    private String parentReserveNo;

    private List<String> serialNoList;

    private String groupCode;

    private String hotelCode;

    private String userName;

    public String getUnionSerial() {
        return unionSerial;
    }

    public void setUnionSerial(String unionSerial) {
        this.unionSerial = unionSerial;
    }

    public String getParentReserveNo() {
        return parentReserveNo;
    }

    public void setParentReserveNo(String parentReserveNo) {
        this.parentReserveNo = parentReserveNo;
    }

    public List<String> getSerialNoList() {
        return serialNoList;
    }

    public void setSerialNoList(List<String> serialNoList) {
        this.serialNoList = serialNoList;
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
}
