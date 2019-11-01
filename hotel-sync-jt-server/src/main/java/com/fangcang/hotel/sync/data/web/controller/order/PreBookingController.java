package com.fangcang.hotel.sync.data.web.controller.order;

import com.alibaba.fastjson.JSON;
import com.fangcang.enums.returncode.ErrorCodeEnum;
import com.fangcang.enums.returncode.FCReturnNoEnum;
import com.fangcang.hotel.sync.common.api.constant.ActiveEnum;
import com.fangcang.hotel.sync.common.api.constant.CommonServerApi;
import com.fangcang.hotel.sync.common.api.dto.mapping.HotelMappingDto;
import com.fangcang.hotel.sync.common.api.dto.mapping.PricePlanMappingDto;
import com.fangcang.hotel.sync.common.api.dto.mapping.RoomMappingDto;
import com.fangcang.hotel.sync.common.api.dto.query.HotelMappingQuery;
import com.fangcang.hotel.sync.common.api.dto.query.PricePlanMappingQuery;
import com.fangcang.hotel.sync.common.api.dto.query.RoomMappingQuery;
import com.fangcang.hotel.sync.data.constants.SupplierClass;
import com.fangcang.hotel.sync.data.constants.SyncConstants;
import com.fangcang.hotel.sync.data.dto.PreBookingResult;
import com.fangcang.hotel.sync.data.service.CommonServerRequestService;
import com.fangcang.hotel.sync.data.service.RegisterSupplyService;
import com.fangcang.hotel.sync.data.service.SupplyConfigBaseService;
import com.fangcang.hotel.sync.data.service.SupplyOrderService;
import com.fangcang.hotel.sync.data.thread.LogDataThread;
import com.fangcang.hotel.sync.log.model.SupplyPreBookingLog;
import com.fangcang.hotel.sync.order.constants.CanBookEnum;
import com.fangcang.hotel.sync.order.dto.request.PreBookingRequest;
import com.fangcang.hotel.sync.order.dto.response.PreBookingResponse;
import com.fangcang.log.api.BusinessLogUtil;
import com.fangcang.log.model.BusinessTraceLog;
import com.fangcang.redis.dao.LogDataDao;
import com.fangcang.redis.entity.LogData;
import com.fangcang.util.DateUtil;
import com.fangcang.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author py
 * @date 2019/10/31 16:09
 **/
@Controller
public class PreBookingController {

    private static final Log log = LogFactory.getLog(PreBookingController.class);

    @Autowired
    private RegisterSupplyService registerSupplyService;

    @Autowired
    private TaskExecutor logDataExecutor;

    @Autowired
    private LogDataDao logDataDao;

    @Autowired
    private CommonServerRequestService commonServerRequestService;

