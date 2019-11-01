package com.fangcang.hotel.sync.jt.api.response;

/**
 * 房量返回参数
 * @author py
 * @date 2019/10/23 19:48
 **/
public class ResRoomStockOutputResponse {

    /**
     * 集团编号
     */
    private String groupCode;

    /**
     * 酒店编号
     */
    private String hotelCode;

    /**
     * 营业日期
     */
    private String businessDate;

    /**
     * 房类
     */
    private String roomType;

    /**
     *  房间总数
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

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
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
}
