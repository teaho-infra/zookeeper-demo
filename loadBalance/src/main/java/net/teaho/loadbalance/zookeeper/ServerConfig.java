package net.teaho.loadbalance.zookeeper;

import java.io.Serializable;

/**
 * 配置信息
 */
public class ServerConfig implements Serializable, Comparable<ServerConfig> {


    private static final long serialVersionUID = -4027093035363958568L;

    private String url;
    private Integer port;
    private String name;
    private int balance;
    private String prefix;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", prefix='" + prefix + '\'' +
                '}';
    }

    @Override
    public int compareTo(ServerConfig o) {
        return Integer.compare(this.getBalance(), o.getBalance());
    }
}
