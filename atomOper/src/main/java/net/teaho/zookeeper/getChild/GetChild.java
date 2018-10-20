package net.teaho.zookeeper.getChild;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

public class GetChild {

    public static void main(String[] args) {
        ZkClient zc = new ZkClient("192.168.63.163:2182",10000,10000,new SerializableSerializer());
        System.out.println("conneted ok!");
        
        List<String> cList = zc.getChildren("/user");
        
        System.out.println(cList.toString());
        
    }
    
}