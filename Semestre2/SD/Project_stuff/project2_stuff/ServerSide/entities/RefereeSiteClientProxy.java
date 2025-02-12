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

public class RefereeSiteClientProxy extends Thread implements CoachCloning, RefereeCloning
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
    *  Interface to the RefereeSite.
    */
    private IRefereeSite refereeSiteInt;

    /**
    *  Coach identification.
    */

    private int coachId = -1;

    /**
    *  Coach state.
    */
 
    private int coachState = -1;
 
    /**
     * Referee state. 
     */
    private int refState = -1;

    /**
    *  Instantiation of the service provider agent.
    *
    *     @param sconi communication channel
    *     @param refereeSiteInt interface to the RefereeSite
    */

    public RefereeSiteClientProxy(ServerCom sconi, IRefereeSite refereeSiteInt)
    {
        super ("BenchProxy_" + RefereeSiteClientProxy.getProxyId ());
        this.sconi = sconi;
        this.refereeSiteInt = refereeSiteInt;
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
            cl = Class.forName ("ServerSide.entities.RefereeSiteClientProxy");
        }
        catch (ClassNotFoundException e)
        {   GenericIO.writelnString ("Data type RefereeSiteClientProxy was not found!");
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
        { outMessage = refereeSiteInt.processAndReply (inMessage);         // process it
        }
        catch (MessageException e)
        { GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
          GenericIO.writelnString (e.getMessageVal ().toString ());
          System.exit (1);
        }
        sconi.writeObject (outMessage);                                // send service reply
        sconi.close ();                                                // close the communication channel
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
     * Referee state Setter.
     */
    public void setRefereeState(int refState) {
        this.refState = refState;
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
     * Referee state Getter.
     */

    public int getRefereeState() {
        return refState;
    }
}
