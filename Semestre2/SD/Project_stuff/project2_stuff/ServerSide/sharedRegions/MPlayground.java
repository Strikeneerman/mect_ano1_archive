package ServerSide.sharedRegions;
import ServerSide.entities.PlaygroundClientProxy;
import ServerSide.main.ServerPlayground;
import ServerSide.main.SimulPar;
import ClientSide.stubs.SGeneralRepo;
import ClientSide.entities.CoachStates;
import ClientSide.entities.ContestantStates;
import ClientSide.entities.RefereeStates;

/**
 * Playground monitor
 * <p> 
 * It simulates the playground where the contestants will pull the rope, the referee will make decisions and where the team points are stored
 */

public class MPlayground{

    private int trials;

    private int contestantsPlaying;

    //private int nPlayers;

    private int nGames;

    private boolean knockout;

    private boolean startTrial;
    private boolean endedTrial;

    private int rope;

    private final SGeneralRepo repos;

    // array corresponding to the team's point for each team
    private int teamPoints[] = new int [SimulPar.nCoaches];
    /**
     * Constructor for the Playground
     * 
     * @param repos reference to the general repository
     */
    public MPlayground(SGeneralRepo repos){
        trials = 0;
        rope = 0;
        nGames = 0;
        contestantsPlaying = 0;
        startTrial = false;
        endedTrial = false;
        this.repos = repos;
        //nPlayers = SimulPar.nPlayers * SimulPar.nCoaches;
        
    }

    /**
     * Function to start the trial
     * <p>
     * Referee starts the trial and 
     * wakes up the contestats that are in the Stand In Position state
     */
    public synchronized void startTrial(){

        ((PlaygroundClientProxy) Thread.currentThread()).setRefereeState(RefereeStates.WAIT_FOR_TRIAL_CONCLUSION);
        repos.setRefereeState(RefereeStates.WAIT_FOR_TRIAL_CONCLUSION);
        
        if(contestantsPlaying != SimulPar.nPlayers*SimulPar.nCoaches){
            throw new RuntimeException("Not all contestants where ready when the trial started");
        } 
        
        trials++;
        repos.setTrialNumber(trials);
        startTrial = true;
        notifyAll();
    }

