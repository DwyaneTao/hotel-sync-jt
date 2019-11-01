package com.fangcang.hotel.sync.jt.service.conver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fangcang.hotel.sync.common.api.constant.ActiveEnum;
import com.fangcang.hotel.sync.common.api.dto.product.ProductDeclare;
import com.fangcang.hotel.sync.common.api.dto.product.ProductDetailMiddleDto;
import com.fangcang.hotel.sync.common.api.dto.product.ProductMiddleDto;
import com.fangcang.hotel.sync.common.api.model.supply.SupplyHotel;
import com.fangcang.hotel.sync.common.api.model.supply.SupplyRoom;
import com.fangcang.hotel.sync.data.constants.SupplierClass;
import com.fangcang.hotel.sync.data.dto.BaseSyncInRedisDto;
import com.fangcang.hotel.sync.data.service.CommonServerRequestService;
import com.fangcang.hotel.sync.data.service.RedisService;
import com.fangcang.hotel.sync.data.service.SendEmailService;
import com.fangcang.hotel.sync.data.service.converter.SupplyDataConverter;
import com.fangcang.hotel.sync.data.util.StringUtilExtend;
import com.fangcang.hotel.sync.jt.api.response.*;
import com.fangcang.hotel.sync.jt.api.response.hotel.HotelDetail;
import com.fangcang.hotel.sync.jt.api.response.hotel.RoomType;
import com.fangcang.hotel.sync.jt.constants.JTResponseEnum;
import com.fangcang.hotel.sync.jt.dto.JTProductDataDto;
import com.fangcang.hotel.sync.jt.dto.JTProductDataRequest;
import com.fangcang.hotel.sync.jt.dto.JTProductDto;
import com.fangcang.hotel.sync.jt.dto.JTProductRoomDto;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service("jtDataConverter")
public class JTDataConverter implements SupplyDataConverter {

    private static final Logger log = LoggerFactory.getLogger(JTDataConverter.class);

    @Autowired
    private RedisService redisService;

	@Autowired
	private TaskExecutor inactiveExecutor;

	@Autowired
	private CommonServerRequestService commonServerRequestService;

	@Autowired
	private SendEmailService sendEmailService;

	@Override
	public List<SupplyHotel> converSupplyHotelData(String data) {
		List<SupplyHotel> supplyHotelList = new ArrayList<SupplyHotel>();
        BaseSyncInRedisDto baseSyncInRedisDto = JSON.parseObject(data, BaseSyncInRedisDto.class);
        String hotelDetailJSON = baseSyncInRedisDto.getResponseString();
        QueryHotelDetailResponse htlDetails = JSON.parseObject(hotelDetailJSON, new TypeReference<QueryHotelDetailResponse>(){});
        if (!Integer.valueOf(JTResponseEnum.SUCCESS.key).equals(htlDetails.getCode())) {
            log.error("同步君亭酒店基本数据失败，result:"+ htlDetails.getMessage());
            return supplyHotelList;
        }
		if(CollectionUtils.isEmpty(htlDetails.getHotelDetailList())){
            return supplyHotelList;
        }
        SupplyHotel hotel = null;

		for (HotelDetail hotelDetail : htlDetails.getHotelDetailList()) {
			hotel = new SupplyHotel();
			hotel.setSpHotelAddress(hotelDetail.getHotelInfo().getHotelAddress());
			hotel.setSpHotelName(hotelDetail.getHotelInfo().getHotelFullName());
			hotel.setSpHotelId(hotelDetail.getHotelInfo().getHotelCode());
			hotel.setSupplyClass("JT");
			hotel.setIsActive(ActiveEnum.ACTIVE.getValue());
			hotel.setCreator("system");
			hotel.setModifier("system");
			hotel.setStatus(1);
			List<SupplyRoom> supplyRooms = new LinkedList<>();
			if ( null != hotelDetail.getRoomTypeList() && !hotelDetail.getRoomTypeList().isEmpty()) {
				for (RoomType type : hotelDetail.getRoomTypeList()) {
					SupplyRoom roomType = new SupplyRoom();
					if ( null != type.getRoomType() && !"".equals(type.getRoomType()) ) {
						roomType.setSpRoomId(hotelDetail.getHotelInfo().getHotelCode()+"_"+type.getRoomType());
						roomType.setSpHotelId(hotelDetail.getHotelInfo().getHotelCode());
						roomType.setSpRoomName(type.getDescription());
//						roomType.setSpBedType(type.getRoomType());
//						roomType.setBedTypeName(type.getDescription());
						roomType.setIsActive(ActiveEnum.ACTIVE.getValue());
						roomType.setStatus(1);
						roomType.setSupplyClass("JT");
						roomType.setCreator("system");
						roomType.setModifier("system");
						supplyRooms.add(roomType);
					}
				}
			}
			hotel.setSupplyRooms(supplyRooms);
			supplyHotelList.add(hotel);
		}
        return supplyHotelList;
	}

