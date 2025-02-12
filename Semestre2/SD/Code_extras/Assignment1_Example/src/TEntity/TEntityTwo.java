/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TEntity;

import MSharedAreaOne.IMSharedAreaOne_entityTwo;
import MSharedAreaTwo.IMSharedAreaTwo_entityTwo;

/**
 *
 * @author omp
 */
public class TEntityTwo implements Runnable{

    private final int entityId;
    private final IMSharedAreaOne_entityTwo areaOne;
    private final IMSharedAreaTwo_entityTwo areaTwo;
    
    private TEntityTwo(int entityId, IMSharedAreaOne_entityTwo areaOne, IMSharedAreaTwo_entityTwo areaTwo) {
        this.entityId = 0;
        this.areaOne = areaOne;
        this.areaTwo = areaTwo;
    }
    public static Runnable getInstance(int entityId, IMSharedAreaOne_entityTwo areaOne, IMSharedAreaTwo_entityTwo areaTwo) {
        return new TEntityTwo(entityId, areaOne, areaTwo);
    }
    public void run() {
        // TODO
    }
}
