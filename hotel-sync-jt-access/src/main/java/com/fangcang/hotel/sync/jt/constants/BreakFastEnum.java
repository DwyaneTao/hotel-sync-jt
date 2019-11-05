package com.fangcang.hotel.sync.jt.constants;

import com.fangcang.enums.BreakfastNumEnum;

public enum BreakFastEnum {

    NONE(0, "无早", BreakfastNumEnum.ZERO.key),
    ONE(1, "一份", BreakfastNumEnum.ONE.key),
    TWO(2, "两份", BreakfastNumEnum.TWO.key),
    THREE(3, "三份", BreakfastNumEnum.THREE.key),
    FOUR(4, "四份", BreakfastNumEnum.FOUR.key),
    FIVE(5, "五份", BreakfastNumEnum.FIVE.key),
    SIX(6,"六份", BreakfastNumEnum.SIX.key),
    SEVEN(7,"七份", BreakfastNumEnum.SEVEN.key),
    EIGHT(8,"八份", BreakfastNumEnum.EIGHT.key),
    NINE(9,"九份", BreakfastNumEnum.NINE.key),
    TEN(10,"十份", BreakfastNumEnum.TEN.key),
    BED(99,"床位早",BreakfastNumEnum.MINUSONE.key),
    OTHER(-1, "含早（不确定）", BreakfastNumEnum.HAVE.key);

    public Integer resultCode;
    public String message;
    public Integer fcCode;

    private BreakFastEnum(Integer resultCode, String message, Integer fcCode){
        this.resultCode = resultCode;
        this.message = message;
        this.fcCode = fcCode;
    }

    public Integer getResultCode(){
        return resultCode;
    }

    public static BreakFastEnum getEnumByKey(Integer key) {
        for (BreakFastEnum responseEnum : BreakFastEnum.values()) {
            if (responseEnum.resultCode.equals(key)) {
                return responseEnum;
            }
        }
        return NONE;
    }
}
