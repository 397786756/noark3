/*
 * Copyright © 2018 www.noark.xyz All Rights Reserved.
 *
 * 感谢您选择Noark框架，希望我们的努力能为您提供一个简单、易用、稳定的服务器端框架 ！
 * 除非符合Noark许可协议，否则不得使用该文件，您可以下载许可协议文件：
 *
 *        http://www.noark.xyz/LICENSE
 *
 * 1.未经许可，任何公司及个人不得以任何方式或理由对本框架进行修改、使用和传播;
 * 2.禁止在本项目或任何子项目的基础上发展任何派生版本、修改版本或第三方版本;
 * 3.无论你对源代码做出任何修改和改进，版权都归Noark研发团队所有，我们保留所有权利;
 * 4.凡侵犯Noark版权等知识产权的，必依法追究其法律责任，特此郑重法律声明！
 */
package xyz.noark.core.ioc.manager;

import xyz.noark.core.ioc.wrap.method.ScheduledMethodWrapper;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 延迟任务管理类.
 *
 * @author 小流氓[176543888@qq.com]
 * @since 3.2.6
 */
public class ScheduledMethodManager {
    private static final ScheduledMethodManager INSTANCE = new ScheduledMethodManager();
    private final Map<Long, ScheduledMethodWrapper> handlers = new ConcurrentHashMap<>(128);

    private ScheduledMethodManager() {
    }

    public static ScheduledMethodManager getInstance() {
        return INSTANCE;
    }

    public Collection<ScheduledMethodWrapper> getHandlers() {
        return handlers.values();
    }

    public ScheduledMethodWrapper getHandler(Long id) {
        return handlers.get(id);
    }

    /**
     * 注册延迟任务处理方法.
     *
     * @param scheduledWrapper 延迟任务处理方法包装对象
     */
    public void resetScheduledHandler(ScheduledMethodWrapper scheduledWrapper) {
        handlers.put(scheduledWrapper.getId(), scheduledWrapper);
    }
}