	@Override
	public List<ProductMiddleDto> converSupplyProductData(String data) throws Exception {
		List<ProductMiddleDto> productMiddleDtos = new ArrayList<ProductMiddleDto>();
		if (!StringUtilExtend.isValidString(data)) {
			return productMiddleDtos;
		}
		BaseSyncInRedisDto baseSyncInRedisDto = JSON.parseObject(data, BaseSyncInRedisDto.class);
		JTProductDataRequest baseRequest = JSON.parseObject(baseSyncInRedisDto.getRequestString(), JTProductDataRequest.class);

		JTProductDto baseResponse = JSON.parseObject(baseSyncInRedisDto.getResponseString(),new TypeReference<JTProductDto>(){});
		if(null == baseResponse){
			log.error("君亭转换产品数据处理失败,源数据为空!"+data);
			return productMiddleDtos;
		}
		List<JTProductDataDto> jtProductDtos = baseResponse.getJtProductDtos();

		for (JTProductDataDto jtProductDto : jtProductDtos) {
			ProductMiddleDto productMiddleDto = new ProductMiddleDto();
			productMiddleDto.setPricePlanName(jtProductDto.getRoomType());
			productMiddleDto.setSpRoomName(jtProductDto.getRoomType());
			productMiddleDto.setSpRoomName("四季商务房");
			productMiddleDto.setSpHotelId("J571005");
			productMiddleDto.setSpHotelName("杭州西溪谷测试酒店");
			productMiddleDto.setSpPricePlanId("J571005_BDKS");
			productMiddleDto.setSpRoomTypeId("J571005_BDKS");
			productMiddleDto.setBedType("1000000");
			productMiddleDto.setCreator("Test");
			productMiddleDto.setTotalAmount(Double.valueOf(jtProductDto.getTotalQuantity()));
			jtProductDto.getTodayDepartureQuantity();
			jtProductDto.getOooQuantity();
			productMiddleDto.setSupplyClass("JT");
			productMiddleDto.setSupplyCode("S10048011");
			productMiddleDto.setSupplyClassName("君亭");

			List<JTProductRoomDto> jtProductRoomDtos = jtProductDto.getJTProductRoomDtos();
			List<ProductDeclare> productDeclares = new LinkedList<>();

			for (JTProductRoomDto jtProductRoomDto : jtProductRoomDtos) {
				ProductDetailMiddleDto productDetailMiddleDto = new ProductDetailMiddleDto();
				ProductDeclare productDeclare = new ProductDeclare();
//				productDetailMiddleDto.set
			    jtProductRoomDto.getRateCode();
				jtProductRoomDto.getRoomRate();
				jtProductRoomDto.getActualRate();
				jtProductRoomDto.getContainBreakfast();
				jtProductRoomDto.getPackeagesQuantity();
				jtProductRoomDto.getSubscribePolicy();
				jtProductRoomDto.getUnsubscribePolicy();
				jtProductRoomDto.getBatchContent();
				jtProductRoomDto.getRemarks();


				productDeclares.add(productDeclare);
			}
			productMiddleDto.setProductDeclares(productDeclares);
			productMiddleDtos.add(productMiddleDto);

		}
		return productMiddleDtos;
	}

//
//    @Override
//    public List<ProductMiddleDto> converSupplyProductData(String data) throws Exception {
//
//    	List<ProductMiddleDto> productMiddleDtos = new ArrayList<ProductMiddleDto>();
//		if (!StringUtilExtend.isValidString(data)) {
//			return productMiddleDtos;
//		}
//
//		BaseSyncInRedisDto baseSyncInRedisDto = JSON.parseObject(data, BaseSyncInRedisDto.class);
//		SJLProductDataRequest baseRequest = JSON.parseObject(baseSyncInRedisDto.getRequestString(), SJLProductDataRequest.class);
//
//		String supplyCode = baseRequest.getSupplyCode();
//
//        boolean isCreatePricePlan = false;
//		if(baseRequest.getIsCreatePricePlan() && SJLConfig.getIsCreatePlan()){
//		    isCreatePricePlan = true;
//        }
//
//		Response<QueryRatePlanResponse> baseResponse = JSON.parseObject(baseSyncInRedisDto.getResponseString(),new TypeReference<Response<QueryRatePlanResponse>>(){});
//		if(null == baseResponse){
//			log.error("深捷旅转换产品数据处理失败,源数据为空!"+data);
//			return productMiddleDtos;
//		}
//
//
//
//
//		if(SJLResponseEnum.SUCCESS.key == baseResponse.getCode() || baseResponse.getCode() == ReturnErrorCode._20004.no) {
//
//			//若是全量的话，就获取该酒店下面所有的价格计划
//			Map<String, Long> pricePlanMapping = new HashMap<String, Long>();
//			if(baseRequest.getFullOrIncr() == 1 && SJLConfig.getControlDel() == 1){
//				String spPricePlanIds = redisService.hget(SJLRedisKey.SJL_HOTEL_PRICEPLAN_MAPPING, String.valueOf(baseRequest.getSpHotelIds().get(0)));
//				if(StringUtil.isNotNull(spPricePlanIds)) {
//					for (String key : spPricePlanIds.split(",")) {
//						String spPricePlanId = key.split("_")[0];
//						Long pricePlanId = Long.valueOf(key.split("_")[1]);
//						pricePlanMapping.put(spPricePlanId, pricePlanId);
//					}
//				}
//
//			}
//
//			if (Integer.valueOf(SJLResponseEnum.SUCCESS.key) == baseResponse.getCode()) {
//				QueryRatePlanResponse rateplanResp = baseResponse.getResult();
//				if (CollectionUtils.isEmpty(rateplanResp.getHotelRatePlanList())) {
//					//TODO 带实现
//					return null;
//				}
//				List<HotelRatePlan> rateplanList = rateplanResp.getHotelRatePlanList();
//				ProductMiddleDto middleDto = null;
//
//				//获取深捷旅的房型映射
//				String hotelMappingCacheKey = StringUtilExtend.uniteString(CacheKeyConstant.ROOM_MAPPING, SupplierClass.SJL);
//
//				for (HotelRatePlan hotelRatePlan : rateplanList) {
//					Integer spHotelId = hotelRatePlan.getHotelId();
//					List<BookingRule> bookingRules = hotelRatePlan.getBookingRules();
//					List<RefundRule> refundRules = hotelRatePlan.getRefundRules();
//
//					Map<String, BookingRule> bookingRuleMap = bookingRules.stream().collect(Collectors.toMap(BookingRule::getBookingRuleId, Function.identity(), (key1, key2) -> key2));
//					Map<String, RefundRule> refundRuleMap = refundRules.stream().collect(Collectors.toMap(RefundRule::getRefundRuleId, Function.identity(), (key1, key2) -> key2));
//
//
//					List<Room> rooms = hotelRatePlan.getRooms();
//					for (Room room : rooms) {
//						String spRoomTypeId = room.getRoomTypeId();
//						String spRoomIdKey = StringUtilExtend.getKey(Constants.UNDERLINE, String.valueOf(spHotelId), spRoomTypeId);
//
//						// 将未映射的房型去除
//						String spRoomRedisKey = StringUtilExtend.getKey(Constants.UNDERLINE, String.valueOf(spHotelId), spRoomIdKey);
//						if (null == redisService.hget(hotelMappingCacheKey, spRoomRedisKey)) {
//							// log.warn("房型为"+ spRoomIdKey +"没映射");
//							continue;
//						}
//
//
//						List<RatePlan> rateplans = room.getRatePlans();
//						for (RatePlan rateplan : rateplans) {
//							if (pricePlanMapping.get(rateplan.getKeyId()) != null) {
//								pricePlanMapping.remove(rateplan.getKeyId());
//							}
//
//							middleDto = new ProductMiddleDto();
//							middleDto.setSpHotelId(String.valueOf(spHotelId));
//							middleDto.setSpRoomTypeId(spRoomIdKey);
//							middleDto.setSpPricePlanId(rateplan.getKeyId());
//							middleDto.setPricePlanName(rateplan.getKeyName());
//							middleDto.setSupplyClass(SupplierClass.SJL.getSupplierClass());
//							middleDto.setSupplyCode(supplyCode);
//							middleDto.setBedType(SJLUtil.getClassifyBedType(rateplan.getBedName()));
//							if (null == rateplan.getPaymentType() || rateplan.getPaymentType().intValue() == 0) {
//								middleDto.setPayMethod(PayMethodEnum.PRE_PAY.key);
//							} else {
//								middleDto.setPayMethod(PayMethodEnum.PAY_NOCOMISSION.key);
//							}
//							middleDto.setGuestType(SJLUtil.getFitPeopleEnumByKey(rateplan.getMarket()));
//							middleDto.setIsCreate(isCreatePricePlan);
//							middleDto.setCurrency(CurrencyEnum.CNY.key);
//							middleDto.setCreator("system");
//							middleDto.setModifier("system");
//							middleDto.getProductDetails().addAll(parseProductDetail(baseRequest, rateplan, bookingRuleMap, refundRuleMap));
//
//							productMiddleDtos.add(middleDto);
//						}
//						rateplans = null;
//					}
//					rooms = null;
//				}
//				rateplanList = null;
//			}
//
//			//处理无效的价格计划
//			if (pricePlanMapping != null && pricePlanMapping.size() > 0) {
//				Set<Long> invalidPricePlan = new HashSet<Long>();
//				for (Long value : pricePlanMapping.values()) {
//					invalidPricePlan.add(value);
//				}
//				log.info("失效的价格计划为:", JSON.toJSONString(invalidPricePlan));
//				inactiveActiveMapping(String.valueOf(baseRequest.getSpHotelIds().get(0)), invalidPricePlan);
//		     }
//
//		}
//		baseResponse = null;
//        return productMiddleDtos;
//    }
//
//    private List<ProductDetailMiddleDto> parseProductDetail(SJLProductDataRequest sJLProductDataRequest, RatePlan rateplan,Map<String, BookingRule> bookingRuleMap,Map<String, RefundRule> refundRuleMap)
//			throws Exception {
//		List<ProductDetailMiddleDto> detailMiddleDtos = new ArrayList<>();
//		List<NightlyRate> nightlyRates = rateplan.getNightlyRates();
//
//		String bookingRuleId = rateplan.getBookingRuleId();
//		String refundRuleId = rateplan.getRefundRuleId();
//
//		final Set<String> queryDays = SyncUtil.getQueryDaysForClosedInterval(sJLProductDataRequest.getStartDate(), sJLProductDataRequest.getEndDate());
//		if(CollectionUtils.isNotEmpty(nightlyRates))
//		{
//			Map<String,NightlyRate> rateMaps = nightlyRates.stream().collect(Collectors.toMap(NightlyRate::getDate, Function.identity(), (key1, key2) -> key2));
//
//			// 未返回数据的日期，初始化为所有日期
//			Set<String> noFallDate = new HashSet<String>();
//			noFallDate.addAll(queryDays);
//
//			ProductDetailMiddleDto middleDto = null;
//			for (NightlyRate nightlyRate : rateMaps.values()) {
//					middleDto = new ProductDetailMiddleDto();
//					middleDto.setSaleDate(nightlyRate.getDate());
//					middleDto.setBasePrice(nightlyRate.getCose().doubleValue());
//					middleDto.setQuotaState(SJLRoomStateEnum.getFcRoomStateByKey(nightlyRate.getStatus()));
//					middleDto.setQuotaNum(nightlyRate.getCurrentAlloment());
//					middleDto.setOverDraft(0);
//					Integer breakfast = nightlyRate.getBreakfast() != null? nightlyRate.getBreakfast(): rateplan.getBreakfast();
//					middleDto.setBreakfastNum(BreakFastEnum.getEnumByKey(breakfast).fcCode);
//
//					String bookRuleId = (null == nightlyRate.getBookingRuleId()?bookingRuleId:nightlyRate.getBookingRuleId());
//					String refundId = (null == nightlyRate.getRefundRuleId()?refundRuleId:nightlyRate.getRefundRuleId());
//
//					setBookingRule(middleDto, bookingRuleMap,bookRuleId);
//					setRefundRule(middleDto,refundRuleMap, refundId);
//
//					detailMiddleDtos.add(middleDto);
//
//					// 移除有返回数据的日期，剩下的则为未返回的日期
//					noFallDate.remove(nightlyRate.getDate());
//			}
//
//			// 未返回日期设满房
//			if (CollectionUtils.isNotEmpty(noFallDate)) {
//				for (String saleDate : noFallDate) {
//					detailMiddleDtos.add(createFullProductDetailMiddleDto(saleDate));
//				}
//			}
//		}
//		else
//		{
//			for (String saleDate : queryDays) {
//				detailMiddleDtos.add(createFullProductDetailMiddleDto(saleDate));
//			}
//		}
//		if(SJLConfig.getMaxQueryProductNum() == 101 ) {
//			log.info("获取的数据" + JSON.toJSONString(detailMiddleDtos));
//		}
//
//		return detailMiddleDtos;
//	}
//
//    /**
//      *   创建满房数据
//     * @param saleDate
//     * @return
//     */
//    private ProductDetailMiddleDto createFullProductDetailMiddleDto(String saleDate) {
//    	ProductDetailMiddleDto middleDto = new ProductDetailMiddleDto();
//		middleDto.setSaleDate(saleDate);
//		middleDto.setBasePrice(BigDecimal.ZERO.doubleValue());
//		middleDto.setBreakfastType(0);
//		middleDto.setQuotaState(RoomStateEnum.FULL_ROOM.key);
//		middleDto.setQuotaNum(0);
//		middleDto.setOverDraft(0);
//		return middleDto;
//    }
//
//	private void setRefundRule(ProductDetailMiddleDto middleDto,Map<String, RefundRule> refundRuleMap,String refundId)
//	{
//		if(MapUtils.isNotEmpty(refundRuleMap) && StringUtil.isValidString(refundId))
//		{
//			RefundRule refundRule = refundRuleMap.get(refundId);
//			if(null != refundRule)
//			{
//				Integer refundRuleType = refundRule.getRefundRuleType();
//				if(null != refundRuleType)
//				{
//					if(SJLRefundRuleTypeEnum.NO_CANCEL.key == refundRuleType.intValue())
//					{
//						middleDto.setCancelRestrictType(CancelRestrictEnum.NO_CANCEL.key);
//					}
//					else if(SJLRefundRuleTypeEnum.PARTIAL_CANCEL.key == refundRuleType.intValue())
//					{
//						middleDto.setCancelRestrictType(CancelRestrictEnum.FULL_CANCEL.key);
//						middleDto.setCancelRestrictDays(SJLUtil.convertHourToDay(refundRule.getRefundRuleHours()));
//						middleDto.setCancelRestrictTime(SJLUtil.convertHourToTime(refundRule.getRefundRuleHours()));
//					}
//				}
//			}
//		}else{
//			middleDto.setCancelRestrictType(CancelRestrictEnum.NO_CANCEL.key);
//		}
//	}
//
//	private void setBookingRule(ProductDetailMiddleDto middleDto,Map<String, BookingRule> bookingRuleMap,String bookRuleId)
//	{
//		if(MapUtils.isNotEmpty(bookingRuleMap) && StringUtil.isValidString(bookRuleId))
//		{
//			BookingRule bookingRule = bookingRuleMap.get(bookRuleId);
//			if(null != bookingRule)
//			{
//
//				/** 预订条款 **/
//				if(null != bookingRule.getMinAdvHours())
//				{
//					middleDto.setBookRestrictType(1);
//					middleDto.setBookRestrictDays(SJLUtil.convertHourToDay(bookingRule.getMinAdvHours()));
//					middleDto.setBookRestrictTime(SJLUtil.convertHourToTime(bookingRule.getMinAdvHours()));
//				}
//				else if(null != bookingRule.getMaxAdvHours())
//				{
//					middleDto.setBookRestrictType(2);
//					middleDto.setBookRestrictDays(SJLUtil.convertHourToDay(bookingRule.getMaxAdvHours()));
//					middleDto.setBookRestrictTime(SJLUtil.convertHourToTime(bookingRule.getMaxAdvHours()));
//				}
//
//				/** 入住条款 **/
//				if(null != bookingRule.getMinDays() && null != bookingRule.getMaxDays())
//				{
//					if(bookingRule.getMinDays().equals(bookingRule.getMaxDays())){
//						middleDto.setOccupancyRestrictType(OccupancyRestrictEnum.LIMIT_OCCUPANCY.key);
//						middleDto.setOccupancyRestrictDays(bookingRule.getMinDays());
//					} else if(bookingRule.getMinDays().intValue() < bookingRule.getMaxDays().intValue()){
//						middleDto.setOccupancyRestrictType(OccupancyRestrictEnum.CONTINUE_OCCUPANCY.key);
//						middleDto.setOccupancyRestrictDays(bookingRule.getMinDays());
//					}
//				}
//
//				if(null != bookingRule.getMinDays() && null == bookingRule.getMaxDays())
//				{
//					middleDto.setOccupancyRestrictType(OccupancyRestrictEnum.CONTINUE_OCCUPANCY.key);
//					middleDto.setOccupancyRestrictDays(bookingRule.getMinDays());
//				}
//
//				if(null == bookingRule.getMinDays() && null != bookingRule.getMaxDays())
//				{
//					middleDto.setOccupancyRestrictType(OccupancyRestrictEnum.GREATEST_OCCUPANCY.key);
//					middleDto.setOccupancyRestrictDays(bookingRule.getMaxDays());
//				}
//
//				/** 间数条款 **/
//				middleDto.setBookRoomsRestrict(bookingRule.getMinAmount());
//
//			}
//		}
//	}
//
//	///** 查询对应酒店下面的价格计划 **/
//  /*  private Map<String, Long> getHotelPricePlanMapping(String hotelId){
//        *//** 酒店id对应的价格计划的前缀 **//*
//        String pricePlanPrefix = redisService.hget(SJLRedisKey.SJL_HOTEL_PRICEPLAN_MAPPING, hotelId);
//        *//** 用于正则匹配 **//*
//        String pricePlanMatch  = StringUtilExtend.uniteString("^",pricePlanPrefix, "#[0-9#A-Za-z]*");
//
//        String pricePlanMappingCacheKey= StringUtilExtend.uniteString(CacheKeyConstant.PRICE_PLAN_MAPPING_CACHE_KEY,SupplierClass.SJL);
//        Map<String, String> pricePlanMapping = redisService.hgetall(pricePlanMappingCacheKey);
//
//        *//** hotel下面对应的价格计划
//         key为sjl的价格计划，value为对应的房仓的价格计划id **//*
//        Map<String, Long> hotelPricePlanMapping = new HashMap<String, Long>();
//
//        Iterator iterator = pricePlanMapping.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry entry = (Map.Entry) iterator.next();
//            String key = (String)entry.getKey();
//            String value = (String) entry.getValue();
//
//            if(key.matches(pricePlanMatch)){
//                ProductMiddleDto middleDto = JSON.parseObject(value, ProductMiddleDto.class);
//                if(middleDto != null){
//                    hotelPricePlanMapping.put(key, middleDto.getPricePlanId());
//                }
//            }
//        }
//
//        return hotelPricePlanMapping;
//    }*/
//
//	/**
//	 * 描述：失效价格计划
//	 *
//	 * @return supplyHotelId
//	 */
//	private void inactiveActiveMapping(String supplyHotelId, Set<Long> pricePlans) {
//
//		ModifyMappingActiveRequest modifyMappingActiveRequest = new ModifyMappingActiveRequest();
//		modifyMappingActiveRequest.setIsActive(ActiveEnum.INVALID.getValue());
//		modifyMappingActiveRequest.setSupplyClass(SupplierClass.SJL.getSupplierClass());
// 		modifyMappingActiveRequest.setType(3);
//		modifyMappingActiveRequest.setPricePlanIds(pricePlans);
//		ExecuteInactiveProductThread inactiveProductThread = new ExecuteInactiveProductThread(modifyMappingActiveRequest, sendEmailService, commonServerRequestService);
//		inactiveExecutor.execute(inactiveProductThread);
//	}
//


}
