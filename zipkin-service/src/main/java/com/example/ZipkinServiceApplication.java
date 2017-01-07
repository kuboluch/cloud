package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.system.EmbeddedServerPortFileWriter;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;
import org.springframework.context.annotation.Bean;

@EnableZipkinStreamServer
@EnableDiscoveryClient
@SpringBootApplication
public class ZipkinServiceApplication {

    private static final String SERVICE_NAME = "zipkin-service";

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZipkinServiceApplication.class)
                .listeners(
                        new ApplicationPidFileWriter(SERVICE_NAME + ".pid"),
                        new EmbeddedServerPortFileWriter(SERVICE_NAME + ".port"))
                .run(args);
    }
}
