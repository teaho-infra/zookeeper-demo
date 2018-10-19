package net.teaho.loadbalance.server;

import net.teaho.loadbalance.zookeeper.ServerConfig;

import java.util.concurrent.*;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-10
 */
public class ServerStarter {


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i=1; i <= 5; i++) {

            int finalI = i;
            executorService.submit(() -> {
                ServerConfig serverConfig = new ServerConfig();
                serverConfig.setPrefix("/server");
                serverConfig.setName("server" + finalI);
                serverConfig.setUrl("192.168.63.104");
                serverConfig.setPort(22240 + finalI);
                serverConfig.setBalance(0);

                Server server = new Server();
                server.run(serverConfig);
            });

        }

    }


}
