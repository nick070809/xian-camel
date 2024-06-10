package org.xian.camel.dubbo.demo.impl;

import org.apache.dubbo.rpc.RpcContext;
import org.xian.camel.dubbo.demo.client.BizService;
import org.xian.camel.dubbo.demo.client.HelloService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ipipman on 2020/12/23.
 *
 * @version V1.0
 * @Package com.ipman.dubbo.nacos.sample.impl
 * @Description: (用一句话描述该文件做什么)
 * @date 2020/12/23 6:49 下午
 */
public class BizServiceImpl implements BizService {

    @Override
    public String dealBiz(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "DEAL BIZ " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

}
