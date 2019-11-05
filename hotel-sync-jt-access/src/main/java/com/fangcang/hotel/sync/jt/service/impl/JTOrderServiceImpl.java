package com.fangcang.hotel.sync.jt.service.impl;

import com.alibaba.fastjson.JSON;
import com.fangcang.enums.CurrencyEnum;
import com.fangcang.enums.ReturnCodeEnum;
import com.fangcang.enums.RoomStateEnum;
import com.fangcang.enums.returncode.ErrorCodeEnum;
import com.fangcang.enums.returncode.FCReturnNoEnum;
import com.fangcang.finance.exception.ErrorCode;
import com.fangcang.hotel.sync.common.api.constant.OrderOperateEnum;
import com.fangcang.hotel.sync.common.api.dto.mapping.PricePlanMappingDto;
import com.fangcang.hotel.sync.common.api.dto.order.SupplyOrderDto;
import com.fangcang.hotel.sync.data.dto.PreBookingResult;
import com.fangcang.hotel.sync.data.dto.SupplierOrderResult;
import com.fangcang.hotel.sync.data.service.SupplyOrderService;
import com.fangcang.hotel.sync.jt.api.request.*;
import com.fangcang.hotel.sync.jt.api.response.FitReserveResponse;
import com.fangcang.hotel.sync.jt.api.response.ResRoomResourceLiteOutputResponse;
import com.fangcang.hotel.sync.jt.api.response.Response;
import com.fangcang.hotel.sync.jt.api.response.RoomResourceSingleTrialOutputResponse;
import com.fangcang.hotel.sync.jt.config.JTExtendConfig;
import com.fangcang.hotel.sync.jt.constants.JTResponseEnum;
import com.fangcang.hotel.sync.jt.manager.JTManager;
import com.fangcang.hotel.sync.jt.service.JTExtendsConfigService;
import com.fangcang.hotel.sync.order.constants.CanBookEnum;
import com.fangcang.hotel.sync.order.constants.SupplyResultEnum;
import com.fangcang.hotel.sync.order.dto.HotelSupplyOrderDTO;
import com.fangcang.hotel.sync.order.dto.SupplyDetailsDTO;
import com.fangcang.hotel.sync.order.dto.SupplyGuestDTO;
import com.fangcang.hotel.sync.order.dto.request.CreateSupplyOrderRequest;
import com.fangcang.hotel.sync.order.dto.request.PreBookingRequest;
import com.fangcang.hotel.sync.order.dto.request.PriceInfoDetail;
import com.fangcang.util.DateUtil;
import com.fangcang.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("jtOrderService")
public class JTOrderServiceImpl implements SupplyOrderService {

    private Log log = LogFactory.getLog(JTOrderServiceImpl.class);

    @Autowired
    private JTManager jtManager;

    @Autowired
    private JTExtendsConfigService JTExtendConfig;

    @Autowired
    private JTExtendsConfigService jtExtendsConfigService;

