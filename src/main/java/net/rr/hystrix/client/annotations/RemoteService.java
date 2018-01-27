package net.rr.hystrix.client.annotations;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class RemoteService {

  private RestTemplate restTemplate;

  public RemoteService(RestTemplate rest) {
    this.restTemplate = rest;
  }


  public String callService(Long timeout) {
    return restTemplate
        .getForObject("http://localhost:8080/remote?timeout=" + timeout, String.class);
  }

  @HystrixCommand(fallbackMethod = "handleBadService")
  public String callServiceHystrix(Long timeout) {
    return restTemplate
        .getForObject("http://localhost:8080/remote?timeout=" + timeout, String.class);
  }

  private String handleBadService(Long timeout) {
    return "Hystrix saving you!";
  }
}
