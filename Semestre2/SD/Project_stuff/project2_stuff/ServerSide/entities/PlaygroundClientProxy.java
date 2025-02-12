package ServerSide.entities;

import ServerSide.sharedRegions.*;
import ClientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

/**
 *  Service provider agent for access to the Bench.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class PlaygroundClientProxy extends Thread implements CoachCloning, ContestantCloning, RefereeCloning
{

    /**
    *  Number of instantiayed threads.
    */

    private static int nProxy = 0;

    /**
    *  Communication channel.
    */
 
    private ServerCom sconi;
 
    /**
    *  Interface to the Playground.
    */
    private IPlayground playgroundInt;

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
    private int contestantStrength = -2;

    /**
     * Referee state. 
     */
    private int refereeState = -1;

    /**
    *  Instantiation of the service provider agent.
    *
    *     @param sconi communication channel
    *     @param playgroundInt interface to the Bench
    */

    public PlaygroundClientProxy(ServerCom sconi, IPlayground playgroundInt)
    {
        super ("BenchProxy_" + PlaygroundClientProxy.getProxyId ());
        this.sconi = sconi;
        this.playgroundInt = playgroundInt;
    }

    /**
    *  Generation of the instantiation identifier.
    *
    *     @return instantiation identifier
    */

    private static int getProxyId ()
    {
        Class<?> cl = null;                                            // representation of the BarberShopClientProxy object in JVM
        int proxyId;                                                   // instantiation identifier

        try
        { 
            cl = Class.forName ("ServerSide.entities.PlaygroundClientProxy");
        }
        catch (ClassNotFoundException e)
        {   GenericIO.writelnString ("Data type PlaygroundClientProxy was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
        synchronized (cl)
        { proxyId = nProxy;
            nProxy += 1;
        }
        return proxyId;
    }

    /**
    * Coach identification Setter.
    */
    
    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    /**
     * Coach state Setter.
     */
    
    public void setCoachState(int coachState) {
        this.coachState = coachState;
    }
    
    /**
     * Contestant identification Setter.
     */
    
    public void setContestantId(int contestantId) {
        this.contestantId = contestantId;
    }

    /**
     * Contestant state Setter.
     */

    public void setContestantState(int contestantState) {
        this.contestantState = contestantState;
    }

    /**
     * Contestant strength Setter.
     */
    
    public void setStrength(int strength) {
        contestantStrength = strength;
    }

    /**
     * Referee state Setter.
     */
    public void setRefereeState(int refState) {
        // if(refState < 0 || refState >= RefereeStates.END_OF_A_MATCH) {
        //     System.out.println("PlayGroundProxy: Invalid Referee State!");
        //     System.exit(1);
        // }
        this.refereeState = refState;
    }

    /**
     * Coach identification Getter.
     */

     public int getCoachId() {
        return coachId;
    }

    /**
     * Coach state Getter.
     */

    public int getCoachState() {
        return coachState;
    }

    /**
     * Contestant identification Getter.
     */
    public int getContestantId() {
        return contestantId;
    }

    /**
     * Contestant state Getter.
     */

    public int getContestantState() {
        return contestantState;
    }

    /**
     * Contestant strength Getter.
     */
    public int getStrength() {
        return contestantStrength;
    }

    /**
     * Referee state Getter.
     */

    public int getRefereeState() {
        return refereeState;
    }

    /**
     * Service provider agent life cycle.
     */

    @Override
    public void run ()
    {
        Message inMessage = null,                                      // service request
                outMessage = null;                                     // service reply
  
        /* service providing */
  
        inMessage = (Message) sconi.readObject ();                     // get service request
        try
        { outMessage = playgroundInt.processAndReply (inMessage);         // process it
        }
        catch (MessageException e)
        { GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
          GenericIO.writelnString (e.getMessageVal ().toString ());
          System.exit (1);
        }
        sconi.writeObject (outMessage);                                // send service reply
        sconi.close ();                                                // close the communication channel
    }
}
