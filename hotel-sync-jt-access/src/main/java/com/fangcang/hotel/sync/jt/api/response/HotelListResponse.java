package com.fangcang.hotel.sync.jt.api.response;

import java.util.List;

public class HotelListResponse extends BusinessResponse{

    private List<HotelResponse> hotelResponses;

    public List<HotelResponse> getHotelResponses() {
        return hotelResponses;
    }

    public void setHotelResponses(List<HotelResponse> hotelResponses) {
        this.hotelResponses = hotelResponses;
    }
}
