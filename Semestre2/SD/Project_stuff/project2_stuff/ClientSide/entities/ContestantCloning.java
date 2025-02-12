package ClientSide.entities;

/**
 *  This file defines the Contestant Cloning interface
 *  The Contestant Cloning interface is implemented by Contestant
 *  and used by the ContestantClientProxy
 */

public interface ContestantCloning {
        
        /**
        * Set the contestant identification
        * 
        * @param id the contestant identification
        */
        public void setContestantId(int id);
        
        /**
        * Get the contestant identification
        * 
        * @return the contestant identification
        */
        public int getContestantId();
        
        /**
        *  Set the state of the contestant
        * 
        *  @param state the state of the contestant
        */
        public void setContestantState(int state);
    
        /**
        *  Get the state of the contestant
        * 
        *  @return the state of the contestant
        */
        public int getContestantState();

        /**
         * Set the strength of the contestant
         * 
         * @param strength the strength of the contestant
         */

        public void setStrength(int strength);

        /**
         * Get the strength of the contestant
         * 
         * @return the strength of the contestant
         */

        public int getStrength();
}
