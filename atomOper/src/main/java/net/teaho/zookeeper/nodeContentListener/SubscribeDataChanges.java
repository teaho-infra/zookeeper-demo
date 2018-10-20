package net.teaho.zookeeper.nodeContentListener;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class SubscribeDataChanges {
    
    private static class ZkDataListener implements IZkDataListener {

        @Override
        public void handleDataChange(String dataPath, Object data)
                throws Exception {
            System.out.println(dataPath+":"+data.toString());
        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {
            System.out.println(dataPath);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ZkClient zc = new ZkClient("192.168.63.163:2182",10000,10000,new SerializableSerializer());
        System.out.println("conneted ok!");
        
        zc.subscribeDataChanges("/user", new ZkDataListener());
        Thread.sleep(Integer.MAX_VALUE);

    }
    
}