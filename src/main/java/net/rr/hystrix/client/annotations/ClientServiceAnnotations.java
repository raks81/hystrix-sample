package net.rr.hystrix.client.annotations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientServiceAnnotations {

  @Autowired
  private RemoteService remoteService;

  @Bean
  public RestTemplate buildRestTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @RequestMapping("/v2/service")
  public String remoteService(@RequestParam Long timeout) {
    return remoteService.callService(timeout);
  }

  @RequestMapping("/v2/serviceHystrix")
  public String remoteServiceHystrix(@RequestParam Long timeout) {
    return remoteService.callServiceHystrix(timeout);
  }
}
