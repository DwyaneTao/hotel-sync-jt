package com.fangcang.hotel.sync.jt.service.impl;

import com.alibaba.fastjson.JSON;
import com.fangcang.hotel.sync.common.api.dto.mapping.HotelMappingDto;
import com.fangcang.hotel.sync.data.constants.CacheKeyConstant;
import com.fangcang.hotel.sync.data.service.CacheService;
import com.fangcang.hotel.sync.data.service.CommonServerRequestService;
import com.fangcang.hotel.sync.data.service.RedisService;
import com.fangcang.hotel.sync.data.util.StringUtilExtend;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("jtCacheService")
public class JTCacheService {

	private static final Log log = LogFactory.getLog(JTCacheService.class);

	@Autowired
	private RedisService redisService;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private CommonServerRequestService commonServerRequestService;

	/**
	 * 查询酒店映射
	 * @param supplyClass
	 * @param supplyHotelId
	 * @return
	 */
	public HotelMappingDto getHotelMapping(String supplyClass,String supplyHotelId) throws Exception{
		HotelMappingDto hotelMappingDto=null;
		String hotelMappingCacheKey=StringUtilExtend.uniteString(CacheKeyConstant.HOTEL_MAPPING,supplyClass);
		List<String> cacheResult = redisService.hmget(hotelMappingCacheKey,supplyHotelId);
		/*if (isNullList(cacheResult)){
			log.info("重新加载酒店映射到缓存:"+supplyClass+":because not found:"+supplyHotelId);
			List<HotelMappingDto> hotelMappingDtos=cacheService.addHotelMappingCache(supplyClass);
			cacheResult=redisService.hmget(hotelMappingCacheKey,supplyHotelId);
			if (CollectionUtils.isEmpty(cacheResult) && CollectionUtils.isNotEmpty(hotelMappingDtos)){
				for (HotelMappingDto temp:hotelMappingDtos){
					if (StringUtilExtend.equalsWithTrim(temp.getSpHotelId(),supplyHotelId)){
						return temp;
					}
				}
			}
			cacheService.addRoomMappingCache(supplyClass);
		}
*/
		if (!isNullList(cacheResult)){
			String hotelMappingJsonStr=cacheResult.get(0);
			hotelMappingDto= JSON.parseObject(hotelMappingJsonStr,HotelMappingDto.class);
		}
		return hotelMappingDto;
	}

	/*//初始化酒店-价格计划映射
	public Map<String, Set<String>> addHotelPricePlanMapping(String supplyClass) throws Exception {
		Map<String, Set<String>> hotelPricePlanMapping = new HashMap<String, Set<String>>();
		if(StringUtilExtend.equalsWithTrim(SupplierClass.SJL.getSupplierClass(),supplyClass)){
			log.error("只加载深捷旅酒店-价格计划映射");
			return null;
		}
		String hotelPricePlanKey = SJLRedisKey.SJL_HOTEL_PRICEPLAN_MAPPING;
		PricePlanMappingQuery planMappingQuery = new PricePlanMappingQuery();
		planMappingQuery.setSupplyClass(supplyClass);
		planMappingQuery.setIsActive(ActiveEnum.ACTIVE.getValue());
		planMappingQuery.setIsPage(false);
		Map<String, String> oldPlanKeySet=redisService.hgetall(hotelPricePlanKey);

		Map<String, Set<String>> validPlanKeySet = new HashMap<String, Set<String>>();
		Set<String> delPlanKeySet = new HashSet<String>();

		*//**
		 * 逻辑：1. 先将同一个酒店的筛选出来放在map
		 *       2. 再将筛选出来的map和旧的map进行比较，把新增的，删除的，修改的筛选出来
		 *       3. 把删除的用统一逻辑处理
		 *//*
		List<PricePlanMappingDto> pricePlanMappings = commonServerRequestService.getListResult(CommonServerApi.QUERY_PRICEPLAN_MAPPING_LIST, planMappingQuery, PricePlanMappingDto.class);
		if (CollectionUtils.isNotEmpty(pricePlanMappings)) {
			for (PricePlanMappingDto pricePlanMappingDto : pricePlanMappings) {
				if(validPlanKeySet != null){
					Set<String> hotelPriceKey = new HashSet<String>();
					if(validPlanKeySet.containsKey(pricePlanMappingDto.getSpHotelId())){
						hotelPriceKey = validPlanKeySet.get(pricePlanMappingDto.getSpHotelId());
					}

					hotelPriceKey.add(StringUtilExtend.uniteString(pricePlanMappingDto.getSpPricePlanKey(), "_", pricePlanMappingDto.getPricePlanId()));
					validPlanKeySet.put(pricePlanMappingDto.getSpHotelId(), hotelPriceKey);
				}
			}

			Iterator<Map.Entry<String, Set<String>>> it = validPlanKeySet.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String,  Set<String>> entry = it.next();
				if(oldPlanKeySet.containsKey(entry.getKey())){
					oldPlanKeySet.remove(entry.getKey());
					//修改的redis Key
					if(!JSON.toJSONString(entry.getValue()).equals(oldPlanKeySet.get(entry.getKey()))){
						redisService.hset(hotelPricePlanKey, entry.getKey(), JSON.toJSONString(entry.getValue()));
					}
				}else { //新增的redis Key
					redisService.hset(hotelPricePlanKey, entry.getKey(), JSON.toJSONString(entry.getValue()));
				}
			}
		}

		//需要删除的redis的key
		if(oldPlanKeySet != null && oldPlanKeySet.size() > 0){
			for(String key: oldPlanKeySet.keySet()){
				delPlanKeySet.add(key);
			}
		}

		if (delPlanKeySet.size() > 0){
			redisService.hdel(hotelPricePlanKey,delPlanKeySet.toArray(new String[delPlanKeySet.size()]));
			log.info("delete cache key:"+ hotelPricePlanKey +",field:"+JSON.toJSONString(delPlanKeySet));
		}

		return validPlanKeySet;
	}*/
	
	private boolean isNullList(List<String> source){
		boolean isNull=false;
		if (CollectionUtils.isEmpty(source)){
			isNull=true;
		}
		if (CollectionUtils.isNotEmpty(source) && source.size()==1){
			if (!StringUtilExtend.isValidString(source.get(0))){
				isNull=true;
			}
		}
		return isNull;
	}


	private void deleteCache(Set<String> oldSet,Set<String> newSet,String cacheKey){
		Set<String> result=new HashSet<>();
		for (String temp:oldSet){
			if (!newSet.contains(temp)){
				result.add(temp);
			}
		}
		if (result.size()>0){
			redisService.hdel(cacheKey,result.toArray(new String[result.size()]));
			log.info("delete cache key:"+cacheKey+",field:"+JSON.toJSONString(result));
		}
	}
}
