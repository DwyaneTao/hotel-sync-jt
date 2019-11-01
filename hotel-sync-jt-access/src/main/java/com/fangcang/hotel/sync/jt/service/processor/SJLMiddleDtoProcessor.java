package com.fangcang.hotel.sync.jt.service.processor;

import com.fangcang.hotel.sync.common.api.dto.mapping.HotelMappingDto;
import com.fangcang.hotel.sync.common.api.dto.mapping.PricePlanMappingDto;
import com.fangcang.hotel.sync.common.api.dto.mapping.RoomMappingDto;
import com.fangcang.hotel.sync.common.api.dto.product.ProductMiddleDto;
import com.fangcang.hotel.sync.data.annotations.DataProcessorPlugin;
import com.fangcang.hotel.sync.data.service.CommonMiddleDtoProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@DataProcessorPlugin(supplierClass = "JT")
@Service("jtMiddleDtoProcessor")
public class SJLMiddleDtoProcessor extends CommonMiddleDtoProcessor {
	
	private Log log= LogFactory.getLog(SJLMiddleDtoProcessor.class);
	
	/** 
     * 个性化处理数据
     */
	@Override
    protected void customizedHandle(ProductMiddleDto productMiddleDto, HotelMappingDto hotelMapping, RoomMappingDto roomTypeMapping, PricePlanMappingDto pricePlanMapping) throws Exception {
    	if (productMiddleDto.getIsCreate() && pricePlanMapping==null){
            log.info("君亭初始化产品，价格计划不存在，需要新增价格计划："+productMiddleDto.getSpRoomTypeId());
        }
        if (!productMiddleDto.getIsCreate() && pricePlanMapping==null){
            log.info("君亭定时同步，价格计划不存在，不需要新增价格计划："+productMiddleDto.getSpRoomTypeId());
        }
    }

    @Override
    protected String getPricePlanKeyWithPlanMapping(PricePlanMappingDto pricePlanMapping) {
        return pricePlanMapping.getSpPricePlanKey();
    }

    @Override
    protected String getPricePlanKeyWithMiddleDto(ProductMiddleDto productMiddleDto) {
        return productMiddleDto.getSpPricePlanId();
    }
}
