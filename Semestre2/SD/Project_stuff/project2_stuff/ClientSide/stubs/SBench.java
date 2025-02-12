package ClientSide.stubs;


import ClientSide.entities.TCoach;
import ClientSide.entities.CoachStates;
import ClientSide.entities.TContestant;
import ClientSide.entities.ContestantStates;
import commInfra.ClientCom;
import commInfra.Message;
import commInfra.MessageType;
import genclass.GenericIO;

/**
 *  Stub to the bench.
 *
 *    It instantiates a remote reference to the bench.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class SBench {
    /**
   *  Name of the platform where is located the barber shop server.
   */

   private String serverHostName;

   /**
    *  Port number for listening to service requests.
    */
 
    private int serverPortNumb;
 
   /**
    *   Instantiation of a stub to the bench.
    *
    *     @param serverHostName name of the platform where is located the bench server
    *     @param serverPortNumb port number for listening to service requests
    */
    
    public SBench (String serverHostName, int serverPortNumb)
    {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     *  Sit the contestant at the bench.
     *
     *    The contestant updates his strength and waits for the next trial.
     *
     *    @param strength contestant strength
     *    @return updated strength
     */

    public int sitDown (int strength)
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
        outMessage = new Message(MessageType.SRSTDWN,
                                ((TContestant) Thread.currentThread()).getContestantId(),
                                ((TContestant) Thread.currentThread()).getCoachId(),
                                ((TContestant) Thread.currentThread()).getContestantState(),
                                strength);
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPSTDWN){
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
        if((inMessage.getContestantStrength()<=0 && inMessage.getContestantStrength() != -1)){
            GenericIO.writelnString("Thread" + Thread.currentThread().getName() + "Invalid contestant strength !");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        com.close();
        ((TContestant) Thread.currentThread()).setContestantState(inMessage.getContestantState());
        return inMessage.getContestantStrength();
    }

    /**
     *  Call the contestants to play the game.
     *
     *    The coach calls the contestants to play the game.
     */
    public void callContestants ()
    {
        ClientCom com ;                                 //comunication channel
        Message outMessage,                             //outgoing message
                inMessage;                              //incoming message
        // send message to the bench
        com = new ClientCom (serverHostName,serverPortNumb);
        while(!com.open())
        {   try 
            {
                Thread.currentThread().sleep((long)(10));
            }catch(InterruptedException e){}
        }
        outMessage = new Message(MessageType.SRCALLCONT,((TCoach) Thread.currentThread()).getCoachId(),
        ((TCoach) Thread.currentThread()).getCoachState());
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPCALLCONT){
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
     *  Review the notes.
     *
     *    The coach reviews the notes.
     */
    public void reviewNotes ()
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
        outMessage = new Message(MessageType.SRREVNOT,((TCoach) Thread.currentThread()).getCoachId(),
        ((TCoach) Thread.currentThread()).getCoachState());
        com.writeObject(outMessage);

        // receive reply
        inMessage = (Message) com.readObject();
        if(inMessage.getMsgType()!= MessageType.REPREVNOT){
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
