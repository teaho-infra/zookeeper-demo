package net.teaho.zookeeper.loadBalance.server;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import net.teaho.zookeeper.loadBalance.zookeeper.BalanceUpdateProvider;

/**
 *
 *
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-10
 */
public class ServerChannelHandler extends ChannelDuplexHandler {

    private BalanceUpdateProvider balanceUpdateProvider;

    public ServerChannelHandler(BalanceUpdateProvider balanceUpdateProvider) {
        this.balanceUpdateProvider = balanceUpdateProvider;

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        balanceUpdateProvider.addBalance();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        balanceUpdateProvider.reduceBalance();
        super.channelInactive(ctx);
    }
}