    /**
     * 预订试单
     * @param preBookingRequest
     * @param pricePlanMappingDto
     * @return
     * @throws Exception
     */
    @Override
    public PreBookingResult preBooking(PreBookingRequest preBookingRequest, PricePlanMappingDto pricePlanMappingDto) throws Exception {
        Date preBookStartTime = new Date();
        PreBookingResult bookResult = new PreBookingResult();
        String supplyCode = preBookingRequest.getSupplyCode();
        String merchantCode = preBookingRequest.getMerchantCode();
        String ratePlanId = pricePlanMappingDto.getSpPricePlanKey();

        JTExtendConfig config = (JTExtendConfig) jtExtendsConfigService.getExtendConfig(supplyCode, merchantCode);
        if(config == null){
            log.error("君庭试预定失败，没有该商家的配置信息.supplyCode:" + supplyCode +" ,merchantCode:" + merchantCode);
            bookResult.setReturnNo(FCReturnNoEnum._010601033.returnNo);
            bookResult.setReturnCode(FCReturnNoEnum._010601033.returnCode);
            bookResult.setReturnDesc(FCReturnNoEnum._010601033.returnDesc);
            return  bookResult;
        }
        Date preBookTime1 = new Date();
        String checkInDate = preBookingRequest.getCheckInDate();
        String checkOutDate = preBookingRequest.getCheckOutDate();

        bookResult.setRequesTime(new Date());
        bookResult.setCheckInDate(checkInDate);
        bookResult.setCheckOutDate(checkOutDate);

        /**封装试预定请求参数模型**/
        SingleTrialFitReserveRequest reserveRequest = new SingleTrialFitReserveRequest();
        reserveRequest.setCardNo(pricePlanMappingDto.getGuestType());//会员卡号
        reserveRequest.setChannelCode("hongsejl");//渠道编码
        reserveRequest.setComCardNo(preBookingRequest.getSupplyRateId());//推荐会员卡号
        reserveRequest.setStartDate(preBookingRequest.getCheckInDate());//起始日期
        reserveRequest.setEndDate(preBookingRequest.getCheckOutDate());//结束日期
        String roomType = pricePlanMappingDto.getPricePlanName();
        roomType.subSequence(roomType.indexOf("_")+1, roomType.length());
        reserveRequest.setRoomType(roomType);//房型
        reserveRequest.setGroupCode("0003");//集团编号
        reserveRequest.setRoomQuantity(preBookingRequest.getRoomNum());//预订数量
        reserveRequest.setHotelCode(pricePlanMappingDto.getSpHotelId());//酒店编号
        reserveRequest.setId(0);//ID
        reserveRequest.setSupplyCode(supplyCode);
        Date preBookTime2 = new Date();

        Response<RoomResourceSingleTrialOutputResponse> response = null;
        try {
            response = jtManager.singleTrialFitReserve(reserveRequest);
        }catch (Exception e ){
            log.error("试预定君庭订单失败，系统异常.supplyCode:"+supplyCode+",merchantCode:"+merchantCode+
                    ",ratePlanId:"+ratePlanId+",e is" ,e);

            bookResult.setExceptionMsg(e.getMessage());
            bookResult.setReturnNo(FCReturnNoEnum._010601081.returnNo);
            bookResult.setReturnCode(FCReturnNoEnum._010601081.returnCode);
            bookResult.setReturnDesc(FCReturnNoEnum._010601081.returnDesc);
            return bookResult;
        }finally{
            bookResult.setRequestContent(JSON.toJSONString(reserveRequest));
        }
        Date preBookTime3 = new Date();
        bookResult.setResponseContent(JSON.toJSONString(response));
        bookResult.setResponseTime(new Date());

//        /** 获取试预定查询天数 */
//        Set<String> preDays = getQueryDays(DateUtil.stringToDate(checkInDate), DateUtil.stringToDate(checkOutDate));

        // 试预定详情
        List<PriceInfoDetail> priceInfoDetails = new ArrayList<PriceInfoDetail>();
        if(response != null && response.getData() != null){
            PriceInfoDetail priceInfoDetail = new PriceInfoDetail();
            priceInfoDetail.setCurrency(CurrencyEnum.CNY.key);
            priceInfoDetail.setQuotaNum(response.getData().getCurrentQuantity());
            priceInfoDetail.setDate(response.getData().getBusinessDate());
            //若是返回的天数与查询的天数相同
            String businessDate = response.getData().getBusinessDate();
            businessDate.subSequence(0,10);
            if (response.getData().getCurrentQuantity() == 0) {
                bookResult.setCanBook(CanBookEnum.CAN_NOT_BOOK.value);
//            bookResult.setCanImmediate(canImmediate);
                bookResult.setReturnNo(FCReturnNoEnum._010601001.returnNo);
                bookResult.setReturnCode(FCReturnNoEnum._010601001.returnCode);
                bookResult.setReturnDesc(FCReturnNoEnum._010601001.returnDesc + "，调用试预定接口成功，但在" + businessDate + "等日期满房");
                priceInfoDetail.setDate(businessDate);
                priceInfoDetail.setCurrency(CurrencyEnum.CNY.key);
                priceInfoDetail.setQuotaNum(0);
                priceInfoDetail.setPrice(0D);
                priceInfoDetail.setCanbook(CanBookEnum.CAN_NOT_BOOK.value);
                priceInfoDetail.setNeedQuery(0);
                priceInfoDetail.setRoomStatus(RoomStateEnum.FULL_ROOM.key);
                priceInfoDetail.setCanOverDraft(0);
                priceInfoDetails.add(priceInfoDetail);
            }else{
                bookResult.setCanBook(CanBookEnum.CAN_BOOK.value);
//            bookResult.setCanImmediate(canImmediate);
                bookResult.setReturnNo(FCReturnNoEnum._010000000.returnNo);
                bookResult.setReturnCode(FCReturnNoEnum._010000000.returnCode);
                bookResult.setReturnDesc(FCReturnNoEnum._010000000.returnDesc);
                priceInfoDetail.setDate(businessDate);
                priceInfoDetail.setCurrency(CurrencyEnum.CNY.key);
                priceInfoDetail.setQuotaNum(0);
                priceInfoDetail.setPrice(0D);
                priceInfoDetail.setCanbook(CanBookEnum.CAN_BOOK.value);
                priceInfoDetail.setNeedQuery(0);
                priceInfoDetail.setRoomStatus(RoomStateEnum.HAVA_ROOM.key);
                priceInfoDetail.setCanOverDraft(0);
                priceInfoDetails.add(priceInfoDetail);
            }
            bookResult.setPreBookingResponseDetails(priceInfoDetails);

        }else {
            log.error("君庭试单验证失败，返回失败结果.");
        }
        bookResult.setSupplyCode(supplyCode);
        bookResult.setHotelId(preBookingRequest.getHotelId());
        bookResult.setRoomId(preBookingRequest.getRoomId());
        bookResult.setPricePlanId(pricePlanMappingDto.getPricePlanId().toString());
        long step1 = preBookTime1.getTime() - preBookStartTime.getTime();
        long step2 = preBookTime2.getTime() - preBookTime1.getTime();
        long step3 = preBookTime3.getTime() - preBookTime2.getTime();
        log.info("君庭试预订耗时（单位：毫秒）情况：1获取参数耗时：" + step1 + ";2getPrices耗时：" + step2 + ";3组装返回参数耗时：" + step3);
        return bookResult;
    }

