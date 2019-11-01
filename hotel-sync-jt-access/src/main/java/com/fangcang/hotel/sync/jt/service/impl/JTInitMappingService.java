package com.fangcang.hotel.sync.jt.service.impl;

import com.alibaba.fastjson.JSON;
import com.fangcang.hotel.sync.common.api.constant.ActiveEnum;
import com.fangcang.hotel.sync.common.api.constant.CommonServerApi;
import com.fangcang.hotel.sync.common.api.dto.mapping.HotelMappingDto;
import com.fangcang.hotel.sync.common.api.dto.mapping.PricePlanMappingDto;
import com.fangcang.hotel.sync.common.api.dto.query.HotelMappingQuery;
import com.fangcang.hotel.sync.common.api.dto.query.PricePlanMappingQuery;
import com.fangcang.hotel.sync.data.service.CommonServerRequestService;
import com.fangcang.hotel.sync.data.service.RedisService;
import com.fangcang.hotel.sync.jt.constants.JTRedisKey;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class JTInitMappingService {

    private static final Logger logger = LoggerFactory.getLogger(JTInitMappingService.class);

    @Autowired
    private CommonServerRequestService commonServerRequestService;

    @Autowired
    private RedisService redisService;

    public List<HotelMappingDto> initHotelConsumeCache() throws Exception {

        String consumeHotelKey = JTRedisKey.JT_FULL_CONSUME_HOTEL_KEY;
        HotelMappingQuery hotelMappingQuery = new HotelMappingQuery();
        hotelMappingQuery.setIsActive(ActiveEnum.ACTIVE.getValue());
        hotelMappingQuery.setSupplyClass("JT");//SupplierClass.SJL.getSupplierClass()
        hotelMappingQuery.setIsPage(false);
        List<HotelMappingDto> hotelMappings = commonServerRequestService.getListResult(CommonServerApi.QUERY_HAS_MAPPING_HOTEL, hotelMappingQuery, HotelMappingDto.class);
        if (CollectionUtils.isNotEmpty(hotelMappings)) {
            Iterator var7 = hotelMappings.iterator();

            while(var7.hasNext()) {
                HotelMappingDto hotelMappingDto = (HotelMappingDto)var7.next();
                redisService.hset(consumeHotelKey, hotelMappingDto.getSpHotelId(), JSON.toJSONString(hotelMappingDto));

            }
        }
        return hotelMappings;
    }

    /**
     * 初始化酒店-价格计划的映射
     * @return
     * @throws Exception
     */
    public List<PricePlanMappingDto> initHotelPricePlanMapping() throws Exception{
        String hotelPriceRedisKey = JTRedisKey.JT_HOTEL_PRICEPLAN_MAPPING;
        PricePlanMappingQuery planMappingQuery = new PricePlanMappingQuery();
        planMappingQuery.setSupplyClass("JT");//SupplierClass.SJL.getSupplierClass()
        planMappingQuery.setIsActive(ActiveEnum.ACTIVE.getValue());
        planMappingQuery.setIsPage(false);
        List<PricePlanMappingDto> pricePlanMappings = commonServerRequestService.getListResult(CommonServerApi.QUERY_PRICEPLAN_MAPPING_LIST, planMappingQuery, PricePlanMappingDto.class);

        Map<String, String> hotelPriceMap = new HashMap<String, String>();
        for(PricePlanMappingDto pricePlanMappingDto: pricePlanMappings){
            String spHotelId = pricePlanMappingDto.getSpHotelId();
            String spPricePlanId = pricePlanMappingDto.getSpPricePlanKey() + "_" + pricePlanMappingDto.getPricePlanId();
            if(hotelPriceMap == null || hotelPriceMap.get(spHotelId) == null){
                hotelPriceMap.put(spHotelId, spPricePlanId);
            }else{
                String spPricePlanIds = hotelPriceMap.get(spHotelId);
                spPricePlanIds = spPricePlanIds.concat(",").concat(spPricePlanId);
                hotelPriceMap.put(spHotelId, spPricePlanIds);
            }
        }

        redisService.hmset(hotelPriceRedisKey, hotelPriceMap);
        return pricePlanMappings;
    }
}
