package com.concurrent.utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Benjamin Winterberg
 *
 * 更多 CompletableFuture 特性详见：https://blog.csdn.net/u011726984/article/details/79320004
 */
public class CompletableFuture1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();

        future.complete("42");

        future.thenAccept(System.out::println)
                .thenAccept(v -> System.out.println("done"));
    }
}
