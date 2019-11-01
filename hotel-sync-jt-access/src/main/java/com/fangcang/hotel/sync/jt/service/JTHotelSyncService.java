package com.fangcang.hotel.sync.jt.service;

import com.fangcang.hotel.sync.data.dto.MsgResponse;
import com.fangcang.hotel.sync.jt.api.request.*;

public interface JTHotelSyncService {

    /**
     *酒店基础信息列表
     */
    MsgResponse queryHotelList(QueryHotelListRequest requset) throws Exception;

    /**
     * 房型基础信息接口
     */
    MsgResponse queryRoomTypeList(QueryRoomTypeListRequest requset) throws Exception;

    /**
     * 订单创建
     */
    MsgResponse createFitReserve(QueryFitReserveRequest requset) throws Exception;

    MsgResponse  cancelFitReserve(CancelFitReserveRequest requset) throws Exception;

    MsgResponse  batchRoomResourceList(RoomResourceListRequest requset) throws Exception;

    MsgResponse  singleTrialFitReserve(SingleTrialFitReserveRequest requset) throws Exception;

    MsgResponse prepayFitReserve(PrepayFitReserveRequest requset) throws Exception;

    MsgResponse getRoomStockList(RoomStockListRequest requset) throws Exception;

    MsgResponse getDailyRoomPriceList(DailyRoomPriceListRequest requset) throws Exception;

    MsgResponse getIncrementDailyRoomPriceList(IncrementDailyRoomPriceListRequest requset) throws Exception;

    MsgResponse getHotelIncrementRoomPriceList (HotelIncrementRoomPriceListRequest requset) throws Exception;


}
