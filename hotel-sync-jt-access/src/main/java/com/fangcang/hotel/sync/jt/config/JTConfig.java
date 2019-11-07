package com.fangcang.hotel.sync.jt.config;


import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

@DisconfFile(filename = "jt.properties")
public class JTConfig {

    private static String url;

    private static String contenttype;

    private static String nonce;

    private static String clientId;

    private static String clientSecret;

    private static String authorization;

    private static String key;

    private static String groupCode;

    private static String channelCode;

    private static String rateCode;

    @DisconfFileItem(name = "jt.rateCode")
    public static String getRateCode() {
        return rateCode;
    }

    public static void setRateCode(String rateCode) {
        JTConfig.rateCode = rateCode;
    }

    @DisconfFileItem(name = "jt.channelCode")
    public static String getChannelCode() {
        return channelCode;
    }

    public static void setChannelCode(String channelCode) {
        JTConfig.channelCode = channelCode;
    }

    @DisconfFileItem(name = "jt.url")
    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        JTConfig.url = url;
    }

    @DisconfFileItem(name = "jt.contenttype")
    public static String getContenttype() {
        return contenttype;
    }

    public static void setContenttype(String contenttype) {
        JTConfig.contenttype = contenttype;
    }

    @DisconfFileItem(name = "jt.nonce")
    public static String getNonce() {
        return nonce;
    }

    public static void setNonce(String nonce) {
        JTConfig.nonce = nonce;
    }

    @DisconfFileItem(name = "jt.clientid")
    public static String getClientId() {
        return clientId;
    }

    public static void setClientId(String clientId) {
        JTConfig.clientId = clientId;
    }

    @DisconfFileItem(name = "jt.clientSecret")
    public static String getClientSecret() {
        return clientSecret;
    }

    public static void setClientSecret(String clientSecret) {
        JTConfig.clientSecret = clientSecret;
    }

    @DisconfFileItem(name = "jt.authorization")
    public static String getAuthorization() {
        return authorization;
    }

    public static void setAuthorization(String authorization) {
        JTConfig.authorization = authorization;
    }

    @DisconfFileItem(name = "jt.key")
    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        JTConfig.key = key;
    }

    @DisconfFileItem(name = "jt.groupcode")
    public static String getGroupCode() {
        return groupCode;
    }

    public static void setGroupCode(String groupCode) {
        JTConfig.groupCode = groupCode;
    }
}
