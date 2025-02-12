/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import MSharedAreaOne.IMSharedAreaOne;
import MSharedAreaOne.IMSharedAreaOne_entityOne;
import MSharedAreaOne.IMSharedAreaOne_entityTwo;
import MSharedAreaOne.MSharedAreaOne;
import MSharedAreaTwo.IMSharedAreaTwo;
import MSharedAreaTwo.IMSharedAreaTwo_entityOne;
import MSharedAreaTwo.IMSharedAreaTwo_entityTwo;
import MSharedAreaTwo.MSharedAreaTwo;
import TEntity.TEntityOne;
import TEntity.TEntityTwo;

/**
 *
 * @author omp
 */
public class ASS1_Example {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IMSharedAreaOne areaOne = MSharedAreaOne.getInstance();
        IMSharedAreaTwo areTwo = MSharedAreaTwo.getInstance();
        
        Runnable entityOne = TEntityOne.getInstance(0,
                                                    (IMSharedAreaOne_entityOne) areaOne,
                                                    (IMSharedAreaTwo_entityOne) areTwo);
        Runnable entityTwo = TEntityTwo.getInstance(1,
                                                    (IMSharedAreaOne_entityTwo)areaOne,
                                                    (IMSharedAreaTwo_entityTwo) areTwo);
        Thread tEntityOne = new Thread( entityOne );
        Thread tEntityTwo = new Thread( entityTwo );
        tEntityOne.start();
        tEntityTwo.start();
        
        try {
            tEntityOne.join();
            System.out.println("Entity One has finished!");
            tEntityTwo.join();
            System.out.println("Entity Two has finished!");
        } catch(Exception ex){}
        
        System.out.println("End of simulation!");
        
    }
    
}
