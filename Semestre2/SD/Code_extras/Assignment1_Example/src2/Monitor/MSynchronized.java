/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitor;

import Common.Common;

/**
 * @author omp
 */
public class MSynchronized implements IAll {
    
    private static MSynchronized mSynchronized = null;
    private int value = 0;

    private MSynchronized() {
    }
    public static IAll getInstance() {
        // singleton
        if ( mSynchronized == null )
            mSynchronized = new MSynchronized();
        return mSynchronized;
    }
    private void print(String color, String text, int threadId, int value, int decrement) {
        System.out.println(color + text + "(" + threadId + ") - {" + value + "," + decrement + "}");           
    }
    private void print(String color, String text, int threadId, int oldValue, int difValue, int value) {
        System.out.println(color + text + "(" + threadId + ") - {" +
                           (oldValue) + "," + difValue + "," + value + "}");          
    }
    @Override
    public synchronized void get(int threadId, int decrement) {
        
        Common.print(Common.YELLOW, "IS TRYING: Consumer", threadId, value, decrement);
        while (value < decrement) {
            try {
                Common.print(Common.BLUE, "IS WAITING: Consumer", threadId, value, decrement);
                wait();
                Common.print(Common.PURPLE, "WAS AWAKEN: Consumer", threadId, value, decrement);
            } catch (InterruptedException e) {
            }
        }
        value -= decrement;
        Common.print(Common.RED, "DECREMENT: Consumer", threadId, (value+decrement), decrement, value);
    }
    @Override
    public synchronized void put(int threadId, int increment, boolean wakeUpAll) {
        value += increment;
        Common.print(Common.GREEN, "Producer", threadId, (value-increment), increment, value );
        
        if ( wakeUpAll )
            notifyAll();
        else notify();
    }
}
