package commInfra;

/**
 *   Type of the exchanged messages.
 *   the
 *   Implementation of a client-server model of type 2 (server replication).
 *   Communication is based on a communication channel under the TCP protocol.
 */
public class MessageType
{
  /**
   *  Initialization of the logging file name and the number of iterations (service request).
   */

   public static final int SETNFIC = 1;

  /**
   *  Logging file was initialized (reply).
   */

   public static final int NFICDONE = 2;

   /**
    * Server shutdown (service request)
    */

    public static final int SHUT = 3;

    /**
    * Server was shutdown (reply)
    */

    public static final int SHUTDONE = 4;
    


    //----------------Bench---------------------//

    /**
     * Contestant - sitDown (service request)
     */

     public static final int SRSTDWN = 5;

     /**
     * Contestant - sitDown (reply)
     */

     public static final int REPSTDWN = 6;

     /**
     * Coach - reviewNotes (service request)
     */

     public static final int SRREVNOT = 7;

     /**
     * Coach - reviewNotes (reply)
     */

     public static final int REPREVNOT = 8;

     /**
     * Coach - callContestants (service request)
     */

     public static final int SRCALLCONT = 9;

     /**
     * Coach - callContestants (reply)
     */

     public static final int REPCALLCONT = 10;


     //----------------Playground---------------------//

     /**
     * Contestant - getReady (service request)
     */

     public static final int SRGTRDY = 11;

     /**
     * Contestant - getReady (reply)
     */

     public static final int REPGTRDY = 12;

     /**
     * Contestant - pullTheRope (service request)
     */

     public static final int SRPUTRP = 13;

     /**
     * Contestant - pullTheRope (reply)
     */

     public static final int REPPUTRP = 14;

     /**
     * Contestant - amDone (service request)
     */

     public static final int SRAMDONE = 15;

     /**
     * Contestant - amDone (reply)
     */

     public static final int REPAMDONE = 16;

     /**
     * Coach - waitForContestants (service request)
     */

     public static final int SRWTFCONT = 17;

     /**
     * Coach - waitForContestants (reply)
     */

     public static final int REPWTFCONT = 18;

     /**
     * Coach - watchTrial(service request)
     */

     public static final int SRWTCHTRL = 19;

     /**
     * Coach - watchTrial (reply)
     */

     public static final int REPWTCHTRL = 20;

     /**
     * Referee - startTrial(service request)
     */

     public static final int SRSTRTRL = 21;

     /**
     * Referee - startTrial (reply)
     */

     public static final int REPSTRTRL = 22;

     /**
     * Referee - assertTrialDecision(service request)
     */

     public static final int SRASSTRLDSN = 23;

     /**
     * Referee - assertTrialDecision (reply)
     */

     public static final int REPASSTRLDSN = 24;

     /**
     * Referee - declareGameWinner(service request)
     */

     public static final int SRDCLGMWIN = 25;

     /**
     * Referee - declareGameWinner (reply)
     */

     public static final int REPDCLGMWIN = 26;

     /**
     * Referee - declareMatchWinner(service request)
     */

     public static final int SRDCLMTWIN = 27;

     /**
     * Referee - declareMatchWinner (reply)
     */

     public static final int REPDCLMTWIN = 28;



     //----------------Referee site---------------------//

     /**
     * Coach - waitForRefereeCommand (service request)
     */

     public static final int SRWTFREFCOM = 29;

     /**
     * Coach - waitForRefereeCommand (reply)
     */

     public static final int REPWTFREFCOM = 30;

     /**
     * Coach - informReferee (service request)
     */

     public static final int SRINFREF = 31;

     /**
     * Coach - informReferee (reply)
     */

     public static final int REPINFREF = 32;

     /**
     * Referee - startGame(service request)
     */

     public static final int SRSTRGM = 33;

     /**
     * Referee - startGame (reply)
     */

     public static final int REPSTRGM = 34;

     /**
     * Referee - callTrial(service request)
     */

     public static final int SRCALLTRL = 35;

     /**
     * Referee - callTrial (reply)
     */

     public static final int REPCALLTRL = 36;



     //----------------General Repo---------------------//

     /**
     * setContestantState (service request)
     */

     public static final int SRSETCONTSTAT = 37;

     /**
     * setContestantState (reply)
     */

     public static final int REPSETCONTSTAT = 38;

     /**
     * setContestantStrength (service request)
     */

     public static final int SRSETCONTSTRG = 39;

     /**
     * setContestantStrength (reply)
     */

     public static final int REPSETCONTSTRG = 40;

     /**
     * basic_update (service request)
     */

     public static final int SRBASUPDT = 41;

     /**
     * basic_update  (reply)
     */

     public static final int REPBASUPDT = 42;

     /**
     * setCoachState (service request)
     */

     public static final int SRSETCOASTAT = 43;

     /**
     * setCoachState  (reply)
     */

     public static final int REPSETCOASTAT = 44;

     /**
     * setContestantPosition (service request)
     */

     public static final int SRSETCONTPOS = 45;

     /**
     * setContestantPosition  (reply)
     */

     public static final int REPSETCONTPOS = 46;

     /**
     * setTrialNumber(service request)
     */

     public static final int SRSETTRLNUM = 47;

     /**
     * setTrialNumber (reply)
     */

     public static final int REPSETTRLNUM = 48;

     /**
     * setRefereeState(service request)
     */

     public static final int SRSETREFSTAT= 49;

     /**
     * setRefereeState (reply)
     */

     public static final int REPSETREFSTAT = 50;

     /**
     * setGameWinner(service request)
     */

     public static final int SRSETGMWIN= 51;

     /**
     * setGameWinner (reply)
     */

     public static final int REPSETGMWIN = 52;

     /**
     * setGameNumber(service request)
     */

     public static final int SRSETGMNUM = 53;

     /**
     * setGameNumber (reply)
     */

     public static final int REPSETGMNUM = 54;

     /**
     * setPositionRope(service request)
     */

     public static final int SRSETPOSROPE = 55;

     /**
     * setPositionRope (reply)
     */

     public static final int REPSETPOSROPE = 56;

     /**
     * setMatchWinner(service request)
     */

     public static final int SRSETMTWIN = 57;

     /**
     * setMatchWinner (reply)
     */

     public static final int REPSETMTWIN = 58;

     /**
     * setMatchDraw(service request)
     */

     public static final int SRSETMTDRAW = 59;

     /**
     * setMatchDraw (reply)
     */

     public static final int REPSETMTDRAW = 60;

     /**
      * setGameDraw(service request)
      */

    public static final int SRSETGMDRAW = 61;

    /**
     * setGameDraw (reply)
     */

    public static final int REPSETGMDRAW = 62;

    /**
     * initSimul (service request)
     */

     public static final int SRPINITSIM = 63;

    /**
     * initSimul (service reply)
     */

     public static final int REPINITSIM = 64;

    /**
     * SACK (service reply)
     */

      public static final int SACK = 65;

}
