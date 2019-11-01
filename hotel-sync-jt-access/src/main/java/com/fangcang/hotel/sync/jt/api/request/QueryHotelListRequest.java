package com.fangcang.hotel.sync.jt.api.request;

import com.fangcang.hotel.sync.data.dto.BaseSyncRequest;
import com.fangcang.hotel.sync.jt.constants.JTRedisKey;

public class QueryHotelListRequest extends BaseSyncRequest {

    /**
     * 酒店全称
     */
    private String hotelFullName;

    /**
     * 酒店所在的城市
     */
    private String hotelCity;

    /**
     * 状态
     */
    private String status;

    /**
     * 酒店id
     */
    private Integer id;

    /**
     * 集团编码
     */
    private String groupCode;

    /**
     * 酒店编码
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




    public String getHotelFullName() {
        return hotelFullName;
    }

    public void setHotelFullName(String hotelFullName) {
        this.hotelFullName = hotelFullName;
    }

    public String getHotelCity() {
        return hotelCity;
    }

    public void setHotelCity(String hotelCity) {
        this.hotelCity = hotelCity;
    }

    @Override
    public boolean check() {
        return true;
    }

    @Override
    public String toJsonString() {
        return null;
    }

    @Override
    public String obtainRedisKey() {
        return JTRedisKey.HOTEL_BASIC_INFO_KEY;
    }

    @Override
    public String obtainSyncHotelIds() {
        return null;
    }

    @Override
    public String obtainExceptionRedisKey() {
        return null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
