/*
 * Copyright © 2018 www.noark.xyz All Rights Reserved.
 * 
 * 感谢您选择Noark框架，希望我们的努力能为您提供一个简单、易用、稳定的服务器端框架 ！
 * 除非符合Noark许可协议，否则不得使用该文件，您可以下载许可协议文件：
 * 
 * 		http://www.noark.xyz/LICENSE
 *
 * 1.未经许可，任何公司及个人不得以任何方式或理由对本框架进行修改、使用和传播;
 * 2.禁止在本项目或任何子项目的基础上发展任何派生版本、修改版本或第三方版本;
 * 3.无论你对源代码做出任何修改和改进，版权都归Noark研发团队所有，我们保留所有权利;
 * 4.凡侵犯Noark版权等知识产权的，必依法追究其法律责任，特此郑重法律声明！
 */
package xyz.noark.core.thread;

import xyz.noark.core.Modular;
import xyz.noark.core.annotation.Autowired;
import xyz.noark.core.annotation.Component;
import xyz.noark.core.annotation.Value;

/**
 * 线程模块.
 *
 * @since 3.0
 * @author 小流氓(176543888@qq.com)
 */
@Component(name = Modular.THREAD_MODULAR)
public class ThreadModular implements Modular {

	/** 处理业务逻辑的线程数量 */
	public static final String THREAD_POOL_SIZE = "thread.pool.size";
	/** 线程名称前缀 */
	public static final String THREAD_NAME_PREFIX = "thread.name.prefix";
	/** 队列超时销毁时间，单位：分钟 */
	public static final String THREAD_TASK_QUEUE_TIMEOUT = "thread.task.queue.timeout";

	/** 处理业务逻辑的线程数量 */
	@Value(ThreadModular.THREAD_POOL_SIZE)
	private int poolSize = 8;
	/** 线程名称前缀 */
	@Value(ThreadModular.THREAD_NAME_PREFIX)
	private String threadNamePrefix = "business";
	/** 队列超时销毁时间，单位：分钟 */
	@Value(ThreadModular.THREAD_NAME_PREFIX)
	private int timeout = 1;

	@Autowired
	private ThreadDispatcher threadDispatcher;

	@Override
	public void init() {
		threadDispatcher.init(poolSize, threadNamePrefix, timeout);
	}

	@Override
	public void destroy() {
		threadDispatcher.shutdown();
	}
}