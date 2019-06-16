package net.teaho.dataSubcribe;


import com.google.common.base.Charsets;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.data.Stat;

public class GetCommand {

    public static void main(String[] args) {
        ZkClient zc = new ZkClient("39.108.53.140:8181",10000,10000,new MyZkSerializer());
        System.out.println("conneted ok!");
        
        Stat stat = new Stat();
        String u = zc.readData("/dubbo/com.kalikali.videoservice.sdk.api.VideoServiceApi/providers",stat);
        System.out.println(u);
        System.out.println(stat);
        
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


}