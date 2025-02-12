package ServerSide.sharedRegions;

import ClientSide.entities.RefereeStates;
import ClientSide.entities.CoachStates;
import ClientSide.stubs.SGeneralRepo;
import ServerSide.entities.RefereeSiteClientProxy;
import ServerSide.main.ServerRefereeSite;
import ServerSide.main.SimulPar;

/**
 * Monitor for the Referee Site
 * <p>
 * It simulates the referee site where the referee will wait for the coaches to be ready to start a game
 */
public class MRefereeSite{
    
    private static int nCoachesWaiting;
    private boolean startTrial;
    private int nCoachesReady;
    private boolean terminate;

    private final SGeneralRepo repos;

    /**
     * Constructor for the Referee Site
     * 
     * @param repos reference to the general repository
     */
    public MRefereeSite(SGeneralRepo repos) {
        nCoachesWaiting = 0;
        nCoachesReady = 0;
        startTrial = false;
        terminate = false;
        this.repos = repos;
    }

    /** 
     * Function to start the game
     * <p>
     * Referee will wait for the coaches to be ready so it can start a game
     */

    public synchronized void startGame() { //start of the match??
        ((RefereeSiteClientProxy) Thread.currentThread()).setRefereeState(RefereeStates.START_OF_A_GAME);
        repos.setRefereeState(RefereeStates.START_OF_A_GAME);

        while(nCoachesWaiting != SimulPar.nCoaches){
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        nCoachesWaiting = 0;
        //set state of referee 
        System.out.println("Referee started the game");
    }

    /**  
     *  Function for waiting for the referee command
     *  <p>
     *  if true the coach will terminate
     *  else it will continue to the next state
     * 
     *  @return   boolean : whether the coach will terminate or not
     */

    public synchronized boolean waitForRefereeCommand() {

        int coachID = ((RefereeSiteClientProxy) Thread.currentThread()).getCoachId();
        ((RefereeSiteClientProxy) Thread.currentThread()).setCoachState(CoachStates.WAIT_FOR_REFEREE_COMMAND);
        repos.setCoachState(coachID,CoachStates.WAIT_FOR_REFEREE_COMMAND);
        
        startTrial = false;
        nCoachesWaiting++;
        if(nCoachesWaiting == SimulPar.nCoaches){
            //notify the referee that the coaches are ready
            notifyAll();
        }
        while(startTrial == false){
            try {
                if(terminate) return true;
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }

    
    /**
     * Function to call the trial
     * <p>
     * Referee will call the trial and wait for the coaches to have both teams ready
     * and will advance to the next state
     */
    public synchronized void callTrial() {
        nCoachesReady = 0; 
        //set state of referee
        startTrial = true;
        notifyAll();
        System.out.println("Referee called the trial");
        ((RefereeSiteClientProxy) Thread.currentThread()).setRefereeState(RefereeStates.TEAMS_READY);
        repos.setRefereeState(RefereeStates.TEAMS_READY);
        //referee should wait to be awaken by the last coach here
        while(nCoachesReady != SimulPar.nCoaches){
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //cleans the CoachesReady array
        nCoachesReady = 0; 
    }

    /**
     * Function to inform the referee
     * <p>
     * Coach calls this function to inform the referee that the team is ready,
     * waking up the referee that is blocked in the waitTeamsReady state
     * 
     */
     public synchronized void informReferee() {
        nCoachesReady++;
        if (nCoachesReady == SimulPar.nCoaches){
            notifyAll();
        }
     }

    /**
     * Function to change the state of the terminate variable
     * <p>
     * Referee will call this function to inform that the match is over
     */
    public synchronized void terminate() {
        terminate = true;
        while(nCoachesWaiting != SimulPar.nCoaches){
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        notifyAll();
        ServerRefereeSite.waitConnection = false;
    }
}