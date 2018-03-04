package net.rr.hystrix.remote;

import java.util.Random;
import java.util.concurrent.Semaphore;
import net.rr.hystrix.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteController {

  private final Semaphore semaphore = new Semaphore(20);

  private Random random = new Random();

  @GetMapping("/remote")
  public String remoteService(@RequestParam Long timeout, @RequestParam Double errors,
      @RequestParam(defaultValue = "0.0") Double input) {
    try {
      semaphore.acquire();
      Thread.sleep((long) (timeout + random.nextGaussian() * random.nextGaussian()));
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