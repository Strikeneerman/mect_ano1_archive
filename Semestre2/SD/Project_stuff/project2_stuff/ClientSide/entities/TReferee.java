package ClientSide.entities;

import ClientSide.stubs.*;


/**
 * Referee thread
 * 
 * It simulates the referee life cicle
 */

public class TReferee extends Thread{

    /**
     * Reference to the Referee Site
     */

    private final SRefereeSite refereeSite;

    /**
     * Reference to the playground
     */

    private final SPlayGround playground;

    /**
     * Referee identification
     */

    private final int refereeId;

    /**
     * Referee state
     */
    private int refereeState;


    /**
     * Instantiation of the referee thread
     * 
     * @param refereeId     ID of the referee
     * @param rSite         reference to the referee site
     * @param pGround       reference to the playground
     */

    public TReferee(int refereeId, SRefereeSite rSite, SPlayGround pGround){
        this.refereeId = refereeId;
        this.refereeState = RefereeStates.START_OF_THE_MATCH;
        this.refereeSite = rSite;
        this.playground= pGround;
    }

    
    
    /**
     * Referee life cycle
     *
     * <ul>
     * <li> The referee will wait for the teams to be ready</li>
     * <li> The referee will call a trial</li>
     * <li> The referee will start the trial when both teams are in the playground</li>
     * <li> The referee will wait for the trial to end</li>
     * <li> The referee will access the trial decision</li>
     * <li> The referee will repeat the process until a team wins the game</li>
     * <li> The referee will declare the game winner</li>
     * <li> The referee will repeat the process until the match is over</li>
     * <li> The referee will terminate letting the coaches know that the match ended</li>
     * </ul>
     * 
     */

    @Override
    public void run() {
        //System.out.println("Referee " + refereeId + " started on state " + refereeState);
        
        
        try {
            sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
            }
            //while not enough coaches waiting does nothing
            //refereeSite.announceNewGame();
            
            //for nGames
            while(true){
                while(true){
                    //wait for both teams to be ready
                    refereeSite.startGame();
                    // randomdelay(100);
                    //call trial
                    refereeSite.callTrial();
                    // randomdelay(100);
                    //start trial
                    playground.startTrial();
                    // randomdelay(100);
                    
                    //wait for trial to end
                    
                    if(playground.assertTrialDecision()) break;
                    
                        //print "newTrial" in color yellow
                        System.out.println("\u001B[33m" + "New Trial" + "\u001B[0m");
                    }
                // //end for nTrials
                //print in the color green
                System.out.println("\u001B[32m" + "New Game"+ "\u001B[0m");
            
                // //declare game winner
                if(playground.declareGameWinner()) break;
                
                // //end for nGames
            }

            // //declare match winner

            playground.declareMatchWinner();
    }
    
    /**
     *   Get referee id
     * 
     *   @return int
     */
    
    public int getRefereeID(){
        return refereeId;
    }
    
    
    /**
     *  Get referee state
     *  <p>
     *  The referee state can be:
     *  <ul>
     *      <li> 0 - Start of the match</li>
     *      <li> 1 - Start of a game</li>
     *      <li> 2 - Teams Ready</li>
     *      <li> 3 - Wait for trial conclusion</li>
     *      <li> 4 - End of a game</li>
     *      <li> 5 - End of a match</li>
     *  </ul>
     * 
     *   @return int
     */
    
    public int getRefereeState(){
        return refereeState;
    }
    
    /**
     *  Set referee state
     *  <p>
     *      The referee state can be:
     *  <ul>
     *      <li> 0 - Start of the match</li>
     *      <li> 1 - Start of a game</li>
     *      <li> 2 - Teams Ready</li>
     *      <li> 3 - Wait for trial conclusion</li>
     *      <li> 4 - End of a game</li>
     *      <li> 5 - End of a match</li>
     *  </ul>
     * 
     *   @param state referee State
     */
    
    public void setRefereeState(int state){
        this.refereeState = state;
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
