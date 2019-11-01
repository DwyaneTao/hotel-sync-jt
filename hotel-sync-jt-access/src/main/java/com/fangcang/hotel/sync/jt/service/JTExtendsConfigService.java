package com.fangcang.hotel.sync.jt.service;

import com.fangcang.hotel.sync.data.annotations.SupplyConfigPlugin;
import com.fangcang.hotel.sync.data.dto.BaseExtendConfig;
import com.fangcang.hotel.sync.data.service.SupplyConfigBaseService;
import com.fangcang.hotel.sync.data.service.SupplyOrderService;
import com.fangcang.hotel.sync.jt.config.JTConfig;
import com.fangcang.hotel.sync.jt.config.JTExtendConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jtExtendsConfigService")
@SupplyConfigPlugin(supplierClass = "JT")
public class JTExtendsConfigService extends SupplyConfigBaseService {

    @Autowired
    private SupplyOrderService jtOrderService;

    @Override
    public BaseExtendConfig createExtendConfig() {
        return new JTExtendConfig();
    }

    @Override
    public SupplyOrderService setOrderService() {
        return jtOrderService;
    }

    @Override
    public void setSpecialField(String fieldName, String fieldValue, BaseExtendConfig extendsConfig) {
        JTExtendConfig jtExtendConfig = (JTExtendConfig) extendsConfig;
        if ("secretKey".equals(fieldName)) {
            jtExtendConfig.setSecretKey(fieldValue);
        } else if ("appKey".equals(fieldName)) {
            jtExtendConfig.setAppKey(fieldValue);
        } else if ("contactName".equals(fieldName)) {
            jtExtendConfig.setContactName(fieldValue);
        } else if ("contactPhone".equals(fieldName)) {
            jtExtendConfig.setContactPhone(fieldValue);
        } else if ("email".equals(fieldName)) {
            jtExtendConfig.setEmail(fieldValue);
        }
    }
}
