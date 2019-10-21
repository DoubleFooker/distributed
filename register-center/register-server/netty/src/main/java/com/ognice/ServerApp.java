package com.ognice;

import com.ognice.server.NettyServer;

/**
 * Hello world!
 *
 */
public class ServerApp
{
    public static void main( String[] args )
    {
        NettyServer nettyServer=new NettyServer(8888);
        try {
            nettyServer.start();
        } catch (Exception e) {
            System.err.println("start err!");
            e.printStackTrace();
        }

    }
}
