/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2012. 4. 8.
 * =========================================================
 * Copyright (c) 2012 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.server.service.factory;

import net.smartworks.server.engine.basicwork.notice.manager.INoticeManager;
import net.smartworks.server.engine.factory.SwManagerFactory;
import net.smartworks.server.service.ICommunityService;
import net.smartworks.server.service.IInstanceService;
import net.smartworks.server.service.INoticeService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SwServiceFactory {

	public SwServiceFactory() {
		super();
		if (logger.isInfoEnabled())
			logger.info(this.getClass().getName() + " created");
	}

	private static SwServiceFactory factory;
	public synchronized static SwServiceFactory createInstance() {
		if(factory == null) 
			factory = new SwServiceFactory();
		return factory;
	}

	public static SwServiceFactory getInstance() {
		return factory;
	}
	protected final Log logger = LogFactory.getLog(getClass());
	
	private IInstanceService instanceService;
	private ICommunityService communityService;
	private INoticeService noticeService;

	public ICommunityService getCommunityService() {
		return communityService;
	}
	public void setCommunityService(ICommunityService communityService) {
		this.communityService = communityService;
	}
	public IInstanceService getInstanceService() {
		return instanceService;
	}
	public void setInstanceService(IInstanceService instanceService) {
		this.instanceService = instanceService;
	}
	public INoticeService getNoticeService() {
		return noticeService;
	}
	public void setNoticeService(INoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
}
