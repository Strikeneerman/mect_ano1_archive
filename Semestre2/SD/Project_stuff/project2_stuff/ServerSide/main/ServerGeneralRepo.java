package ServerSide.main;

import ServerSide.entities.*;
import ServerSide.sharedRegions.*;
import commInfra.*;
import genclass.GenericIO;
import java.net.*;

/**
 *    Server side of the General Repository of Information.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 *
 */
public class ServerGeneralRepo
{
  /**
   *  Flag signaling the service is active.
   */

   public static boolean waitConnection;

  /**
   *  Main method.
   *
   *    @param args runtime arguments
   *        args[0] - port nunber for listening to service requests
   */

   public static void main (String [] args)
   {
      MGeneralRepo repos;                                            // general repository of information (service to be rendered)
      IGeneralRepo reposInter;                              // interface to the general repository of information
      ServerCom scon, sconi;                                         // communication channels
      int portNumb = -1;                                             // port number for listening to service requests

      if (args.length != 1)
         { GenericIO.writelnString ("Wrong number of parameters!");
           System.exit (1);
         }
      try
      { portNumb = Integer.parseInt (args[0]);
      }
      catch (NumberFormatException e)
      { GenericIO.writelnString ("args[0] is not a number!");
        System.exit (1);
      }
      if ((portNumb < 4000) || (portNumb >= 65536))
         { GenericIO.writelnString ("args[0] is not a valid port number!");
           System.exit (1);
         }

     /* service is established */

      repos = new MGeneralRepo ();                                   // service is instantiated
      reposInter = new IGeneralRepo (repos);                // interface to the service is instantiated
      scon = new ServerCom (portNumb);                               // listening channel at the public port is established
      scon.start ();
      GenericIO.writelnString ("Service is established!");
      GenericIO.writelnString ("Server is listening for service requests.");

     /* service request processing */

      GeneralRepoClientProxy cliProxy;                                  // service provider agent

      waitConnection = true;
      while (waitConnection)
      { try
        { sconi = scon.accept ();                                              // enter listening procedure
          cliProxy = new GeneralRepoClientProxy (sconi, reposInter);          // start a service provider agent to address
          cliProxy.start ();                                                   //   the request of service
        }
        catch (SocketTimeoutException e) {}
      }
      scon.end ();                                                   // operations termination
      GenericIO.writelnString ("Server was shutdown.");
   }
}
