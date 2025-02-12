package commInfra;

import java.io.*;
import java.util.Arrays;

import ServerSide.main.SimulPar;
import genclass.GenericIO;

/**
 *   Internal structure of the exchanged messages.
 *
 *   Implementation of a client-server model of type 2 (server replication).
 *   Communication is based on a communication channel under the TCP protocol.
 */

public class Message implements Serializable
{
  /**
   *  Serialization key.
   */

   private static final long serialVersionUID = 2021L;

  /**
   *  Message type.
   */

   private int msgType = -1;

  /**
   *  Coach identification.
   */

   private int coachId = -1;

  /**
   *  Coach state.
   */

   private int coachState = -1;

   /**
   *  Contestant identification.
   */

   private int contestantId = -1;

  /**
   *  Contestant state.
   */

   private int contestantState = -1;

  /**
   * Contestant strength.
    */ 
   private int contestantStrength = -1;

   /**
   *  Referee identification.
   */

   private int refereeId = -1;

  /**
   *  Referee state.
   */

   private int refereeState = -1;

   /**
   *  Trial decision flag.
   */

   private boolean trialDecision = false;

   /**
   *  Game is over flag.
   */

   private boolean gameOver = false;

   /**
   *  Game is terminating flag.
   */

   private boolean coachTerminated = false;

   /**
   *  Position of the contestants playing.
   */

   private int[] contestantPosition = new int[SimulPar.nPlayers];

   /**
   *  Trial number.
   */

   private int trialNumber = -1;

   /**
   *  Position of the rope.
   */

   private int ropePosition = -1;

   /**
   *  Game number.
   */

   private int gameNumber = -1;

   /**
   *  A Game's winning team number.
   */

   private int teamGameWinner = -1;

   /**
   *  A Match's winning team number.
   */

   private int teamMatchWinner = -1;

   /**
   *  Knockout flag.
   */

   private boolean knockout = false;


  /**
   *  End of operations (referee).
   */

   private boolean endOp = false;

  /**
   *  Name of the logging file.
   */

   private String fName = null;

  /**
   *  Message instantiation (form 1).
   *
   *     @param type message type
   */

   public Message (int type)
   {
      msgType = type;
   }

   /**
   *  Message instantiation (form 2).
   *
   *     @param type message type
   *     @param name name of the logging file
   */

   public Message (int type, String name)
   {
      msgType = type;
      fName = name;
   }

  /**
   *  Message instantiation (form 3).
   *
   *     @param type message type
   *     @param id coach / contestant / referee identification
   *     @param state coach / contestant / referee state
   */

   public Message (int type, int id, int state)
   {
      msgType = type;
      if ((msgType == MessageType.SRREVNOT) || (msgType == MessageType.REPREVNOT) || (msgType == MessageType.SRCALLCONT) || 
          (msgType == MessageType.REPCALLCONT) || (msgType == MessageType.SRSETCOASTAT) || (msgType == MessageType.REPSETCOASTAT) || 
          (msgType == MessageType.SRWTFREFCOM) ||
          (msgType == MessageType.SRWTFCONT) || (msgType == MessageType.REPWTFCONT) ||
          (msgType == MessageType.SRWTCHTRL) || (msgType == MessageType.REPWTCHTRL) ||
          (msgType == MessageType.SRINFREF) || (msgType == MessageType.REPINFREF)
          ) //coach
         { 
           coachId= id;
           coachState = state;
         }
      else if(( (msgType == MessageType.SRAMDONE) || (msgType == MessageType.REPAMDONE))){
            contestantId = id;
            contestantState = state;
      }
      else if ((msgType == MessageType.SRSTRTRL) || (msgType == MessageType.REPSTRTRL) || 
               (msgType == MessageType.SRASSTRLDSN) || (msgType == MessageType.REPASSTRLDSN)||
               (msgType == MessageType.SRDCLGMWIN) ||
               (msgType == MessageType.SRDCLMTWIN ) || (msgType == MessageType.REPDCLMTWIN )||
               (msgType == MessageType.SRCALLTRL) || (msgType == MessageType.REPCALLTRL)||
               (msgType == MessageType.SRSTRGM) || (msgType == MessageType.REPSTRGM)) //referee
               { 
                 refereeId= id;
                 refereeState = state;
               }
            

                 else { GenericIO.writelnString ("(Form 3) Message type = " + msgType + ": non-implemented instantiation!");
                        System.exit (1);
                      }
   }


