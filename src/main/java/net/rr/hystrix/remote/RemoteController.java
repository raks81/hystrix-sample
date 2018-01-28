package net.rr.hystrix.remote;

import java.util.Random;
import java.util.concurrent.Semaphore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteController {

  private final Semaphore semaphore = new Semaphore(20);

  private Random random = new Random();

  @GetMapping("/remote")
  public String remoteService(@RequestParam Long timeout, @RequestParam Double errors) {
    try {
//      System.out.println("Available Permits: " + semaphore.availablePermits());
      semaphore.acquire();

//      System.out.println(random.hashCode() + " " + Thread.currentThread().getId());
      // Wait for the timeout
      Thread.sleep((long) (timeout + random.nextGaussian() * random.nextGaussian()));

//      System.out.println("Params: " + errors + " : " + Math.random());
      if (Math.random() * 100 < errors) {
        throw new RuntimeException("Error in remote service");
      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      semaphore.release();
//      System.out.println("Releasing semaphore. Available: " + semaphore.availablePermits());
    }
    return "Remote says hi!";
  }
}