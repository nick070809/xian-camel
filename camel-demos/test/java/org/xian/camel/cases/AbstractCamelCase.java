package org.xian.camel.cases;

import cn.hutool.core.lang.UUID;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.junit.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xian.camel.cases.dto.CamelUser;
import org.xian.camel.cases.beans.Chap3_DataTransferCases;
import org.xian.util.generate.BeanGenerate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 15:33
 */
public abstract class AbstractCamelCase {

    public Logger logger = LoggerFactory.getLogger(Chap3_DataTransferCases.class);
    public CamelContext camelContext;
    public ProducerTemplate template;

    
    public abstract void createCamelContext() throws Exception;

    @After
    public void stopCamelContext() throws Exception {
        camelContext.stop();
    }

    public void execute(Map<String, Object> headers) throws Exception {
        template.sendBodyAndHeaders("direct:start", UUID.fastUUID().toString(), headers);
    }

    public void execute() throws Exception {
        template.sendBody("direct:start", UUID.fastUUID().toString());
    }

    public Object executeAndResponse() throws Exception {
       return template.requestBody("direct:start", UUID.fastUUID().toString());
    }
    public <T> T executeAndResponse(Class<T> type) throws Exception {
        return template.requestBody("direct:start", UUID.fastUUID().toString(),type);
    }


    public void execute(Object body) throws Exception {
        template.sendBody("direct:start", body);
    }

    public Map<String, Object> generateOneMap() {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("param1", "value1");
        headers.put("param2", "value2");
        return headers;
    }

    public List<Map<String, Object>> generateMapList() {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("param1", "value1");
        headers.put("param2", "value2");
        headers.put("param3", "value3");
        return new ArrayList<Map<String, Object>>(){{
            add(headers);
        }};
    }


    public CamelUser generateOneUser() {
        return BeanGenerate.generateObj(CamelUser.class);
    }
    public List  generateUserList() {
        return BeanGenerate.generateObjs(CamelUser.class);
    }

}
