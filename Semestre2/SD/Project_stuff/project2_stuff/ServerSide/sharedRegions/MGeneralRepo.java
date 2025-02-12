package ServerSide.sharedRegions;

import ClientSide.entities.CoachStates;
import ClientSide.entities.ContestantStates;
import ClientSide.entities.RefereeStates;
import ServerSide.main.ServerGeneralRepo;
import ServerSide.main.SimulPar;
import java.util.Objects;
import genclass.TextFile;
import genclass.GenericIO;


/**
 * General Repository
 * 
 * It is used to provide information on the internal state 
 * of the problem and generate the logging file. 
 * 
 */

public class MGeneralRepo {

    /**
     * The name of the logging file
     */

    private String logFileName;

    /**
     * State of the referee
     */

    private int refereeState;

    /**
     * State of each coach
     */

    private int coachState[];

    /**
     * State of each contestant
     */

    private int contestantState[][];

    /**
     * Number of the current trial
     */

    private int trialNumber;

    /**
     * Strength of each contestant
     */

    private int contStrength[][];

    /**
     * Position of the centre of the rope during the trial
     */

    private int positionRope;

    /**
     * Contestants chosen to pull the rope 
     */

    private static int contestantPullingPosition[][];

    /**
     * Number of the current game
     */

    private static int gameNumber;

    /**
     * The score of a team
     */

    private int teamScore[];

    /**
     * Identify if the game result was a draw
     */

    private boolean gameDraw;

    /**
     * Identify if the game was won by a knockout
     */

    private boolean gameKnockout;

    /**
     * Winner of a game
     */
    private int gameWinner;

    /**
     * Winner of the match
     */

    private int matchWinner;

    /**
     * Identify if the match result was a draw
     */

    private boolean matchDraw;


    /**
     * Instatiation of the general repository object
     * 
     * @param logFileName name of the file output
     */

    public MGeneralRepo(String logFileName){

        if ((logFileName == null ) || Objects.equals(logFileName,"")){
            this.logFileName = "logger" ;
        }
        else{
             this.logFileName = logFileName;
        }
        
        gameNumber=0;

        teamScore = new int [SimulPar.nCoaches];

        refereeState = RefereeStates.START_OF_THE_MATCH;

        coachState = new int [SimulPar.nCoaches];

        contestantPullingPosition = new int [SimulPar.nCoaches][SimulPar.nPlayers];

        contStrength = new int [SimulPar.nCoaches][SimulPar.nContestants];

        for(int i = 0; i < SimulPar.nCoaches; i++){
            coachState[i] = CoachStates.WAIT_FOR_REFEREE_COMMAND;
            for(int j = 0; j< SimulPar.nPlayers; j++){
                contestantPullingPosition[i][j] = -1;
            }
        }

        contestantState = new int [SimulPar.nCoaches][SimulPar.nContestants];
        for (int i = 0; i < SimulPar.nCoaches; i++){
            for (int j = 0; j < SimulPar.nContestants; j++){
                contestantState[i][j] = ContestantStates.SEAT_AT_THE_BENCH;
            }
        }
        
        reportInitialStatus();

    }

    /**
     * Instatiation of the general repository object without a log file name
     * 
     */

     public MGeneralRepo(){

        this.logFileName = "logger" ;
        
        gameNumber=0;

        teamScore = new int [SimulPar.nCoaches];

        refereeState = RefereeStates.START_OF_THE_MATCH;

        coachState = new int [SimulPar.nCoaches];

        contestantPullingPosition = new int [SimulPar.nCoaches][SimulPar.nPlayers];

        contStrength = new int [SimulPar.nCoaches][SimulPar.nContestants];

        for(int i = 0; i < SimulPar.nCoaches; i++){
            coachState[i] = CoachStates.WAIT_FOR_REFEREE_COMMAND;
            for(int j = 0; j< SimulPar.nPlayers; j++){
                contestantPullingPosition[i][j] = -1;
            }
        }

        contestantState = new int [SimulPar.nCoaches][SimulPar.nContestants];
        for (int i = 0; i < SimulPar.nCoaches; i++){
            for (int j = 0; j < SimulPar.nContestants; j++){
                contestantState[i][j] = ContestantStates.SEAT_AT_THE_BENCH;
            }
        }
        
        reportInitialStatus();

    }

    /**
     * Set Referee State
     * 
     * @param state State of the referee
     */

    public synchronized void setRefereeState(int state){
        refereeState = state;
        reportStatus();
    }

    /**
     * Set Coach State
     * 
     * @param coachId Coaches ID
     * @param state State of the coach
     */

    public synchronized void setCoachState(int coachId, int state){
        coachState[coachId] = state;
        reportStatus();
    }

    /**
     * Set Contestant State
     * 
     * @param coachID Coaches ID
     * @param contestantId Contestants ID
     * @param state State of the contestant
     */

