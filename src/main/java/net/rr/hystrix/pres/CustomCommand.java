package net.rr.hystrix.pres;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class CustomCommand extends HystrixCommand<String> {

  private final String name;

  public CustomCommand(String name) {
    super(Setter
        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("CustomCommandGroup")).andCommandKey(
            HystrixCommandKey.Factory.asKey("SomeCommand")).andCommandPropertiesDefaults(
            HystrixCommandProperties.Setter().withCircuitBreakerEnabled(true)
                .withExecutionTimeoutInMilliseconds(5000)
                .withCircuitBreakerErrorThresholdPercentage(50)
                .withCircuitBreakerRequestVolumeThreshold(10)
                .withCircuitBreakerSleepWindowInMilliseconds(5_000)));
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
