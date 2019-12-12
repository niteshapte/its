package com.discovery.interstellar.transport.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


/**
 * The Class InterstellarTransportSystemApplication.
 */
@SpringBootApplication
public class InterstellarTransportSystemApplication {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(InterstellarTransportSystemApplication.class, args);
    }

    /**
     * Rest template.
     *
     * @return the rest template
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
