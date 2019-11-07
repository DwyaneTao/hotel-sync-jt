package com.fangcang.hotel.sync.jt.api.response;

import java.util.List;

public class RoomResourceSingleTrialListResponse extends BusinessResponse  {

    private List<RoomResourceSingleTrialOutputResponse> roomResourceSingleTrialOutputResponses;

    public List<RoomResourceSingleTrialOutputResponse> getRoomResourceSingleTrialOutputResponses() {
        return roomResourceSingleTrialOutputResponses;
    }

    public void setRoomResourceSingleTrialOutputResponses(List<RoomResourceSingleTrialOutputResponse> roomResourceSingleTrialOutputResponses) {
        this.roomResourceSingleTrialOutputResponses = roomResourceSingleTrialOutputResponses;
    }
}
