package net.teaho.loadbalance.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.teaho.loadbalance.zookeeper.BalanceUpdateProvider;
import net.teaho.loadbalance.zookeeper.ServerConfig;
import net.teaho.loadbalance.zookeeper.ZkRegisterProvider;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-10
 */
public class Server {
    private static final String zkServer = "192.168.63.163:2181";

//    private void initServer(ServerConfig serverConfig) {
//        ZkProcessor.getInstance().registerNode(serverConfig);
//    }

    public void run(ServerConfig serverConfig) {

        ZkClient zk = new ZkClient(zkServer, 10000, 10000, new SerializableSerializer());

        ZkRegisterProvider zkRegisterProvider = new ZkRegisterProvider(zk, serverConfig);
        zkRegisterProvider.register();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ServerChannelHandler(new BalanceUpdateProvider(zk, serverConfig)));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(serverConfig.getPort()).sync();

            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }



    }


    public static void main(String[] args) {




    }
}
