package net.teaho.loadbalance.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-10
 */
@Deprecated
public class ZkProcessor {

    private ZkClient zkClient;

    private static ZkProcessor instance;

    private static final String zkServer = "192.168.63.163:2181";


    private ZkProcessor(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    public static ZkProcessor getInstance() {

        if (instance == null) {
            synchronized (ZkProcessor.class) {
                if (instance == null) {
                    ZkClient zc = new ZkClient(zkServer, 10000, 10000, new SerializableSerializer());
                    instance = new ZkProcessor(zc);
                }
            }
        }
        return instance;
    }

    public void registerNode(ServerConfig serverConfig) {

        // could add child node like this /user/u1, /user/u2
        String path = zkClient.create("/server/" + serverConfig.getName(), serverConfig, CreateMode.PERSISTENT);


    }

    public void registerTmpNode(ServerConfig serverConfig) {
        String path = zkClient.create("/server/" + serverConfig.getName(), serverConfig, CreateMode.EPHEMERAL);

    }


    public void writeData() {


    }







}
