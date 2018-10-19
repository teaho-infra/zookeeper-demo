package net.teaho.loadbalance.zookeeper;

import net.teaho.loadbalance.gateway.Client;
import net.teaho.loadbalance.server.Server;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  TODO it's better to update balance in gateway side program
 * 跑了几次，我认为将更新balance的代码迁移到gateway处为好，client server自己更新balance会造成大量的bad version exception
 *
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-10
 */
public class BalanceUpdateProvider {

//    private AtomicInteger atomicInteger = new AtomicInteger();
    private Logger LOG = LoggerFactory.getLogger(BalanceUpdateProvider.class);

    private ZkClient zkClient;
    private ServerConfig serverConfig;

    public BalanceUpdateProvider(ZkClient zkClient, ServerConfig serverConfig) {
        this.zkClient = zkClient;
        this.serverConfig = serverConfig;
    }


    public void addBalance() {
        Stat stat = new Stat();

        try {
            ServerConfig sc = zkClient.readData(serverConfig.getPrefix() + "/"  + serverConfig.getName(), stat);
            sc.setBalance(sc.getBalance() + 1);
            LOG.info("added balance" + sc.toString());
            zkClient.writeData(serverConfig.getPrefix() + "/"  + serverConfig.getName(), sc, stat.getVersion());
        } catch (ZkBadVersionException e) {
            // ignore
            e.printStackTrace();
            //add balance again
            addBalance();
        }
    }

    public void reduceBalance() {
        Stat stat = new Stat();
        try {
            ServerConfig sc = zkClient.readData(serverConfig.getPrefix() + "/"  + serverConfig.getName(), stat);
            if (sc.getBalance() > 0) {
                LOG.info("reducing balance" + sc.toString());
                sc.setBalance(sc.getBalance() - 1);
                // 带上版本，因为可能有其他客户端连接到服务器修改了负载
                zkClient.writeData(serverConfig.getPrefix() + "/"  + serverConfig.getName(), sc, stat.getVersion());
            }
        } catch (ZkBadVersionException e) {
            // ignore
            e.printStackTrace();
            //add balance again
            reduceBalance();
        }


    }


}
