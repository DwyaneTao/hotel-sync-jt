package com.fangcang.hotel.sync.jt.service.impl;

import com.alibaba.fastjson.JSON;
import com.fangcang.enums.returncode.FCReturnNoEnum;
import com.fangcang.hotel.sync.common.api.dto.mapping.PricePlanMappingDto;
import com.fangcang.hotel.sync.common.api.dto.order.SupplyOrderDto;
import com.fangcang.hotel.sync.data.dto.PreBookingResult;
import com.fangcang.hotel.sync.data.dto.SupplierOrderResult;
import com.fangcang.hotel.sync.data.service.SupplyOrderService;
import com.fangcang.hotel.sync.jt.api.request.RoomResourceListRequest;
import com.fangcang.hotel.sync.jt.api.response.ResRoomResourceLiteOutputResponse;
import com.fangcang.hotel.sync.jt.api.response.Response;
import com.fangcang.hotel.sync.jt.api.response.RoomResourceListResponse;
import com.fangcang.hotel.sync.jt.config.JTExtendConfig;
import com.fangcang.hotel.sync.jt.manager.JTManager;
import com.fangcang.hotel.sync.jt.service.JTExtendsConfigService;
import com.fangcang.hotel.sync.order.dto.HotelSupplyOrderDTO;
import com.fangcang.hotel.sync.order.dto.SupplyGuestDTO;
import com.fangcang.hotel.sync.order.dto.request.CreateSupplyOrderRequest;
import com.fangcang.hotel.sync.order.dto.request.PreBookingRequest;
import com.fangcang.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("jtOrderService")
public class JTOrderService implements SupplyOrderService {

    private Log log = LogFactory.getLog(JTOrderService.class);

    @Autowired
    private JTExtendsConfigService jtExtendsConfigService;

    @Autowired
    private JTManager jtManager;


    @Override
    public PreBookingResult preBooking(PreBookingRequest preBookingRequest, PricePlanMappingDto pricePlanMappingDto) throws Exception {
        return null;
    }

    @Override
    public SupplierOrderResult createSupplierOrder(CreateSupplyOrderRequest createSupplyOrderRequest, PricePlanMappingDto pricePlanMappingDto, HotelSupplyOrderDTO hotelSupplyOrderDTO) throws Exception {
        return null;
    }

    @Override
    public SupplierOrderResult querySupplierOrderStatus(SupplyOrderDto supplyOrderDto) throws Exception {

        SupplierOrderResult  orderResult = new SupplierOrderResult();

        String supplyCode = supplyOrderDto.getSupplyCode();
        String merchantCode = supplyOrderDto.getMerchantCode();
        String supplyOrderCode = supplyOrderDto.getSupplyOrderCode();


        JTExtendConfig sjlExtendConfig = (JTExtendConfig) jtExtendsConfigService.getExtendConfig(supplyCode, merchantCode);
        if (sjlExtendConfig == null){
            log.error("查询君亭订单单失败，没有找到商家配置！supplyCode:" + supplyCode +",merchantCode:"
                    + merchantCode);
            fillResult(orderResult, FCReturnNoEnum._010604033);
            return orderResult;
        }

        RoomResourceListRequest request = new RoomResourceListRequest();
//        if(StringUtil. isValidString(supplyOrderDto.getSupplierOrderCode())){
//            request.setOrderCode(supplyOrderDto.getSupplierOrderCode());
//        }
//        if(StringUtil.isValidString(supplyOrderDto.getSupplyOrderCode()) ){
//            request.setCustomerOrderCode(supplyOrderDto.getSupplyOrderCode());
//        }
//        request.setSupplyCode(supplyCode);
        request.setHotelCode("J571005");
        List<String> list = new LinkedList<>();
        list.add("180313113407796135");
        request.setSerialList(list);
        request.setParentReserveNo("1");
        request.setGroupCode("0003");
        Response<ResRoomResourceLiteOutputResponse> orderResponse = null;
        try {
            orderResponse = jtManager.batchRoomResourceList(request);
            orderResult.setResponseContent(JSON.toJSONString(orderResponse));
        }catch (Exception e){
            log.error("君亭查询订单失败，系统异常.supplyCode:"+supplyCode+",merchantCode:"+merchantCode+
                    ",supplyOrderCode:"+supplyOrderCode+",e is" ,e);
            orderResult.setExceptionMsg(e.getMessage());
            fillResult(orderResult, FCReturnNoEnum._010604081);
            return orderResult;
        }finally{
            orderResult.setRequestContent(JSON.toJSONString(request));
        }


        if(null == orderResponse){
            fillResult(orderResult, FCReturnNoEnum._010604082);
            return orderResult;
        }else {
//            if(orderResponse.getResult() != null && orderResponse.getResult().getOrderDetailList() != null
//                    && Integer.valueOf(SJLResponseEnum.SUCCESS.key).equals(orderResponse.getCode())){
//                Integer status = orderResponse.getResult().getOrderDetailList().get(0).getOrderStatus();
//                switch (status) {
//                    case 1: //待确认
//                        orderResult.setSupplyResult(OrderResponseEnum.WILLSURE.value);
//                        orderResult.setSupplierOrderStatus(Integer.parseInt(OrderResponseEnum.WILLSURE.key));
//                        break;
//                    case 2: //已确认
//                        orderResult.setSupplyResult(OrderResponseEnum.BESURE.value);
//                        orderResult.setSupplierOrderStatus(Integer.parseInt(OrderResponseEnum.BESURE.key));
//                        break;
//                    case 3://已拒单
//                        orderResult.setSupplyResult(OrderResponseEnum.REFUSE.value);
//                        orderResult.setSupplierOrderStatus(Integer.parseInt(OrderResponseEnum.REFUSE.key));
//                        break;
//                    default://已取消
//                        orderResult.setSupplyResult(OrderResponseEnum.CANCEL.value);
//                        orderResult.setSupplierOrderStatus(Integer.parseInt(OrderResponseEnum.CANCEL.key));
//                        break;
//                }
//                orderResult.setSupplierOrderCode(supplyOrderDto.getSupplierOrderCode());
//                fillResult(orderResult, FCReturnNoEnum._010000000);
//            }else{
//                String errorMsg = orderResponse.getErrorMsg();
//                log.error("查询君亭旅订单失败，订单不成功.supplyCode:"+supplyCode+",merchantCode:"+merchantCode+
//                        ",supplyOrderCode:"+supplyOrderCode+",errorMsg :"+ errorMsg);
//                orderResult.setSupplierFailMessage(errorMsg);
//                fillResult(orderResult, FCReturnNoEnum._010604052);
//            }
        }
        return orderResult;
    }

    @Override
    public SupplierOrderResult cancelSupplierOrder(SupplyOrderDto supplyOrderDto, String s) throws Exception {




        return null;
    }



    private void fillResult(SupplierOrderResult result, FCReturnNoEnum  fcEnum) {
        result.setReturnNo(fcEnum.returnNo);
        result.setReturnCode(fcEnum.returnCode);
        result.setReturnDesc(fcEnum.returnDesc);
    }

}
