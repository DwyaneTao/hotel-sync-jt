package com.fangcang.hotel.sync.jt.api.response;

import com.fangcang.hotel.sync.jt.api.response.hotel.HotelDetail;

import java.util.List;

public class QueryHotelDetailResponse extends BusinessResponse {
	
	private List<HotelDetail> hotelDetailList;


	/**
	 * 是否成功
	 */
	private Boolean Success;

	/**
	 * 代码
	 */
	private Integer Code;

	/**
	 * 消息
	 */
	private String Message;

	public List<HotelDetail> getHotelDetailList() {
		return hotelDetailList;
	}

	public void setHotelDetailList(List<HotelDetail> hotelDetailList) {
		this.hotelDetailList = hotelDetailList;
	}

	public Boolean getSuccess() {
		return Success;
	}

	public void setSuccess(Boolean success) {
		Success = success;
	}

	public Integer getCode() {
		return Code;
	}

	public void setCode(Integer code) {
		Code = code;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}
}
