package com.fangcang.hotel.sync.jt.service.impl;

import com.fangcang.hotel.sync.data.dto.BaseSyncRequest;
import com.fangcang.hotel.sync.data.service.SupplyFetchService;

import com.fangcang.hotel.sync.jt.api.request.QueryHotelListRequest;
import com.fangcang.hotel.sync.jt.dto.JTHotelBasicInfoRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jtFetchService")
public class JTFetchServiceImpl implements SupplyFetchService {

    private Log log = LogFactory.getLog(JTFetchServiceImpl.class);


    @Autowired
    private JTSyncServiceImpl jtSyncService;

    @Override
    public void fetchHotelBasicInfo(BaseSyncRequest baseSyncRequest) throws Exception {
        QueryHotelListRequest jtHotelBasicInfoRequest = (QueryHotelListRequest) baseSyncRequest;
        jtSyncService.queryHotelBasicInfo(jtHotelBasicInfoRequest);
    }

    @Override
    public void fetchHotelProductInfo(BaseSyncRequest baseSyncRequest) throws Exception {
        jtSyncService.queryProductInfo(baseSyncRequest);
    }

    @Override
    public boolean checkAuthno(BaseSyncRequest baseSyncRequest) {
        return false;
    }
}
