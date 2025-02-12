package ClientSide.stubs;

import ClientSide.entities.*;
import commInfra.ClientCom;
import commInfra.Message;
import commInfra.MessageType;
import genclass.GenericIO;

/**
 *  Stub to the playground.
 *
 *    It instantiates a remote reference to the playground.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */


public class SPlayGround {

    /**
   *  Name of the platform where is located the playground server.
   */

   private String serverHostName;

   /**
    *  Port number for listening to service requests.
    */
 
    private int serverPortNumb;
 
   /**
    *   Instantiation of a stub to the barber shop.
    *
    *     @param serverHostName name of the platform where is located the playground server
    *     @param serverPortNumb port number for listening to service requests
    */

    public SPlayGround (String serverHostName, int serverPortNumb)
    {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     * Function to start the trial
     * <p>
     * Referee starts the trial and 
     * wakes up the contestats that are in the Stand In Position state
     */

    public void startTrial ()
    {
        ClientCom com ;                                 //comunication channel
        Message outMessage,                             //outgoing message
                inMessage;                              //incoming message
        // send message to the bench
        com = new ClientCom (serverHostName,serverPortNumb);
        while(!com.open())
        {try 
            {
                Thread.currentThread().sleep((long)(10));
            }catch(InterruptedException e){}
        }
        outMessage = new Message(MessageType.SRSTRTRL,((TReferee) Thread.currentThread()).getRefereeID(),
        ((TReferee) Thread.currentThread()).getRefereeState());
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPSTRTRL){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid message type !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(inMessage.getRefereeId()!= ((TReferee) Thread.currentThread()).getRefereeID()){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid Referee id !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if((inMessage.getRefereeState()<RefereeStates.START_OF_THE_MATCH)||(inMessage.getRefereeState()>RefereeStates.END_OF_A_MATCH)){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid Referee state !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        com.close();
        ((TReferee) Thread.currentThread()).setRefereeState(inMessage.getRefereeState());
        
    }

    /**
     *  Coach function to wait for the contestants
     * 
     */
    
    public void waitForContestants ()
    {
        ClientCom com ;                                 //comunication channel
        Message outMessage,                             //outgoing message
                inMessage;                              //incoming message
        // send message to the bench
        com = new ClientCom (serverHostName,serverPortNumb);
        while(!com.open())
        {try 
            {
                Thread.currentThread().sleep((long)(10));
            }catch(InterruptedException e){}
        }
        outMessage = new Message(MessageType.SRWTFCONT,((TCoach) Thread.currentThread()).getCoachId(),
        ((TCoach) Thread.currentThread()).getCoachState());
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPWTFCONT){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid message type !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(inMessage.getCoachId()!= ((TCoach) Thread.currentThread()).getCoachId()){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid coach id !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if((inMessage.getCoachState()<CoachStates.WAIT_FOR_REFEREE_COMMAND)||(inMessage.getCoachState()>CoachStates.WATCH_TRIAL)){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid coach state !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        com.close();
        ((TCoach) Thread.currentThread()).setCoachState(inMessage.getCoachState());
        
    }


   
    
    /**
     * Referee function to assert trial decision
     * 
     * 
     * @return boolean : whether the trail decision has been made
     */

     public boolean assertTrialDecision ()
    {
        ClientCom com ;                                 //comunication channel
        Message outMessage,                             //outgoing message
                inMessage;                              //incoming message
        // send message to the bench
        com = new ClientCom (serverHostName,serverPortNumb);
        while(!com.open())
        {try 
            {
                Thread.currentThread().sleep((long)(10));
            }catch(InterruptedException e){}
        }
        outMessage = new Message(MessageType.SRASSTRLDSN,((TReferee) Thread.currentThread()).getRefereeID(),
        ((TReferee) Thread.currentThread()).getRefereeState());
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPASSTRLDSN){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid message type !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(inMessage.getRefereeId()!= ((TReferee) Thread.currentThread()).getRefereeID()){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid Referee id !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        com.close();
        return inMessage.assertTrialDecion();
    }

    /**
     * Referee function to declare the game winner
     * 
     * @return boolean : whether the number of games has reached the maximum
     */

     public boolean declareGameWinner ()
    {
        ClientCom com ;                                 //comunication channel
        Message outMessage,                             //outgoing message
                inMessage;                              //incoming message
        // send message to the bench
        com = new ClientCom (serverHostName,serverPortNumb);
        while(!com.open())
        {try 
            {
                Thread.currentThread().sleep((long)(10));
            }catch(InterruptedException e){}
        }
        
        outMessage = new Message(MessageType.SRDCLGMWIN,
                                ((TReferee) Thread.currentThread()).getRefereeID(),
                                ((TReferee) Thread.currentThread()).getRefereeState());
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPDCLGMWIN){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid message type !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(inMessage.getRefereeId()!= ((TReferee) Thread.currentThread()).getRefereeID()){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid Referee id !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        

        com.close();
        return inMessage.gameDone();
    }

    /**
     * Function to declare match winner
     * <p>
     * Referee starts the trial and 
     * wakes up the contestats that are in the Stand In Position state
     */

    public void declareMatchWinner ()
    {
        ClientCom com ;                                 //comunication channel
        Message outMessage,                             //outgoing message
                inMessage;                              //incoming message
        // send message to the bench
        com = new ClientCom (serverHostName,serverPortNumb);
        while(!com.open())
        {try 
            {
                Thread.currentThread().sleep((long)(10));
            }catch(InterruptedException e){}
        }
        outMessage = new Message(MessageType.SRDCLMTWIN,((TReferee) Thread.currentThread()).getRefereeID(),
        ((TReferee) Thread.currentThread()).getRefereeState());
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPDCLMTWIN){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid message type !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(inMessage.getRefereeId()!= ((TReferee) Thread.currentThread()).getRefereeID()){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid Referee id !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if((inMessage.getRefereeState()<RefereeStates.START_OF_THE_MATCH)||(inMessage.getRefereeState()>RefereeStates.END_OF_A_MATCH)){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid Referee state !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        com.close();
        ((TReferee) Thread.currentThread()).setRefereeState(inMessage.getRefereeState());
        
    }

    /**
     * Function to get ready
     * 
     * Contestants enter the playground waiting for the referee to start_trial
     * 
     */

    public void getReady ()
    {
        ClientCom com ;                                 //comunication channel
        Message outMessage,                             //outgoing message
                inMessage;                              //incoming message
        // send message to the bench
        com = new ClientCom (serverHostName,serverPortNumb);
        while(!com.open())
        {try 
            {
                Thread.currentThread().sleep((long)(10));
            }catch(InterruptedException e){}
        }
        outMessage = new Message(MessageType.SRGTRDY,((TContestant) Thread.currentThread()).getContestantId(),
        ((TContestant) Thread.currentThread()).getCoachId(),
        ((TContestant) Thread.currentThread()).getContestantState(),0);
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPGTRDY){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid message type !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(inMessage.getContestantId()!= ((TContestant) Thread.currentThread()).getContestantId()){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid contestant id !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(inMessage.getCoachId()!= ((TContestant) Thread.currentThread()).getCoachId()){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid coach id !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if((inMessage.getContestantState()<ContestantStates.SEAT_AT_THE_BENCH)||(inMessage.getContestantState()>ContestantStates.DO_YOUR_BEST)){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid contestant state !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        com.close();
        ((TContestant) Thread.currentThread()).setContestantState(inMessage.getContestantState());
        
    }
 
    /**
     *  The contestant pulls the rope
     * 
     *
     *    @param strength contestant strength before pulling
     *    @return updated strength after pulling
     */
 
    public int pullTheRope (int strength)
    {   
        ClientCom com ;                                 //comunication channel
        Message outMessage,                             //outgoing message
                inMessage;                              //incoming message
        // send message to the bench
        com = new ClientCom (serverHostName,serverPortNumb);
        while(!com.open())
        {try 
            {
                Thread.currentThread().sleep((long)(10));
            }catch(InterruptedException e){}
        }
        outMessage = new Message(MessageType.SRPUTRP,((TContestant) Thread.currentThread()).getContestantId(),((TContestant) Thread.currentThread()).getCoachId(),
        ((TContestant) Thread.currentThread()).getContestantState(),strength);
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPPUTRP){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid message type !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(inMessage.getContestantId()!= ((TContestant) Thread.currentThread()).getContestantId()){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid contestant id !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(inMessage.getCoachId()!= ((TContestant) Thread.currentThread()).getCoachId()){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid coach id !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if((inMessage.getContestantState()<ContestantStates.SEAT_AT_THE_BENCH)||(inMessage.getContestantState()>ContestantStates.DO_YOUR_BEST)){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid contestant state !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if((inMessage.getContestantStrength()<=0)){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid contestant strength !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        com.close();
        ((TContestant) Thread.currentThread()).setContestantState(inMessage.getContestantState());
        return inMessage.getContestantStrength();
    }
 
    /**
     * Function to notify that the contestant is done
     * 
     */
 
    public void amDone ()
    {
        ClientCom com ;                                 //comunication channel
        Message outMessage,                             //outgoing message
                inMessage;                              //incoming message
        // send message to the bench
        com = new ClientCom (serverHostName,serverPortNumb);
        while(!com.open())
        {try 
            {
                Thread.currentThread().sleep((long)(10));
            }catch(InterruptedException e){}
        }
        outMessage = new Message(MessageType.SRAMDONE,((TContestant) Thread.currentThread()).getContestantId(),
        ((TContestant) Thread.currentThread()).getContestantState());
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPAMDONE){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid message type !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(inMessage.getContestantId()!= ((TContestant) Thread.currentThread()).getContestantId()){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid contestant id !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if((inMessage.getContestantState()<ContestantStates.SEAT_AT_THE_BENCH)||(inMessage.getContestantState()>ContestantStates.DO_YOUR_BEST)){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid contestant state !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        com.close();
        ((TContestant) Thread.currentThread()).setContestantState(inMessage.getContestantState());
        
    }

    
    /**
     * Coach function to watch the trial
     * 
     */

    public void watchTrial ()
    {
        ClientCom com ;                                 //comunication channel
        Message outMessage,                             //outgoing message
                inMessage;                              //incoming message
        // send message to the bench
        com = new ClientCom (serverHostName,serverPortNumb);
        while(!com.open())
        {try 
            {
                Thread.currentThread().sleep((long)(10));
            }catch(InterruptedException e){}
        }
        outMessage = new Message(MessageType.SRWTCHTRL,((TCoach) Thread.currentThread()).getCoachId(),
        ((TCoach) Thread.currentThread()).getCoachState());
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPWTCHTRL){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid message type !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(inMessage.getCoachId()!= ((TCoach) Thread.currentThread()).getCoachId()){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid coach id !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if((inMessage.getCoachState()<CoachStates.WAIT_FOR_REFEREE_COMMAND)||(inMessage.getCoachState()>CoachStates.WATCH_TRIAL)){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid coach state !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        com.close();
        ((TCoach) Thread.currentThread()).setCoachState(inMessage.getCoachState());
        
    }



    /**
     *  Operation server shutdown.
     *
     *    New Operation.
     */

    public void shutdown ()
    {

        ClientCom com ;                                 //comunication channel
        Message outMessage,                             //outgoing message
                inMessage;                              //incoming message
        // send message to the bench
        com = new ClientCom (serverHostName,serverPortNumb);
        while(!com.open())
        {try 
            {
                Thread.currentThread().sleep((long)(10));
            }catch(InterruptedException e){}
        }
        outMessage = new Message(MessageType.SHUT);
        com.writeObject(outMessage);
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.SHUTDONE){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid message type !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        com.close();

    }
}
