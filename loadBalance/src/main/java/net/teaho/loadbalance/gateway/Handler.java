package net.teaho.loadbalance.gateway;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-10
 */
public class Handler extends ChannelInboundHandlerAdapter {
    private Logger LOG = LoggerFactory.getLogger(Handler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        LOG.info("connect successfully!!");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("client error!! ", cause);
        super.exceptionCaught(ctx, cause);
    }
}
