package kopo.poly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PolyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolyApplication.class, args);
    }

}
