package net.rr.hystrix.remote;

import java.util.Random;
import java.util.concurrent.Semaphore;
import net.rr.hystrix.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RemoteController {

  // Only 5 concurrent requests to the remote service is possible
  private final Semaphore semaphore = new Semaphore(5);

  private Random random = new Random();



  /**
   *  - Simulate max concurrency : Only allows 20 concurrent requests
   *  - Simulate errors          : 'errors' % of requests will be errors
   *  - Simulate latency         : will take about 'timeout' millis to respond
   *
   * @param timeout
   * @param errors
   * @param input
   * @return
   */
  @GetMapping("/remote")
  public String remoteService(@RequestParam Long timeout, @RequestParam Double errors,
      @RequestParam(defaultValue = "0.0") Double input) {
    try {
      semaphore.acquire();

      // Sleep for around 'timeout' milli seconds
      long to =(long) (timeout + random.nextGaussian() * random.nextGaussian());
      System.out.println(to);
      Thread.sleep(to);

      // Simulate errors based on 'errors'
      if (Math.random() * 100 < errors) {
        throw new ResourceNotFoundException();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      semaphore.release();
    }
    return Double.toString(input * 10);
  }
}