package net.teaho.zookeeper.dataSubcribe;

import net.teaho.zookeeper.atomOperation.util.User;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

public class SendCommand {

    public static void main(String[] args) {
        ZkClient zc = new ZkClient("192.168.63.163:2181",10000,10000,new SerializableSerializer());
        System.out.println("conneted ok!");

        boolean e = zc.exists("/command");
        if (e) {
            zc.writeData("/command", "modify");
        } else {
            String path = zc.create("/command", "create", CreateMode.PERSISTENT);
            System.out.println("created path:" + path);
        }

    }
    
}