package ClientSide.entities;

import ClientSide.stubs.*;

/**
 * Coach Thread
 * Used to simulate the coach life cicle 
 * 
 */

public class TCoach extends Thread{

    private final SBench bench;
    private final SRefereeSite refereeSite;
    private final SPlayGround playground;
    private final int id;
    private int state;
    
    /**
     * Coach Constructor
     * 
     * @param id Coach id
     * @param rSite Stub to the referee site
     * @param bench Stub to the bench
     * @param playground Stub to the playground
     */
    
    public TCoach(int id, SRefereeSite rSite, SBench bench, SPlayGround playground){//, MBench bench, MRefereeSite refereeSite) {
        this.id = id;
        state = CoachStates.WAIT_FOR_REFEREE_COMMAND;
        this.bench = bench;
        this.refereeSite = rSite;
        this.playground = playground;
    }
    
    /** 
     * Coach life cicle
     * 
     *
     * <ul>
     * <li> The coach will review the notes (wait for contestants to sit down)</li>
     * <li> The coach will wait for the referee to start the game</li>
     * <li> The coach will call the contestants to play</li>
     * <li> The coach will inform the referee when all choseen contestants leave the bench</li>
     * <li> The coach will watch the trial</li>
     * <li> The coach will repeat the process until the game is over</li>
     * </ul>
     *
     */
    @Override
    public void run() {
        System.out.println("Coach " + id + " started on state " + state);
        //delay coaches
        // randomdelay(100);
        
        //while match is not over
        while(true){   
            
            //review notes
            bench.reviewNotes();
            
            //wait for referee command
            if (refereeSite.waitForRefereeCommand()){
                break;
            }
            // randomdelay(100);
            bench.callContestants();
            // randomdelay(100);
            playground.waitForContestants();
            //acordar o arbitro
            refereeSite.informReferee();
            // randomdelay(100);
            
            playground.watchTrial();
            // randomdelay(100);
            
        }
    }

    /** 
     * Returns the coach id
     * @return int
     */
    public int getCoachId(){
        return id;
    }

     /**
     * Get the state of the contestant
     * <p>
     * The states are:
     * <ul>
     * <li>0 - WAIT_FOR_REFEREE_COMMAND
     * <li>1 - ASSEMBLE_TEAM
     * <li>2 - WATCH_TRIAL
     * </ul>
     * @return state
     */
    public int getCoachState() {
        return state;
    }

    /** 
     * Set the coach state
     * <p>
     * The states are:
     * <ul>
     * <li>0 - WAIT_FOR_REFEREE_COMMAND
     * <li>1 - ASSEMBLE_TEAM
     * <li>2 - WATCH_TRIAL
     * </ul>
     * @param state Sets Coach State
     */
    public void setCoachState(int state){
        this.state = state;
    }

    // /**
    //  * Will add a random delay to the thread with a maximum of l 
    //  * @param l
    //  */
    // private void randomdelay(long l) {
    //     try {
    //         sleep((long) (Math.random() * l));
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }
}
