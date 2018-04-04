package net.rr.hystrix.pres;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class CustomCommand extends HystrixCommand<String> {

  private final String name;

  public CustomCommand(String name) {
    super(Setter
        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("CustomCommandGroup"))
        .andCommandPropertiesDefaults(
            HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(5000)
                .withCircuitBreakerErrorThresholdPercentage(50)));
    this.name = name;
  }

  @Override
  protected String run() {
    // Make the downstream remote call here
    // ...
    return "Hello from " + name;
  }

  @Override
  protected String getFallback() {
    return "Hello from fallback";
  }
}
