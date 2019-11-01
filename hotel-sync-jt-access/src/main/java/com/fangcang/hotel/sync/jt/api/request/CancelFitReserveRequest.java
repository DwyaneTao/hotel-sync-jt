package com.fangcang.hotel.sync.jt.api.request;

public class CancelFitReserveRequest extends BusinessRequest{

    private String serial;

    private String unionSerial;

    private String modifyUser;

    private String modifyDate;

    /**
     * 集团编号
     */
    private String groupCode;

    /**
     * 酒店编号
     */
    private String hotelCode;

    /**
     * 操作员名称
     */
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getUnionSerial() {
        return unionSerial;
    }

    public void setUnionSerial(String unionSerial) {
        this.unionSerial = unionSerial;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
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
