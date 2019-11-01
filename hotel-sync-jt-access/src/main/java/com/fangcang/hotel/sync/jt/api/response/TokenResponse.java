package com.fangcang.hotel.sync.jt.api.response;

public class TokenResponse extends  BusinessResponse{

    /**
     * token
     */
    private String accessToken;

    /**
     * 类型
     */
    private String tokenType;

    /**
     * 有效时间
     */
    private  String expiresIn;

    private String  userId;

    private String customerid;

    private String serialno;

    private String groupcode;

    private String hotelcode;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getHotelcode() {
        return hotelcode;
    }

    public void setHotelcode(String hotelcode) {
        this.hotelcode = hotelcode;
    }
}
