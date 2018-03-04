package net.rr.hystrix.pres;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CustomCommandTest {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    String resultSync = new CustomCommand("test").execute();


    System.out.println("Calling sync");
    System.out.println(resultSync);
    System.out.println("Got sync result...");

    System.out.println("Calling async 1...");

    Future<String> resultAsync1 = new CustomCommand("test").queue();

    System.out.println("Calling async 2...");
    Future<String> resultAsync2 = new CustomCommand("test").queue();

    System.out.println("Printing 1");


    System.out.println(resultAsync1.get());



    System.out.println("Printing 2");
    System.out.println(resultAsync2.get());
    System.out.println(new CustomCommandAnnotations().makeDownstreamCall());
  }
}
