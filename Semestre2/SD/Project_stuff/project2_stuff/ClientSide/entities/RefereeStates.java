package ClientSide.entities;

/**
 * Definition of the internal states of the Referee life cicle
 * 
 */

public final class RefereeStates {


    /**
     *  The referee signals the start of the match
     * 
     */

    public static final int START_OF_THE_MATCH = 0; //SOM


    /**
     *  The referee signals the start of a game 
     * 
     */

    public static final int START_OF_A_GAME = 1; //SOG

    /**
     *  The referee is waiting for the coaches to signal that the teams are ready
     * 
     */

    public static final int TEAMS_READY = 2; //TRY

    /**
     *  The referee is waiting for the conclusion of a trial
     * 
     */

    public static final int WAIT_FOR_TRIAL_CONCLUSION = 3; //WTC

    /**
     *  The referee signals the end of a game
     * 
     */

    public static final int END_OF_A_GAME = 4; //EOG

    /**
     *  The referee signals the end of the match
     * 
     */

    public static final int END_OF_A_MATCH = 5; //EOM

    
}
