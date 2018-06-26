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
package com.company.test.module.delay;

import java.io.Serializable;
import java.util.Date;

import xyz.noark.core.annotation.PlayerId;
import xyz.noark.core.annotation.orm.Column;
import xyz.noark.core.annotation.orm.Entity;
import xyz.noark.core.annotation.orm.Entity.FeatchType;
import xyz.noark.core.annotation.orm.Table;
import xyz.noark.core.event.PlayerEvent;
import xyz.noark.game.event.delay.AbstractDelayEvent;

/**
 * 延迟任务.
 *
 * @since 3.0
 * @author 小流氓(176543888@qq.com)
 */
@Table(name = "building_upgrade")
@Entity(fetch = FeatchType.START)
public class PlayerBuildingUpgradeEvent extends AbstractDelayEvent implements PlayerEvent {

	@PlayerId
	@Column(name = "playerId", nullable = false, comment = "玩家ID")
	private long playerId;

	@Column(name = "appealed", nullable = false, comment = "求助过")
	private boolean appealed;

	@Column(name = "state", nullable = false, comment = "状态")
	private int state;

	@Column(name = "create_time", nullable = false, comment = "创建时间", defaultValue = "2018-01-01 00:00:00")
	private Date createTime;

	@Column(name = "modify_time", comment = "修改时间", defaultValue = "2018-01-01 00:00:00")
	private Date modifyTime;

	@Override
	public String toString() {
		return "PlayerBuildingUpgradeEvent [playerId=" + playerId + ", appealed=" + appealed + ", state=" + state + ", createTime=" + createTime + ", modifyTime=" + modifyTime + "]";
	}

	@Override
	public Serializable getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public boolean isAppealed() {
		return appealed;
	}

	public void setAppealed(boolean appealed) {
		this.appealed = appealed;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}