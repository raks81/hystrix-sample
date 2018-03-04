package net.rr.hystrix.pres;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


public class CustomCommandAnnotations {

  @HystrixCommand(groupKey = "CustomCommandGroup")
  public String makeDownstreamCall() {
    return "Hello from annotations!!";
  }
}
