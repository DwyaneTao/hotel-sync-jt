package com.fangcang.hotel.sync.jt.api.request;

/**
 * 预订试单请求参数
 * @author py
 * @date 2019/10/23 18:26
 **/
public class SingleTrialFitReserveRequest extends  BusinessRequest {

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 起始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 房型
     */
    private String roomType;

    /**
     * 预订数量
     */
    private Integer roomQuantity;

    /**
     * 会员卡号
     */
    private Integer cardNo;

    /**
     * 推荐会员卡号
     */
    private String comCardNo;

    /**
     * ID
     */
    private Integer id;

    /**
     * 集团编号
     */
    private String groupCode;

    /**
     * 酒店编号
     */
    private String hotelCode;

    /**
     * 升序字段,多项用英文逗号隔开
     */
    private String ascFields;

    /**
     * 降序字段,多项用英文逗号隔开
     */
    private String descFields;

    /**
     * 页码
     */
    private Integer pageIndex;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(Integer roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    public Integer getCardNo() {
        return cardNo;
    }

    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    public String getComCardNo() {
        return comCardNo;
    }

    public void setComCardNo(String comCardNo) {
        this.comCardNo = comCardNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAscFields() {
        return ascFields;
    }

    public void setAscFields(String ascFields) {
        this.ascFields = ascFields;
    }

    public String getDescFields() {
        return descFields;
    }

    public void setDescFields(String descFields) {
        this.descFields = descFields;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
