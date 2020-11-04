package io.github.kimmking.gateway.outbound.httpclient;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class LiShiYanHttpOutboundHandler {
    private final OkHttpClient httpClient;
    private final String outboundUrl;

    public LiShiYanHttpOutboundHandler(String outboundUrl) {
        this.outboundUrl = outboundUrl;
        httpClient = new OkHttpClient();
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        final String url = this.outboundUrl + fullRequest.uri();
        get(url, fullRequest, ctx);
    }

    public void get(String url, final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        System.out.println("url: "+url);
        System.out.println("outboundUrl: "+outboundUrl);

        // 创建 HttpGet 请求
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection", "keep-alive") // 设置长连接
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N)" +
                        " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Mobile Safari/537.36") // 设置代理（模拟浏览器版本）
                .build();

        try {
            Response response = httpClient.newCall(request).execute();
            handleResponse(fullRequest, ctx, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final Response endpointResponse) {
        FullHttpResponse response = null;
        try {
            byte[] body = endpointResponse.body().bytes();

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            //response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));
            response.headers().setInt("Content-Length", body.length);
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }
}
