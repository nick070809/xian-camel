package org.xian.camel.dubbo.client;

import com.alibaba.fastjson.JSON;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.Map;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/9 23:47
 */
public class DubboClientProducer extends DefaultProducer {
    private DubboClientEndpoint endpoint;
    GenericService genericService ;

    public DubboClientProducer(DubboClientEndpoint endpoint,GenericService genericService) {
        super(endpoint);
        this.endpoint = endpoint;
        this.genericService = genericService;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("DubboClientProducer process");
        Map<String, Object> parameters = endpoint.getParameters() ;
        String method = (String) parameters.get("method");

        String[] parameterTypes = ((String) parameters.get("parameterTypes")).split(",");

        Object[] args = ((String) parameters.get("args")).split(",");

        Object response =  genericService.$invoke(method, parameterTypes, args);

        log.info("DubboClientProducer process response: " + JSON.toJSONString(response) );
        // 将处理结果设置到消息中
        exchange.getIn().setBody(response);
    }
}
