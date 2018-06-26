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
package com.company.test.module.building;

import static xyz.noark.log.LogHelper.logger;

import com.company.test.module.delay.PlayerBuildingUpgradeEvent;
import com.company.test.module.delay.DelayTaskRepository;

import xyz.noark.core.annotation.Autowired;
import xyz.noark.core.annotation.Controller;
import xyz.noark.core.annotation.PlayerId;
import xyz.noark.core.annotation.controller.EventListener;
import xyz.noark.core.annotation.controller.ExecThreadGroup;
import xyz.noark.core.annotation.controller.PacketMapping;
import xyz.noark.game.event.EventManager;

/**
 * 
 *
 * @since 3.0
 * @author 小流氓(176543888@qq.com)
 */
@Controller(threadGroup = ExecThreadGroup.PlayerThreadGroup)
public class BuildingControlle {
	@Autowired
	private DelayTaskRepository delayTaskRepository;
	@Autowired
	private EventManager eventManager;

	@PacketMapping(opcode = 103)
	public void upgrade(@PlayerId long playerId, long buildId) {

		// 扣钱，扣资源

		// 可以升级了

		long count = delayTaskRepository.cacheCount(playerId, v -> true);

		// eventManager.publish(event);
	}

	/**
	 * 升级结束了.
	 */
	@EventListener(PlayerBuildingUpgradeEvent.class)
	public void upgradeOver(PlayerBuildingUpgradeEvent event) {
		logger.info("11111111111,{}", event);
		delayTaskRepository.cacheDelete(event);
	}

	/**
	 * 加速建造.
	 */
	public void accelerated(@PlayerId long playerId, long taskId) {
		PlayerBuildingUpgradeEvent task = delayTaskRepository.cacheGet(playerId, taskId);

		// // 队列中移除
		// boolean flag = EventBus.cancelEvent();
		//
		// // 取消失败
		// if(!flag) {
		//
		// }
		//
		// // 修正时间，再ADD进去
		// delayTaskRepository.cache
		//

	}

}