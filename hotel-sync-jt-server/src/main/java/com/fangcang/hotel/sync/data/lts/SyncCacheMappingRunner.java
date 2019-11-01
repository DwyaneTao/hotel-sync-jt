package com.fangcang.hotel.sync.data.lts;

import com.fangcang.hotel.sync.data.service.CacheService;
import com.fangcang.hotel.sync.data.service.lts.Register2LTSService;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 2019-9-24 临时处理：每天定时全量同步【酒店/房型】到缓存
 *
 * @date 2019年9月24日
 */
@Service
public class SyncCacheMappingRunner implements Register2LTSService, JobRunner {
    private static final String runnerName = "SyncCacheMappingRunner";
    private static final String taskName = "SyncCacheMappingTask";
    private static final Log logger = LogFactory.getLog(SyncCacheMappingRunner.class);

    @Autowired
    private CacheService cacheService;

    @Override
    public String getJobRunnerName() {
        return runnerName;
    }

    @Override
    public String getCronExpression() {
        return "0 5 1 * * ?";
    }

    @Override
    public String getTaskId() {
        return taskName;
    }

    @Override
    public boolean isReplaceJob() {
        return false;
    }

    @Override
    public Result run(JobContext jobContext) throws Throwable {



        return null;
    }
}
