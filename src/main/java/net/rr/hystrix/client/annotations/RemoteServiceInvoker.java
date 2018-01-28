package net.rr.hystrix.client.annotations;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class RemoteServiceInvoker {

  private RestTemplate restTemplate;

  public RemoteServiceInvoker(RestTemplate rest) {
    this.restTemplate = rest;
  }


  public String callService(Long timeout, Double errors) {
    return restTemplate
        .getForObject("http://localhost:8080/remote?timeout=" + timeout + "&errors=" + errors,
            String.class);
  }

  @HystrixCommand(fallbackMethod = "handleBadService", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout", value = "true")
  })
  public String callServiceHystrix(Long timeout, Double errors) {
    String response = null;
    response = restTemplate
        .getForObject("http://localhost:8080/remote?timeout=" + timeout + "&errors=" + errors,
            String.class);
    return response;
  }

  private String handleBadService(Long timeout, Double errors) {
    throw new RuntimeException("From fallback");
  }
}
