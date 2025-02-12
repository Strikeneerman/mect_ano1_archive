package ClientSide.main;

import ClientSide.entities.TReferee;
import ClientSide.stubs.*;
import genclass.GenericIO;

/**
 * Referee Main Client Constructor
 */
public class clientReferee {
    /**
   *  Main method.
   *
   *    @param args runtime arguments
   *        args[0] - name of the platform where is located the bench server
   *        args[1] - port number for listening to service requests
   *        args[2] - name of the platform where is located the playground server
   *        args[3] - port number for listening to service requests
   *        args[4] - name of the platform where is located the refereesite server
   *        args[5] - port number for listening to service requests
   *        args[6] - name of the platform where is located the general repository server
   *        args[7] - port number for listening to service requests
   */
    public static void main(String[] args) {
        String benchServerHostName = null;
        int benchServerPortNumb = -1;
        String playgroundServerHostName = null;
        int playgroundServerPortNumb = -1;
        String refereeSiteServerHostName = null;
        int refereeSiteServerPortNumb = -1;
        String generalRepoServerHostName = null;
        int generalRepoServerPortNumb = -1;
        TReferee referee;
        if(args.length != 8) {
            System.out.println("Invalid number of arguments!");
            System.exit(1);
        }

        try {
            benchServerHostName = args[0];
            benchServerPortNumb = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Server location (machine name) and number of the listening port missing!");
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println("Port number must be an integer!");
            System.exit(1);
        }
        try {
            playgroundServerHostName = args[2];
            playgroundServerPortNumb = Integer.parseInt(args[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Server location (machine name) and number of the listening port missing!");
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println("Port number must be an integer!");
            System.exit(1);
        }
        try {
            refereeSiteServerHostName = args[4];
            refereeSiteServerPortNumb = Integer.parseInt(args[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Server location (machine name) and number of the listening port missing!");
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println("Port number must be an integer!");
            System.exit(1);
        }
        try {
            generalRepoServerHostName = args[6];
            generalRepoServerPortNumb = Integer.parseInt(args[7]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Server location (machine name) and number of the listening port missing!");
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println("Port number must be an integer!");
            System.exit(1);
        }

    /* problem initializati>on */
    SBench bench = new SBench(benchServerHostName, benchServerPortNumb);
    SPlayGround playground = new SPlayGround(playgroundServerHostName, playgroundServerPortNumb);
    SRefereeSite refereeSite = new SRefereeSite(refereeSiteServerHostName, refereeSiteServerPortNumb);

    SGeneralRepo generalRepo = new SGeneralRepo(generalRepoServerHostName, generalRepoServerPortNumb);
    
    referee = new TReferee(1, refereeSite, playground);
    referee.start();
    /* waiting for the end of the simulation */

    GenericIO.writelnString ();
    try 
    { 
        referee.join();
    }
    catch (InterruptedException e) {}
    GenericIO.writelnString ("The referee has terminated.");
    
    GenericIO.writelnString ();
    //wait 10ms
    try
    { 
        Thread.sleep (10);
    }
    catch (InterruptedException e) {}
    //terminate servers
    refereeSite.shutdown();
    playground.shutdown();
    bench.shutdown();
    try
    { 
        Thread.sleep (100);
    }
    catch (InterruptedException e) {}
    generalRepo.shutdown();
}
}
