package com.fangcang.hotel.sync.jt.api.request;

public class QueryHotelDetailRequest extends BusinessRequest{

    /**
     * 酒店全称
     */
    private String HotelFullName;

    /**
     * 酒店所在的城市
     */
    private String HotelCity;

    /**
     * 开始时间
     */
    private String StartDate;

    /**
     * 结束时间
     */
    private String EndDate;

    /**
     * 状态
     */
    private String Status;

    /**
     * 酒店id
     */
    private Integer id;

    /**
     * 集团编码
     */
    private String GroupCode;

    /**
     * 酒店编码
     */
    private String HotelCode;

    /**
     * 升序字段,多项用英文逗号隔开
     */
    private String AscFields;

    /**
     * 降序字段,多项用英文逗号隔开
     */
    private String DescFields;

    /**
     * 页码
     */
    private Integer pageIndex;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    public String getHotelFullName() {
        return HotelFullName;
    }

    public void setHotelFullName(String hotelFullName) {
        HotelFullName = hotelFullName;
    }

    public String getHotelCity() {
        return HotelCity;
    }

    public void setHotelCity(String hotelCity) {
        HotelCity = hotelCity;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAscFields() {
        return AscFields;
    }

    public void setAscFields(String ascFields) {
        AscFields = ascFields;
    }

    public String getDescFields() {
        return DescFields;
    }

    public void setDescFields(String descFields) {
        DescFields = descFields;
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
