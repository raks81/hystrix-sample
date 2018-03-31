package net.rr.hystrix.pres;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


public class CustomCommandAnnotations {

  @HystrixCommand(groupKey = "CustomCommandGroup", fallbackMethod = "sendFallbackResponse", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "80")})
  public String makeDownstreamCall() {
    return "Hello from annotations!!";
  }

  private String sendFallbackResponse() {
    return "Hello from fallback";
  }
}
