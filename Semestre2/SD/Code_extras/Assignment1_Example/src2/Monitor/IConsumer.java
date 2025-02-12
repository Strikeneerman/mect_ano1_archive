/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitor;
/**
 *
 * @author omp
 */
public interface IConsumer {
    void get( int threadId, int decrement );
}
