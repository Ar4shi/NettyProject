package com.netty.c3;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestJdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future = service.submit(() -> {
            System.out.println("calculating...");
            Thread.sleep(1000);
            return 50;
        });
        System.out.println("waiting...");
        System.out.println(Thread.currentThread().getName() + " result : " + future.get());
    }
}
