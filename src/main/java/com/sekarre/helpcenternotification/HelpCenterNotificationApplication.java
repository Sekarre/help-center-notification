package com.sekarre.helpcenternotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HelpCenterNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelpCenterNotificationApplication.class, args);
    }

}
