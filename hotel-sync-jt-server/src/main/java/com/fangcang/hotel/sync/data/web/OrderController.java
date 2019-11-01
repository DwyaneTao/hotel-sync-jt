/**
 * Copyright (c) 2006-2015 Fangcang Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Fangcang. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Fangcang,http://www.fangcang.com.
 *  
 */
package com.fangcang.hotel.sync.data.web;

import com.alibaba.fastjson.JSON;
import com.fangcang.enums.returncode.FCReturnNoEnum;
import com.fangcang.hotel.sync.common.api.constant.CommonServerApi;
import com.fangcang.hotel.sync.common.api.constant.InteractiveResult;
import com.fangcang.hotel.sync.common.api.constant.OrderOperateEnum;
import com.fangcang.hotel.sync.common.api.dto.order.SupplyOrderDto;
import com.fangcang.hotel.sync.common.api.dto.query.SupplyOrderQuery;
import com.fangcang.hotel.sync.data.service.CommonOrderService;
import com.fangcang.hotel.sync.data.service.CommonServerRequestService;
import com.fangcang.hotel.sync.log.model.InteractiveWithSaasLog;
import com.fangcang.hotel.sync.order.constants.SupplyResultEnum;
import com.fangcang.hotel.sync.order.dto.request.CancelOrderRequest;
import com.fangcang.hotel.sync.order.dto.request.CreateSupplyOrderRequest;
import com.fangcang.hotel.sync.order.dto.request.QuerySupplyOrderRequest;
import com.fangcang.hotel.sync.order.dto.response.CancelOrderResponse;
import com.fangcang.hotel.sync.order.dto.response.ConfirmNoResponse;
import com.fangcang.hotel.sync.order.dto.response.CreateSupplyOrderResponse;
import com.fangcang.hotel.sync.order.dto.response.QuerySupplyOrderResponse;
import com.fangcang.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Date;

/**
 * <p>
 * 
 * 订单请求接口
 * 
 * </p>
 * 
 * @author jiangzuku
 * @date 2016年6月28日 下午6:51:01
 * @version
 */
@Controller
public class OrderController {

    private static final Log log = LogFactory.getLog(OrderController.class);

    @Autowired
    private CommonOrderService commonOrderService;

	@Autowired
	private CommonServerRequestService commonServerRequestService;

