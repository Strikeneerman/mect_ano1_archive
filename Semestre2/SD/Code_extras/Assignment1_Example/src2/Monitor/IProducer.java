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
public interface IProducer {
    void put(int threadId, int increment, boolean wakeUpAll);
}
