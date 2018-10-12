package net.teaho.zookeeper.atomOperation.nodeExists;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class NodeExists {

    public static void main(String[] args) {
        ZkClient zc = new ZkClient("192.168.63.163:2182",10000,10000,new SerializableSerializer());
        System.out.println("conneted ok!");
        
        boolean e = zc.exists("/user");
        
        System.out.println(e);
        
    }
    
}