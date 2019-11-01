package com.fangcang.hotel.sync.jt.api.request;

public class Request<T extends BusinessRequest> {

    private Header head;

    private T data;

    public Header getHead() {
        return head;
    }

    public void setHead(Header head) {
        this.head = head;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
