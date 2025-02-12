package ServerSide.sharedRegions;

import ServerSide.main.*;
import ServerSide.entities.*;
import ClientSide.entities.*;
import commInfra.*;
/**
 *  Playground interface.
 * 
 * It provides the functionality to access Playground.
 */

public class IPlayground {

    /**
     * Referneces Playground
     */    
    private MPlayground playground;

    /**
     * Constructor
     * 
     * @param playground Playground
     */
    public IPlayground(MPlayground playground) {
        this.playground = playground;
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
    boolean decision;
    // validation of incoming message
    switch (inMessage.getMsgType()) {
        case MessageType.SRSTRTRL:
            if (inMessage.getRefereeState() != RefereeStates.TEAMS_READY) {
                throw new MessageException ("Invalid Referee State!", inMessage);
            }
            break;
        case MessageType.SRWTFCONT:
            if (inMessage.getCoachId() < 0 || inMessage.getCoachId() >= SimulPar.nCoaches) {
                throw new MessageException ("Invalid Coach Id!", inMessage);
            }
            if (inMessage.getCoachState() != CoachStates.ASSEMBLE_TEAM) {
                throw new MessageException ("Invalid Coach State!", inMessage);
            }
            break;
        case MessageType.SRASSTRLDSN:
            if(inMessage.getRefereeState() != RefereeStates.WAIT_FOR_TRIAL_CONCLUSION) {
                throw new MessageException ("Invalid Referee State!", inMessage);
            }
            break;
        case MessageType.SRDCLGMWIN:
            if (inMessage.getRefereeState() != RefereeStates.WAIT_FOR_TRIAL_CONCLUSION) {
                throw new MessageException ("Invalid Referee State!", inMessage);
            }
            break;
        case MessageType.SRDCLMTWIN:
            if (inMessage.getRefereeState() != RefereeStates.WAIT_FOR_TRIAL_CONCLUSION) {
                throw new MessageException ("Invalid Referee State!", inMessage);
            }
            break;
        case MessageType.SRGTRDY:
            if (inMessage.getCoachId() < 0 || inMessage.getCoachId() >= SimulPar.nCoaches) {
                throw new MessageException ("Invalid Contestant Id!", inMessage);
            }
            if (inMessage.getContestantId() < 0 || inMessage.getContestantId() >= SimulPar.nContestants) {
                throw new MessageException ("Invalid Contestant Id!", inMessage);
            }
            if (inMessage.getContestantState() != ContestantStates.SEAT_AT_THE_BENCH) {
                throw new MessageException ("Invalid Contestant State!", inMessage);
            }

            break;
        case MessageType.SRPUTRP:
            if (inMessage.getCoachId() < 0 || inMessage.getCoachId() >= SimulPar.nCoaches) {
                throw new MessageException ("Invalid Contestant Id!", inMessage);
            }
            if (inMessage.getContestantId() < 0 || inMessage.getContestantId() >= SimulPar.nContestants) {
                throw new MessageException ("Invalid Contestant Id!", inMessage);
            }
            if (inMessage.getContestantState() != ContestantStates.STAND_IN_POSITION) {
                throw new MessageException ("Invalid Contestant State!", inMessage);
            }
            break;
        case MessageType.SRAMDONE:
            if (inMessage.getContestantId() < 0 || inMessage.getContestantId() >= SimulPar.nContestants) {
                throw new MessageException ("Invalid Contestant Id!", inMessage);
            }
            if (inMessage.getContestantState() != ContestantStates.DO_YOUR_BEST) {
                throw new MessageException ("Invalid Contestant State!", inMessage);
            }
            break;
        case MessageType.SRWTCHTRL:
            if (inMessage.getCoachId() < 0 || inMessage.getCoachId() >= SimulPar.nCoaches) {
                throw new MessageException ("Invalid Coach Id!", inMessage);
            }
            if (inMessage.getCoachState() != CoachStates.ASSEMBLE_TEAM) {
                throw new MessageException ("Invalid Coach State!", inMessage);
            }
            break;
        case MessageType.SHUT:
            break;
        default:
            throw new MessageException ("Invalid message type!", inMessage);
    }

    // message processing

    switch (inMessage.getMsgType()) {
        case MessageType.SRSTRTRL:       
            playground.startTrial();
            outMessage = new Message(MessageType.REPSTRTRL,
                                    1,
                                    ((PlaygroundClientProxy) Thread.currentThread()).getRefereeState()
                                    );
            break;
        case MessageType.SRWTFCONT:
            ((PlaygroundClientProxy) Thread.currentThread()).setCoachId(inMessage.getCoachId());
            ((PlaygroundClientProxy) Thread.currentThread()).setCoachState(inMessage.getCoachState());
            playground.waitForContestants();
            outMessage = new Message(MessageType.REPWTFCONT,
                                    ((PlaygroundClientProxy) Thread.currentThread()).getCoachId(),
                                    ((PlaygroundClientProxy) Thread.currentThread()).getCoachState()
                                    );
            break;
        case MessageType.SRASSTRLDSN:
            ((PlaygroundClientProxy) Thread.currentThread()).setRefereeState(inMessage.getRefereeState());
            decision = playground.assertTrialDecision();
            outMessage = new Message(MessageType.REPASSTRLDSN,
                                    1,
                                    decision
                                    );
            break;
        case MessageType.SRDCLGMWIN:
            ((PlaygroundClientProxy) Thread.currentThread()).setRefereeState(inMessage.getRefereeState());
            decision = playground.declareGameWinner();
            outMessage = new Message(MessageType.REPDCLGMWIN,
                                     1,
                                     decision
                                     );
            break;
        case MessageType.SRDCLMTWIN:
            ((PlaygroundClientProxy) Thread.currentThread()).setRefereeState(inMessage.getRefereeState());
            playground.declareMatchWinner();
            outMessage = new Message(MessageType.REPDCLMTWIN,
                                     1,
                                     ((PlaygroundClientProxy) Thread.currentThread()).getRefereeState()
                                     );
            break;
        case MessageType.SRGTRDY:
            ((PlaygroundClientProxy) Thread.currentThread()).setCoachId(inMessage.getCoachId());
            ((PlaygroundClientProxy) Thread.currentThread()).setContestantId(inMessage.getContestantId());
            ((PlaygroundClientProxy) Thread.currentThread()).setContestantState(inMessage.getContestantState());
            playground.getReady();
            outMessage = new Message(MessageType.REPGTRDY,
                                    ((PlaygroundClientProxy) Thread.currentThread()).getContestantId(),
                                    ((PlaygroundClientProxy) Thread.currentThread()).getCoachId(),
                                    ((PlaygroundClientProxy) Thread.currentThread()).getContestantState(),
                                    0
                                    );
            break;
        case MessageType.SRPUTRP:
            ((PlaygroundClientProxy) Thread.currentThread()).setCoachId(inMessage.getCoachId());
            ((PlaygroundClientProxy) Thread.currentThread()).setContestantId(inMessage.getContestantId());
            ((PlaygroundClientProxy) Thread.currentThread()).setContestantState(inMessage.getContestantState());
            int strenght = playground.pullTheRope(inMessage.getContestantStrength());
            outMessage = new Message(MessageType.REPPUTRP, 
                                    ((PlaygroundClientProxy) Thread.currentThread()).getContestantId(),
                                    ((PlaygroundClientProxy) Thread.currentThread()).getCoachId(),
                                    ((PlaygroundClientProxy) Thread.currentThread()).getContestantState(),
                                    strenght);
            break;
        case MessageType.SRAMDONE:
            ((PlaygroundClientProxy) Thread.currentThread()).setContestantId(inMessage.getContestantId());
            ((PlaygroundClientProxy) Thread.currentThread()).setContestantState(inMessage.getContestantState());
            playground.amDone();
            outMessage = new Message(MessageType.REPAMDONE,
                                    ((PlaygroundClientProxy) Thread.currentThread()).getContestantId(),
                                    ((PlaygroundClientProxy) Thread.currentThread()).getContestantState()
                                    );
            break;
        case MessageType.SRWTCHTRL:
            ((PlaygroundClientProxy) Thread.currentThread()).setCoachId(inMessage.getCoachId());
            ((PlaygroundClientProxy) Thread.currentThread()).setCoachState(inMessage.getCoachState());
            playground.watchTrial();
            outMessage = new Message(MessageType.REPWTCHTRL,
                                    ((PlaygroundClientProxy) Thread.currentThread()).getCoachId(),
                                    ((PlaygroundClientProxy) Thread.currentThread()).getCoachState());
            break;
        case MessageType.SHUT:
            playground.terminate();
            outMessage = new Message(MessageType.SHUTDONE);
            break;
    }
    return(outMessage);
  }

}
