package ServerSide.sharedRegions;

import ServerSide.main.*;
import ServerSide.entities.*;
import ClientSide.entities.*;
import commInfra.*;
/**
 *  Referee Site interface.
 * 
 * It provides the functionality to access the Referee Site
 */

public class IRefereeSite {

    /**
     * Referneces Referee Site
     */    
    private MRefereeSite refereeSite;

    /**
     * Constructor
     * 
     * @param refereeSite Referee Site
     */
    public IRefereeSite(MRefereeSite refereeSite) {
        this.refereeSite = refereeSite;
    }

    /**
     *  Processing of the incoming messages.
     *
     *  Validation, execution of the corresponding method and generation of the outgoing message.
     *
     *    @param inMessage service request
     *    @return service reply
     *    @throws MessageException if the incoming message is not valid
     */
    public Message processAndReply (Message inMessage) throws MessageException
    {
      Message outMessage = null;                          // response message
      // validation of the incoming message
      switch (inMessage.getMsgType ()) {
        case MessageType.SRSTRGM:
          if(inMessage.getRefereeState() != RefereeStates.START_OF_THE_MATCH && inMessage.getRefereeState() != RefereeStates.WAIT_FOR_TRIAL_CONCLUSION && inMessage.getRefereeState() != RefereeStates.END_OF_A_GAME) {
            throw new MessageException ("Invalid Referee State!", inMessage);
          }
          break;
        case MessageType.SRWTFREFCOM:
          if(inMessage.getCoachId() < 0 || inMessage.getCoachId() >= SimulPar.nCoaches) {
            throw new MessageException ("Invalid Coach Id!", inMessage);
          }
          if(inMessage.getCoachState() != CoachStates.WAIT_FOR_REFEREE_COMMAND) {
            throw new MessageException ("Invalid Coach State!", inMessage);
          }
          break;
        case MessageType.SRCALLTRL:
          if(inMessage.getRefereeState() != RefereeStates.START_OF_A_GAME) {
            throw new MessageException ("Invalid Referee State!", inMessage);
          }
          break;
        case MessageType.SRINFREF:
          if(inMessage.getCoachId() < 0 || inMessage.getCoachId() >= SimulPar.nCoaches) {
            throw new MessageException ("Invalid Coach Id!", inMessage);
          }
          if(inMessage.getCoachState() != CoachStates.ASSEMBLE_TEAM) {
            throw new MessageException ("Invalid Coach State!", inMessage);
          }
        case MessageType.SHUT:
          break;
        default: 
          throw new MessageException ("Message type not valid!", inMessage);
      }

      // message processing

      switch (inMessage.getMsgType ()) {
        case MessageType.SRSTRGM:
          ((RefereeSiteClientProxy) Thread.currentThread()).setRefereeState(inMessage.getRefereeState());
          refereeSite.startGame();
          outMessage = new Message(MessageType.REPSTRGM, 
                                  1,
                                  ((RefereeSiteClientProxy) Thread.currentThread()).getRefereeState());  
          break;
        case MessageType.SRWTFREFCOM:
          ((RefereeSiteClientProxy) Thread.currentThread()).setCoachId(inMessage.getCoachId());
          ((RefereeSiteClientProxy) Thread.currentThread()).setCoachState(inMessage.getCoachState());
          boolean terminate = refereeSite.waitForRefereeCommand();
          outMessage = new Message(MessageType.REPWTFREFCOM, 
                                  ((RefereeSiteClientProxy) Thread.currentThread()).getCoachId(),
                                  terminate);
          break;
        case MessageType.SRCALLTRL:
          ((RefereeSiteClientProxy) Thread.currentThread()).setRefereeState(inMessage.getRefereeState());
          
          refereeSite.callTrial();
          outMessage = new Message(MessageType.REPCALLTRL, 
                                  1,
                                  ((RefereeSiteClientProxy) Thread.currentThread()).getRefereeState()
                                  );
          break;
        case MessageType.SRINFREF:
          ((RefereeSiteClientProxy) Thread.currentThread()).setCoachId(inMessage.getCoachId());
          ((RefereeSiteClientProxy) Thread.currentThread()).setCoachState(inMessage.getCoachState());
          refereeSite.informReferee();
          outMessage = new Message(MessageType.REPINFREF, 
                                  ((RefereeSiteClientProxy) Thread.currentThread()).getCoachId(),
                                  ((RefereeSiteClientProxy) Thread.currentThread()).getCoachState()
                                  );
          break;
        case MessageType.SHUT:
          refereeSite.terminate();
          outMessage = new Message(MessageType.SHUTDONE);
          break;
      }
      return(outMessage);
    }

}
