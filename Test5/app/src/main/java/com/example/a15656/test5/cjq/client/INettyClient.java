package com.example.a15656.test5.cjq.client;

public class INettyClient{
	
	protected String host;
	protected int port;
	protected boolean isConnectSuccess;
	
	public INettyClient(String host,int port){
		this.setHost(host);
		this.setPort(port);
	}
	
	public void connect()throws Exception{
		// TODO Auto-generated method stub	
	}


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