    /**
     * 创建订单
     * @param createOrderRequest
     * @param pricePlanMappingDto
     * @param hotelSupplyOrderDTO
     * @return
     * @throws Exception
     */
    @Override
    public SupplierOrderResult createSupplierOrder(CreateSupplyOrderRequest createOrderRequest, PricePlanMappingDto pricePlanMappingDto, HotelSupplyOrderDTO hotelSupplyOrderDTO) throws Exception {
        SupplierOrderResult response = new SupplierOrderResult();
        String supplyCode = createOrderRequest.getSupplyCode();//商家编码
        String merchantCode = createOrderRequest.getMerchantCode(); //供货商编码
        String supplyOrderCode = hotelSupplyOrderDTO.getSupplyOrderCode();//供货单编码

        JTExtendConfig sjlExtendConfig = (JTExtendConfig) jtExtendsConfigService.getExtendConfig(createOrderRequest.getSupplyCode(),
                createOrderRequest.getMerchantCode());
        if (sjlExtendConfig == null){
            log.error("创建君庭订单失败，没有找到商家配置！supplyCode:" + createOrderRequest +",merchantCode:"
                    + createOrderRequest.getMerchantCode());
            fillResult(response, FCReturnNoEnum._010602033);
            return response;
        }
        //请求参数
        QueryFitReserveRequest request = new QueryFitReserveRequest();
        List<SupplyGuestDTO> guestList = hotelSupplyOrderDTO.getSupplyGuests();
        for (SupplyGuestDTO guestDTO : guestList){
            request.setName(guestDTO.getGuestName());//入住人
            request.setReservePhone(guestDTO.getPhone());//手机
        }
        request.setRoomQuantity(String.valueOf(hotelSupplyOrderDTO.getRoomNum()));//预定总房数
        request.setRoomType(hotelSupplyOrderDTO.getRoomTypeName());//房类
        List<SupplyDetailsDTO> detailsDTOList = hotelSupplyOrderDTO.getSupplyDetails();
        String priceCheck = null;
        String roomIndex = null;
        for (SupplyDetailsDTO detailsDTO : detailsDTOList){
            priceCheck = detailsDTO.getPriceCheck();
            roomIndex = detailsDTO.getRoomIndex();
            request.setRoomRate(priceCheck);//门市价
            request.setRateCode(roomIndex);//房价码

        }
        request.setPayAmount(hotelSupplyOrderDTO.getOrderSum());//支付金额
        request.setActualRate(hotelSupplyOrderDTO.getOrderSum().toString());//成交价
        request.setCouponsAmount(new BigDecimal(10));//优惠金额（可选择的）
        request.setChannelCode("hongsejl");//渠道编码
        request.setHoldDate("");//保留时间（可选择的）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date checkInDate = hotelSupplyOrderDTO.getCheckInDate();
        Date checkOutDate = hotelSupplyOrderDTO.getCheckOutDate();
        long noofDays = (checkInDate.getTime()-checkOutDate.getTime())/(24*60*60*1000);
        request.setNoofDays(String.valueOf(noofDays));//入住天数（可选择的）
        request.setArrivalDate(sdf.format(hotelSupplyOrderDTO.getCheckInDate()));//入住日
        request.setDepartureDate(sdf.format(hotelSupplyOrderDTO.getCheckOutDate()));//离开日
        request.setStatus(String.valueOf(hotelSupplyOrderDTO.getPayState()) );//状态  支付状态（可选择的）
        request.setRemarks(createOrderRequest.getRemark());//备注（可选择的）
        request.setCreateUser(hotelSupplyOrderDTO.getCreateName());//创建人
        request.setCreateDate(hotelSupplyOrderDTO.getCreateTime().toString());//创建时间（可选择的）
        request.setSalesId("");//工号（可选择的）
        request.setTotalPrice(hotelSupplyOrderDTO.getOrderSum());//订单总金额 （可选择的）
        request.setSupplyCode(supplyCode);
        ResRoomResourcePriceInputModel priceInputModel = new ResRoomResourcePriceInputModel();
//        priceInputModel.setSerial(null);//中央订单号 （可选择的）
//        priceInputModel.setAccountNo(null);//酒店订单号（可选择的）
        priceInputModel.setBusinessDate(null);//营业日期
        priceInputModel.setRoomType(hotelSupplyOrderDTO.getRoomTypeName());//房类
        priceInputModel.setRateCode(roomIndex);//房价码
//        priceInputModel.setRoomRate(new BigDecimal(priceCheck));//门市价
        priceInputModel.setActualRateText("￥" + hotelSupplyOrderDTO.getOrderSum());
        priceInputModel.setActualRate(hotelSupplyOrderDTO.getOrderSum());//成交价
//        priceInputModel.setCouponsAmount(null);//优惠金额（可选择的）
//        priceInputModel.setReasion(null);//变价理由（可选择的）
//        priceInputModel.setRemarks(null);//备注（可选择的）
        priceInputModel.setRoomPrice(hotelSupplyOrderDTO.getOrderSum());
        priceInputModel.setGroupCode("0003");//集团编号
        priceInputModel.setHotelCode(pricePlanMappingDto.getSpHotelId());//酒店编号
        priceInputModel.setUserName(sjlExtendConfig.getContactName());//操作人
        List<ResRoomResourcePriceInputModel> priceInputModels = new ArrayList<>();
        priceInputModels.add(priceInputModel);
        request.setDayPriceList(priceInputModels);//每日房价列表：一日一房价 （可选择的）

        Response<FitReserveResponse> orderResponse = null;
        try {
            orderResponse = jtManager.createFitReserve(request);
            response.setResponseContent(JSON.toJSONString(orderResponse));
        }catch (Exception e){
            log.error("创建君庭订单失败，系统异常.supplyCode:"+supplyCode+",merchantCode:"+merchantCode+
                    ",supplyOrderCode:"+supplyOrderCode+",e is" ,e);
            response.setExceptionMsg(e.getMessage());
            fillResult(response, FCReturnNoEnum._010602081);
            return response;
        }finally {
            //保存存得是真正请求到供应商的报文
            response.setRequestContent(JSON.toJSONString(request));
        }
        if(orderResponse != null && orderResponse.getData() != null
                && orderResponse.getCode() == Integer.valueOf(JTResponseEnum.SUCCESS.key)){
            orderResponse.setMessage("创建君庭订单成功");
        }else{
            log.error("创建君庭订单单失败，没有找到商家配置！supplyCode:" + createOrderRequest +",merchantCode:"
                    + createOrderRequest.getMerchantCode());
            orderResponse.setMessage("创建君庭订单失败，系统异常");
            fillResult(response, FCReturnNoEnum._010603085);
        }
        return response;
    }

