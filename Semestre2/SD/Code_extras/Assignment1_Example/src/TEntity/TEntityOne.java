/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEntity;

import MSharedAreaOne.IMSharedAreaOne_entityOne;
import MSharedAreaTwo.IMSharedAreaTwo_entityOne;

/**
 *
 * @author omp
 */
public class TEntityOne implements Runnable {

    private final int entityId;
    private final IMSharedAreaOne_entityOne areaOne;
    private final IMSharedAreaTwo_entityOne areaTwo;
    
    private TEntityOne(int entityId, IMSharedAreaOne_entityOne areaOne, IMSharedAreaTwo_entityOne areaTwo) {
        this.entityId = 0;
        this.areaOne = areaOne;
        this.areaTwo = areaTwo;
    }
    public static Runnable getInstance(int entityId, IMSharedAreaOne_entityOne areaOne, IMSharedAreaTwo_entityOne areaTwo) {
        return new TEntityOne(entityId, areaOne, areaTwo);
    }
    @Override
    public void run() {
        // entity's life cycle example
        boolean run = true;
        while(run) {
            areaOne.mOneOne_1();
            areaTwo.mOneTwo_1();
            areaOne.mOneOne_2();
            run = areaTwo.mOneTwo_2();
        }
    }
}
