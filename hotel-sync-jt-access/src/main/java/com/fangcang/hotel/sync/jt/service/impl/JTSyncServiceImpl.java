package com.fangcang.hotel.sync.jt.service.impl;

import com.alibaba.fastjson.JSON;
import com.fangcang.hotel.sync.data.annotations.SyncPlugin;
import com.fangcang.hotel.sync.data.dto.BaseSyncRequest;
import com.fangcang.hotel.sync.data.service.SupplySyncService;
import com.fangcang.hotel.sync.jt.api.request.DailyRoomPriceListRequest;
import com.fangcang.hotel.sync.jt.api.request.QueryHotelListRequest;
import com.fangcang.hotel.sync.jt.api.request.QueryRoomTypeListRequest;
import com.fangcang.hotel.sync.jt.api.request.RoomStockListRequest;
import com.fangcang.hotel.sync.jt.api.response.*;
import com.fangcang.hotel.sync.jt.api.response.hotel.DayCmsRmPriceListResponse;
import com.fangcang.hotel.sync.jt.api.response.hotel.HotelDetail;
import com.fangcang.hotel.sync.jt.api.response.hotel.HotelInfo;
import com.fangcang.hotel.sync.jt.api.response.hotel.RoomType;
import com.fangcang.hotel.sync.jt.config.JTConfig;
import com.fangcang.hotel.sync.jt.dto.*;
import com.fangcang.hotel.sync.jt.manager.JTManager;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("jtSyncService")
@SyncPlugin(supplierClass = "JT")
public class JTSyncServiceImpl implements SupplySyncService {

    private static final Logger logger = LoggerFactory.getLogger(JTSyncServiceImpl.class);

    @Autowired
    private JTManager jtManager;

    @Override
    public String queryHotelBasicInfo(BaseSyncRequest baseSyncRequest) throws Exception {
        //基础数据
        QueryHotelListRequest hotelRequest = (QueryHotelListRequest) baseSyncRequest;
        Response<HotelListResponse> hotelListResponseResponse = jtManager.queryHotelList(hotelRequest);
        Integer totalCount = hotelListResponseResponse.getTotalCount();
        hotelRequest.setPageSize(totalCount);
        List<HotelResponse> hotelResponses = jtManager.queryHotelList(hotelRequest).getData().getHotelResponses();
        QueryHotelDetailResponse queryHotelDetailResponse = new QueryHotelDetailResponse();
        List<HotelDetail> list = new LinkedList<>();
        for (HotelResponse hotelRespons : hotelResponses) {
            HotelDetail hotelDetail = new HotelDetail();
            HotelInfo hotelInfo = new HotelInfo();
            hotelInfo.setGroupCode(hotelRespons.getGroupCode());
            hotelInfo.setHotelCode(hotelRespons.getHotelCode());
            hotelInfo.setHotelFullName(hotelRespons.getHotelFullName());
            hotelInfo.setHotelAddress(hotelRespons.getHotelAddress());
            hotelDetail.setHotelInfo(hotelInfo);
            QueryRoomTypeListRequest  roomTypeListRequest = new QueryRoomTypeListRequest();
            roomTypeListRequest.setGroupCode(hotelRespons.getGroupCode());
            roomTypeListRequest.setHotelCode(hotelRespons.getHotelCode());
            roomTypeListRequest.setChannelCode(JTConfig.getChannelCode());
            Response<RoomTypeListResponse> roomTypeListResponseResponse = jtManager.queryRoomTypeList(roomTypeListRequest);
            if ( null != roomTypeListResponseResponse && null !=roomTypeListResponseResponse.getData() ) {
                List<RoomTypeResponse> roomTypeResponsees = roomTypeListResponseResponse.getData().getRoomTypeResponsees();
                List<RoomType>  roomTypeList = new LinkedList<>();
                if ( null != roomTypeResponsees && !roomTypeResponsees.isEmpty()) {
                    for (RoomTypeResponse roomTypeResponsee : roomTypeResponsees) {
                        RoomType roomType = new RoomType();
                        roomType.setRoomType(roomTypeResponsee.getRoomType());
                        roomType.setDescription(roomTypeResponsee.getDescription());
                        roomType.setEnglishDescription(roomTypeResponsee.getEnglishDescription());
                        roomType.setGroupCode(roomTypeResponsee.getGroupCode());
                        roomType.setHotelCode(roomTypeResponsee.getHotelCode());
                        roomType.setUserName(roomTypeResponsee.getUserName());
                        roomTypeList.add(roomType);

                    }
                    hotelDetail.setRoomTypeList(roomTypeList);
                }
            }
            list.add(hotelDetail);
        }
        queryHotelDetailResponse.setHotelDetailList(list);
        queryHotelDetailResponse.setCode(hotelListResponseResponse.getCode());
        queryHotelDetailResponse.setMessage(hotelListResponseResponse.getMessage());
        queryHotelDetailResponse.setSuccess(hotelListResponseResponse.getSuccess());
        return JSON.toJSONString(queryHotelDetailResponse);
    }


