package com.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.system.EmbeddedServerPortFileWriter;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@EnableDiscoveryClient
@EnableFeignClients
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableBinding(Sink.class)
@IntegrationComponentScan
@SpringBootApplication
public class MessageClientApplication {

    private static final String SERVICE_NAME = "message-client";

    /**
     * the service with which we're communicating
     */
        static final String MESSAGE_SERVICE_NAME = "message-service";


        @Bean
        Sampler sampler() {
            return span -> true;
        }

        @Bean
        RestTemplate restTemplate() {
            return new RestTemplate();
        }


        public static void main(String[] args) {
            new SpringApplicationBuilder(MessageClientApplication.class)
                    .listeners(
                            new ApplicationPidFileWriter(SERVICE_NAME + ".pid"),
                            new EmbeddedServerPortFileWriter(SERVICE_NAME + ".port"))
                    .run(args);
        }
    }

    @MessageEndpoint
    class MessageProcessor {

        private Log log = LogFactory.getLog(getClass());

        @ServiceActivator(inputChannel = Sink.INPUT)
        public void onMessage(String msg) {
            this.log.info("received message: '" + msg + "'.");
        }
    }

    @FeignClient(serviceId = MessageClientApplication.MESSAGE_SERVICE_NAME)
    interface RestMessageReader {

        @RequestMapping(
                method = RequestMethod.GET,
                value = "/",
                consumes = MediaType.APPLICATION_JSON_VALUE)
        Map<String, String> readMessage();
    }

    @RestController
    @RequestMapping("/message")
    class MessageClientRestController {

        private final RestTemplate restTemplate;

        private final RestMessageReader restReader;

        @Autowired
        public MessageClientRestController(RestTemplate restTemplate, RestMessageReader restReader) {
            this.restTemplate = restTemplate;
            this.restReader = restReader;
    }

    @RequestMapping("/template")
    ResponseEntity<Map<String, String>> template() {

        String url = "http://" + MessageClientApplication.MESSAGE_SERVICE_NAME;

        ParameterizedTypeReference<Map<String, String>> ptr =
                new ParameterizedTypeReference<Map<String, String>>() {
                };

        ResponseEntity<Map<String, String>> responseEntity =
                this.restTemplate.exchange(url, HttpMethod.GET, null, ptr);

        return ResponseEntity
                .ok()
                .contentType(responseEntity.getHeaders().getContentType())
                .body(responseEntity.getBody());
    }

    @RequestMapping("/feign")
    Map<String, String> feign() {
        return this.restReader.readMessage();
    }
}


