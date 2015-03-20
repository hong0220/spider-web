package com.webcollector.net;

import com.webcollector.net.HttpResponse;
import com.webcollector.proxy.Proxys;

public interface HttpRequester {
	public HttpResponse getResponse(String url) throws Exception;

	public Proxys getProxys();

	public void setProxys(Proxys proxys);
}
