/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Monitor.IAll;
import Monitor.IConsumer;
import Monitor.IProducer;
import Monitor.MReentratLock;
import Monitor.MSynchronized;
import Thread.TProducer;
import Thread.TConsumer;
import java.util.Scanner;
/**
 *
 * @author omp
 * This simple example demonstrates the use of "synchronized" and "ReentrantLock" in the same "use case"   
 */
public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner( System.in );
        int nConsumers = 0, nProducers = 0, decrement = 0, increment = 0, technique = 0, oneAll = 0;
        IAll monitor;
        
        while ( nConsumers <= 0 ) {
            System.out.print("How many Consumers: " );
            nConsumers = scanner.nextInt();
        }
        while ( nProducers <= 0 ) {
            System.out.print("How many Producers: " );
            nProducers = scanner.nextInt();
        }
        while ( decrement <= 0 ) {
            System.out.print("Value to decrement: " );
            decrement = scanner.nextInt();
        }
        while ( increment <= 0 ) {
            System.out.print("Max. value to increment: " );
            increment = scanner.nextInt();
        }
        while ( oneAll < 1 || oneAll > 2 ) {
            System.out.print("wake up? 1->one, 2->all: ");
            oneAll = scanner.nextInt();
        }
        while ( technique < 1 || technique > 2 ) {
            System.out.print("use case? 1->synchronized, 2->reentrant lock: ");
            technique = scanner.nextInt();
        }       
        if ( technique == 1 )
            monitor = MSynchronized.getInstance();
        else monitor = MReentratLock.getInstance();
        for ( int i=0; i< nConsumers; i++)
            new Thread( TConsumer.getInstance(i, (IConsumer)monitor, decrement)).start();
        for ( int i=0; i<nProducers; i++)
            new Thread(TProducer.getInstance(i, (IProducer)monitor, increment, oneAll==2)).start();
    }
}
