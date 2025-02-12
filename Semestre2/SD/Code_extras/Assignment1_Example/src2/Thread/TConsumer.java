/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thread;

import Monitor.IConsumer;

/**
 *
 * @author omp
 */
public class TConsumer implements Runnable{

    private final int threadId;
    private final  IConsumer consumer;
    private final int decrement;
    
    private TConsumer(int threadId, IConsumer consumer, int decrement) {
        this.threadId = threadId;
        this.consumer = consumer;
        this.decrement = decrement;
    }
    public static Runnable getInstance( int threadId, IConsumer consumer, int decrement) {
        return new TConsumer(threadId, consumer, decrement);
    }
    @Override
    public void run() {
        while (true) {
            try {
                consumer.get( threadId, decrement );
            } catch (Exception e) { }
        }
    }
}
