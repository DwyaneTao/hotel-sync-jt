package com.fangcang.hotel.sync.jt.constants;

import com.fangcang.hotel.sync.order.constants.SupplyResultEnum;

public enum OrderResponseEnum {

    WILLSURE("1", "待确认", SupplyResultEnum.NEED_CONFIRM.result),
    BESURE("2", "已确认", SupplyResultEnum.CONFIRM.result),
    REFUSE("3", "已拒单", SupplyResultEnum.NO_CONFIRM.result),
    CANCEL("4", "已取消", SupplyResultEnum.CANCEL.result);

    public String key;
    public String remark;
    public Integer value;

    private OrderResponseEnum(String key, String remark, Integer value) {
        this.key = key;
        this.remark = remark;
        this.value = value;
    }

    public String getKey() {
        return key;
    }


    public static OrderResponseEnum getEnumByKey(String key) {
        for (OrderResponseEnum responseEnum : OrderResponseEnum.values()) {
            if (responseEnum.key.equals(key)) {
                return responseEnum;
            }
        }
        return null;
    }
}