    /**
     * 取消订单
     * @param supplyOrderDto
     * @param cancelReason
     * @return
     * @throws Exception
     */
    @Override
    public SupplierOrderResult cancelSupplierOrder(SupplyOrderDto supplyOrderDto, String cancelReason) throws Exception {
        SupplierOrderResult orderResult = new SupplierOrderResult();

        String supplyCode = supplyOrderDto.getSupplyCode();
        String merchantCode = supplyOrderDto.getMerchantCode();
        String supplyOrderCode = supplyOrderDto.getSupplyOrderCode();

        JTExtendConfig jtExtendConfig = (JTExtendConfig)JTExtendConfig.getExtendConfig(supplyCode,merchantCode);
        if (jtExtendConfig == null){
            log.error("取消君庭订单失败，没有找到商家配置！supplyCode:" + supplyCode +",merchantCode:"
                    + merchantCode);
            fillResult(orderResult, FCReturnNoEnum._010603033);
            return orderResult;
        }
        orderResult.setSupplierOrderCode(supplyOrderCode);
        CancelFitReserveRequest reserveRequest = new CancelFitReserveRequest();

        if (StringUtil. isValidString(supplyOrderDto.getSupplierOrderCode())){
            reserveRequest.setSerial(supplyOrderDto.getSupplierOrderCode());//
        }
        if(StringUtil.isValidString(supplyOrderDto.getSupplyOrderCode())){
            reserveRequest.setUnionSerial(supplyOrderDto.getSupplyOrderCode());//
        }
        reserveRequest.setSupplyCode(supplyCode);
        reserveRequest.setGroupCode("0003");//集团编号
        reserveRequest.setHotelCode(supplyOrderDto.getSpHotelId());//酒店编号
        reserveRequest.setModifyDate(new Date().toString());//修改时间
        reserveRequest.setModifyUser("xhy");//修改人
        reserveRequest.setUserName("xhy");//操作人

        Response orderResponse = new Response();
        try{
            orderResponse = jtManager.cancelFitReserve(reserveRequest);
            orderResult.setResponseContent(JSON.toJSONString(orderResponse));
        }catch (Exception e){
            log.error("君庭取消订单失败，系统异常.supplyCode:"+supplyCode+",merchantCode:"+merchantCode+
                    ",supplyOrderCode:"+supplyOrderCode+",e is" ,e);
            orderResult.setExceptionMsg(e.getMessage());
            fillResult(orderResult, FCReturnNoEnum._010603081);
            return orderResult;
        }finally{
            orderResult.setRequestContent(JSON.toJSONString(reserveRequest));
        }

        if(null == orderResponse){
            fillResult(orderResult, FCReturnNoEnum._010603082);
        }else{
            if(orderResponse.getData() != null && orderResponse.getSuccess() != null
                    &&  Integer.valueOf(JTResponseEnum.SUCCESS.key).equals(orderResponse.getSuccess())){
                orderResult.setSupplyResult(SupplyResultEnum.CANCEL.result);
                fillResult(orderResult, FCReturnNoEnum._010000000);
            }else{
                String Message = orderResponse.getMessage();
                orderResult.setSupplierFailMessage(Message);
                log.error("取消君庭订单失败，订单不成功.supplyCode:"+supplyCode+",merchantCode:"+merchantCode+
                        ",supplyOrderCode:"+supplyOrderCode+",errorMsg :"+ Message);
                fillResult(orderResult, FCReturnNoEnum._010603085);
            }
        }
        return orderResult;
    }

