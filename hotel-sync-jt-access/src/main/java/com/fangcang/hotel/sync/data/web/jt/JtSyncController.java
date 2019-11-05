package com.fangcang.hotel.sync.data.web.jt;

import com.alibaba.fastjson.JSON;
import com.fangcang.hotel.sync.common.api.dto.mapping.HotelMappingDto;
import com.fangcang.hotel.sync.data.dto.MsgResponse;
import com.fangcang.hotel.sync.data.service.SupplyFetchService;
import com.fangcang.hotel.sync.data.util.StringUtilExtend;
import com.fangcang.hotel.sync.jt.api.request.QueryHotelListRequest;
import com.fangcang.hotel.sync.jt.config.JTConfig;
import com.fangcang.hotel.sync.jt.dto.JTProductDataRequest;
import com.fangcang.hotel.sync.jt.service.JTExtendsConfigService;
import com.fangcang.hotel.sync.jt.service.JTHotelSyncService;
import com.fangcang.hotel.sync.jt.service.impl.JTInitMappingService;
import com.fangcang.hotel.sync.jt.service.impl.JTOrderServiceImpl;
import com.fangcang.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class JtSyncController {

    private static final Logger logger = LoggerFactory.getLogger(JtSyncController.class);

    @Autowired
    private JTHotelSyncService jtHotelSyncService;

    @Autowired
    private SupplyFetchService jtFetchService;

    @Autowired
    private JTExtendsConfigService jtExtendsConfigService;

    @Autowired
    private JTOrderServiceImpl jtOrderService;


    @Autowired
    private JTInitMappingService jtInitMappingService;


    /**
     * 酒店基础信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/init/jt/hotelList", produces = "text/html;charset=UTF-8")
    public String initHotelList() throws Exception {
        QueryHotelListRequest requset = new QueryHotelListRequest();
        MsgResponse msgResponse = new MsgResponse();
        requset.setSupplyClass("JT");
        requset.setSupplyCode("S10048011");
        requset.setCreater("system");
        requset.setIsCache2Redis(true);
        requset.setGroupCode(JTConfig.getGroupCode());
        jtFetchService.fetchHotelBasicInfo(requset);
        msgResponse.setCode(1);
        msgResponse.setMsg("SUCCESS");
        return JSON.toJSONString(msgResponse);
    }




    @RequestMapping(value = "/sync/jt/product", produces = "application/json")
    @ResponseBody
    public MsgResponse syncSJLProduct(@RequestParam(value = "coHotelIds", required = true) String coHotelIds, @RequestParam(value = "supplyCode", required = false) String supplyCode) {
        MsgResponse response = new MsgResponse();
        try {
            if (StringUtils.isBlank(supplyCode)) {
                supplyCode = jtExtendsConfigService.getAllSupplyCode().iterator().next();
            }
            JTProductDataRequest jtProductDataRequest = new JTProductDataRequest();
            jtProductDataRequest.setIsCache2Redis(true);
            if (StringUtil.isValidString(coHotelIds) && !"all".equalsIgnoreCase(coHotelIds)) {
                jtProductDataRequest.setSpHotelIds(Arrays.asList(coHotelIds.split("[,|，|\\s]")));
            }
            jtFetchService.fetchHotelProductInfo(jtProductDataRequest);

            response.setCode(1);
            response.setMsg("SUCCESS");
        } catch (Exception e) {
            logger.error("sync jt product info error.", e);
            response.setCode(0);
            response.setMsg("FAIL");
        }
        return response;
    }

    @RequestMapping(value = "/init/hotel/consume", produces = "application/json")
    @ResponseBody
    public String initHotelConsumeCache(){
        String result = null;
        try {
            List<HotelMappingDto> hotelMappingDtos = jtInitMappingService.initHotelConsumeCache();
            result= StringUtilExtend.uniteString("SUCCESS,size:",hotelMappingDtos.size());
        }catch (Exception e){
            result = "ERROR";
        }
        return result;
    }




}
