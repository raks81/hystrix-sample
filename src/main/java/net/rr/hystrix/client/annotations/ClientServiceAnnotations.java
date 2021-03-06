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
  private RemoteServiceInvoker remoteService;

  @Bean
  public RestTemplate buildRestTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  /**
   * The endpoint without Hystrix
   *
   *
   * @param timeout
   * @param errors
   * @param input
   * @return
   */
  @RequestMapping("/v2/service")
  public String remoteService(@RequestParam Long timeout, @RequestParam Double errors,
      @RequestParam(defaultValue = "0.0") Double input) {
    return remoteService.callService(timeout, errors, input);
  }

  /**
   * The endpoint WITH Hystrix
   *
   * @param timeout
   * @param errors
   * @param input
   * @return
   */
  @RequestMapping("/v2/serviceHystrix")
  public String remoteServiceHystrix(@RequestParam Long timeout, @RequestParam Double errors,
      @RequestParam(defaultValue = "0.0") Double input) {
    return remoteService.callServiceHystrix(timeout, errors, input);
  }
}