    public synchronized void setContestantState(int coachID, int contestantId, int state){
        contestantState[coachID][contestantId] = state;
        //If there is more than 3 contestants in state DO_YOUR_BEST trow an error
        int count = 0;
        for(int i = 0; i < SimulPar.nContestants; i++){
            if(contestantState[coachID][i] == ContestantStates.DO_YOUR_BEST){
                count++;
            }
        }
        if(count > 3){
            System.out.println("ERROR: More than 3 contestants are pulling the rope for the coach " + coachID);
            System.exit(1);
        }
        //reportStatus();
    }


    /**
     * Set the strength of the contestant
     * 
     * @param coachID Coaches ID
     * @param contID Contestants ID
     * @param Strength Strength of the contestant
     */

    public synchronized void setContestantStrength(int coachID,int contID, int Strength){
        contStrength[coachID][contID] = Strength;

    }

    /**
     * Set contestant pulling the rope
     * 
     * @param coachID Coaches ID
     * @param position Array containing the id's of the contestants chosen to pull the rope
     */

    public synchronized void setContestantPosition(int coachID,int[]position){
        for(int i = 0; i < SimulPar.nPlayers; i++){
            contestantPullingPosition[coachID][i] = position[i] + 1;
        }
       reportStatus();
        
    }

    /**
     * Set trial number
     * 
     * @param number Number of the trial
     */

    public synchronized void setTrialNumber(int number){

        trialNumber = number;

    }

    /**
     * Set the position of the centre of the rope at the beggining of the trial 
     * 
     * @param position Position of the rope
     */


    public synchronized void setRopePosition(int position){

        positionRope=position;
        reportStatus();

    }

    /**
     * Set game number 
     * 
     * @param number Number of the game
     */


     public synchronized void setGameNumber(int number){

        gameNumber=number;
        reportCurrentGameStatus();
     
     }

     /**
     * Set the winner of a game
     * @param team Team that won the game
     * @param knockout Identify if the game was won by knockout
     */

     public synchronized void setGameWinner(int team, boolean knockout){

        gameWinner = team;
        teamScore[team]++;
        gameKnockout = knockout;
        gameDraw=false;
        reportCurrentGameResult();
     }

     /**
      * Set the result of a game as a draw
      */

      public synchronized void setGameDraw(){

        gameDraw=true;
        reportCurrentGameResult();
        
     }

     /**
      * Update the general repository
      * In use to update contestants states all at once (caled by contestants when they all are in the same state)
      */
     public synchronized void basic_update(){
        reportStatus();
     }

     /**
      * Set the winner of the match
      * @param team Team that won the match
      */

     public synchronized void setMatchWinner(int team){

        matchWinner = team;
        matchDraw=false;
        reportMatchResult();

     }

     /**
      * Set the result of a game as a draw
      */

      public synchronized void setMatchDraw(){

        matchDraw=true;
        reportMatchResult();
        
     }


    /**
     * Write the header to the logger file 
     * 
     */

    private void reportInitialStatus(){

        TextFile log = new TextFile(); //instatiation of a text file log

        if(!log.openForWriting(".", logFileName)){
            GenericIO.writeString("The operation of creating the file " + logFileName + " failed!");
            System.exit(1);
        }
        log.writelnString("                               Game of The Rope - Description of the internal state \n");
        log.writelnString("Ref Coa 1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa 2 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5       Trial");
        log.writelnString("Sta  Stat Sta SG Sta SG Sta SG Sta SG Sta SG Stat  Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS");
        log.writelnString("Game " + (gameNumber+1));
        if (!log.close()){
            GenericIO.writeString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }

    }
    /**
     * Write the header to the logger file when the game changes
     * 
     */

    private void reportCurrentGameStatus(){

        TextFile log = new TextFile(); //instatiation of a text file log

        if(!log.openForAppending(".", logFileName)){
            GenericIO.writeString("The operation of creating the file " + logFileName + " failed!");
            System.exit(1);
        }
        if (gameNumber < SimulPar.nGames){

        log.writelnString("Game "+ (gameNumber+1));
        log.writelnString("Ref Coa 1 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5 Coa 2 Cont 1 Cont 2 Cont 3 Cont 4 Cont 5       Trial");
        log.writelnString("Sta  Stat Sta SG Sta SG Sta SG Sta SG Sta SG Stat  Sta SG Sta SG Sta SG Sta SG Sta SG 3 2 1 . 1 2 3 NB PS");
        }
        if (!log.close()){
            GenericIO.writeString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }

    }


    /**
     * Write the current status to the logger file
     * 
     */

