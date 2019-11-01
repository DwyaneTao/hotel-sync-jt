package com.fangcang.hotel.sync.jt.api.request;

public class Header {

    /**
     * 集团编号^酒店编号^用户账号^Authorization
     */
    private String clientId;

    /**
     * 授权密码
     */
    private String clientSecret;

    /**
     * 当前UNIX时间戳
     */
    private String timestamp;

    /**
     * 随机正整数，与 Timestamp 联合起来, 用于防止重放攻击
     */
    private String nonce;

    /**
     * 验证
     */
    private String Authorization;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }
}
