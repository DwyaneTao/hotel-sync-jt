package com.fangcang.hotel.sync.jt.api.response.hotel;

import java.util.List;

public class HotelDetail {
	
	/** 酒店静态信息 **/
	private HotelInfo hotelInfo;
	/** 房型数组 **/
	private List<RoomType> roomTypeList;

	public HotelInfo getHotelInfo() {
		return hotelInfo;
	}

	public void setHotelInfo(HotelInfo hotelInfo) {
		this.hotelInfo = hotelInfo;
	}

	public List<RoomType> getRoomTypeList() {
		return roomTypeList;
	}

	public void setRoomTypeList(List<RoomType> roomTypeList) {
		this.roomTypeList = roomTypeList;
	}
}
