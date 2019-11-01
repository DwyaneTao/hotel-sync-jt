package com.fangcang.hotel.sync.jt.api.response;

import java.math.BigDecimal;

public class RoomResourceListResponse extends BusinessResponse {

    /**
     * 中央订单号
     */
    private String serial;

    /**
     *入住人
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
     private BigDecimal roomRate;

    /**
     * 成交价
     */
    private BigDecimal actualRate;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 预付款金额
     */
    private BigDecimal payamount;

    /**
     * 取消备注
     */
    private String cancelReserveRemarks;

    /**
     *集团编号
     */
    private String groupCode;

    /**
     * 酒店编号
     */
    private String hotelCode;

    /**
     *操作员名称
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

    public BigDecimal getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(BigDecimal roomRate) {
        this.roomRate = roomRate;
    }

    public BigDecimal getActualRate() {
        return actualRate;
    }

    public void setActualRate(BigDecimal actualRate) {
        this.actualRate = actualRate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPayamount() {
        return payamount;
    }

    public void setPayamount(BigDecimal payamount) {
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