   /**
   *  Message instantiation (form 4).
   *
   *     @param type message type
   *     @param contId contestant identification
   *     @param coaId coach identification
   *     @param state contestant state
   *     @param value int value
   */
   public Message (int type, int contId,int coaId,int state, int value){
      msgType = type;
      if ((msgType == MessageType.SRSTDWN) || (msgType == MessageType.REPSTDWN) || 
          (msgType == MessageType.SRPUTRP) || (msgType == MessageType.REPPUTRP))
          {
            contestantId = contId;
            coachId = coaId;
            contestantState = state;
            contestantStrength = value;
      }
      else if ((msgType == MessageType.SRGTRDY) || (msgType == MessageType.REPGTRDY))
               { 
                 contestantId = contId;
                 coachId = coaId;
                 contestantState = state;
               }
      else { GenericIO.writelnString ("(Form 4) Message type = " + msgType + ": non-implemented instantiation!");
                        System.exit (1);
           }

   }

   /**
   *  Message instantiation (form 5).
   *
   *     @param type message type   
   *     @param coaId coach identification
   *     @param contId contestant identification
   *     @param value int value
   */
  public Message (int type,int coaId, int contId,int value){
   msgType = type;
   if ((msgType == MessageType.SRSETCONTSTAT) || (msgType == MessageType.REPSETCONTSTAT))
       {
         coachId = coaId;
         contestantId = contId;
         contestantState = value;
      }
   
   else if ((msgType == MessageType.SRSETCONTSTRG) || (msgType == MessageType.REPSETCONTSTRG))
      {
        coachId = coaId;
        contestantId = contId;
        contestantStrength = value;
     }

   else { GenericIO.writelnString ("(Form 5) Message type = " + msgType + ": non-implemented instantiation!");
                     System.exit (1);
        }

   }


   /**
   *  Message instantiation (form 6).
   *
   *     @param type message type
   *     @param value trial Numer/ropePosition/gameNumber/teamMach Winner
   */

   public Message (int type, int value)
   {
      msgType = type;
      if ((msgType == MessageType.SRSETREFSTAT) || (msgType == MessageType.REPSETREFSTAT)){
         refereeState = value;
      }
      else if (((msgType == MessageType.SRSETTRLNUM) || (msgType == MessageType.REPSETTRLNUM))){
         trialNumber = value;
      }
      else if (((msgType == MessageType.SRSETPOSROPE) || (msgType == MessageType.REPSETPOSROPE))){
         ropePosition = value;
      }
      else if (((msgType == MessageType.SRSETGMNUM) || (msgType == MessageType.REPSETGMNUM))){
         gameNumber = value;
      }
      else if (((msgType == MessageType.SRSETMTWIN) || (msgType == MessageType.REPSETMTWIN))){
         teamMatchWinner = value;
      }      
      else { GenericIO.writelnString ("(Form 6) Message type = " + msgType + ": non-implemented instantiation!");
      System.exit (1);
    }
   }

   /**
   *  Message instantiation (form 7).
   *
   *     @param type message type
   *     @param coaId coach identification
   *     @param value array value
   */

   public Message (int type, int coaId, int[] value)
   {
      msgType = type;
      if ((msgType == MessageType.SRSETCONTPOS) || (msgType == MessageType.REPSETCONTPOS)){
         coachId = coaId;
         contestantPosition = value;
      }
      else { GenericIO.writelnString ("(Form 7) Message type = " + msgType + ": non-implemented instantiation!");
      System.exit (1);
    }
   }

     /**
   *  Message instantiation (form 8).
   *
   *     @param type message type
   *     @param intvalue int value
   *     @param bolvalue boolean value
   */

   public Message (int type, int intvalue, boolean bolvalue)
   {
      msgType = type;
      if ((msgType == MessageType.SRSETGMWIN) || (msgType == MessageType.REPSETGMWIN) || (msgType == MessageType.REPDCLMTWIN)){
         teamGameWinner = intvalue;
         knockout = bolvalue;
      }
      else if((msgType == MessageType.REPASSTRLDSN)){
         refereeId = intvalue;
         trialDecision = bolvalue;
      }else if((msgType == MessageType.REPDCLGMWIN)){
         refereeId = intvalue;
         gameOver = bolvalue;
      }else if((msgType == MessageType.REPWTFREFCOM)){
         coachId = intvalue;
         coachTerminated = bolvalue;
      }
      else { GenericIO.writelnString ("(Form 8) Message type = " + msgType + ": non-implemented instantiation!");
      System.exit (1);
    }
   }


