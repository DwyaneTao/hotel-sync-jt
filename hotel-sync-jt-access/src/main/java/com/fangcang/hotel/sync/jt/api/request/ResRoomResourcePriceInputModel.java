package com.fangcang.hotel.sync.jt.api.request;

import java.math.BigDecimal;

public class ResRoomResourcePriceInputModel extends  BusinessRequest {

    /**
     * 中央订单号
     */
    private String serial;

    /**
     * 酒店订单号
     */
    private String accountNo;

    /**
     * 营业日期
     */
    private String businessDate;

    /**
     * 房类
     */
    private String roomTyper;

    /**
     * 房价码
     */
    private String rateCode;
    /**
     *门市价
     */
    private BigDecimal roomRate;

    /**
     * 促销价
     */
    private BigDecimal promotionPricel;

    /**
     * 指导价
     */
    private BigDecimal guidePrice;

    /**
     * 成交价
     */
    private BigDecimal actualRate;

    /**
     *优惠金额
     */
    private BigDecimal couponsAmount;

    /**
     * 变价理由
     */
    private String reasion;

    /**
     * 备注
     */
    private String remarks;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getRoomTyper() {
        return roomTyper;
    }

    public void setRoomTyper(String roomTyper) {
        this.roomTyper = roomTyper;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public BigDecimal getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(BigDecimal roomRate) {
        this.roomRate = roomRate;
    }

    public BigDecimal getPromotionPricel() {
        return promotionPricel;
    }

    public void setPromotionPricel(BigDecimal promotionPricel) {
        this.promotionPricel = promotionPricel;
    }

    public BigDecimal getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(BigDecimal guidePrice) {
        this.guidePrice = guidePrice;
    }

    public BigDecimal getActualRate() {
        return actualRate;
    }

    public void setActualRate(BigDecimal actualRate) {
        this.actualRate = actualRate;
    }

    public BigDecimal getCouponsAmount() {
        return couponsAmount;
    }

    public void setCouponsAmount(BigDecimal couponsAmount) {
        this.couponsAmount = couponsAmount;
    }

    public String getReasion() {
        return reasion;
    }

    public void setReasion(String reasion) {
        this.reasion = reasion;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
