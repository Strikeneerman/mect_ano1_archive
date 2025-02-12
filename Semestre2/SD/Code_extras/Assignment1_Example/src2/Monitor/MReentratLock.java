/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitor;

import Common.Common;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author omp
 */
public class MReentratLock implements IAll {

    private static MReentratLock mReentratLock = null;
    private final ReentrantLock rl;
    private final Condition cDecrement;
    
    private int value = 0;

    private MReentratLock() {
        rl = new ReentrantLock(true);
        cDecrement = rl.newCondition();
    }
    public static IAll getInstance() {
        // singleton
        if ( mReentratLock == null )
            mReentratLock = new MReentratLock();
        return mReentratLock;
    }
    @Override
    public void get(int threadId, int decrement) {
        try {
            rl.lock();
            Common.print(Common.YELLOW, "IS TRYING: Consumer", threadId, value, decrement);
            while (value < decrement) {
                try {
                    Common.print(Common.BLUE, "IS WAITING: Consumer", threadId, value, decrement);
                    cDecrement.await();
                    Common.print(Common.PURPLE, "WAS AWAKEN: Consumer", threadId, value, decrement);
                } catch (InterruptedException e) {}
            }
            value -= decrement;
            Common.print(Common.RED, "DECREMENT: Consumer", threadId, (value+decrement), decrement, value);
        } finally {
            rl.unlock();
        }
    }
    /**
     * 
     * @param threadId
     * @param increment
     * @param wakeUpAll
     */
    @Override
    public void put(int threadId, int increment, boolean wakeUpAll) {
        try {
            rl.lock();
            value += increment;
            Common.print(Common.GREEN, "Producer", threadId, (value-increment), increment, value);
            if ( wakeUpAll )
                cDecrement.signalAll();
            else cDecrement.signal();
        } finally {
            rl.unlock();
        }
    }
}
