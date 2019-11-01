package com.fangcang.hotel.sync.jt.service.impl;

import com.fangcang.hotel.sync.data.service.RedisService;
import com.fangcang.hotel.sync.data.service.SupplyAnalyzeService;
import com.fangcang.hotel.sync.data.service.SupplyRedisService;
import com.fangcang.hotel.sync.jt.constants.JTRedisKey;
import com.fangcang.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

@Service("jtAutoAnalyzeService")
public class JTAutoAnalyzeServiceImpl implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(JTAutoAnalyzeServiceImpl.class);
	
	@Autowired
	private TaskExecutor syncJTHotelExecutor;
	
	@Autowired
	private TaskExecutor syncJTProductExecutor;

//	@Autowired
//	private TaskExecutor syncSJLProductInrcExecutor;

	@Autowired
	private RedisService redisService;

    @Autowired
    private SupplyRedisService sjlRedisService;

	@Autowired
	private SupplyAnalyzeService sjlAnalyzeService;

	
	private Semaphore analyzeHotelSemaphore = new Semaphore(15);
	
	private Semaphore analyzeProductSemaphore = new Semaphore(30);

	private final AtomicBoolean analyzeStatus = new AtomicBoolean(false);
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		startAutoAnalyze();
	}
	
	public void startAutoAnalyze() {

		if (analyzeStatus.compareAndSet(false, true)) {
			new Thread(() -> {
				while (true) {
					try {
						analyzeProductData();
					} catch (Exception e) {
						logger.error("解析君亭产品数据失败", e);
					}
					try {
						analyzeHotelInfo();
					} catch (Exception e) {
						logger.error("解析君亭酒店基础数据失败", e);
					}
					try {
						Thread.sleep(5000);
					} catch (Exception e) {
						logger.error("自动解析君亭服务异常", e);
					}
				}
			}).start();

			logger.info("成功启动君亭数据解析任务");
		}
	}
	
	public void analyzeHotelInfo() {
		String key = JTRedisKey.HOTEL_BASIC_INFO_KEY;
		if (!redisService.exists(key)) {
			return;
		}
		while (analyzeHotelSemaphore.tryAcquire()) {
			// 多线程消费
			try {
				syncJTHotelExecutor.execute(() -> {
					try {
						while (redisService.exists(key)) {
							// 获取数据
							String data = redisService.rpop(key);
							if (StringUtil.isValidString(data)) {
								sjlAnalyzeService.analyzeHotelBasicInfo(data);
							}
						}
					} catch (Exception e) {
						logger.error("解析君亭酒店基础数据失败.", e);
					} finally {
						// 确保锁释放
						analyzeHotelSemaphore.release();
					}
				});
			} catch (Exception e) {
				// 确保锁释放
				analyzeHotelSemaphore.release();
				throw e;
			}
		}
	}

	public void analyzeProductData() {
		String key = JTRedisKey.PRODUCT_INFO_KEY;
		if (!redisService.exists(key)) {
			return;
		}
		while (analyzeProductSemaphore.tryAcquire()) {
			// 多线程消费
			try {
				syncJTProductExecutor.execute(() -> {
					try {
						while (redisService.exists(key)) {
							// 获取数据
							String data = sjlRedisService.popProductData(key);
							if (StringUtil.isValidString(data)) {
								sjlAnalyzeService.analyzeHotelProductInfo(data);
							}
						}
					} catch (Exception e) {
						logger.error("解析君亭产品数据失败.", e);
					} finally {
						// 确保锁释放
						analyzeProductSemaphore.release();
					}
				});
			} catch (Exception e) {
				// 确保锁释放
				analyzeProductSemaphore.release();
				throw e;
			}
		}
	}


}
