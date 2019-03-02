package com.lujia.zookeeper;

import com.lujia.zookeeper.lock.DistributionLock;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException {



        //assertTrue( true );
    }


    public static void main(String[] args) throws IOException {
        CountDownLatch countDownLatch=new CountDownLatch(10);

        for (int i=0;i<10;i++){
            new Thread(()->{
                try {
                    countDownLatch.await();

                    DistributionLock distributionLock=new DistributionLock();

                    distributionLock.lock();

                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            },"thread-"+i).start();
            countDownLatch.countDown();
        }
        System.in.read();
    }
}
