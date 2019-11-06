package com.fangcang.hotel.sync.jt.api.request;

import com.fangcang.hotel.sync.data.dto.BaseSyncRequest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class QueryFitReserveRequest extends BusinessRequest{

      /**
     * 入住人
     */
    private String name;

    /**
     * 手机
     */
    private String reservePhone;

    /**
     * 预订总房数
     */
    private String roomQuantity;

    /**
     * 房类
     */
    private String roomType;

    /**
     * 门市价
     */
    private String roomRate;

    /**
     *成交价
     */
    private String actualRate;

    /**
     * 会员卡号（可选择的）
     */
    private String cardNo;

    /**
     * 会员卡类型（可选择的）
     */
    private String cardType;

    /**
     * 优惠券券码（可选择的）
     */
    private String couponsCode;

    /**
     * 优惠金额（可选择的）
     */
    private BigDecimal couponsAmount;

    /**
     * 房价码
     */
    private String rateCode;

    /**
     * 市场码（可选择的）
     */
    private String marketCode;

    /**
     * 来源码（可选择的）
     */
    private String sourceCode;

    /**
     * 包价码（可选择的）
     */
    private String packages;

    /**
     * 预订类型（可选择的）
     */
    private String reserveType;

    /**
     *  预订来源（可选择的）
     */
    private String reserveSource;

    /**
     *渠道编码
     */
    private String channelCode;

    /**
     * 到日
     */
    private String arrivalDate;

    /**
     * 离日
     */
    private String departureDate;

    /**
     * 保留时间（可选择的）
     */
    private String holdDate;

    /**
     *入住天数
     */
    private String noofDays;

    /**
     * 状态（可选择的）
     */
    private String status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间（可选择的）
     */
    private String createDate;

    /**
     * 工号（可选择的）
     */
    private String salesId;

    /**
     * 订单预订总金额（可选择的）
     */
    private BigDecimal totalPrice;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     *  每日房价列表：一日一房价（可选择的）
     */
    private List<ResRoomResourcePriceInputModel> dayPriceList;

    /**
     * 集团编号
     */
    private String groupCode;

    /**
     * 酒店编号
     */
    private String hotelCode;

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReservePhone() {
        return reservePhone;
    }

    public void setReservePhone(String reservePhone) {
        this.reservePhone = reservePhone;
    }

    public String getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(String roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(String roomRate) {
        this.roomRate = roomRate;
    }

    public String getActualRate() {
        return actualRate;
    }

    public void setActualRate(String actualRate) {
        this.actualRate = actualRate;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCouponsCode() {
        return couponsCode;
    }

    public void setCouponsCode(String couponsCode) {
        this.couponsCode = couponsCode;
    }

    public BigDecimal getCouponsAmount() {
        return couponsAmount;
    }

    public void setCouponsAmount(BigDecimal couponsAmount) {
        this.couponsAmount = couponsAmount;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getReserveType() {
        return reserveType;
    }

    public void setReserveType(String reserveType) {
        this.reserveType = reserveType;
    }

    public String getReserveSource() {
        return reserveSource;
    }

    public void setReserveSource(String reserveSource) {
        this.reserveSource = reserveSource;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
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

    public String getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(String holdDate) {
        this.holdDate = holdDate;
    }

    public String getNoofDays() {
        return noofDays;
    }

    public void setNoofDays(String noofDays) {
        this.noofDays = noofDays;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ResRoomResourcePriceInputModel> getDayPriceList() {
        return dayPriceList;
    }

    public void setDayPriceList(List<ResRoomResourcePriceInputModel> dayPriceList) {
        this.dayPriceList = dayPriceList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
