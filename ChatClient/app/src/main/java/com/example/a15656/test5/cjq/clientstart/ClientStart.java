package com.example.a15656.test5.cjq.clientstart;

import com.example.a15656.test5.cjq.client.TestNettyClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientStart implements Runnable {

	private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
//	private static String ip = "10.0.2.2";
	private static String ip = "10.1.1.222";
	private static int port = 8888;
    private static ClientStart  clientStart;
	private static TestNettyClient nettyClient;

	public static ClientStart getInstance(){
		if(clientStart==null)
			clientStart = new ClientStart();
		return clientStart;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new TestNettyClient(ip, port).connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startClient_1(Runnable connectRunable){
		service.scheduleAtFixedRate(connectRunable, 0, 1, TimeUnit.SECONDS);
	}

	public void startClient(){

		startClient_1(new Thread(getInstance()));
	//	new Thread(getInstance()).start();
	}

	public void run(){
		nettyClient = new TestNettyClient(ip,port);
		try {
			nettyClient.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		ClientStart.ip = ip;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		ClientStart.port = port;
	}

	public TestNettyClient getNettyClient(){return nettyClient;}
}