    /**
     * Function to wair for the contestants
     * <p>
     * Coach will wait for the all the contestants to be ready before calling the referee
     */
    public synchronized void waitForContestants(){
        endedTrial = false;
        try {
            while(contestantsPlaying != SimulPar.nPlayers*SimulPar.nCoaches){
                wait();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Function to assert trial decision
     * <p>
     * Referee will be woken up when the last contestant is done pulling the rope
     * <p>
     * Wakes up Coaches, to review notes and 
     * Wakes up Contestants, that will go back to the bench
     * <p>
     * Here the Referee should check the "rope" if it's a diference of 4 or is the last trial.
     * 
     * @return boolean
     */

    public synchronized boolean assertTrialDecision(){
        
        try {
            while(contestantsPlaying != 0){
                wait();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        startTrial = false;
        endedTrial = true;

        if(trials == SimulPar.nTrials || Math.abs(rope) >= 4){
            System.out.println("Ended trials");
            trials = 0;
            if(Math.abs(rope) >= 4){
                knockout = true;
            }else{
                knockout = false;
            }
            notifyAll();
            return true;
        }
        knockout = false;
        notifyAll();
        return false; //new game
        
    }

    /**
     * Function to declare the game winner
     * <p>
     * Referee enters this function when the assertTrialDecision is true
     * After the trials the referee will give a point to the winner team
     * The winner team is determined by the value of the "rope"
     * 
     * @return boolean : whether the number of games has reached the maximum
     */
    public synchronized boolean declareGameWinner(){
        

        ((PlaygroundClientProxy) Thread.currentThread()).setRefereeState(RefereeStates.END_OF_A_GAME);
        repos.setRefereeState(RefereeStates.END_OF_A_GAME);

        if (rope < 0){
            System.out.println("Team 0 wins");
            teamPoints[0]++;

            repos.setGameWinner(0,knockout);

        }
        else if (rope > 0){
            System.out.println("Team 1 wins");
            teamPoints[1]++;
            repos.setGameWinner(1,knockout);
        }
        else {
            System.out.println("It was a Draw");
            repos.setGameDraw();
        }
       

        nGames++;
        repos.setGameNumber(nGames);

        rope = 0;
        repos.setRopePosition(rope);

        return nGames == SimulPar.nGames;
    }

    /**
     * Fuction to declare the match winner
     * <p>
     * when all the games are done the referre will declare the match winner and all the threads will terminate
     * 
     */
    public synchronized void declareMatchWinner(){

        ((PlaygroundClientProxy) Thread.currentThread()).setRefereeState(RefereeStates.END_OF_A_MATCH);
        repos.setRefereeState(RefereeStates.END_OF_A_MATCH);

        if (teamPoints[0] > teamPoints[1]){
            System.out.println("Team 0 wins the match");
            teamPoints[0]++;
            repos.setMatchWinner(0);

        }
        else if (teamPoints[0] < teamPoints[1]){
            System.out.println("Team 1 wins the match");
            teamPoints[1]++;
            repos.setMatchWinner(1);
        }
        else {
            System.out.println("The match ended in a Draw");
            repos.setMatchDraw();
        }

    }

    /**
     * Function to get ready
     * <p>
     * Contestants enter the playground waiting for the referee to start_trial
     * 
     */
    public synchronized void getReady(){
        //repos.setPositionRope(rope);
        int contID = ((PlaygroundClientProxy) Thread.currentThread()).getContestantId();
        int coachId = ((PlaygroundClientProxy) Thread.currentThread()).getCoachId();
        ((PlaygroundClientProxy) Thread.currentThread()).setContestantState(ContestantStates.STAND_IN_POSITION);
        repos.setContestantState(coachId,contID, ContestantStates.STAND_IN_POSITION);
        contestantsPlaying++;

        // Last contestant wakes up the Coaches
        if(contestantsPlaying == SimulPar.nPlayers*SimulPar.nCoaches) {
            notifyAll();
            repos.basic_update();
        }
        try {
            while(!startTrial){
                wait();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return;
    }

    /**
     * Function to pull the rope
     * <p>
     * each contestant will wait a random amount of time before pulling the rope
     * @param strenght Contestant Strenght
     * @return Contestant's updated Strenght
     */
    public int pullTheRope(int strenght){
        int contID = ((PlaygroundClientProxy) Thread.currentThread()).getContestantId();
        int coachId = ((PlaygroundClientProxy) Thread.currentThread()).getCoachId();
        ((PlaygroundClientProxy) Thread.currentThread()).setContestantState(ContestantStates.DO_YOUR_BEST);
        repos.setContestantState(coachId,contID, ContestantStates.DO_YOUR_BEST);
        //get contestant coach id

        try {
            Thread.sleep((long) (Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if(coachId == 0){  
            moveRope(strenght);
        }else{
            moveRope(-strenght);
        }
    
        return strenght-1;
    }
    
    /**
     * Function to notify that the contestant is done
     * <p>
     * After the contestants pull the rope, they wait in the playground for the referee to assert the trial decision
     * <p>
     * the last contestant to pull the rope will wake up the referee
     */
    public synchronized void amDone(){
        //System.out.print("Contestant " + ((TContestant) Thread.currentThread()).getContestantId() + " is done\n");
        contestantsPlaying--;
        if(contestantsPlaying == 0){
            notifyAll();
        }
        try {
            while(!endedTrial){
                wait();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Function to watch the trial
     * <p>
     * Coach will wait for the trial to end
     * 
     */
    public synchronized void watchTrial(){
        int coachID = ((PlaygroundClientProxy) Thread.currentThread()).getCoachId();
        ((PlaygroundClientProxy) Thread.currentThread()).setCoachState(CoachStates.WATCH_TRIAL);
        repos.setCoachState(coachID,CoachStates.WATCH_TRIAL);
        System.out.println("watching trial");

        try {
            while(!endedTrial){
                wait();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println("Trial has been watched");
    }
    /**
     * Auxiliary function to update the rope value
     * 
     * @param value value of the rope ater being pulled by a contestant
     */
    public synchronized void moveRope(int value){
        rope += value;
        repos.setRopePosition(rope);
    }

    /**
     * terminate the playground
     */
    public synchronized void terminate(){
        ServerPlayground.waitConnection = false;
    }
}
