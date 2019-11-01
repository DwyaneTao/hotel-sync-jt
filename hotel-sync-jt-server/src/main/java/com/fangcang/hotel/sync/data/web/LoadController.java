package com.fangcang.hotel.sync.data.web;

import com.fangcang.hotel.sync.common.api.dto.mapping.HotelMappingDto;
import com.fangcang.hotel.sync.common.api.dto.mapping.PricePlanMappingDto;
import com.fangcang.hotel.sync.common.api.dto.mapping.RoomMappingDto;
import com.fangcang.hotel.sync.data.constants.SupplierClass;
import com.fangcang.hotel.sync.data.service.CacheService;
import com.fangcang.hotel.sync.data.util.StringUtilExtend;
import com.fangcang.hotel.sync.jt.service.impl.JTCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/load")
public class LoadController {
    @Autowired
    private CacheService cacheService;

    @Autowired
    private JTCacheService jtlCacheService;

    @RequestMapping(value = "/hotelmapping/cache",produces = { "application/json;charset=UTF-8;" })
    @ResponseBody
    public String hotelMappingCache() {
        String result=null;
        try{
            String supplyClass = "JT";// SupplierClass.SJL.getSupplierClass();
			List<HotelMappingDto> hotelMappingDtos=cacheService.addHotelMappingCache(supplyClass );
            result= StringUtilExtend.uniteString("SUCCESS,size:",hotelMappingDtos.size());
        }catch (Exception e){
            result="ERROR";
        }
        return result;
    }

    @RequestMapping(value = "/roommapping/cache",produces = { "application/json;charset=UTF-8;" })
    @ResponseBody
    public String roomMappingCache() {
        String result=null;
        try{
        	String supplyClass = "JT";//SupplierClass.SJL.getSupplierClass();
            List<RoomMappingDto> roomMappingDtos=cacheService.addRoomMappingCache(supplyClass);
            result= StringUtilExtend.uniteString("SUCCESS,size:",roomMappingDtos.size());
        }catch (Exception e){
            result="ERROR";
        }
        return result;
    }

    @RequestMapping(value = "/priceplanmapping/cache",produces = { "application/json;charset=UTF-8;" })
    @ResponseBody
    public String pricePlanMappingCache() {
        String result=null;
        try{
        	String supplyClass = "JT";//SupplierClass.SJL.getSupplierClass();
            List<PricePlanMappingDto> pricePlanMappingDtos=cacheService.addPricePlanMappingCache(supplyClass);
            result= StringUtilExtend.uniteString("SUCCESS,size:",pricePlanMappingDtos.size());
        }catch (Exception e){
            result="ERROR";
        }
        return result;
    }

    @RequestMapping(value = "/all/cache",produces = { "application/json;charset=UTF-8;" })
    @ResponseBody
    public String allMappingCache() {
        String result=null;
        try{
        	String supplyClass = "JT";//SupplierClass.SJL.getSupplierClass();
        	cacheService.addHotelMappingCache(supplyClass);
        	cacheService.addRoomMappingCache(supplyClass);
        	cacheService.addPricePlanMappingCache(supplyClass);
            result= StringUtilExtend.uniteString("SUCCESS");
        }catch (Exception e){
            result="ERROR";
        }
        return result;
    }
}
