/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Common;

/**
 *
 * @author omp
 */
public class Common {
 
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURPLE = "\u001B[35m";
    
    // can be any java object
    private static final Object obj = new Object();

    public static void print(String color, String text, int threadId, int value, int decrement) {
        // a different way to use synchronized: necessary only if called by Threads from more than one Monitor
        //synchronized( obj ) {
            System.out.println(color + text + "(" + threadId + ") - {c:" + value + ", d:" + decrement + "}");  
        //}
    }
    public static void print(String color, String text, int threadId, int oldValue, int difValue, int value) {
        // a different way to use synchronized: necessary only if called by Threads from more than one Monitor
        //synchronized( obj ) {
            System.out.println(color + text + "(" + threadId + ") - {o:" +
                               (oldValue) + ", x:" + difValue + ", c:" + value + "}");  
        //}
    }
    private Common() {
    }
}
