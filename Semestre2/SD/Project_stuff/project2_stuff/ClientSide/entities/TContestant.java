package ClientSide.entities;

import ClientSide.stubs.*;

/**
 * Contestant Thread
 * Used to simulate the contestant life cicle 
 * 
 */
public class TContestant extends Thread{
    private final int id;
    private final int coachId;
    private final SPlayGround playground;
    private final SBench bench;
    private int state;
    private int strength;

    /**
    * Contestant Constructor
    *
    * @param id        contestant's id
    * @param coachId   coach id that the contestant belongs to
    * @param playground playground monitor interface  
    * @param bench     bench monitor interface
    */
    public TContestant(int id,int coachId,SPlayGround playground, SBench bench) {
        this.id = id;
        this.coachId = coachId;
        this.playground = playground;
        this.bench = bench;
        state = ContestantStates.SEAT_AT_THE_BENCH;
        //strenght is a random number between 5 and 8
        strength = (int) (Math.random() * 4) + 5;
    }

    /**
     * Contestant life cicle
     *
     * <ul>
     * <li> The contestant will sit at the bench waiting for the coach to call him to play</li>
     * <li> The contestant will go to the playground awaiting the referee to start the trial</li>
     * <li> The contestant will pull the rope</li>
     * <li> The contestant will wait for the referee to assert the trial decision</li>
     * <li> The contestant will repeat the process until the game is over</li>
     * </ul>
     *
     * 
     */
    public void run() {
        // System.out.printf("Contestant %d from %d started on state %d\n", id, coachId, state);
        // randomdelay(1000);
        while(true){
            
            strength = bench.sitDown(strength);
            
            if(strength == -1) break;
            // randomdelay(100);
            
            playground.getReady(); //se o arbitro mandar os jogadores para o campo sem nenhum jogador em campo dá erro
            // randomdelay(100);
            
            strength = playground.pullTheRope(strength);
            // randomdelay(100);

            
            playground.amDone();     
            // randomdelay(100);
        }
    }

    
    // /** 
    //  * Will add a random delay to the thread with a maximum of l 
    //  * @param l maximum lenght
    //  */
    // private void randomdelay(long l) {
    //     try {
    //         sleep((long) (Math.random() * l));
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
        

    // }

    
    /** 
     * Get the contestant id
     * @return int
     */
    public int getContestantId() {
        return id;
    }
    
    /** 
     * Get the coach id
     * @return int
     */
    public int getCoachId() {
        return coachId;
    }
    /**
     * Get the state of the contestant
     * <p>
     * The states are:
     * <ul>
     * <li>0 - SEAT_AT_THE_BENCH
     * <li>1 - STAND_IN_POSITION
     * <li>2 - DO_YOUR_BEST
     * </ul>
     * @return state
     */
    public int getContestantState() {
        return state;
    }

    /**
     * Change the state of the contestant
     * <p>
     * The states are:
     * <ul>
     * <li>0 - SEAT_AT_THE_BENCH
     * <li>1 - STAND_IN_POSITION
     * <li>2 - DO_YOUR_BEST
     * </ul>
     * @param state state to change to
     */
    public void setContestantState(int state) {
        this.state = state;
    }
}
