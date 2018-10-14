package net.teaho.zookeeper.dataSubcribe;

import net.teaho.zookeeper.atomOperation.util.User;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.data.Stat;

public class GetCommand {

    public static void main(String[] args) {
        ZkClient zc = new ZkClient("192.168.63.163:2182",10000,10000,new SerializableSerializer());
        System.out.println("conneted ok!");
        
        Stat stat = new Stat();
        String u = zc.readData("/command",stat);
        System.out.println(u);
        System.out.println(stat);
        
    }
    
}