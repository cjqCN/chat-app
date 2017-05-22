package com.cjq.serverstart;

import com.cjq.nettyserver.NettyServer;
import com.cjq.nettyserver.impl.AbstractSever;

public class ServerStart {

	private static int port = 8888;
	private static AbstractSever server = null;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
    
		 if(args!=null && args.length>0){
			 try{
			 port = Integer.valueOf(args[0]);
			 }catch (Exception e) {
				// TODO: handle exception
				 System.err.println("¶Ë¿Ú´íÎó......");
			}
		 }
		
		 server = new NettyServer();
		 server.bind(port);
	}

}
