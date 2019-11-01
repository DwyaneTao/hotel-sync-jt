package com.fangcang.hotel.sync.jt.constants;

public class JTConstants {


    /**
     * 获得访问令牌
     */
    public static final String getToken = "/oauth2/GetToken";

    /**
     * 刷新令牌接口
     */
//    public static final String refreshToken = "/oauth2/RefreshToken";

    /**
     *酒店基础信息列表
     */
    public static final String getHotelList = "/MinReserve/GetHotelList";


    /**
     * 房型基础信息列表
     */
    public static final String getCmsRoomTypeList = "/cms/GetCmsRoomTypeList";


    /**
     * 订单创建
     */
    public static final String createFitReserve = "/MinReserve/CreateFitReserve";


    /**
     * 订单取消接口
     */
    public static final String cancelFitReserve = "/MinReserve/CancelFitReserve";


    /**
     * 订单状态查询接口
     */
    public static final String getBatchRoomResourceList = "/MinReserve/GetBatchRoomResourceList";


    /**
     * 预订试单
     */
    public static final String singleTrialFitReserve = "/MinReserve/SingleTrialFitReserve";

    /**
     * 订单预付
     */
    public static final String prepayFitReserve = "/MinReserve/PrepayFitReserve";


    /**
     * 房量
     */
    public static final String getRoomStockList = "/Cms/GetRoomStockList";


    /**
     * 房价
     */
    public static final String getDailyRoomPriceList = "/Cms/GetDailyRoomPriceList";


    /**
     * 房价变化增量
     */
    public static final String getIncrementDailyRoomPriceList = "/Cms/GetIncrementDailyRoomPriceList";


    /**
     *  酒店房价变化增量
     */
    public static final String getHotelIncrementRoomPriceList  = "/Cms/GetHotelIncrementRoomPriceList";

}
