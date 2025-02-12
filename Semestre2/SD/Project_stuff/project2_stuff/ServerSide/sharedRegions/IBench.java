package ServerSide.sharedRegions;

import ServerSide.main.*;
import ServerSide.entities.*;
import commInfra.*;
/**
 *  Bench interface.
 * 
 * It provides the functionality to access Bench.
 */

public class IBench {

    /** 
     *  References Bench
     * 
     */
    private MBench bench;

    /**
     *  Constructor
     * 
     *  @param bench Bench
     */
    public IBench(MBench bench) {
        this.bench = bench;
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
        Message outMessage = null;                           // response message

        // validation of incoming message
        switch (inMessage.getMsgType()) {
            case MessageType.SRSTDWN: //needs contestant id, coachId, strength
                if (inMessage.getContestantId() < 0 || inMessage.getContestantId() >= SimulPar.nContestants) {
                    throw new MessageException ("Invalid Contestant Id!", inMessage);
                }
                if (inMessage.getCoachId() < 0 || inMessage.getCoachId() >= SimulPar.nCoaches) {
                    throw new MessageException ("Invalid Coach Id!", inMessage);
                }
                if (inMessage.getContestantStrength() < 0) {
                    throw new MessageException ("Invalid Strength!", inMessage);
                }
                break;
            case MessageType.SRREVNOT:
                if (inMessage.getCoachId() < 0 || inMessage.getCoachId() >= SimulPar.nCoaches) {
                    throw new MessageException ("Invalid Coach Id!", inMessage);
                }
                break;
            case MessageType.SRCALLCONT:
                if (inMessage.getCoachId() < 0 || inMessage.getCoachId() >= SimulPar.nCoaches) {
                    throw new MessageException ("Invalid Coach Id!", inMessage);
                }
                break;
            case MessageType.SHUT:
                break;
            default:
                throw new MessageException ("Invalid message type!", inMessage);
        }

        // message processing
        switch (inMessage.getMsgType()) {
            case MessageType.SRSTDWN:
                ((BenchClientProxy) Thread.currentThread()).setContestantId(inMessage.getContestantId());
                ((BenchClientProxy) Thread.currentThread()).setCoachId(inMessage.getCoachId());
                int strenght = bench.sitDown(inMessage.getContestantStrength());   
                outMessage = new Message(MessageType.REPSTDWN, 
                                        ((BenchClientProxy) Thread.currentThread()).getContestantId(),
                                        ((BenchClientProxy) Thread.currentThread()).getCoachId(),
                                        ((BenchClientProxy) Thread.currentThread()).getContestantState(), 
                                        strenght);
                break;
            case MessageType.SRREVNOT:
                ((BenchClientProxy) Thread.currentThread()).setCoachId(inMessage.getCoachId());
                bench.reviewNotes();
                outMessage = new Message (MessageType.REPREVNOT, 
                                         ((BenchClientProxy) Thread.currentThread()).getCoachId(), 
                                         ((BenchClientProxy) Thread.currentThread()).getCoachState());
                break;
            case MessageType.SRCALLCONT:
                ((BenchClientProxy) Thread.currentThread()).setCoachId(inMessage.getCoachId());
                bench.callContestants();
                outMessage = new Message(MessageType.REPCALLCONT, 
                                        ((BenchClientProxy) Thread.currentThread()).getCoachId(), 
                                        ((BenchClientProxy) Thread.currentThread()).getCoachState());
                break;
            case MessageType.SHUT:
                bench.terminate();
                outMessage = new Message (MessageType.SHUTDONE);
                break;
        }

        return (outMessage);
    }
    
}
