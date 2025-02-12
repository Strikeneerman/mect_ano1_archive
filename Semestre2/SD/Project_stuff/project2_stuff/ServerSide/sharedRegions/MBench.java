package ServerSide.sharedRegions;

import ServerSide.entities.BenchClientProxy;
import ServerSide.main.ServerBench;
import ServerSide.main.SimulPar;
import ClientSide.stubs.SGeneralRepo;
import ClientSide.entities.CoachStates;
import ClientSide.entities.ContestantStates;

/**
 * Bench monitor
 * <p> 
 * It simulates the bench where the contestants wait and the coach calls the contestants to play the game
 */
public class MBench {
    //array of contestants to play the game
    private static int[][] contestantsPlaying;
    private static boolean[][] notifyContestants;
    //array containing contestants strength
    private int[][] contestantsStrength;

    //number of contestants on the bench for each coach
    private int[] nContestants;
    
    private int giveStrength[][];

    private boolean terminate;

    private final SGeneralRepo repos;

    /**
     * Constructor for the bench
     * 
     * @param repos reference to the general repository
     * 
     */
    public MBench(SGeneralRepo repos) {
        contestantsPlaying = new int[SimulPar.nCoaches][SimulPar.nPlayers];
        notifyContestants = new boolean[SimulPar.nCoaches][SimulPar.nContestants];
        contestantsStrength = new int[SimulPar.nCoaches][SimulPar.nContestants];
        nContestants = new int[SimulPar.nCoaches];
        giveStrength = new int[SimulPar.nCoaches][SimulPar.nContestants];
        terminate = false;
        this.repos = repos;
        //initialize the arrays
        for (int i = 0; i < SimulPar.nCoaches; i++) {
            for (int j = 0; j < SimulPar.nPlayers; j++) {
                contestantsPlaying[i][j] = -1;
            }
        }

        for (int i = 0; i < SimulPar.nCoaches; i++) {
            for (int j = 0; j < SimulPar.nContestants; j++) {
                contestantsStrength[i][j] = 0;
                notifyContestants[i][j] = false;
                giveStrength[i][j] = 0;
            }
        
        }
    }
    
    /**
     * Function for the contestant to sit down
     * <p>
     * Contestants sit down on the bench waiting for the coach to call them or gaining strength
     * @param strength Contestants Strength
     * @return boolean true if the match is over
     */

    public synchronized int sitDown(int strength) {   
        int contestantId = ((BenchClientProxy) Thread.currentThread()).getContestantId();
        int coachId = ((BenchClientProxy) Thread.currentThread()).getCoachId();
        nContestants[coachId]++;
        System.out.printf("(%d) Contestant %d from coach %d is sitting down\n",nContestants[coachId],contestantId, coachId);
        contestantsStrength[coachId][contestantId] = strength;
        repos.setContestantState(coachId,contestantId, ContestantStates.SEAT_AT_THE_BENCH); 
        ((BenchClientProxy) Thread.currentThread()).setContestantState(ContestantStates.SEAT_AT_THE_BENCH);

        repos.setContestantStrength(coachId,contestantId,contestantsStrength[coachId][contestantId]);
        
        //last Contestant sit down
        if (nContestants[coachId] == SimulPar.nContestants) {
            notifyAll();
            repos.basic_update();
        }
        
        try {
            while(!notifyContestants[coachId][contestantId] && !terminate){
                wait();
                
                if(giveStrength[coachId][contestantId] > 0){
                    giveStrength[coachId][contestantId]--;
                    contestantsStrength[coachId][contestantId] += 1;
                    repos.setContestantStrength(coachId,contestantId,contestantsStrength[coachId][contestantId]);
                    System.out.printf("Contestant %d from coach %d gained strength\n", contestantId, coachId);
                }
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        if (terminate) return -1;

        nContestants[coachId]--;

        notifyContestants[coachId][contestantId] = false;

        return contestantsStrength[coachId][contestantId];
    }
    
    /**
     * Coach calls the contestants to stand in position
     */

    public synchronized void callContestants() {
        
        //get Coach id
        int coachId = ((BenchClientProxy) Thread.currentThread()).getCoachId();
        ((BenchClientProxy) Thread.currentThread()).setCoachState(CoachStates.ASSEMBLE_TEAM);
        repos.setCoachState(coachId,CoachStates.ASSEMBLE_TEAM);
        
        if (nContestants[coachId] != SimulPar.nContestants){
            throw new RuntimeException("Not all contestants are seated");
        }

        //sees the coach has at least 3 contestants with more than 0 strength
        int nPlayers = 0;
        for (int i = 0; i < SimulPar.nContestants; i++) {
            if (contestantsStrength[coachId][i] > 0){
                nPlayers++;
            }
        }
        if (nPlayers < SimulPar.nPlayers){
            throw new RuntimeException("Not enough players with strength to play the game");
        }

        //goes to the ContestantsStrength array and picks the first 3 contestants with most strength
        // int contestant_picked[] = new int[SimulPar.nPlayers];
        boolean picked[] = new boolean[SimulPar.nContestants]; // to keep track of picked contestants

        for (int i = 0; i < SimulPar.nPlayers; i++) {
            int max = 0;
            int maxIndex = 0;
            
            //goes through the contestants and puts them in the contestant_picked if theres an will replace the one with less strength
            for (int j = 0; j < SimulPar.nContestants; j++) {
                if (!picked[j] && contestantsStrength[coachId][j] > max){
                    max = contestantsStrength[coachId][j];
                    maxIndex = j;
                }
            }
            // contestant_picked[i] = maxIndex;
            contestantsPlaying[coachId][i] = maxIndex;
            notifyContestants[coachId][maxIndex] = true; // notify the contestant that he was picked
            picked[maxIndex] = true; // mark the contestant as picked
        }

        repos.setContestantPosition(coachId,contestantsPlaying[coachId]);
        
        //checks if the sum of the given strength is 0
        int sum = 0;
        for(int i = 0; i < SimulPar.nContestants; i++){
            sum = giveStrength[coachId][i];
        }
        if(sum != 0){
            throw new RuntimeException("Some contestants didn't get strength!");
        }

        //for the players that wheren't called to play the game add them to the giveStrength array
        for (int i = 0; i < SimulPar.nContestants; i++) {
            if (contestantsPlaying[coachId][0] != i && contestantsPlaying[coachId][1] != i && contestantsPlaying[coachId][2] != i){
                giveStrength[coachId][i] = 1;
            }
        }
        
        //notify all the players that they can leave the bench
        notifyAll();
    }

    
    /**
     * Coach will wait for all contestants to sit down
     */
    public synchronized void reviewNotes(){
        int coachId = ((BenchClientProxy) Thread.currentThread()).getCoachId();
        repos.setContestantPosition(coachId,contestantsPlaying[coachId]);
        try {
            while(nContestants[coachId] != SimulPar.nContestants){
                System.out.println("Waiting for all contestants to sit down");
                wait();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        ((BenchClientProxy) Thread.currentThread()).setCoachState(CoachStates.WAIT_FOR_REFEREE_COMMAND);
        repos.setCoachState(coachId,CoachStates.WAIT_FOR_REFEREE_COMMAND);
        
    }
    
    /**
     * Coach will call this function to inform the contestants that the match is over
     */
    public synchronized void terminate(){
        terminate = true;
        notifyAll();
        ServerBench.waitConnection = false;
    }
}
