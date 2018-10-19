package net.teaho.zookeeper.loadBalance.gateway;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.teaho.zookeeper.loadBalance.server.ServerChannelHandler;
import net.teaho.zookeeper.loadBalance.zookeeper.BalanceProvider;
import net.teaho.zookeeper.loadBalance.zookeeper.BalanceUpdateProvider;
import net.teaho.zookeeper.loadBalance.zookeeper.ServerConfig;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-10
 */
public class Client {
    private static final String ZK_SERVER = "192.168.63.163:2181";

    private Channel channel;

    private EventLoopGroup workerGroup;

    private Logger LOG = LoggerFactory.getLogger(Client.class);


    public void connect() {
        ZkClient zk = new ZkClient(ZK_SERVER, 10000, 10000, new SerializableSerializer());
        //TODO could place into a job and update it by a timer
        BalanceProvider balanceProvider = new BalanceProvider(zk);
        List<ServerConfig> l = balanceProvider.loadBalanceServerConfig();
        ServerConfig config = balanceProvider.getServerConfig(l);

        workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new Handler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128);

            ChannelFuture f = b.connect(config.getUrl(), config.getPort()).syncUninterruptibly();
            channel = f.channel();
//            f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }


    }

    public void disconnect() {
        try {
            channel.close().syncUninterruptibly();
            workerGroup.shutdownGracefully();
            LOG.info("bye bye");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
