package net.teaho.zookeeper.loadBalance.zookeeper;

import net.teaho.zookeeper.loadBalance.server.Server;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-10
 */
public class BalanceProvider {


    private ZkClient zkClient;
    private final String serverPath = "/server";

    public BalanceProvider(ZkClient zkClient) {
        this.zkClient = zkClient;
    }


    public List<ServerConfig> loadBalanceServerConfig() {
        List<ServerConfig> l = new ArrayList<>();

        List<String> serverNames = zkClient.getChildren(serverPath);

        serverNames.forEach(s -> {

            ServerConfig serverConfig = zkClient.readData(serverPath + "/" + s);
            l.add(serverConfig);
        });

        return l;
    }

    public ServerConfig getServerConfig(List<ServerConfig> list) {
        Collections.sort(list);
        return list.get(0);
    }



}
