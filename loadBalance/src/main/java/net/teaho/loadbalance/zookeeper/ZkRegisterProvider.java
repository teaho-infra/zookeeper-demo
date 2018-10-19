package net.teaho.loadbalance.zookeeper;

import net.teaho.loadbalance.server.Server;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-10
 */
public class ZkRegisterProvider {

    private ZkClient zkClient;
    private ServerConfig serverConfig;

    private static final Logger LOG = Logger.getLogger(ZkRegisterProvider.class);

    public ZkRegisterProvider(ZkClient zkClient, ServerConfig serverConfig) {
        this.zkClient = zkClient;
        this.serverConfig = serverConfig;
    }

    public void register() {
        try {
            String path = zkClient.create(serverConfig.getPrefix() + "/" + serverConfig.getName(), serverConfig, CreateMode.EPHEMERAL);
            LOG.info("path: " + path);
        } catch (Exception e) {
//            e.printStackTrace();
            zkClient.createPersistent(serverConfig.getPrefix(), true);
            register();
        }

    }


}
