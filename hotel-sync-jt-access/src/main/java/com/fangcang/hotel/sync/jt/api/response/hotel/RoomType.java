package com.fangcang.hotel.sync.jt.api.response.hotel;

public class RoomType {

	/**
	 * 房类
	 */
	private String roomType;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 英文描述
	 */
	private String englishDescription;

	/**
	 * 集团编号
	 */
	private String groupCode;

	/**
	 *酒店编号
	 */
	private String hotelCode;

	/**
	 *操作员名称
	 */
	private String userName;

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEnglishDescription() {
		return englishDescription;
	}

	public void setEnglishDescription(String englishDescription) {
		this.englishDescription = englishDescription;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
