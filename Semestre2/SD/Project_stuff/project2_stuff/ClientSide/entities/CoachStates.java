package ClientSide.entities;

/**
 * Definition of the internal states of the Coach during his life cicle
 */

public final class CoachStates {

    /**
     * The coach is waiting for the referee command
     */
    public static final int WAIT_FOR_REFEREE_COMMAND = 0; //WFRC
    /**
     * The coach is assembling the team
     */
    public static final int ASSEMBLE_TEAM = 1; //ASTM
    /**
     * The coach is watching the trial
     */
    public static final int WATCH_TRIAL = 2; //WATL
    
    CoachStates(){
    }
}