    /**
     * 预订试单
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/init/jt/singleTrial", produces = "text/html;charset=UTF-8")
    public PreBookingResponse singleTrialFitReserve(HttpServletRequest httpServletRequest) throws Exception {
        Date preBookStartTime = new Date();
        PreBookingResponse preBookingResponse = new PreBookingResponse();
        LogData logData = new LogData();
        logData.setStart(new Date());
        PreBookingRequest preBookingRequest = null;
        String supplyClass = null;
        SupplyPreBookingLog preBookingLog = new SupplyPreBookingLog();
        // 试预订前的校验转换追踪日志
        BusinessTraceLog checkTraceLog = new BusinessTraceLog();
        // 业务日志
        List<Object> businessLogs = new ArrayList<Object>();
        businessLogs.add(preBookingLog);
        businessLogs.add(checkTraceLog);

        try {
            checkTraceLog.setTeam("SUPPLY");
            checkTraceLog.setBusiness_type("PREBOOKING");
            checkTraceLog.setTrace_order(1);
            checkTraceLog.setTrace_name("CHECK_CONVER_PARAM");
            checkTraceLog.setBegintime(new Date());
            checkTraceLog.setResult(0);

            BufferedReader reader = httpServletRequest.getReader();
            StringBuffer stringBuffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                stringBuffer.append(line);
                line = reader.readLine();
            }
            preBookingRequest = JSON.parseObject(stringBuffer.toString(), PreBookingRequest.class);
            if (preBookingRequest == null) {
                log.error("试预定失败,请求参数转换异常,request:" + stringBuffer.toString());
                checkTraceLog.setCosttime(System.currentTimeMillis() - checkTraceLog.getBegintime().getTime());
                checkTraceLog.setReason("CONVER_PARAM_FAIL");

                preBookingResponse.setReturnNo(FCReturnNoEnum._011201005.returnNo);
                preBookingResponse.setReturnCode(FCReturnNoEnum._011201005.returnCode);
                preBookingResponse.setReturnDesc(FCReturnNoEnum._011201005.returnDesc);
                return preBookingResponse;
            }
            if (!StringUtil.isValidString(preBookingRequest.getRequestNo())) {
                preBookingRequest.setRequestNo(UUID.randomUUID().toString());
            }
            checkTraceLog.setRequestno(preBookingRequest.getRequestNo());
            preBookingLog.setRequestNo(preBookingRequest.getRequestNo());
            if (!checkRequest(preBookingRequest)) {
                log.error("试预定失败,请求参数不完整,request Json:" + JSON.toJSONString(preBookingRequest));
                checkTraceLog.setCosttime(System.currentTimeMillis() - checkTraceLog.getBegintime().getTime());
                checkTraceLog.setReason("INVALID_INPUTPARAM");

                preBookingResponse.setReturnNo(FCReturnNoEnum._011201005.returnNo);
                preBookingResponse.setReturnCode(FCReturnNoEnum._011201005.returnCode);
                preBookingResponse.setReturnDesc(FCReturnNoEnum._011201005.returnDesc);
                return preBookingResponse;
            }
            preBookingLog.setSupplyway(2);
            preBookingLog.setSupplyCode(preBookingRequest.getSupplyCode());
            preBookingLog.setMerchantCode(preBookingRequest.getMerchantCode());
            preBookingLog.setPriceplanId(NumberUtils.toLong(preBookingRequest.getPricePlanId()));
            preBookingLog.setHotelId(NumberUtils.toLong(preBookingRequest.getHotelId()));
            preBookingLog.setRoomId(NumberUtils.toLong(preBookingRequest.getRoomId()));
            preBookingLog.setCheckin(DateUtil.stringToDate(preBookingRequest.getCheckInDate()));
            preBookingLog.setCheckout(DateUtil.stringToDate(preBookingRequest.getCheckOutDate()));
            preBookingLog.setReserveNum(preBookingRequest.getRoomNum());
            SupplyOrderService supplyOrderService = registerSupplyService.getOrderService(preBookingRequest.getSupplyCode(), preBookingRequest.getMerchantCode());
            if (supplyOrderService == null) {
                log.error("试预定失败，找不到匹配的供应商或商家！supplyCode:" + preBookingRequest.getSupplyCode() + ",merchantCode:" + preBookingRequest.getMerchantCode());
                checkTraceLog.setCosttime(System.currentTimeMillis() - checkTraceLog.getBegintime().getTime());
                checkTraceLog.setReason("NOMATCH_SUPPLYANDMECHANT");
                preBookingResponse.setReturnNo(FCReturnNoEnum._011201049.returnNo);
                preBookingResponse.setReturnCode(FCReturnNoEnum._011201049.returnCode);
                preBookingResponse.setReturnDesc(FCReturnNoEnum._011201049.returnDesc);
                return preBookingResponse;
            }
            Date preBookTime1 = new Date();
            PricePlanMappingDto pricePlanMappingDto = null;
            if (StringUtil.isValidString(preBookingRequest.getSupplyRateId())) {
                /** 产品不落地方案， 检查有效的酒店和房型映射 */
                log.info("君庭试预订走不落地流程！");