    /**
     * 产品
     */
    @Override
    public String queryProductInfo(BaseSyncRequest baseSyncRequest) throws Exception {
        String responseJson = null;
        JTProductDataRequest request = (JTProductDataRequest) baseSyncRequest;

        QueryHotelListRequest queryHotelListRequest = new QueryHotelListRequest();
        queryHotelListRequest.setSupplyCode(request.getSupplyCode());

        RoomStockListRequest roomStockListRequest = new RoomStockListRequest();
        roomStockListRequest.setChannelCode(JTConfig.getChannelCode());
        roomStockListRequest.setGroupCode(JTConfig.getGroupCode());
        roomStockListRequest.setHotelCode("J571005");
        roomStockListRequest.setStartDate("2019-11-05");
        roomStockListRequest.setEndDate("2019-11-05");

        DailyRoomPriceListRequest dailyRoomPriceListRequest = new DailyRoomPriceListRequest();
        dailyRoomPriceListRequest.setChannelCode(JTConfig.getChannelCode());
        dailyRoomPriceListRequest.setRateCode("VIP1");
        dailyRoomPriceListRequest.setGroupCode(JTConfig.getGroupCode());
        dailyRoomPriceListRequest.setHotelCode("J571005");

        Response<ResRoomStockOutputListResponse> roomStockList = jtManager.getRoomStockList(roomStockListRequest);

        JTProductDto jtProductDto = new JTProductDto();
        List<JTProductDataDto> jtProductDataDtoList = new LinkedList<>();
        for (ResRoomStockOutputResponse resRoomStockOutputRespons : roomStockList.getData().getResRoomStockOutputResponses()) {

            JTProductDataDto jtProductDataDto = new JTProductDataDto();
            jtProductDataDto.setGroupCode(JTConfig.getGroupCode());
            jtProductDataDto.setHotelCode(resRoomStockOutputRespons.getHotelCode());
            jtProductDataDto.setBusinessDate(resRoomStockOutputRespons.getBusinessDate());
            jtProductDataDto.setRoomType(resRoomStockOutputRespons.getRoomType());
            jtProductDataDto.setTotalQuantity(resRoomStockOutputRespons.getTotalQuantity().toString());//房间总数
            jtProductDataDto.setCurrentQuantity(resRoomStockOutputRespons.getCurrentQuantity().toString());//当前可用数
            jtProductDataDto.setTodayDepartureQuantity(resRoomStockOutputRespons.getTodayDepartureQuantity().toString());//本日将离数
            jtProductDataDto.setOooQuantity(resRoomStockOutputRespons.getOooQuantity().toString());//维修房的房数

            List<DayCmsRmPriceResponse> dayCmsRmPriceResponses = null;
            dailyRoomPriceListRequest.setRoomType(resRoomStockOutputRespons.getRoomType());
            Response<DayCmsRmPriceListResponse> dailyRoomPriceList = jtManager.getDailyRoomPriceList(dailyRoomPriceListRequest);
            if ( null != dailyRoomPriceList.getData() && !dailyRoomPriceList.getData().getDayCmsRmPriceResponses().isEmpty() ) {
                dayCmsRmPriceResponses = dailyRoomPriceList.getData().getDayCmsRmPriceResponses();
                jtProductDataDto.setJTProductRoomDtos(dayCmsRmPriceResponses);
            }
            jtProductDataDtoList.add(jtProductDataDto);
        }
        jtProductDto.setJtProductDtos(jtProductDataDtoList);
        request.setRequestJson(JSON.toJSONString(request));
        return JSON.toJSONString(jtProductDto);
    }
}
