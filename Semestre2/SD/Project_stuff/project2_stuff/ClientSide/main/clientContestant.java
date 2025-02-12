package ClientSide.main;


import ClientSide.entities.TContestant;
import ClientSide.stubs.*;
import ServerSide.main.SimulPar;
import genclass.GenericIO;

/**
 * Contestant Main Client Constructor
 */
public class clientContestant {
    /**
   *  Main method.
   *
   *    @param args runtime arguments
   *        args[0] - name of the platform where is located the bench server
   *        args[1] - port number for listening to service requests
   *        args[2] - name of the platform where is located the playground server
   *        args[3] - port number for listening to service requests
   */
    public static void main(String[] args) {
        String benchServerHostName = null;
        int benchServerPortNumb = -1;
        String playgroundServerHostName = null;
        int playgroundServerPortNumb = -1;

        TContestant contestants[][] = new TContestant[SimulPar.nCoaches][SimulPar.nContestants];
        if(args.length != 4) {
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
        
    /* problem initializati>on */
    SBench bench = new SBench(benchServerHostName, benchServerPortNumb);
    SPlayGround playground = new SPlayGround(playgroundServerHostName, playgroundServerPortNumb);
    
    
    for (int j = 0; j < SimulPar.nCoaches; j++) {
        for (int i = 0; i < SimulPar.nContestants; i++) {
            contestants[j][i] = new TContestant(i, j, playground, bench);
            contestants[j][i].start();
        }
    }
    
    /* waiting for the end of the simulation */
    
    GenericIO.writelnString ();
    for (int j = 0; j < SimulPar.nCoaches; j++) {
        for (int i = 0; i < SimulPar.nContestants; i++)
        {   try 
            { 
                contestants[j][i].join();
            }
            catch (InterruptedException e) {}
            GenericIO.writelnString ("The contestant " + (i+1)  + "from coach " + (j+1) + " has terminated.");
        }
    }
    GenericIO.writelnString ();

    }
}
