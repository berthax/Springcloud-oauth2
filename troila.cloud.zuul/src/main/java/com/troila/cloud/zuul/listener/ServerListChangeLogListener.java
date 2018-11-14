package com.troila.cloud.zuul.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListChangeListener;

public class ServerListChangeLogListener implements ServerListChangeListener{
	private static Logger log = LoggerFactory.getLogger(ServerListChangeLogListener.class);
	@Override
	public void serverListChanged(List<Server> oldList, List<Server> newList) {
		log.info("server list updated!");
		log.info("old server list:{}",oldList);
		log.info("new server list:{}",newList);
	}
}
