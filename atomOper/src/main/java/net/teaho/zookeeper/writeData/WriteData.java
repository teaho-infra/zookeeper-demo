package net.teaho.zookeeper.writeData;

import net.teaho.zookeeper.util.User;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class WriteData {

    public static void main(String[] args) {
        ZkClient zc = new ZkClient("192.168.63.163:2182",10000,10000,new SerializableSerializer());
        System.out.println("conneted ok!");
        
        
        User u = new User();
        u.setId(5);
        u.setName("test5");
        zc.writeData("/user", u, 5);
        
    }
    
}