   /**
   *  Message instantiation (form 9).
   *
   *     @param type message type
   *     @param endOp end of the operations flag
   */

   public Message (int type, Boolean endOp)
   {
      msgType = type;
      this.endOp = endOp;
   }


  /**
   *  Getting message type.
   *
   *     @return message type
   */

   public int getMsgType ()
   {
      return (msgType);
   }

  /**
   *  Getting coach identification.
   *
   *     @return coach identification
   */

   public int getCoachId ()
   {
      return (coachId);
   }

  /**
   *  Getting coach state.
   *
   *     @return coach state
   */

   public int getCoachState ()
   {
      return (coachState);
   }

   /**
   *  Getting contestant identification.
   *
   *     @return contestant identification
   */

   public int getContestantId ()
   {
      return (contestantId);
   }

  /**
   *  Getting contestant state.
   *
   *     @return contestant state
   */

   public int getContestantState ()
   {
      return (contestantState);
   }

   /**
    * Getting contestant strength.
    * 
    * @return contestnant strength
    */
   public int getContestantStrength() {
       return contestantStrength;
   }

   /**
   *  Getting referee identification.
   *
   *     @return referee identification
   */

   public int getRefereeId ()
   {
      return (refereeId);
   }

   /**
   *  Getting referee state.
   *
   *     @return referee state
   */

   public int getRefereeState ()
   {
      return (refereeState);
   }

   /**
    * Getter for trial Number
    * @return trial Number
    */
   public int getTrialNumber(){
      return (trialNumber);
   }

   /**
    * Getter for rope position
    * @return rope position
    */
   public int getRopePosition(){
      return (ropePosition);
   }

   /**
    * Getter for contestant position
    * @return contestant position
    */
   public int[] getContestantPosition(){
      return (contestantPosition);
   }

   /**
    * Getter for game number
    * @return game number
    */
   public int getGameNumber(){
      return (gameNumber);
   }

   /**
    * Getter for team game winner
    * @return team game winner
    */

   public int getGameWinner(){
      return (teamGameWinner);
   }

   /**
    * Getter for team match winner
    * @return team match winner
    */
   
   public int getMatchWinner(){
      return (teamMatchWinner);
   }

   /**
   *  Getting trial decision flag.
   *
   *     @return trial decision flag
   */


   public boolean assertTrialDecion(){
      return (trialDecision);
   }

   /**
   *  Getting game over flag.
   *
   *     @return game over flag
   */

   public boolean gameDone(){
      return (gameOver);
   }

   /**
   *  Getting coach terminate flag.
   *
   *     @return coach terminate flag
   */

   public boolean coachTerminate(){
      return (coachTerminated);
   }

   /**
   *  Getting knockout flag.
   *
   *     @return knockout flag
   */

   public boolean getKnockout(){
      return (knockout);
   }


  /**
   *  Getting end of operations flag (barber).
   *
   *     @return end of operations flag
   */

   public boolean getEndOp ()
   {
      return (endOp);
   }

  /**
   *  Getting name of logging file.
   *
   *     @return name of the logging file
   */

   public String getLogFName ()
   {
      return (fName);
   }

  /**
   *  Printing the values of the internal fields.
   *
   *  It is used for debugging purposes.
   *
   *     @return string containing, in separate lines, the pair field name - field value
   */

   @Override
   public String toString ()
   {
      return ("Message type = " + msgType +
              "\nCoach Id = " + coachId +
              "\nCoach State = " + coachState +
              "\nContestant Id = " + contestantId +
              "\nContestant State = " + contestantState +
              "\nContestant Strength = " + contestantStrength +
              "\nReferee Id = " +refereeId +
              "\nReferee State = " + refereeState +
              "\nTrial has been decided = " + trialDecision +
              "\nGame is over = " + gameOver +
              "\nContestants playing position = " + Arrays.toString(contestantPosition) +
              "\nTrial number = " + trialNumber +
              "\nPosition of the rope = " + ropePosition +
              "\nGame number = " + gameNumber +
              "\nTeam game winner = " + teamGameWinner +
              "\nTeam match winner = " + teamMatchWinner +
              "\nKnockout happened = " + knockout +
              "\nEnd of Operations (barber) = " + endOp +
              "\nName of logging file = " + fName);
   }
}

