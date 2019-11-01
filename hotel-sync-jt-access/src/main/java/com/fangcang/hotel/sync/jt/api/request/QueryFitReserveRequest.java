package com.fangcang.hotel.sync.jt.api.request;

import java.math.BigDecimal;
import java.util.List;

public class QueryFitReserveRequest extends BusinessRequest{

      /**
     * 入住人
     */
    private String name;

    /**
     * 手机
     */
    private String reservePhone;

    /**
     * 预订总房数
     */
    private String roomQuantity;

    /**
     * 房类
     */
    private String roomType;

    /**
     * 门市价
     */
    private String roomRate;

    /**
     *成交价
     */
    private String actualRate;

    /**
     *会员卡号
     */
    private String cardNo;

    /**
     *会员卡类型
     */
    private String cardType;

    /**
     * 优惠券券码
     */
    private String couponsCode;

    /**
     * 优惠金额
     */
    private BigDecimal couponsAmount;

    /**
     * 房价码
     */
    private String rateCode;

    /**
     * 市场码
     */
    private String marketCode;

    /**
     *来源码
     */
    private String sourceCode;

    /**
     * 包价码
     */
    private String packages;

    /**
     * 预订类型
     */
    private String reserveType;

    /**
     *  预订来源
     */
    private String reserveSource;

    /**
     *渠道编码
     */
    private String channelCode;

    /**
     * 到日
     */
    private String arrivalDate;

    /**
     * 离日
     */
    private String departureDate;

    /**
     *保留时间
     */
    private String holdDate;

    /**
     *入住天数
     */
    private String noofDays;

    /**
     * 状态
     */
    private String remarks;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     *工号
     */
    private String salesId;

    /**
     * 订单预订总金额
     */
    private BigDecimal totalAmount;

    /**
     *  每日房价列表：一日一房价
     */
    private List<ResRoomResourcePriceInputModel> dayPriceList;

}