    @RequestMapping(value = "/create/supply/order", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public CreateSupplyOrderResponse createSupplyOrder(HttpServletRequest request) {
	CreateSupplyOrderResponse createSupplyOrderResponse = new CreateSupplyOrderResponse();

	/** 与Saas交互日志 */
	InteractiveWithSaasLog interactiveWithSaasLog = new InteractiveWithSaasLog();
	Date current = new Date();
	interactiveWithSaasLog.setRequestTime(current);
	String supplyOrderCode=null;
	try {
	    BufferedReader reader = request.getReader();
	    StringBuffer stringBuffer = new StringBuffer();
	    String line = reader.readLine();
	    while (line != null) {
		stringBuffer.append(line);
		line = reader.readLine();
	    }
	    System.out.println(stringBuffer.toString());
	    CreateSupplyOrderRequest supplyOrderRequest = JSON.parseObject(stringBuffer.toString(), CreateSupplyOrderRequest.class);
	    supplyOrderCode=supplyOrderRequest.getSupplyOrderCode();
	    createSupplyOrderResponse = commonOrderService.createOrder(supplyOrderRequest);

	    interactiveWithSaasLog=commonOrderService.createInteractiveWithSaasLog(supplyOrderRequest);
	    interactiveWithSaasLog.setRequestTime(current);
	    interactiveWithSaasLog.setSupplyClass(createSupplyOrderResponse.getSupplierClass());
	    interactiveWithSaasLog.setSupplyResult(createSupplyOrderResponse.getSupplyResult());
	    interactiveWithSaasLog.setRequestContent(JSON.toJSONString(supplyOrderRequest));
	} catch (Exception e) {
	    log.error("发单异常 supplyOrderCode:"+supplyOrderCode, e);
	    createSupplyOrderResponse.setReturnNo(FCReturnNoEnum._011202901.returnNo);
	    createSupplyOrderResponse.setReturnCode(FCReturnNoEnum._011202901.returnCode);
	    createSupplyOrderResponse.setReturnDesc(FCReturnNoEnum._011202901.returnDesc);
	    
	    /** 系统异常，交互失败 */
	    interactiveWithSaasLog.setResult(InteractiveResult.FAIL.result);
	    interactiveWithSaasLog.setFailReason(commonOrderService.getStackTrace(e));
	} finally {
	    /** 记录Saas-供应端交互日志 */
	    interactiveWithSaasLog.setResponseTime(new Date());
	    interactiveWithSaasLog.setResponseContent(JSON.toJSONString(createSupplyOrderResponse));
	    interactiveWithSaasLog.setCreateDate(new Date());
	    commonOrderService.saveInteractiveSaasLog(interactiveWithSaasLog);
	}
	return createSupplyOrderResponse;
    }

    @RequestMapping(value = "/query/supply/order", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public QuerySupplyOrderResponse querySupplyOrder(HttpServletRequest request) {
	QuerySupplyOrderResponse querySupplyOrderResponse = new QuerySupplyOrderResponse();

	InteractiveWithSaasLog interactiveWithSaasLog = new InteractiveWithSaasLog();
	interactiveWithSaasLog.setRequestTime(new Date());
	interactiveWithSaasLog.setOperateType(OrderOperateEnum.QUERY_ORDER.operateType);
	interactiveWithSaasLog.setResult(InteractiveResult.SUCCESS.result);
	try {
	    BufferedReader reader = request.getReader();
	    StringBuffer stringBuffer = new StringBuffer();
	    String line = reader.readLine();
	    while (line != null) {
		stringBuffer.append(line);
		line = reader.readLine();
	    }
	    QuerySupplyOrderRequest querySupplyOrderRequest = JSON.parseObject(stringBuffer.toString(), QuerySupplyOrderRequest.class);
	    interactiveWithSaasLog.setSupplyOrderCode(querySupplyOrderRequest.getSupplyOrderCode());
	    /** 1供货单号非空校验 */
	    String supplyOrderCode = querySupplyOrderRequest.getSupplyOrderCode();
	    if (!StringUtil.isValidString(supplyOrderCode)) {
		log.error("查询供货单状态失败，供货单号为空:" + JSON.toJSONString(querySupplyOrderRequest));
		querySupplyOrderResponse.setReturnNo(FCReturnNoEnum._011204005.returnNo);
		querySupplyOrderResponse.setReturnCode(FCReturnNoEnum._011204005.returnCode);
		querySupplyOrderResponse.setReturnDesc(FCReturnNoEnum._011204005.returnDesc);
		return querySupplyOrderResponse;
	    }
	    /** 2根据请求供货单号查询供货单 */

		SupplyOrderQuery supplyOrderQuery = new SupplyOrderQuery();
		supplyOrderQuery.setSupplyOrderCode(supplyOrderCode);
		SupplyOrderDto supplyOrderDto = commonServerRequestService.getFirstOneFromList(CommonServerApi.QUERY_ORDER, supplyOrderQuery, SupplyOrderDto.class);
	    if (supplyOrderDto == null) {
		log.error("查询供货单状态失败，供货单不存在:" + JSON.toJSONString(querySupplyOrderRequest));
		querySupplyOrderResponse.setReturnNo(FCReturnNoEnum._011204034.returnNo);
		querySupplyOrderResponse.setReturnCode(FCReturnNoEnum._011204034.returnCode);
		querySupplyOrderResponse.setReturnDesc(FCReturnNoEnum._011204034.returnDesc);
		return querySupplyOrderResponse;
	    }
	    interactiveWithSaasLog.setRequestContent(JSON.toJSONString(querySupplyOrderRequest));
	    interactiveWithSaasLog.setSupplyUser(querySupplyOrderRequest.getQueryUser());
	    
	    interactiveWithSaasLog.setMerchantCode(supplyOrderDto.getMerchantCode());
	    interactiveWithSaasLog.setFcOrderCode(supplyOrderDto.getFcOrderCode());
	    interactiveWithSaasLog.setSupplyCode(supplyOrderDto.getSupplyCode());
	    interactiveWithSaasLog.setSupplyClass(supplyOrderDto.getSupplyClass());
	    
	    querySupplyOrderResponse = commonOrderService.querySupplyOrderStatus(querySupplyOrderRequest,supplyOrderDto);

	    interactiveWithSaasLog.setResponseContent(JSON.toJSONString(querySupplyOrderResponse));
	} catch (Exception e) {
	    log.error("查询供货单异常:", e);
	    querySupplyOrderResponse.setReturnNo(FCReturnNoEnum._011204901.returnNo);
	    querySupplyOrderResponse.setReturnCode(FCReturnNoEnum._011204901.returnCode);
	    querySupplyOrderResponse.setReturnDesc(FCReturnNoEnum._011204901.returnDesc);

	    interactiveWithSaasLog.setResult(InteractiveResult.FAIL.result);
	    interactiveWithSaasLog.setFailReason(commonOrderService.getStackTrace(e));
	} finally {
	    /** 保存与Saas交互日志 */
	    interactiveWithSaasLog.setResponseTime(new Date());
	    interactiveWithSaasLog.setSupplyResult(querySupplyOrderResponse.getSupplyResult());
	    commonOrderService.saveInteractiveSaasLog(interactiveWithSaasLog);
	}
	return querySupplyOrderResponse;
    }

    @RequestMapping(value = "/cancel/supply/order", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public CancelOrderResponse cancelSupplyOrder(HttpServletRequest request) {
	CancelOrderResponse cancelOrderResponse = new CancelOrderResponse();
	InteractiveWithSaasLog interactiveWithSaasLog = new InteractiveWithSaasLog();
	interactiveWithSaasLog.setRequestTime(new Date());
	try {
	    BufferedReader reader = request.getReader();
	    StringBuffer stringBuffer = new StringBuffer();
	    String line = reader.readLine();
	    while (line != null) {
		stringBuffer.append(line);
		line = reader.readLine();
	    }
	    
	    CancelOrderRequest cancelOrderRequest = JSON.parseObject(stringBuffer.toString(), CancelOrderRequest.class);
	    interactiveWithSaasLog.setSupplyOrderCode(cancelOrderRequest.getSupplyOrderCode());
	    /** 参数校验 */
	    if (!StringUtil.isValidString(cancelOrderRequest.getSupplyOrderCode()) || !StringUtil.isValidString(cancelOrderRequest.getCancelRequestUser())) {
		cancelOrderResponse.setReturnNo(FCReturnNoEnum._011203005.returnNo);
		cancelOrderResponse.setReturnCode(FCReturnNoEnum._011203005.returnCode);
		cancelOrderResponse.setReturnDesc(FCReturnNoEnum._011203005.returnDesc);
		log.info("取消订单失败，必要参数为空：" + JSON.toJSONString(cancelOrderRequest));
		return cancelOrderResponse;
	    }
	    
	    String supplyOrderCode = cancelOrderRequest.getSupplyOrderCode();
		SupplyOrderQuery supplyOrderQuery = new SupplyOrderQuery();
		supplyOrderQuery.setSupplyOrderCode(supplyOrderCode);
		SupplyOrderDto supplyOrderDto = commonServerRequestService.getFirstOneFromList(CommonServerApi.QUERY_ORDER, supplyOrderQuery, SupplyOrderDto.class);
	    if (supplyOrderDto == null) {
		cancelOrderResponse.setReturnNo(FCReturnNoEnum._011203034.returnNo);
		cancelOrderResponse.setReturnCode(FCReturnNoEnum._011203034.returnCode);
		cancelOrderResponse.setReturnDesc(FCReturnNoEnum._011203034.returnDesc);
		log.info("取消订单失败，供货单不存在：" + JSON.toJSONString(cancelOrderRequest));
		return cancelOrderResponse;
	    }
	    
	    interactiveWithSaasLog.setRequestContent(JSON.toJSONString(cancelOrderRequest));
	    interactiveWithSaasLog.setSupplyUser(cancelOrderRequest.getCancelRequestUser());
	    interactiveWithSaasLog.setOperateType(OrderOperateEnum.CANCEL_ORDER.operateType);
	    interactiveWithSaasLog.setMerchantCode(supplyOrderDto.getMerchantCode());
	    interactiveWithSaasLog.setFcOrderCode(supplyOrderDto.getFcOrderCode());
	    interactiveWithSaasLog.setSupplyCode(supplyOrderDto.getSupplyCode());
	    interactiveWithSaasLog.setSupplyClass(supplyOrderDto.getSupplyClass());
	    
	    cancelOrderResponse = commonOrderService.cancelOrder(cancelOrderRequest,supplyOrderDto);
	    
	    interactiveWithSaasLog.setResult(InteractiveResult.SUCCESS.result);
	    interactiveWithSaasLog.setResponseContent(JSON.toJSONString(cancelOrderResponse));
	    if(FCReturnNoEnum._010000000.returnNo.equals(cancelOrderResponse.getReturnNo())){
		interactiveWithSaasLog.setSupplyResult(SupplyResultEnum.CANCEL.result);
	    }
	} catch (Exception e) {
	    log.error("取消订单异常", e);
	    cancelOrderResponse.setReturnNo(FCReturnNoEnum._011203901.returnNo);
	    cancelOrderResponse.setReturnCode(FCReturnNoEnum._011203901.returnCode);
	    cancelOrderResponse.setReturnDesc(FCReturnNoEnum._011203901.returnDesc);
	    
	    interactiveWithSaasLog.setResult(InteractiveResult.FAIL.result);
	    interactiveWithSaasLog.setFailReason(commonOrderService.getStackTrace(e));
	} finally {
	    /** 保存与Saas交互日志 */
	    interactiveWithSaasLog.setResponseTime(new Date());
	    commonOrderService.saveInteractiveSaasLog(interactiveWithSaasLog);
	}
	return cancelOrderResponse;
    }

	@RequestMapping(value = "/query/confirmno", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ConfirmNoResponse queryConfirmNo(HttpServletRequest httpServletRequest){
		ConfirmNoResponse confirmNoResponse=new ConfirmNoResponse();
		try {
			BufferedReader reader = httpServletRequest.getReader();
			StringBuffer stringBuffer = new StringBuffer();
			String line = reader.readLine();
			while (line != null) {
				stringBuffer.append(line);
				line = reader.readLine();
			}
			QuerySupplyOrderRequest querySupplyOrderRequest = JSON.parseObject(stringBuffer.toString(), QuerySupplyOrderRequest.class);
			/** 1供货单号非空校验 */
			String supplyOrderCode = querySupplyOrderRequest.getSupplyOrderCode();
			if (!StringUtil.isValidString(supplyOrderCode)) {
				log.error("查询确认号，供货单号为空:" + JSON.toJSONString(querySupplyOrderRequest));
				confirmNoResponse.setReturnNo(FCReturnNoEnum._011204005.returnNo);
				confirmNoResponse.setReturnCode(FCReturnNoEnum._011204005.returnCode);
				confirmNoResponse.setReturnDesc(FCReturnNoEnum._011204005.returnDesc);
				return confirmNoResponse;
			}
			/** 2根据请求供货单号查询供货单 */
			SupplyOrderQuery supplyOrderQuery = new SupplyOrderQuery();
			supplyOrderQuery.setSupplyOrderCode(supplyOrderCode);
			SupplyOrderDto supplyOrderDto = commonServerRequestService.getFirstOneFromList(CommonServerApi.QUERY_ORDER, supplyOrderQuery, SupplyOrderDto.class);
			if (supplyOrderDto == null) {
				log.error("查询确认号，供货单不存在:" + JSON.toJSONString(querySupplyOrderRequest));
				confirmNoResponse.setReturnNo(FCReturnNoEnum._011204034.returnNo);
				confirmNoResponse.setReturnCode(FCReturnNoEnum._011204034.returnCode);
				confirmNoResponse.setReturnDesc(FCReturnNoEnum._011204034.returnDesc);
				return confirmNoResponse;
			}

			confirmNoResponse=commonOrderService.queryConfirmno(supplyOrderDto);

		}catch (Exception e){
			log.error("查询确认号系统异常",e);
			confirmNoResponse.setReturnNo(FCReturnNoEnum._011204901.returnNo);
			confirmNoResponse.setReturnCode(FCReturnNoEnum._011204901.returnCode);
			confirmNoResponse.setReturnDesc("查询确认号系统异常");
		}
		return confirmNoResponse;
	}
}
