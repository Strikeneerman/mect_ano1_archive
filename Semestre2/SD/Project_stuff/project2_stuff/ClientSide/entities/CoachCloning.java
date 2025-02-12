package ClientSide.entities;

/**
 *  This file defines the Coach Cloning interface
 *  The Coach Cloning interface is implemented by Coach
 *  and used by the CoachClientProxy
 */

public interface CoachCloning {
    
    /**
     * Set the coach identification
     * 
     * @param id the coach identification
     */
    public void setCoachId(int id);
    
    /**
     * Get the coach identification
     * 
     * @return the coach identification
     */
    public int getCoachId();
    
    /**
     *  Set the state of the coach
     * 
     *  @param state the state of the coach
     */
    public void setCoachState(int state);

    /**
     *  Get the state of the coach
     * 
     *  @return the state of the coach
     */
    public int getCoachState();


}
