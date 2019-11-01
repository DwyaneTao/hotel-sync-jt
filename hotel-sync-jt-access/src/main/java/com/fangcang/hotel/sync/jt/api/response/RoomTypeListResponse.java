package com.fangcang.hotel.sync.jt.api.response;

import java.util.List;

public class RoomTypeListResponse extends BusinessResponse{


    private List<RoomTypeResponse> roomTypeResponsees;

    public List<RoomTypeResponse> getRoomTypeResponsees() {
        return roomTypeResponsees;
    }

    public void setRoomTypeResponsees(List<RoomTypeResponse> roomTypeResponsees) {
        this.roomTypeResponsees = roomTypeResponsees;
    }
}
