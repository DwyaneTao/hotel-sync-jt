package com.fangcang.hotel.sync.jt.api.response;

/**
 * @author py
 * @date 2019/10/23 18:08
 **/
public class ResRoomResourceLiteOutputResponse extends BusinessResponse {

    /**
     * 中央订单号
     */
    private String serial;

    /**
     * 入住人
     */
    private String name;

    /**
     * 到日
     */
    private String arrivalDate;

    /**
     * 离日
     */
    private String departureDate;

    /**
     * 状态
     */
    private String status;

    /**
     * 门店状态
     */
    private String hStatus;

    /**
     * 房类
     */
    private String roomType;

    /**
     * 房类描述
     */
    private String roomTypeName;

    /**
     * 门市价
     */
    private Number roomRate;

    /**
     * 成交价
     */
    private Number actualRate;

    /**
     * 总金额
     */
    private Number totalAmount;

    /**
     * 预付款金额
     */
    private Number payamount;

    /**
     * 取消备注
     */
    private String cancelReserveRemarks;

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
    private String userName;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public Number getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(Number roomRate) {
        this.roomRate = roomRate;
    }

    public Number getActualRate() {
        return actualRate;
    }

    public void setActualRate(Number actualRate) {
        this.actualRate = actualRate;
    }

    public Number getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Number totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Number getPayamount() {
        return payamount;
    }

    public void setPayamount(Number payamount) {
        this.payamount = payamount;
    }

    public String getCancelReserveRemarks() {
        return cancelReserveRemarks;
    }

    public void setCancelReserveRemarks(String cancelReserveRemarks) {
        this.cancelReserveRemarks = cancelReserveRemarks;
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

