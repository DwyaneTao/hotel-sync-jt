package com.fangcang.hotel.sync.jt.constants;

import com.fangcang.hotel.sync.data.constants.Constants;
import com.fangcang.hotel.sync.data.util.SystemUtil;

public class JTRedisKey {

    /** 昱恒酒店基本信息 */
    public static final String HOTEL_BASIC_INFO_KEY = Constants.SP_Redis_Key_Prefiex + "JT_HOTEL_BASIC_INFO" + SystemUtil.get_FcSystemParam();

    public static final String PRODUCT_INFO_KEY = Constants.SP_Redis_Key_Prefiex + "JT_PRODUCT_INFO" + SystemUtil.get_FcSystemParam();

    /** 需要消费的全量酒店 **/
    public static final String JT_FULL_CONSUME_HOTEL_KEY = Constants.SP_Redis_Key_Prefiex + "JT_FULL_CONSUME_HOTEL_KEY" ;

    /**酒店-价格计划的映射*/
    public static final String JT_HOTEL_PRICEPLAN_MAPPING = Constants.SP_Redis_Key_Prefiex + "JT_HOTEL_PRICE_PLAN_MAPPING";
}
