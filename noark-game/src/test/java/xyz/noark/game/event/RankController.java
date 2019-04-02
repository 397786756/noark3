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
package xyz.noark.game.event;

import static xyz.noark.log.LogHelper.logger;

import xyz.noark.core.annotation.Controller;
import xyz.noark.core.annotation.controller.EventListener;
import xyz.noark.core.annotation.controller.ExecThreadGroup;
import xyz.noark.core.annotation.controller.Scheduled;

/**
 * 排行榜入口.
 *
 * @since 3.2.6
 * @author 小流氓(176543888@qq.com)
 */
@Controller(threadGroup = ExecThreadGroup.ModuleThreadGroup)
public class RankController {

	@EventListener
	public void handleAllianceCreateEvent(AllianceCreateEvent event) {
		logger.info("处理联盟排行榜 新加一个联盟={}", event.getAllianceName());
	}

	@EventListener
	public void handleAllianceJoinEvent(AllianceJoinEvent event) {
		logger.info("修正玩家联盟信息 playerId={}, allianceName={}", event.getPlayerId(), event.getAllianceName());
	}

	@Scheduled(cron = "0/1 * * * * *")
	public void testCronScheduled() {
		logger.debug("测试CRON表达式，testCronScheduled");
	}

	@Scheduled(initialDelay = 1000, fixedRate = 2000)
	public void testFixedScheduled() {
		logger.debug("测试定时任务，testFixedScheduled");
	}
}