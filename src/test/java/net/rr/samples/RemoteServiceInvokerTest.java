package net.rr.samples;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.netflix.hystrix.Hystrix;
import net.rr.hystrix.Application;
import net.rr.hystrix.client.annotations.RemoteServiceInvoker;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.MOCK)
public class RemoteServiceInvokerTest {

  @Autowired
  RemoteServiceInvoker invoker;

  @Rule
  public WireMockRule rule = new WireMockRule(options().port(8080));

  @Test
  public void testCallServiceHystrixForSuccess() {
    Hystrix.reset();
    rule.stubFor(get(urlEqualTo("/remote?timeout=200&errors=0.0&input=1.0")).willReturn(
        aResponse()
            .withStatus(200)
            .withFixedDelay(200)
            .withBody("-100")));
    final String result = invoker.callServiceHystrix(200l, 0.0, 1.0);
    System.out.println(result);
  }

  @Test
  public void testCallServiceHystrixFor2SecondDelay() {
    Hystrix.reset();
    rule.stubFor(get(urlEqualTo("/remote?timeout=2000&errors=0.0&input=1.0")).willReturn(
        aResponse()
            .withStatus(200)
            .withFixedDelay(2000)
            .withBody("-100")));
    final String result = invoker.callServiceHystrix(2000l, 0.0, 1.0);
    System.out.println(result);
  }

  @Test
  public void testCallServiceHystrixFor500Response() {
    Hystrix.reset();
    rule.stubFor(get(urlEqualTo("/remote?timeout=200&errors=0.0&input=1.0")).willReturn(
        aResponse()
            .withStatus(500)
            .withFixedDelay(200)
            .withBody("-100")));
    final String result = invoker.callServiceHystrix(200l, 0.0, 1.0);
    System.out.println(result);
  }

  @Test
  public void testCallServiceHystrixForConcurrentRequests() {
    Hystrix.reset();
    rule.stubFor(get(urlEqualTo("/remote?timeout=200&errors=0.0&input=1.0")).willReturn(
        aResponse()
            .withStatus(500)
            //.withFixedDelay(200)
            .withBody("-100")));
    for (int i = 0; i < 10; i++) {
      final String result = invoker.callServiceHystrix(200l, 0.0, 1.0);
      System.out.println(result);
    }
  }
}