    /**
     * 订单状态查询接口
     * @param supplyOrderDto
     * @return
     * @throws Exception
     */
    @Override
    public SupplierOrderResult querySupplierOrderStatus(SupplyOrderDto supplyOrderDto) throws Exception {
        SupplierOrderResult  orderResult = new SupplierOrderResult();

        String supplyCode = supplyOrderDto.getSupplyCode();
        String merchantCode = supplyOrderDto.getMerchantCode();
        String supplyOrderCode = supplyOrderDto.getSupplyOrderCode();

        JTExtendConfig jtExtendConfig = (JTExtendConfig)JTExtendConfig.getExtendConfig(supplyCode,merchantCode);
        if (jtExtendConfig == null){
            log.error("查询君庭订单失败，没有找到商家配置！supplyCode:" + supplyCode +",merchantCode:"
                    + merchantCode);
            fillResult(orderResult, FCReturnNoEnum._010603033);
            return orderResult;
        }
        RoomResourceListRequest request = new RoomResourceListRequest();
        if(StringUtil. isValidString(supplyOrderDto.getSupplierOrderCode())){
            request.setParentReserveNo(supplyOrderDto.getSupplierOrderCode());
        }
        request.setGroupCode("0003");
        request.setHotelCode(supplyOrderDto.getSpHotelId());
        request.setSupplyCode(supplyCode);

        Response<ResRoomResourceLiteOutputResponse> orderResponse = new Response<ResRoomResourceLiteOutputResponse>();
        try {
            orderResponse = jtManager.batchRoomResourceList(request);
            orderResult.setResponseContent(JSON.toJSONString(orderResponse));
        }catch (Exception e){
            log.error("君亭查询订单失败，系统异常.supplyCode:"+supplyCode+",merchantCode:"+merchantCode+
                    ",supplyOrderCode:"+supplyOrderCode+",e is" ,e);
            orderResult.setExceptionMsg(e.getMessage());
            fillResult(orderResult, FCReturnNoEnum._010604081);
            return orderResult;
        }finally {
            orderResult.setRequestContent(JSON.toJSONString(request));
        }

//
//                String status = orderResponse.getData().gethStatus();
//                switch (status) {
//                    case "1": //待确认
//                        orderResult.setSupplyResult(OrderEnum.WILLSURE.value);
//                        orderResult.setSupplierOrderStatus(Integer.parseInt(OrderResponseEnum.WILLSURE.key));
//                        break;
//                    case "2": //已确认
//                        orderResult.setSupplyResult(OrderResponseEnum.BESURE.value);
//                        orderResult.setSupplierOrderStatus(Integer.parseInt(OrderResponseEnum.BESURE.key));
//                        break;
//                    case "3"://已拒单
//                        orderResult.setSupplyResult(OrderResponseEnum.REFUSE.value);
//                        orderResult.setSupplierOrderStatus(Integer.parseInt(OrderResponseEnum.REFUSE.key));
//                        break;
//                    default://已取消
//                        orderResult.setSupplyResult(OrderResponseEnum.CANCEL.value);
//                        orderResult.setSupplierOrderStatus(Integer.parseInt(OrderResponseEnum.CANCEL.key));
//                        break;
//                }
//            }
//        }
        return orderResult;
    }

    private void fillResult(SupplierOrderResult result, FCReturnNoEnum  fcEnum) {
        result.setReturnNo(fcEnum.returnNo);
        result.setReturnCode(fcEnum.returnCode);
        result.setReturnDesc(fcEnum.returnDesc);
    }

    public Set<String> getQueryDays(Date startDate, Date endDate) {
        Set<String> queryDays = new HashSet<String>();
        long days = DateUtil.getDay(startDate, endDate);
        while (days-- > 0) {
            String dateStr = DateUtil.dateToString(startDate);
            queryDays.add(dateStr);
            startDate = DateUtil.getDate(startDate, 1, 0);
        }
        return queryDays;
    }

    private void fillFullRoom(PreBookingResult preBookingResult,  String checkInDate, String checkOutDate){
        Set<String> preDays = getQueryDays(DateUtil.stringToDate(checkInDate), DateUtil.stringToDate(checkOutDate));

        // 试预定详情
        List<PriceInfoDetail> priceInfoDetails = new ArrayList<PriceInfoDetail>();

        for(String saleDate: preDays){

        }


    }

}
