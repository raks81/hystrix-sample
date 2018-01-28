package net.rr.hystrix.client.vanilla;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientServiceVanilla {

  @GetMapping("/v1/service")
  public String remoteService(@RequestParam Long timeout, @RequestParam Double errors) {
    return callService(timeout, errors);
  }

  @GetMapping("/v1/serviceHystrix")
  public String remoteServiceHystrix(@RequestParam Long timeout, @RequestParam Double errors) {

    HystrixCommand.Setter key = HystrixCommand.Setter
        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("service")).andCommandKey(
            HystrixCommandKey.Factory.asKey("SomeCommand")).andCommandPropertiesDefaults(
            HystrixCommandProperties.Setter().withCircuitBreakerEnabled(true)
                .withCircuitBreakerErrorThresholdPercentage(50)
                .withCircuitBreakerRequestVolumeThreshold(10)
                .withCircuitBreakerSleepWindowInMilliseconds(5_000));

    HystrixCommand<String> cmd = new HystrixCommand<String>(key) {
      @Override
      protected String run() throws Exception {
        return callService(timeout, errors);
      }

      @Override
      protected String getFallback() {
        System.out.println("Falling back...");
        return "Nothing from remote!";
      }
    };
    return cmd.execute();
  }

  private String callService(Long timeout, Double errors) {
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate
        .getForObject("http://localhost:8080/remote?timeout=" + timeout + "&errors=" + errors,
            String.class);
  }
}
