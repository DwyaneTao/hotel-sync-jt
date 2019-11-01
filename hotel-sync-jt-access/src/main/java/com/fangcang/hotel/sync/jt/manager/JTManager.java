package com.fangcang.hotel.sync.jt.manager;

import com.fangcang.hotel.sync.jt.api.request.*;
import com.fangcang.hotel.sync.jt.api.response.*;
import com.fangcang.hotel.sync.jt.api.response.hotel.DayCmsRmPriceListResponse;

public interface JTManager {


    Response<HotelListResponse> queryHotelList(QueryHotelListRequest request)  throws Exception;


    Response<RoomTypeListResponse> queryRoomTypeList(QueryRoomTypeListRequest request) throws Exception;


    Response<FitReserveResponse> createFitReserve(QueryFitReserveRequest requset) throws Exception;

    Response<FitReserveResponse> cancelFitReserve(CancelFitReserveRequest requset) throws Exception;

    Response<ResRoomResourceLiteOutputResponse> batchRoomResourceList(RoomResourceListRequest requset) throws Exception;

    Response<RoomResourceSingleTrialOutputResponse> singleTrialFitReserve(SingleTrialFitReserveRequest requset) throws Exception;

    Response prepayFitReserve(PrepayFitReserveRequest requset) throws Exception;

    Response<ResRoomStockOutputListResponse> getRoomStockList(RoomStockListRequest requset) throws Exception;

    Response<DayCmsRmPriceListResponse> getDailyRoomPriceList(DailyRoomPriceListRequest requset) throws Exception;
//
//    Response<DayCmsRmPriceResponse> getIncrementDailyRoomPriceList(IncrementDailyRoomPriceListRequest requset) throws Exception;

    Response<IncrementRmPriceInputResponse> getHotelIncrementRoomPriceList (HotelIncrementRoomPriceListRequest requset) throws Exception;

}
