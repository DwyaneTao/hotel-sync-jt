package com.fangcang.hotel.sync.jt.lts.jobbrunner;

import com.fangcang.hotel.sync.common.api.dto.mapping.HotelMappingDto;
import com.fangcang.hotel.sync.data.service.lts.Register2LTSService;
import com.fangcang.hotel.sync.data.util.StringUtilExtend;
import com.fangcang.hotel.sync.jt.service.JTExtendsConfigService;
import com.fangcang.hotel.sync.jt.service.impl.JTInitMappingService;
import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class InitConsumeHotelRunner implements JobRunner, Register2LTSService {

    private static final Logger logger = LoggerFactory.getLogger(InitConsumeHotelRunner.class);

    private static final String initConsumeHotelRunner = "initConsumeHotelRunner";

    private static final String initConsumeHotelTaskName = "initConsumeHotelTask";

    @Autowired
    private JTExtendsConfigService jtExtendsConfigService;


    @Autowired
    private JTInitMappingService jtInitMappingService;

    @Override
    public Result run(JobContext jobContext) throws Throwable {
        try {
            logger.info("启动初始化同步全量的酒店任务成功.");

            Set<String> supplyCodes = jtExtendsConfigService.getAllSupplyCode();
            supplyCodes.stream().forEach(supplyCode -> {
                try {
                    List<HotelMappingDto> hotelMappingDtos = jtInitMappingService.initHotelConsumeCache();
                    String result= StringUtilExtend.uniteString("SUCCESS,size:",hotelMappingDtos.size());
                    logger.info("初始化同步全量任务的酒店：", result);
                } catch (Exception e) {
                    logger.error(supplyCode + "初始化同步全量的酒店任务失败，系统异常.", e);
                }
            });
            logger.info("初始化同步全量的酒店任务执行成功.");
        } catch (Exception e) {
            logger.error("初始化同步全量的酒店任务失败，系统异常.", e);
            return new Result(Action.EXECUTE_EXCEPTION, e.getMessage());
        }
        return new Result(Action.EXECUTE_SUCCESS, "初始化同步全量的酒店任务执行成功");
    }

    @Override
    public String getJobRunnerName() {
        return initConsumeHotelRunner;
    }


    @Override
    public String getTaskId() {
        return initConsumeHotelTaskName;
    }

    @Override
    public boolean isReplaceJob() {
        return false;
    }

    @Override
    public String getCronExpression() {
        return "0 15 5 * * ?";
    }
}
