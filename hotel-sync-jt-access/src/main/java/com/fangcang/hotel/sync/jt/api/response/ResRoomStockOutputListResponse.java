package com.fangcang.hotel.sync.jt.api.response;

import java.util.List;

public class ResRoomStockOutputListResponse  extends BusinessResponse  {

    private List<ResRoomStockOutputResponse> resRoomStockOutputResponses;

    public List<ResRoomStockOutputResponse> getResRoomStockOutputResponses() {
        return resRoomStockOutputResponses;
    }

    public void setResRoomStockOutputResponses(List<ResRoomStockOutputResponse> resRoomStockOutputResponses) {
        this.resRoomStockOutputResponses = resRoomStockOutputResponses;
    }
}
