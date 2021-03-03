package lesson11.guava.concurrency;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ListenableFuture 接口继承了 JDK concurrent 包下的Future 接口，可以显著的简化并发的编写，并且提供可以异步监听的 Future
 *
 * @author Webb Dong
 * @date 2021-03-03 4:25 PM
 */
public class ListenableFutureExample {

    private static void example1() {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(
                Executors.newFixedThreadPool(10));
        ListenableFuture<String> future = service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                return "ListenableFuture example1 Done...";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Futures.addCallback(future, new FutureCallback<String>() {

            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println(result);
                service.shutdown();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }

        }, service);
    }

    private static void example2() {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(
                Executors.newFixedThreadPool(10));
        ListenableFuture<?> future = service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("ListenableFuture example2 Done...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        future.addListener(() -> service.shutdown(), service);
    }

    public static void main(String[] args) {
        example1();
        example2();
    }

}
