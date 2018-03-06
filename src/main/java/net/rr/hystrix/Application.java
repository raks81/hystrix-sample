package net.rr.hystrix;

import com.netflix.hystrix.contrib.servopublisher.HystrixServoMetricsPublisher;
import com.netflix.hystrix.strategy.HystrixPlugins;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableCircuitBreaker
@EnableHystrixDashboard
@SpringBootApplication
public class Application {


  public static void main(String[] args) {
//    HystrixPlugins.getInstance()
//        .registerMetricsPublisher(HystrixServoMetricsPublisher.getInstance());
    System.out.println("Publishing metrics.....");
    HystrixPlugins.getInstance().registerMetricsPublisher(
        HystrixServoMetricsPublisher.getInstance());
    SpringApplication.run(Application.class, args);
  }
}
