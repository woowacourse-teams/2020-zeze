package dev.minguinho.zeze.log.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.access.tomcat.LogbackValve;

@Configuration
public class TomcatConfig implements WebServerFactoryCustomizer {
    @Override
    public void customize(WebServerFactory factory) {
        TomcatServletWebServerFactory containerFactory = (TomcatServletWebServerFactory)factory;

        LogbackValve logbackValve = new LogbackValve();
        logbackValve.setFilename("src/main/resources/logback/logback-access.xml");
        containerFactory.addContextValves(logbackValve);
    }
}
