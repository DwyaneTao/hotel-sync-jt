package com.fangcang.hotel.sync.jt.dto;

import com.fangcang.hotel.sync.jt.api.response.DayCmsRmPriceResponse;

import java.util.List;

public class JTProductDataDto {


    private String groupCode;

    private String hotelCode;

    private String businessDate;

    private String roomType;

    private String totalQuantity;

    private String currentQuantity;

    private String todayDepartureQuantity;

    private String oooQuantity;

    private List<DayCmsRmPriceResponse> JTProductRoomDtos;

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

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(String currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public String getTodayDepartureQuantity() {
        return todayDepartureQuantity;
    }

    public void setTodayDepartureQuantity(String todayDepartureQuantity) {
        this.todayDepartureQuantity = todayDepartureQuantity;
    }

    public String getOooQuantity() {
        return oooQuantity;
    }

    public void setOooQuantity(String oooQuantity) {
        this.oooQuantity = oooQuantity;
    }

    public List<DayCmsRmPriceResponse> getJTProductRoomDtos() {
        return JTProductRoomDtos;
    }

    public void setJTProductRoomDtos(List<DayCmsRmPriceResponse> JTProductRoomDtos) {
        this.JTProductRoomDtos = JTProductRoomDtos;
    }
}