                // 1.检查酒店映射
                supplyClass = SupplyConfigBaseService.getSupplyClassByCode(preBookingRequest.getSupplyCode());
                if (supplyClass == null) {
                    log.error("试预定失败，找不到匹配的供应商或商家！supplyCode:" + preBookingRequest.getSupplyCode() + ",merchantCode:" + preBookingRequest.getMerchantCode());
                    checkTraceLog.setCosttime(System.currentTimeMillis() - checkTraceLog.getBegintime().getTime());
                    checkTraceLog.setReason("NOMATCH_SUPPLYANDMECHANT");
                    preBookingResponse.setReturnNo(FCReturnNoEnum._011201049.returnNo);
                    preBookingResponse.setReturnCode(FCReturnNoEnum._011201049.returnCode);
                    preBookingResponse.setReturnDesc(FCReturnNoEnum._011201049.returnDesc);
                    return preBookingResponse;
                }
                HotelMappingQuery hotelMappingQuery = new HotelMappingQuery();
                hotelMappingQuery.setFcHotelId(NumberUtils.toLong(preBookingRequest.getHotelId())); // 房仓酒店id
                hotelMappingQuery.setIsActive(ActiveEnum.ACTIVE.getValue());
                hotelMappingQuery.setSupplyClass(supplyClass);
                HotelMappingDto hotelMappingDto = commonServerRequestService.getFirstOneFromList(CommonServerApi.QUERY_HAS_MAPPING_HOTEL, hotelMappingQuery, HotelMappingDto.class);
                if (hotelMappingDto == null) {
                    log.error("试预定失败，未查询到有效的酒店映射！supplyRateId:" + preBookingRequest.getSupplyRateId() + "--hotelId:" + preBookingRequest.getHotelId());
                    checkTraceLog.setCosttime(System.currentTimeMillis() - checkTraceLog.getBegintime().getTime());
                    checkTraceLog.setReason("NOMATCH_SUPPLYANDMECHANT");

                    preBookingResponse.setReturnNo(FCReturnNoEnum._011201029.returnNo);
                    preBookingResponse.setReturnCode(FCReturnNoEnum._011201029.returnCode);
                    preBookingResponse.setReturnDesc(FCReturnNoEnum._011201029.returnDesc);
                    return preBookingResponse;
                }
                RoomMappingDto roomMapping = null; // 试预定房型的有效映射


                // 2.检查房型映射
                RoomMappingQuery roomMappingQuery = new RoomMappingQuery();
                roomMappingQuery.setSpHotelId(hotelMappingDto.getSpHotelId());
                roomMappingQuery.setFcHotelId(NumberUtils.toLong(preBookingRequest.getHotelId()));
                roomMappingQuery.setSupplyClass(supplyClass);
                roomMappingQuery.setIsActive(ActiveEnum.ACTIVE.getValue());
                roomMappingQuery.setIsPage(false);
                List<RoomMappingDto> roomMappingDtos = commonServerRequestService.getListResult(CommonServerApi.QUERY_HAS_MAPPING_ROOM_Light, roomMappingQuery,
                        RoomMappingDto.class);
                if (CollectionUtils.isNotEmpty(roomMappingDtos)) {
                    for (RoomMappingDto roomMappingDto : roomMappingDtos) {
                        if (preBookingRequest.getRoomId().equals(String.valueOf(roomMappingDto.getRoomId()))) {
                            roomMapping = roomMappingDto;
                            break;
                        }
                    }
                }

                if (roomMapping == null) {
                    log.error("试预定失败，未查询到有效的房型映射！supplyRateId:" + preBookingRequest.getSupplyRateId() + "--RoomId:" + preBookingRequest.getRoomId());
                    checkTraceLog.setCosttime(System.currentTimeMillis() - checkTraceLog.getBegintime().getTime());
                    checkTraceLog.setReason("NOMATCH_SUPPLYANDMECHANT");

                    preBookingResponse.setReturnNo(FCReturnNoEnum._011201029.returnNo);
                    preBookingResponse.setReturnCode(FCReturnNoEnum._011201029.returnCode);
                    preBookingResponse.setReturnDesc(FCReturnNoEnum._011201029.returnDesc);
                    return preBookingResponse;
                }


