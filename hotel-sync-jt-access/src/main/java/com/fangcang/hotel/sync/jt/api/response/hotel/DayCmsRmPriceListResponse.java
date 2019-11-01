package com.fangcang.hotel.sync.jt.api.response.hotel;

import com.fangcang.hotel.sync.jt.api.response.BusinessResponse;
import com.fangcang.hotel.sync.jt.api.response.DayCmsRmPriceResponse;

import java.util.List;

public class DayCmsRmPriceListResponse extends BusinessResponse {

    private List<DayCmsRmPriceResponse> dayCmsRmPriceResponses;

    public List<DayCmsRmPriceResponse> getDayCmsRmPriceResponses() {
        return dayCmsRmPriceResponses;
    }

    public void setDayCmsRmPriceResponses(List<DayCmsRmPriceResponse> dayCmsRmPriceResponses) {
        this.dayCmsRmPriceResponses = dayCmsRmPriceResponses;
    }
}
