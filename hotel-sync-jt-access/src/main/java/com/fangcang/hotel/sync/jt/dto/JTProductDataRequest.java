package com.fangcang.hotel.sync.jt.dto;

import com.alibaba.fastjson.JSON;
import com.fangcang.hotel.sync.data.dto.BaseSyncRequest;
import com.fangcang.hotel.sync.jt.constants.JTRedisKey;

import java.util.List;

/**
 *  报价查询和增量查询请求参数
 * */
public class JTProductDataRequest extends BaseSyncRequest{

	private String requestJson;

	private List<String> spHotelIds;

	private List<String> spRateIds;

	/**增量类型*/
	private Integer type;

	/**增量开始时间,与入住日期不同*/
	private String beginDate;

	/**
	 * 是增量还是全量
	 * 1为全量，2为增量
	 */
	private Integer fullOrIncr;

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public String toJsonString() {
		return JSON.toJSONString(this);
	}

	@Override
	public String obtainRedisKey() {
		return JTRedisKey.PRODUCT_INFO_KEY;
	}

	@Override
	public String obtainSyncHotelIds() {
		return null;
	}

	@Override
	public String obtainExceptionRedisKey() {
		return null;
	}

	public List<String> getSpHotelIds() {
		return spHotelIds;
	}

	public void setSpHotelIds(List<String> spHotelIds) {
		this.spHotelIds = spHotelIds;
	}

	public List<String> getSpRateIds() {
		return spRateIds;
	}

	public void setSpRateIds(List<String> spRateIds) {
		this.spRateIds = spRateIds;
	}

	public String getRequestJson() {
		return requestJson;
	}

	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}


	public Integer getFullOrIncr() {
		return fullOrIncr;
	}

	public void setFullOrIncr(Integer fullOrIncr) {
		this.fullOrIncr = fullOrIncr;
	}
}
