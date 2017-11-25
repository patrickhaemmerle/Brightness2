package io.haemmi.brightness2;

import org.junit.Test;


import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Patrick on 23.11.2017.
 */

public class RxJavaExampleTest {

    private long myLong;

    @Test
    public void getHelloWorldOk() {
        TestObservable o = new TestObservable();
        o.getHelloWorldOk().subscribe(ret -> {
            assertEquals("Hallo Welt!", ret);
        });
    }

    @Test
    public void testThrowsUncheckedException() {
        new TestObservable().throwsUncheckedException().
                subscribe((String ret) -> {
                    fail("Ooops, expected an exception!");
                }, (Throwable e) -> {
                    assertEquals("Surprise!", e.getMessage());
                });
    }

    @Test
    public void testThrowsCheckedException() {
        new TestObservable().throwsCheckedException().
                subscribe((String ret) -> {
                    fail("Ooops, expected an exception!");
                }, (Throwable e) -> {
                    assertTrue(e instanceof IllegalStateException);
                    assertEquals("Surprise!", e.getMessage());
                });
    }

    @Test
    public void testAsyncExecution() throws InterruptedException {
        new TestObservable().asyncExecution().
                subscribeOn(Schedulers.computation()).
                subscribe(i -> myLong = i);

        long before = System.currentTimeMillis();
        Thread.sleep(501);
        long after = System.currentTimeMillis();

        assertTrue(before + 500 < myLong);
        assertTrue(after > myLong);
    }

    private class TestObservable {

        public Observable<String> getHelloWorldOk() {
           return Observable.fromCallable(() -> {
               return "Hallo Welt!";
           });
        }

        public Observable<String> throwsUncheckedException() {
            return Observable.fromCallable(() -> {
               throw new RuntimeException("Surprise!");
            });
        }

        public Observable<String> throwsCheckedException() throws IllegalStateException {
            return Observable.fromCallable(() -> {
                throw new IllegalStateException("Surprise!");
            });
        }

        public Observable<Long> asyncExecution() {
            return Observable.fromCallable(() -> {
                Thread.sleep(500);
                return System.currentTimeMillis();
            });
        }

    }

}
