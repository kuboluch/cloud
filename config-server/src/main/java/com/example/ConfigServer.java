package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.system.EmbeddedServerPortFileWriter;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigServer {

    private static final String SERVICE_NAME = "config-server";

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigServer.class)
                .listeners(
                        new ApplicationPidFileWriter(SERVICE_NAME + ".pid"),
                        new EmbeddedServerPortFileWriter(SERVICE_NAME + ".port"))
                .run(args);
    }
}