                /** 创建试预定请求参数对象 */
                pricePlanMappingDto = new PricePlanMappingDto();
                pricePlanMappingDto.setSpHotelId(hotelMappingDto.getSpHotelId());
                pricePlanMappingDto.setHotelName(hotelMappingDto.getHotelName());
                pricePlanMappingDto.setSpRoomId(roomMapping.getSpRoomId());
                pricePlanMappingDto.setSpRoomName(roomMapping.getSpRoomName());


            } else {
                /** 查询价格计划映射 */
                log.info("君庭试预订走落地流程！");
                PricePlanMappingQuery pricePlanMappingQuery = new PricePlanMappingQuery();
                pricePlanMappingQuery.setPricePlanId(Long.valueOf(preBookingRequest.getPricePlanId()));
                pricePlanMappingQuery.setIsActive(ActiveEnum.ACTIVE.getValue());
                pricePlanMappingDto = commonServerRequestService
                        .getFirstOneFromList(CommonServerApi.QUERY_PRICEPLAN_MAPPING_LIST, pricePlanMappingQuery, PricePlanMappingDto.class);
                if (pricePlanMappingDto == null) {
                    log.error("试预定失败，未查询到有效的价格计划映射！pricePlanId:" + preBookingRequest.getPricePlanId());
                    checkTraceLog.setCosttime(System.currentTimeMillis() - checkTraceLog.getBegintime().getTime());
                    checkTraceLog.setReason("NOMATCH_SUPPLYANDMECHANT");

                    preBookingResponse.setReturnNo(FCReturnNoEnum._011201031.returnNo);
                    preBookingResponse.setReturnCode(FCReturnNoEnum._011201031.returnCode);
                    preBookingResponse.setReturnDesc(FCReturnNoEnum._011201031.returnDesc);
                    return preBookingResponse;
                }

                supplyClass = pricePlanMappingDto.getSupplyClass();
                if (supplyClass.equals(SupplierClass.YL.getSupplierClass())) {
                    preBookingLog.setCoPriceplanId(pricePlanMappingDto.getExt());
                } else {
                    preBookingLog.setCoPriceplanId(pricePlanMappingDto.getSpPricePlanKey());
                }
            }
            Date preBookTime2 = new Date();
            preBookingLog.setCoHotelId(pricePlanMappingDto.getSpHotelId());
            preBookingLog.setHotelName(pricePlanMappingDto.getHotelName());
            preBookingLog.setCoRoomId(pricePlanMappingDto.getSpRoomId());
            preBookingLog.setRoomName(pricePlanMappingDto.getRoomName());
            // preBookingLog.setCityCode(pricePlanMappingDto.get);
            preBookingLog.setSupplyClass(supplyClass);

            // elk日志增加附加值字段
            StringBuffer sb = new StringBuffer();
            sb.append("supplyCode:" + preBookingRequest.getSupplyCode()).append("|").append("merchantCode:" + preBookingRequest.getMerchantCode()).append("|")
                    .append("pricePlanId:" + preBookingRequest.getPricePlanId()).append("|").append("coHotelId:" + pricePlanMappingDto.getSpHotelId()).append("|")
                    .append("coRoomId:" + pricePlanMappingDto.getSpRoomId());
            if (StringUtil.isValidString(preBookingRequest.getSupplyRateId())) {
                sb.append("|supplyRateId:" + preBookingRequest.getSupplyRateId());
            }
            String additionalContent = sb.toString();

            PreBookingResult preBookingResult = new PreBookingResult();
            Date startDate = new Date();
            Date preBookTime3 = new Date();
            try {
                preBookingResult = supplyOrderService.preBooking(preBookingRequest, pricePlanMappingDto);

                /** 如试预定异常提示信息字段有值，表示调用供应商接口出现异常 */
                if (StringUtil.isValidString(preBookingResult.getExceptionMsg())) {
                    // 主动抛出异常，进入catch块的处理流程
                    throw new Exception();
                }
            } catch (Exception e) {
                Date endDate = new Date();
                checkTraceLog.setCosttime(startDate.getTime() - checkTraceLog.getBegintime().getTime());
                checkTraceLog.setReason("OUTER_IF_EXCEPTION");

                preBookingResult.setRequesTime(startDate);
                preBookingResult.setResponseTime(endDate);
                // preBookingResult.setResponseContent(e.getMessage());
                preBookingResult.setReturnCode(FCReturnNoEnum._011201901.returnCode);
                preBookingResult.setReturnNo(FCReturnNoEnum._011201901.returnNo);
                preBookingResult.setReturnDesc(FCReturnNoEnum._011201901.returnDesc);

                if (StringUtil.isValidString(preBookingResult.getExceptionMsg())) {
                    preBookingResult.setResponseContent(preBookingResult.getExceptionMsg());
                    log.error("调用供应商接口出现异常，请求信息:" + JSON.toJSONString(preBookingResult.getRequestContent()) + "--" + preBookingResult.getExceptionMsg());
                } else {
                    preBookingResult.setResponseContent(e.getMessage());
                    log.error("试预定处理返回异常，请求信息:" + JSON.toJSONString(preBookingRequest), e);
                }

                preBookingResponse.setCanBook(CanBookEnum.CAN_NOT_BOOK.value);
                preBookingResponse.setHotelId(preBookingRequest.getHotelId());
                preBookingResponse.setRoomId(preBookingRequest.getRoomId());
                preBookingResponse.setCheckInDate(preBookingRequest.getCheckInDate());
                preBookingResponse.setCheckOutDate(preBookingRequest.getCheckOutDate());
                preBookingResponse.setSupplyCode(preBookingRequest.getSupplyCode());
                preBookingResponse.setPricePlanId(preBookingRequest.getPricePlanId());
                preBookingResponse.setReturnNo(FCReturnNoEnum._011201901.returnNo);
                preBookingResponse.setReturnCode(FCReturnNoEnum._011201901.returnCode);
                preBookingResponse.setReturnDesc(FCReturnNoEnum._011201901.returnDesc);

                /* 保存ELK日志 * */
                preBookingResult.setAdditionalContent(additionalContent);
                saveLog2Elk(preBookingResult, supplyClass);
                return preBookingResponse;
            }
            Date preBookTime4 = new Date();
            preBookingResponse.setCanImmediate(preBookingResult.getCanImmediate());
            preBookingResponse.setPreBookingResponseDetails(preBookingResult.getPreBookingResponseDetails());
            preBookingResponse.setCanBook(preBookingResult.getCanBook());
            // preBookingResponse.setCannotBookReasonCode(preBookingResult.getCannotBookReasonCode());
            // preBookingResponse.setCannotBookReason(preBookingResult.getCannotBookReason());
            preBookingResponse.setReturnNo(preBookingResult.getReturnNo());
            preBookingResponse.setReturnCode(preBookingResult.getReturnCode());
            preBookingResponse.setReturnDesc(preBookingResult.getReturnDesc());
            //在B2B的页面上，如果试预订报错，直接返回给客户哪个供应商返回了什么错误信息，把供应商暴露了
            if(preBookingResponse.getReturnNo() != null && preBookingResponse.getReturnNo().length() >= 9){
                String returnDesc = preBookingResponse.getReturnNo().substring(6, 9);
                String errorCode = ErrorCodeEnum.getErrorCodeByKey(returnDesc);
                preBookingResponse.setReturnDesc(ErrorCodeEnum.getDescByValue(errorCode));
            }else {
                preBookingResponse.setReturnDesc(preBookingResult.getReturnDesc());
            }

            preBookingResponse.setHotelId(preBookingRequest.getHotelId());
            preBookingResponse.setRoomId(preBookingRequest.getRoomId());
            preBookingResponse.setCheckInDate(preBookingRequest.getCheckInDate());
            preBookingResponse.setCheckOutDate(preBookingRequest.getCheckOutDate());
            preBookingResponse.setSupplyCode(preBookingRequest.getSupplyCode());
            preBookingResponse.setPricePlanId(preBookingRequest.getPricePlanId());

            /* 保存ELK日志 * */
            preBookingResult.setAdditionalContent(additionalContent);
            saveLog2Elk(preBookingResult, supplyClass);

            preBookingLog.setIsImmediate(preBookingResult.getCanImmediate());
            preBookingLog.setCanbook(preBookingResult.getCanBook());
            preBookingLog.setReturnCode(preBookingResult.getReturnCode());
            preBookingLog.setReturnMSG(preBookingResult.getReturnDesc());

            // 请求供应商追踪日志
            BusinessTraceLog reqSpTraceLog = new BusinessTraceLog();
            reqSpTraceLog.setRequestno(preBookingRequest.getRequestNo());
            reqSpTraceLog.setTeam("SUPPLY");
            reqSpTraceLog.setBusiness_type("PREBOOKING");
            reqSpTraceLog.setTrace_order(2);
            reqSpTraceLog.setTrace_name("REQUEST_SUPPLY_API");
            reqSpTraceLog.setBegintime(preBookingResult.getRequesTime());
            reqSpTraceLog.setResult(0);
            reqSpTraceLog.setReason(preBookingResult.getReturnCode());
            businessLogs.add(reqSpTraceLog);
            // 耗时
            if (preBookingResult.getRequesTime() != null) {
                checkTraceLog.setResult(1);
                checkTraceLog.setReason("SUCCESS");
                checkTraceLog.setCosttime(preBookingResult.getRequesTime().getTime() - checkTraceLog.getBegintime().getTime());
                if (preBookingResult.getResponseTime() != null) {
                    reqSpTraceLog.setResult(1);
                    reqSpTraceLog.setCosttime(preBookingResult.getResponseTime().getTime() - preBookingResult.getRequesTime().getTime());
                }
            } else {
                checkTraceLog.setReason(preBookingResult.getReturnCode());
            }
            Date preBookTime5 = new Date();
            long step1 = preBookTime1.getTime() - preBookStartTime.getTime();
            long step2 = preBookTime2.getTime() - preBookTime1.getTime();
            long step3 = preBookTime3.getTime() - preBookTime2.getTime();
            long step4 = preBookTime4.getTime() - preBookTime3.getTime();
            long step5 = preBookTime5.getTime() - preBookTime4.getTime();
            log.info("君庭试预订controller耗时（单位：毫秒）情况：1获取参数及校验耗时：" + step1
                    + ";2查询映射耗时：" + step2
                    + ";3记录日志参数组装耗时：" + step3
                    + ";4试预订耗时：" + step4
                    + ";5完成试预订耗时：" + step5);

        }catch (Exception e){
            preBookingResponse.setReturnNo(FCReturnNoEnum._011201901.returnNo);
            preBookingResponse.setReturnCode(FCReturnNoEnum._011201901.returnCode);
            preBookingResponse.setReturnDesc(FCReturnNoEnum._011201901.returnDesc);
            log.error("试预定Error!request json:" + JSON.toJSONString(preBookingRequest), e);
        }finally {
            preBookingLog.setResponseTime(new Date());
            preBookingLog.setCostTime(preBookingLog.getResponseTime().getTime() - preBookingLog.getRequestTime().getTime());
            preBookingLog.setCreateTime(new Date());
            // 发送mq记录日志
            BusinessLogUtil.saveBusinessLog(businessLogs);
        }
        return preBookingResponse;
    }

    /**
     * 参数校验
     * @param preBookingRequest
     * @return
     */
    private boolean checkRequest(PreBookingRequest preBookingRequest) {
        boolean isValidate = true;
        if (!StringUtil.isValidString(preBookingRequest.getHotelId()) || !StringUtil.isValidString(preBookingRequest.getRoomId())) {
            isValidate = false;
        } else if (!StringUtil.isValidString(preBookingRequest.getCheckInDate()) || !StringUtil.isValidString(preBookingRequest.getCheckOutDate())) {
            isValidate = false;
        } else if (!StringUtil.isValidString(preBookingRequest.getMerchantCode()) || !StringUtil.isValidString(preBookingRequest.getSupplyCode())) {
            isValidate = false;
        } else if (!StringUtil.isValidString(preBookingRequest.getPricePlanId()) && !StringUtil.isValidString(preBookingRequest.getSupplyRateId())) {
            isValidate = false;
        }
        return isValidate;
    }

    private void saveLog2Elk(PreBookingResult preBookingResult, String supplyClass) {
        try {
            String index = getIndex(supplyClass);
            LogData logData = new LogData();
            logData.setIndex(index);
            logData.setDocType(SyncConstants.PRE_BOOKING);
            logData.setStart(preBookingResult.getRequesTime());
            logData.setEnd(preBookingResult.getResponseTime());
            logData.setRequest(preBookingResult.getRequestContent());
            logData.setResponse(preBookingResult.getResponseContent());
            logData.setStatus(preBookingResult.getReturnCode());
            /**
             * 增加字段记录到elk
             */
            logData.setAdditional(preBookingResult.getAdditionalContent());
            logDataExecutor.execute(new LogDataThread(logDataDao, logData));
        } catch (Exception e) {
            log.error("save prebooking logdata in elk error." + e);
        }
    }

    private String getIndex(String supplyClass) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(SyncConstants.PREFFIX).append(SyncConstants.SEP);
        buffer.append(supplyClass).append(SyncConstants.SEP);
        buffer.append(SyncConstants.PRE_BOOKING);

        return buffer.toString();
    }
}
