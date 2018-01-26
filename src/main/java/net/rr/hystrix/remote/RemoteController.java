package net.rr.hystrix.remote;

import java.util.Random;
import java.util.concurrent.Semaphore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteController {

  private final Semaphore semaphore = new Semaphore(10);

  private Random random = new Random();

  @GetMapping("/remote")
  public String remoteService(@RequestParam Long timeout) {
    try {
      System.out.println("Available Permits: " + semaphore.availablePermits());
      semaphore.acquire();

      // Wait for the timeout
      Thread.sleep((long) (timeout + random.nextGaussian() * random.nextGaussian()));

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      semaphore.release();
      System.out.println("Releasing semaphore. Available: " + semaphore.availablePermits());
    }
    return "Remote says hi!";
  }
}