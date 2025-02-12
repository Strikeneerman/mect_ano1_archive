package ClientSide.entities;

/**
 *  This file defines the Referee Cloning interface
 *  The Referee Cloning interface is implemented by Referee
 *  and used by the RefereeClientProxy
 */

public interface RefereeCloning {
        
        /**
        *  Set the state of the referee
        * 
        *  @param state the state of the referee
        */
        public void setRefereeState(int state);
    
        /**
        *  Get the state of the referee
        * 
        *  @return the state of the referee
        */
        public int getRefereeState();

}
