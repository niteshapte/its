package com.discovery.interstellar.transport.system.configuration;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * The Class WebServiceBean.
 */
@EnableWs
@Configuration
public class WebServiceBean extends WsConfigurerAdapter {
    
    /**
     * Message dispatcher servlet.
     *
     * @param applicationContext the application context
     * @return the servlet registration bean
     */
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    /**
     * Default wsdl 11 definition.
     *
     * @param interstellarSchema the interstellar schema
     * @return the default wsdl 11 definition
     */
    @Bean(name = "interstellar")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema interstellarSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("InterstellarPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        wsdl11Definition.setSchema(interstellarSchema);
        return wsdl11Definition;
    }

    /**
     * Interstellar schema.
     *
     * @return the xsd schema
     */
    @Bean
    public XsdSchema interstellarSchema() {
        return new SimpleXsdSchema(new ClassPathResource("interstellar.xsd"));
    }
}