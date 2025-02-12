package ClientSide.stubs;

import commInfra.ClientCom;
import commInfra.Message;
import commInfra.MessageType;
import genclass.GenericIO;

/**
 *  Stub to the general repository.
 *
 *    It instantiates a remote reference to the general repository. 
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class SGeneralRepo {

    /**
   *  Name of the platform where is located the general repository server.
   */

   private String serverHostName;

   /**
    *  Port number for listening to service requests.
    */
 
    private int serverPortNumb;
 
   /**
    *   Instantiation of a stub to the bench.
    *
    *     @param serverHostName name of the platform where is located the general repository server
    *     @param serverPortNumb port number for listening to service requests
    */
    public SGeneralRepo (String serverHostName, int serverPortNumb)
    {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }
    

    /**
     *   Operation initialization of the simulation.  (de acordo com o exemplo do stor ou é uma nova funcao, ou é relacionada com a reportInitialStatus)
     *
     *     @param fileName logging file name
     *     @param nIter number of iterations
    */

    public void initSimul (String fileName, int nIter)
    {
       ClientCom com;                                                 // communication channel
       Message outMessage,                                            // outgoing message
               inMessage;                                             // incoming message
 
       com = new ClientCom (serverHostName, serverPortNumb);
       while (!com.open ())
       { try
         { Thread.sleep ((long) (10));
         }
         catch (InterruptedException e) {}
       }
       outMessage = new Message (MessageType.SRPINITSIM, fileName);
       com.writeObject (outMessage);
       inMessage = (Message) com.readObject ();
       if (inMessage.getMsgType() != MessageType.REPINITSIM)
          { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
          }
       com.close ();
    }
     

    /**
     * Set Referee State
     * 
     * @param state State of the referee
     */

     public void setRefereeState (int state)
     {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message
  
        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
          { Thread.sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SRSETREFSTAT, state);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.REPSETREFSTAT)
           { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
             GenericIO.writelnString (inMessage.toString ());
             System.exit (1);
           }
        com.close ();
     }

     /**
     * Set Coach State
     * 
     * @param coachId Coaches ID
     * @param state State of the coach
     */

     public void setCoachState (int coachId,int state)
     {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message
  
        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
          { Thread.sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SRSETCOASTAT,coachId, state);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.REPSETCOASTAT)
           { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
             GenericIO.writelnString (inMessage.toString ());
             System.exit (1);
           }
        com.close ();
     }


     /**
     * Set Contestant State
     * 
     * @param coachId Coaches ID
     * @param contestantId Contestants ID
     * @param state State of the contestant
     */

     public void setContestantState (int coachId , int contestantId, int state)
     {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message
  
        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
          { Thread.sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SRSETCONTSTAT,coachId ,contestantId, state);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.REPSETCONTSTAT)
           { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
             GenericIO.writelnString (inMessage.toString ());
             System.exit (1);
           }
        com.close ();
     }

     /**
     * Set the strength of the contestant
     * 
     * @param coachId Coaches ID
     * @param contestantId Contestants ID
     * @param Strength Strength of the contestant
     */

     public void setContestantStrength (int coachId , int contestantId, int Strength)
     {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message
  
        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
          { Thread.sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SRSETCONTSTRG,coachId ,contestantId, Strength);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.REPSETCONTSTRG)
           { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
             GenericIO.writelnString (inMessage.toString ());
             System.exit (1);
           }
        com.close ();
     }


     /**
     * Set contestant pulling the rope
     * 
     * @param coachId Coaches ID
     * @param position Array containing the id's of the contestants chosen to pull the rope
     */

     public void setContestantPosition (int coachId , int[] position)
     {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message
  
        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
          { Thread.sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SRSETCONTPOS, coachId ,position);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.REPSETCONTPOS)
           { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
             GenericIO.writelnString (inMessage.toString ());
             System.exit (1);
           }
        com.close ();
     }



     /**
     * Set trial number
     * 
     * @param number Number of the trial
     */

     public void setTrialNumber (int number)
     {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message
  
        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
          { Thread.sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SRSETTRLNUM, number);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.REPSETTRLNUM)
           { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
             GenericIO.writelnString (inMessage.toString ());
             System.exit (1);
           }
        com.close ();
     }



     /**
     * Set the position of the centre of the rope at the beggining of the trial 
     * 
     * @param position Position of the rope
     */


     public void setRopePosition (int position)
     {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message
  
        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
          { Thread.sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SRSETPOSROPE, position);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.REPSETPOSROPE)
           { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
             GenericIO.writelnString (inMessage.toString ());
             System.exit (1);
           }
        com.close ();
     }



     /**
     * Set game number 
     * 
     * @param number Number of the game
     */

     public void setGameNumber (int number)
     {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message
  
        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
          { Thread.sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SRSETGMNUM, number);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.REPSETGMNUM)
           { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
             GenericIO.writelnString (inMessage.toString ());
             System.exit (1);
           }
        com.close ();
     }



     /**
     * Set the winner of a game
     * @param team Team that won the game
     * @param knockout Identify if the game was won by knockout
     */


     public void setGameWinner (int team, boolean knockout)
     {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message
  
        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
          { Thread.sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SRSETGMWIN, team, knockout);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.REPSETGMWIN)
           { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
             GenericIO.writelnString (inMessage.toString ());
             System.exit (1);
           }
        com.close ();
     }


      /**
      * Update the general repository
      * In use to update contestants states all at once (caled by contestants when they all are in the same state)
      */

     public void basic_update()
     {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message
  
        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
          { Thread.sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SRBASUPDT);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.REPBASUPDT)
           { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
             GenericIO.writelnString (inMessage.toString ());
             System.exit (1);
           }
        com.close ();
     }



      /**
      * Set the winner of the match
      * @param team Team that won the match
      */

      public void setMatchWinner (int team)
     {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message
  
        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
          { Thread.sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SRSETMTWIN, team);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.REPSETMTWIN)
           { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
             GenericIO.writelnString (inMessage.toString ());
             System.exit (1);
           }
        com.close ();
     }




      /**
      * Set the result of a match as a draw
      */


      public void setMatchDraw()
     {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message
  
        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
          { Thread.sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SRSETMTDRAW);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.REPSETMTDRAW)
           { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
             GenericIO.writelnString (inMessage.toString ());
             System.exit (1);
           }
        com.close ();
     }

     /**
      * Set Game Draw
      *
      */
    public void setGameDraw()
    {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
            { Thread.sleep ((long) (10));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SRSETGMDRAW);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.REPSETGMDRAW)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
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
