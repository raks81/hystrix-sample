package net.rr.samples;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;

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

  @Rule
  public WireMockRule rule = new WireMockRule(options().port(8080));

  @Autowired
  private RemoteServiceInvoker invoker;

  private static String FALLBACK_RESPONSE = "-1";

  private static String SUCCESS_RESPONSE = "100";

  @Test
  public void testCallServiceHystrixForSuccess() {
    long delay = 200;
    Hystrix.reset();
    rule.stubFor(get(urlEqualTo("/remote?timeout=" + delay + "&errors=0.0&input=1.0")).willReturn(
        aResponse()
            .withStatus(200)
            .withFixedDelay((int) delay)
            .withBody(SUCCESS_RESPONSE)));
    final String result = invoker.callServiceHystrix(delay, 0.0, 1.0);
    assertEquals(SUCCESS_RESPONSE, result);
  }

  @Test
  public void testCallServiceHystrixFor2SecondDelay() {
    long delay = 2000;
    Hystrix.reset();
    rule.stubFor(get(urlEqualTo("/remote?timeout=" + delay + "&errors=0.0&input=1.0")).willReturn(
        aResponse()
            .withStatus(200)
            .withFixedDelay((int) delay)
            .withBody(SUCCESS_RESPONSE)));
    final String result = invoker.callServiceHystrix(delay, 0.0, 1.0);
    assertEquals(FALLBACK_RESPONSE, result);
  }

  @Test
  public void testCallServiceHystrixFor500Response() {
    long delay = 200;
    Hystrix.reset();
    rule.stubFor(get(urlEqualTo("/remote?timeout=" + delay + "&errors=0.0&input=1.0")).willReturn(
        aResponse()
            .withStatus(500)
            .withFixedDelay((int) delay)
            .withBody("Error in response")));
    final String result = invoker.callServiceHystrix(delay, 0.0, 1.0);
    assertEquals(FALLBACK_RESPONSE, result);
  }
}