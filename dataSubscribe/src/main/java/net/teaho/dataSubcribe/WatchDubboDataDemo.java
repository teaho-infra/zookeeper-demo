package net.teaho.dataSubcribe;


import com.google.common.base.Charsets;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class WatchDubboDataDemo {

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    private static final String VIDEO_DUBBO_PATH = "/dubbo/com.kalikali.videoservice.sdk.api.VideoServiceApi/providers";


    public static void main(String[] args) throws InterruptedException {
        ZkClient zc = new ZkClient("xxx",10000,10000,new MyZkSerializer());
        zc.subscribeChildChanges(VIDEO_DUBBO_PATH, childListener);
        System.out.println("conneted ok!");

        Stat stat = new Stat();
        String u = zc.readData(VIDEO_DUBBO_PATH, stat);
        System.out.println(u);
        System.out.println(stat);

        countDownLatch.await();
    }


    public static class MyZkSerializer implements ZkSerializer {
        /**
         * 序列化，将对象转化为字节数组
         */
        public byte[] serialize(Object obj) throws ZkMarshallingError {
            return String.valueOf(obj).getBytes(Charsets.UTF_8);
        }

        /**
         * 反序列化，将字节数组转化为UTF_8字符串
         */
        public Object deserialize(byte[] bytes) throws ZkMarshallingError {
            return new String(bytes, Charsets.UTF_8);
        }
    }

    public static final IZkChildListener childListener = (parentPath, currentChilds) -> {
        System.out.println("work server list changed, new list is ");
        System.out.println(currentChilds);


    };


}