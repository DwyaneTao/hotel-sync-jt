package com.fangcang.hotel.sync.jt.service.impl;

import com.fangcang.hotel.sync.common.api.dto.product.ProductMiddleDto;
import com.fangcang.hotel.sync.common.api.model.supply.SupplyHotel;
import com.fangcang.hotel.sync.data.annotations.AnalyzePlugin;
import com.fangcang.hotel.sync.data.service.SupplyAnalyzeService;
import com.fangcang.hotel.sync.data.service.converter.SupplyDataConverter;
import com.fangcang.hotel.sync.data.service.process.MiddleDtoProcessor;
import com.fangcang.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 描述：君亭数据解析
*/
@Service("jtAnalyzeService")
@AnalyzePlugin(supplierClass = "JT")
public class JTAnalyzeServiceImpl implements SupplyAnalyzeService {

	private Log log = LogFactory.getLog(JTAnalyzeServiceImpl.class);

    @Autowired
    private SupplyDataConverter jtlDataConverter;

    @Autowired
    private MiddleDtoProcessor jtMiddleDtoProcessor;
	
	@Override
	public void analyzeHotelBasicInfo(String data) {
		
		if (!StringUtil.isValidString(data)) 
		{
		    return;
		}
		
		try {
			/** 1.酒店数据结构转换 */
		    List<SupplyHotel> supplyHotelDtoList =  jtlDataConverter.converSupplyHotelData(data);
		    
		    /** 2.酒店数据落地到房仓 */
		    if (CollectionUtils.isNotEmpty(supplyHotelDtoList)) 
		    {
				jtMiddleDtoProcessor.processSupplyHotelDto(supplyHotelDtoList);
		    }
		    supplyHotelDtoList = null;
		    
		} catch (Exception e) 
		{
		    log.error("君亭落地酒店基础数据异常", e);
		}
	}

	@Override
	public void analyzeHotelProductInfo(String data) {
		
		if (!StringUtil.isValidString(data)) 
		{
		    return;
		}
		
		try {
		    
			/** 1.产品数据结构转换 */
		    List<ProductMiddleDto> supplyProductData = jtlDataConverter.converSupplyProductData(data);
		   
		    /** 2.产品数据落地到房仓 */
		    if (CollectionUtils.isNotEmpty(supplyProductData)) {
				jtMiddleDtoProcessor.processProductMiddleDto(supplyProductData);
		    }
		    
		} catch (Exception e) {
		    log.error("君亭落地产品数据异常", e);
		}
	}



}
