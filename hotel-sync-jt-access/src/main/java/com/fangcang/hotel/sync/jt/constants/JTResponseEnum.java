package com.fangcang.hotel.sync.jt.constants;

/**君亭反映编码*/
public enum JTResponseEnum {

    SUCCESS(0);

    public int key;

    private JTResponseEnum(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }


    public static JTResponseEnum getEnumByKey(int key) {
        for (JTResponseEnum responseEnum : JTResponseEnum.values()) {
            if (responseEnum.key == key) {
                return responseEnum;
            }
        }
        return null;
    }
}
