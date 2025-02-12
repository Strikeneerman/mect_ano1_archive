/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thread;

import Monitor.IProducer;
import java.util.Random;
/**
 *
 * @author omp
 */
public class TProducer implements Runnable{
    
    private final int threadId;
    private final IProducer producer;
    private final int increment;
    private final boolean wakeUpAll;
    private final Random random = new Random();
    
    private TProducer(int threadId, IProducer producer, int increment, boolean wakeUpAll) {
        this.threadId = threadId;
        this.producer = producer;
        this.increment = increment;
        this.wakeUpAll = wakeUpAll;
    }
    public static Runnable getInstance(int threadId, IProducer producer, int increment, boolean wakeUpAll) {
        return new TProducer(threadId, producer, increment, wakeUpAll);
    }
    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(2000);
                int value = random.nextInt(increment+1);
                producer.put(threadId, value, wakeUpAll);
            }
        } catch (InterruptedException e) {
        }
    }
}
