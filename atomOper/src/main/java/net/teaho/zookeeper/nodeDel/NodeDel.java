package net.teaho.zookeeper.nodeDel;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class NodeDel {

    public static void main(String[] args) {
        ZkClient zc = new ZkClient("192.168.63.163:2182",10000,10000,new SerializableSerializer());
        System.out.println("conneted ok!");
        
        boolean e1 = zc.delete("/user");
        System.out.println(e1);
        boolean e2 = zc.deleteRecursive("/user");
//        System.out.println(String.format("%s %s", e1, e2));
        System.out.println(e2);
    }
    
}