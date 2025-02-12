package ClientSide.stubs;

import ClientSide.entities.CoachStates;
import ClientSide.entities.RefereeStates;
import ClientSide.entities.TCoach;
import ClientSide.entities.TReferee;
import commInfra.ClientCom;
import commInfra.Message;
import commInfra.MessageType;
import genclass.GenericIO;

/**
 *  Stub to the referee site.
 *
 *    It instantiates a remote reference to the referee site.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */


public class SRefereeSite{

    /**
     *  Name of the platform where is located the referee site server.
     */

   private String serverHostName;

    /**
     *  Port number for listening to service requests.
     */
 
    private int serverPortNumb;
 
    /**
     *   Instantiation of a stub to the referee site.
     *
     *     @param serverHostName name of the platform where is located the referee site server
     *     @param serverPortNumb port number for listening to service requests
     */

    public SRefereeSite (String serverHostName, int serverPortNumb)
    {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     * Function to start the game
     * <p>
     * Referee will wait for the coaches to be ready so it can start a game
     */

    public void startGame ()
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
        outMessage = new Message(MessageType.SRSTRGM,
                                ((TReferee) Thread.currentThread()).getRefereeID(),
                                ((TReferee) Thread.currentThread()).getRefereeState());
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPSTRGM){
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
     * Function for waiting for the referee command
     *  <p>
     *  if true the coach will terminate
     *  else it will continue to the next state
     * 
     *  @return   boolean : whether the coach will terminate or not
     */
    
    public boolean waitForRefereeCommand ()
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
        outMessage = new Message(MessageType.SRWTFREFCOM,((TCoach) Thread.currentThread()).getCoachId(),
        ((TCoach) Thread.currentThread()).getCoachState());
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPWTFREFCOM){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid message type !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        if(inMessage.getCoachId()!= ((TCoach) Thread.currentThread()).getCoachId()){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid coach id !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        com.close();
        ((TCoach) Thread.currentThread()).setCoachState(inMessage.getCoachState());
        return inMessage.coachTerminate();
        
    }

    /**
     * Function to call the trial
     * <p>
     * Referee will call the trial and wait for the coaches to have both teams ready
     * and will advance to the next state
     */

    public void callTrial ()
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
        outMessage = new Message(MessageType.SRCALLTRL,((TReferee) Thread.currentThread()).getRefereeID(),
        ((TReferee) Thread.currentThread()).getRefereeState());
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPCALLTRL){
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
     * Function to inform the referee
     * <p>
     * Coach calls this function to inform the referee that the team is ready,
     * waking up the referee that is blocked in the waitTeamsReady state
     * 
     */

    public void informReferee ()
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
        outMessage = new Message(MessageType.SRINFREF,((TCoach) Thread.currentThread()).getCoachId(),
        ((TCoach) Thread.currentThread()).getCoachState());
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPINFREF){
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