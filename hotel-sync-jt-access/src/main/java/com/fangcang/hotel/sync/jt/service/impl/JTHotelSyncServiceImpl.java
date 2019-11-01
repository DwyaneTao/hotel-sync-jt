package com.fangcang.hotel.sync.jt.service.impl;

import com.fangcang.hotel.sync.common.api.dto.CommonServerResponse;
import com.fangcang.hotel.sync.data.dto.MsgResponse;
import com.fangcang.hotel.sync.data.service.CommonServerRequestService;
import com.fangcang.hotel.sync.jt.api.request.*;
import com.fangcang.hotel.sync.jt.api.response.FitReserveResponse;
import com.fangcang.hotel.sync.jt.api.response.Response;
import com.fangcang.hotel.sync.jt.manager.JTManager;
import com.fangcang.hotel.sync.jt.service.JTHotelSyncService;
import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("jtHotelSyncServiceImpl")
public class JTHotelSyncServiceImpl implements JTHotelSyncService {


    private static final Logger logger = LoggerFactory.getLogger(JTHotelSyncServiceImpl.class);

    @Autowired
    private JTManager jtManager;

    @Autowired
    private CommonServerRequestService commonSvrReqService;

    @Override
    public MsgResponse queryHotelList(QueryHotelListRequest requset) throws Exception {
        MsgResponse msgResponse = new MsgResponse();
        CommonServerResponse commonServerResponse = null;
//
//
//        try {
//            HotelListResponse response = jtManager.queryHotelList(requset);
//            if ( null != response && !response.getHotelResponses().isEmpty()) {
//                List<HotelMapping> list = new LinkedList<>();
//                for (HotelResponse hotelRespons : response.getHotelResponses()) {
//                    HotelMapping hotel = new HotelMapping();
//                    hotel.setHotelName(hotelRespons.getHotelFullName());
//                    hotel.setHotelAddress(hotelRespons.getHotelAddress());
//                    list.add(hotel);
//
//                }
//                commonServerResponse = commonSvrReqService.sendRequest(CommonServerApi.ADD_HOTEL_MAPPING, list);
//            }
//            msgResponse.setCode(commonServerResponse.getReturnCode());
//            msgResponse.setMsg(commonServerResponse.getReturnMsg());
//        } catch (Exception e){
//            logger.error("获取酒店信息失败。", e);
//            msgResponse.setCode(0);
//            msgResponse.setMsg("FAIL");
//        }
        return null;
    }

    @Override
    public MsgResponse queryRoomTypeList(QueryRoomTypeListRequest requset) throws Exception {
        MsgResponse msgResponse = new MsgResponse();
//        try {
//            Response<RoomTypeResponse> response = jtManager.queryRoomTypeList(requset);
//            msgResponse.setCode(1);
//            msgResponse.setMsg("SUCCESS");
//        } catch (Exception e) {
//            logger.error("获取房型基础信息失败。", e);
//            msgResponse.setCode(0);
//            msgResponse.setMsg("FAIL");
//        }
        return msgResponse;
    }

    @Override
    public MsgResponse createFitReserve(QueryFitReserveRequest requset) throws Exception {
        MsgResponse msgResponse = new MsgResponse();
        try {
            Response<FitReserveResponse> response = jtManager.createFitReserve(requset);
            msgResponse.setCode(1);
            msgResponse.setMsg("SUCCESS");
        } catch (Exception e) {
            logger.error("获取房型基础信息失败。", e);
            msgResponse.setCode(0);
            msgResponse.setMsg("FAIL");
        }
        return msgResponse;
    }

    @Override
    public MsgResponse cancelFitReserve(CancelFitReserveRequest requset) throws Exception {
        return null;
    }

    @Override
    public MsgResponse batchRoomResourceList(RoomResourceListRequest requset) throws Exception {
        return null;
    }

    @Override
    public MsgResponse singleTrialFitReserve(SingleTrialFitReserveRequest requset) throws Exception {
        return null;
    }

    @Override
    public MsgResponse prepayFitReserve(PrepayFitReserveRequest requset) throws Exception {
        return null;
    }

    @Override
    public MsgResponse getRoomStockList(RoomStockListRequest requset) throws Exception {
        return null;
    }

    @Override
    public MsgResponse getDailyRoomPriceList(DailyRoomPriceListRequest requset) throws Exception {
        return null;
    }

    @Override
    public MsgResponse getIncrementDailyRoomPriceList(IncrementDailyRoomPriceListRequest requset) throws Exception {
        return null;
    }

    @Override
    public MsgResponse getHotelIncrementRoomPriceList(HotelIncrementRoomPriceListRequest requset) throws Exception {
        return null;
    }
}
