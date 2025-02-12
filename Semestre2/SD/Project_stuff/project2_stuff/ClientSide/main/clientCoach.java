package ClientSide.main;

import ClientSide.entities.TCoach;
import ClientSide.stubs.*;
import ServerSide.main.SimulPar;
import genclass.GenericIO;

/**
 * Coach Main Client Constructor
 */
public class clientCoach {
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
   */
    public static void main(String[] args) {
        String benchServerHostName = null;
        int benchServerPortNumb = -1;
        String playgroundServerHostName = null;
        int playgroundServerPortNumb = -1;
        String refereeSiteServerHostName = null;
        int refereeSiteServerPortNumb = -1;
        TCoach coaches[] = new TCoach[SimulPar.nCoaches];
        if(args.length != 6) {
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

    /* problem initialization */
    SBench bench = new SBench(benchServerHostName, benchServerPortNumb);
    SPlayGround playground = new SPlayGround(playgroundServerHostName, playgroundServerPortNumb);
    SRefereeSite refereeSite = new SRefereeSite(refereeSiteServerHostName, refereeSiteServerPortNumb);

    //SGeneralRepo generalRepo = new SGeneralRepo(generalRepoServerHostName, generalRepoServerPortNumb);
    
    for (int i = 0; i < SimulPar.nCoaches; i++) {
        coaches[i] = new TCoach(i, refereeSite, bench, playground);
        coaches[i].start();
    }

    /* waiting for the end of the simulation */

    GenericIO.writelnString ();
    for (int i = 0; i < SimulPar.nCoaches; i++)
    {   try 
        { 
            coaches[i].join ();
        }
        catch (InterruptedException e) {}
        GenericIO.writelnString ("The coach " + (i+1) + " has terminated.");
    }
    GenericIO.writelnString ();
    }
}
