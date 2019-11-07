package com.fangcang.hotel.sync.jt.api.response;

/**
 * 预订试单返回值
 * @author py
 * @date 2019/10/23 18:33
 **/
public class RoomResourceSingleTrialOutputResponse {

    /**
     * 房类
     */
    private String roomType;

    /**
     * 营业日期
     */
    private String businessDate;

    /**
     * 房间总数
     */
    private Integer totalQuantity;

    /**
     * 当前可用数
     */
    private Integer currentQuantity;

    /**
     * 本日将离数
     */
    private Integer todayDepartureQuantity;

    /**
     * 维修房的房数
     */
    private Integer oooQuantity;

    /**
     * 备注
     */
    private String remarks;

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Integer getTodayDepartureQuantity() {
        return todayDepartureQuantity;
    }

    public void setTodayDepartureQuantity(Integer todayDepartureQuantity) {
        this.todayDepartureQuantity = todayDepartureQuantity;
    }

    public Integer getOooQuantity() {
        return oooQuantity;
    }

    public void setOooQuantity(Integer oooQuantity) {
        this.oooQuantity = oooQuantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