    private void reportStatus(){
        
        TextFile log = new TextFile(); //instatiation of a text file log

        String lineStatus = "";        //state line to be printed 

        if(!log.openForAppending(".", logFileName)){
            GenericIO.writeString("The operation of opening for appending the file " + logFileName + " failed!");
            System.exit(1);
        }

        switch(refereeState){
            case RefereeStates.START_OF_THE_MATCH:
                lineStatus += "001 ";
                break;
            
            case RefereeStates.START_OF_A_GAME:
                lineStatus += "002 ";
                break;

            case RefereeStates.TEAMS_READY:
                lineStatus += "003 ";
                break;
            
            case RefereeStates.WAIT_FOR_TRIAL_CONCLUSION:
                lineStatus += "004 ";
                break;

            case RefereeStates.END_OF_A_GAME:
                lineStatus += "005 ";
                break;
            
            case RefereeStates.END_OF_A_MATCH:
                lineStatus += "006 ";
                break;
        }

        for(int i = 0; i < SimulPar.nCoaches; i++){
            switch (coachState[i]) {
                case CoachStates.WAIT_FOR_REFEREE_COMMAND:
                    lineStatus += "  001 ";
                    break;

                case CoachStates.ASSEMBLE_TEAM:
                    lineStatus += "  002 ";
                    break;
                
                case CoachStates.WATCH_TRIAL:
                    lineStatus += "  003 ";
                    break;
            }

            for(int j = 0; j < SimulPar.nContestants; j++){
                switch (contestantState[i][j]) {
                    case ContestantStates.SEAT_AT_THE_BENCH:
                        lineStatus += "001 ";
                        break;

                    case ContestantStates.STAND_IN_POSITION:
                        lineStatus += "002 ";
                        break;

                    case ContestantStates.DO_YOUR_BEST:
                        lineStatus += "003 ";
                        break;
                }
                if(contStrength[i][j] < 10){
                    lineStatus += " " + contStrength[i][j] + " ";
                }
                else{
                    lineStatus += contStrength[i][j] + " ";
                }
            }
        }

        for(int i = 0; i <  SimulPar.nCoaches;i++){
            for(int j = 0; j < SimulPar.nPlayers; j++){

                if(contestantPullingPosition[i][j] > 0){
                    lineStatus += contestantPullingPosition[i][j]+" ";   
                }else{
                    lineStatus += "- ";
                }
            }
            if(i==0){
                lineStatus +=". ";
            }

        }
        if(trialNumber == 0){
            lineStatus += "-- ";
        }else if(trialNumber > 9){
            lineStatus += "" + trialNumber + " ";
        }
        else{
            lineStatus += "0" + trialNumber + " ";
        }

            
        if(refereeState != RefereeStates.WAIT_FOR_TRIAL_CONCLUSION ){
            lineStatus += "-- ";    
        }else if(positionRope > 9 || positionRope < 0){
            lineStatus += "" + positionRope;
        }
        else{
            lineStatus += "0" + positionRope;
        }
        
        log.writelnString(lineStatus);

        if (!log.close()){
            GenericIO.writeString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }
    }
    
    /**
     * Write the result of the current game in the logger file
     */

    private void reportCurrentGameResult(){
        
        TextFile log = new TextFile(); //instatiation of a text file log
        
        String lineStatus = "";        //state line to be printed 
        
        if(!log.openForAppending(".", logFileName)){
            GenericIO.writeString("The operation of creating the file " + logFileName + " failed!");
            System.exit(1);
        }
        
        if (gameDraw){
            lineStatus += "Game "+ (gameNumber+1) + " was a draw.";
        }
        else{
            lineStatus += "Game "+ (gameNumber+1) + " was won by team " + (gameWinner+1) + " by ";
            
            if(gameKnockout){
                
                lineStatus += "knock out in " + trialNumber + " trials.";
                
            }
            
            else{
                
                lineStatus += "points.";
                
            }
        }
        trialNumber = 0;

        log.writelnString(lineStatus);
        
        

        if (!log.close()){
            GenericIO.writeString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }

    }

    /**
     * Write the result of the match in the logger file
     */

    private void reportMatchResult(){

        TextFile log = new TextFile(); //instatiation of a text file log

        String lineStatus = "";        //state line to be printed 

        if(!log.openForAppending(".", logFileName)){
            GenericIO.writeString("The operation of creating the file " + logFileName + " failed!");
            System.exit(1);
        }

        if (matchDraw){
            lineStatus += "Match was a draw.";
        }
        else{
            lineStatus += "Match was won by team "+ (matchWinner+1) + " (" + teamScore[0] + "-" + teamScore[1] +").";
        }

        log.writelnString(lineStatus);

        if (!log.close()){
            GenericIO.writeString("The operation of closing the file " + logFileName + " failed!");
            System.exit(1);
        }

    }
    
    /**
     * terminate the general repository
     */

    public synchronized void terminate(){
        ServerGeneralRepo.waitConnection = false;
    }
    
    
}
