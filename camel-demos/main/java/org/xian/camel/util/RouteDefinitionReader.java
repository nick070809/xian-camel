package org.xian.camel.util;

import org.apache.camel.CamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.RoutesDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.net.JarURLConnection;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 18:33
 */
public class RouteDefinitionReader {
    static Logger logger = LoggerFactory.getLogger(RouteDefinitionReader.class);


    public static void loadRouteDefinitionFromResourcePath(CamelContext camelContext , String resourcePath) throws Exception {

        List<RouteDefinition> routeDefinitions = new ArrayList<>();

        ModelCamelContext modelCamelContext =  camelContext.adapt(ModelCamelContext.class);
        // 使用当前类的 ClassLoader 来获取资源
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(resourcePath);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            // 通过判断协议是不是jar文件
            if (url.getProtocol().equals("jar")) {
                logger.info("read config file from jar ");
                JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                JarFile jarFile = urlConnection.getJarFile();
                Enumeration<JarEntry> entries = jarFile.entries(); // 返回jar中所有的文件目录
                while (entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    if (!jarEntry.isDirectory() && jarEntry.getName().startsWith(resourcePath)) {  // 是我们需要的文件类型
                        String name = jarEntry.getName();
                        InputStream resourceAsStream = classLoader.getResourceAsStream(name);
                        RoutesDefinition xmlDefinition = modelCamelContext.loadRoutesDefinition(resourceAsStream);
                        routeDefinitions.addAll( xmlDefinition.getRoutes());
                    }
                }
            } else if (url.getProtocol().equals("file")) {
                logger.info("read config file from classpath ");
                // 获取class 根目录
                URL resource = classLoader.getResource(resourcePath);

                // 获取my/file 下的所有文件
                File[] files = new File(resource.getPath()).listFiles();
                for (File file : files) {
                    InputStream inputStream = new FileInputStream(file);
                    RoutesDefinition xmlDefinition = modelCamelContext.loadRoutesDefinition(inputStream);
                    routeDefinitions.addAll( xmlDefinition.getRoutes());
                }

            }
        }
        modelCamelContext.addRouteDefinitions(routeDefinitions);

    }
}
