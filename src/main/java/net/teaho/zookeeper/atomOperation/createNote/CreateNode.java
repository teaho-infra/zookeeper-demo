package net.teaho.zookeeper.atomOperation.createNote;

import net.teaho.zookeeper.atomOperation.util.User;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

public class CreateNode {

    public static void main(String[] args) {
        ZkClient zc = new ZkClient("192.168.63.163:2181",10000,10000,new SerializableSerializer());
        System.out.println("conneted ok!");

        User u = new User();
        u.setId(1);
        u.setName("test");
        // could add child node like this /user/u1, /user/u2
        String path = zc.create("/user", u, CreateMode.PERSISTENT);
        System.out.println("created path:" + path);
    }
    
}