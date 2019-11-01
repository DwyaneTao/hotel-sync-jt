package com.fangcang.hotel.sync.jt.api.request;

import java.math.BigDecimal;

/**
 * 订单预付请求参数
 * @author py
 * @date 2019/10/23 19:37
 **/
public class PrepayFitReserveRequest extends BusinessRequest {

    /**
     * 中央订单号
     */
    private BigDecimal serial;

    /**
     * 费用码
     */
    private String feeCode;

    /**
     * 预付金额
     */
    private BigDecimal payamount;

    /**
     * 预付类型
     */
    private String paymentType;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 修改时间
     */
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
    private String userName;

    public BigDecimal getSerial() {
        return serial;
    }

    public void setSerial(BigDecimal serial) {
        this.serial = serial;
    }

    public String getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    public BigDecimal getPayamount() {
        return payamount;
    }

    public void setPayamount(BigDecimal payamount) {
        this.payamount = payamount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
