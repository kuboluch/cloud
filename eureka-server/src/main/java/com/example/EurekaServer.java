package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.system.EmbeddedServerPortFileWriter;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServer {

    private static final String SERVICE_NAME = "eureka-server";

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServer.class)
                .listeners(
                        new ApplicationPidFileWriter(SERVICE_NAME + ".pid"),
                        new EmbeddedServerPortFileWriter(SERVICE_NAME + ".port"))
                .run(args);
    }
}
