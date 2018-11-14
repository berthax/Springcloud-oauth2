package com.troila.cloud.zuul.rule;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.zuul.context.RequestContext;

public class UploadRule extends AbstractLoadBalancerRule {
	private AtomicInteger nextServerCyclicCounter;
	private static Logger log = LoggerFactory.getLogger(UploadRule.class);
//	private ServerListChangeLogListener serverListChangeListener = new ServerListChangeLogListener();;
//	private boolean isAddListener = false;
	public UploadRule() {
		nextServerCyclicCounter = new AtomicInteger(0);		
	}

	public UploadRule(ILoadBalancer lb) {
		this();
		setLoadBalancer(lb);
	}

	public Server choose(ILoadBalancer lb, Object key) {
		if (lb == null) {
			log.warn("no load balancer");
			return null;
		}
	/*	if(!isAddListener) {
			if(lb instanceof ZoneAwareLoadBalancer) {
				((ZoneAwareLoadBalancer<?>) lb).addServerListChangeListener(serverListChangeListener);
				isAddListener = true;
			}
		}*/
		Server server = null;
		int count = 0;
		while (server == null && count++ < 10) {
			List<Server> reachableServers = lb.getReachableServers();
			List<Server> allServers = lb.getAllServers();
			allServers = allServers.stream().sorted(Comparator.comparing(Server::getHost)).collect(Collectors.toList());
			int upCount = reachableServers.size();
			int serverCount = allServers.size();
			RequestContext context = RequestContext.getCurrentContext();
			String reqUri = context.getRequest().getRequestURI();
			if (!context.getRequest().getMethod().equals("OPTIONS") && reqUri != null
					&& (reqUri.endsWith("/fileservice/file") || reqUri.endsWith("/fileservice/file/prepare"))) {
				String accessKey = context.getRequest().getHeader("access_key");
				if (accessKey != null && accessKey.length() > 0) {
					int code = accessKey.charAt(0);
					int c = lb.getAllServers().size();
					int index = code % c;
					server = allServers.get(index);
					log.info("服务器表标识符=>{},服务器名称:{}", code, server.getHost());
				}
			} else {
				if ((upCount == 0) || (serverCount == 0)) {
					log.warn("No up servers available from load balancer: " + lb);
					return null;
				}

				int nextServerIndex = incrementAndGetModulo(serverCount);
				server = allServers.get(nextServerIndex);
			}
			if (server == null) {
				/* Transient. */
				Thread.yield();
				continue;
			}

			if (server.isAlive() && (server.isReadyToServe())) {
				return (server);
			}
			// Next.
			server = null;

		}

		if (count >= 10) {
			log.warn("No available alive servers after 10 tries from load balancer: " + lb);
		}
		return server;
	}

	/**
	 * Inspired by the implementation of {@link AtomicInteger#incrementAndGet()}.
	 *
	 * @param modulo
	 *            The modulo to bound the value of the counter.
	 * @return The next value.
	 */
	private int incrementAndGetModulo(int modulo) {
		for (;;) {
			int current = nextServerCyclicCounter.get();
			int next = (current + 1) % modulo;
			if (nextServerCyclicCounter.compareAndSet(current, next))
				return next;
		}
	}

	@Override
	public Server choose(Object key) {
		return choose(getLoadBalancer(), key);
	}

	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig) {
	}

}
