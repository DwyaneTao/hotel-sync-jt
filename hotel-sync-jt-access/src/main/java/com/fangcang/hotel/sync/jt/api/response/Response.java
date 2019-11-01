package com.fangcang.hotel.sync.jt.api.response;

public class Response<T extends BusinessResponse> {

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

    /**
     * 总记录数
     */
    private Integer TotalCount;

    /**
     * 业务实体
     */
    private T Data;


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

    public Integer getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(Integer totalCount) {
        TotalCount = totalCount;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
