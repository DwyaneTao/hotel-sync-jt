package com.fangcang.hotel.sync.jt.aop;

import com.alibaba.fastjson.JSONObject;
import com.fangcang.hotel.sync.data.aop.BaseResponseAspect;
import com.fangcang.hotel.sync.data.dto.BaseSyncInRedisDto;
import com.fangcang.hotel.sync.data.dto.BaseSyncRequest;
import com.fangcang.hotel.sync.data.service.SupplyRedisService;
import com.fangcang.util.StringUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JTResponseAspect extends BaseResponseAspect {

    private static Logger log = LoggerFactory.getLogger(JTResponseAspect.class);

    @Autowired
    private SupplyRedisService spRedisService;

    @Override
    @Pointcut("execution(public * com.fangcang.hotel.sync.jt.service.impl.JTSyncServiceImpl.*(..))")
    public void pointcutFetch() {

    }



    @Override
    protected void lpushDataInQueue(String key, String value) {
        if (StringUtil.isValidString(value)) {
            spRedisService.pushPorductData(key, value);
        }
    }

    @Override
    protected long getVersion() {
        return 0;
    }

    @Override
    public void saveLogDataInElk(BaseSyncInRedisDto baseSyncInRedisDto, BaseSyncRequest baseSyncRequest) {
        try {
            String reqStr = baseSyncInRedisDto.getRequestString();
            if (StringUtil.isValidString(reqStr)) {
                JSONObject jsonObj = JSONObject.parseObject(reqStr);
                if (jsonObj.containsKey("requestJson")) {
                    baseSyncInRedisDto.setRequestString(jsonObj.getString("requestJson"));
                }
            }
            super.saveLogDataInElk(baseSyncInRedisDto, baseSyncRequest);
            baseSyncInRedisDto.setRequestString(reqStr);
        } catch (Exception e) {
            log.error("save logdata in elk error." + e);
        }
    }
}
