package com.fangcang.hotel.sync.jt.dto;

import com.fangcang.hotel.sync.data.util.StringUtilExtend;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SyncSupplyDataRequest {

    private String supplyClass;

    private String supplyCode;

    /**酒店ID，以英文逗号分隔*/
    private String spHotelIds;

    private Set<String> spHotelIdSet =new HashSet<String>();

    private Date startDate;

    private Date endDate;

    /**同步数据类型 1同步房价  2同步房量  3同步房价和房量*/
    private Integer syncDataType;

    /**是否新增价格计划*/
    private boolean isCreatePricePlan=false;

    /**创建人*/
    private String creater;

    /*供应商产品ID，以英文逗号分隔*/
    private String spProductIds;

    public String getSupplyClass() {
        return supplyClass;
    }

    public void setSupplyClass(String supplyClass) {
        this.supplyClass = supplyClass;
    }

    public String getSupplyCode() {
        return supplyCode;
    }

    public void setSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode;
    }

    public String getSpHotelIds() {
        return spHotelIds;
    }

    public void setSpHotelIds(String spHotelIds) {
        this.spHotelIds = spHotelIds;
    }

    public Set<String> getSpHotelIdSet() {
        try{
            if(StringUtilExtend.isValidString(spHotelIds)){
                String[] ids= spHotelIds.split(",");
                for (String temp:ids){
                    spHotelIdSet.add(temp);
                }
            }
        }catch (Exception e){

        }
        return spHotelIdSet;

    }

    public void setSpHotelIdSet(Set<String> spHotelIdSet) {
        this.spHotelIdSet = spHotelIdSet;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<String> splitCoHotelIds(){
        Set<String> set=new HashSet<String>();
        if (StringUtilExtend.isValidString(spHotelIds)){
            String[] ids= spHotelIds.split(",");
            if (ids.length>0){
                for (String id:ids){
                    set.add(id);
                }
            }
        }
        return set;
    }

    public Integer getSyncDataType() {
        return syncDataType;
    }

    public void setSyncDataType(Integer syncDataType) {
        this.syncDataType = syncDataType;
    }

    public boolean isCreatePricePlan() {
        return isCreatePricePlan;
    }

    public void setIsCreatePricePlan(boolean isCreatePricePlan) {
        this.isCreatePricePlan = isCreatePricePlan;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getSpProductIds() {
        return spProductIds;
    }

    public void setSpProductIds(String spProductIds) {
        this.spProductIds = spProductIds;
    }

    public Set<String> splitCoProductIds(){
        Set<String> productIdSet=new HashSet<String>();
        if (StringUtilExtend.isValidString(spProductIds)){
            String[] ids= spProductIds.split(",");
            if (ids.length>0){
                for (String id:ids){
                    productIdSet.add(id);
                }
            }
        }
        return productIdSet;
    }

}
