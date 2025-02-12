package ClientSide.entities;

/**
 * Definition of the internal states of the Contestant during his life cicle
 * 
 */


public final class ContestantStates {


    /**
     * The contestant is sitting in the bench
     */

    public static final int SEAT_AT_THE_BENCH = 0; //SAB

    /**
     * The contestant is standing in position
     */
    public static final int STAND_IN_POSITION = 1; //SIP
    
    /**
     * The contestant is doing his best
     */
    public static final int DO_YOUR_BEST = 2; //DYB
        
    ContestantStates(){
    }

}
