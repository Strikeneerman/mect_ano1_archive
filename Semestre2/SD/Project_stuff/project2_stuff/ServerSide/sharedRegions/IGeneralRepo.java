package ServerSide.sharedRegions;

import ServerSide.main.*;
import ClientSide.entities.*;
import commInfra.*;
/**
 *  General Repository interface.
 * 
 * It provides the functionality to access the Referee Site
 */

public class IGeneralRepo {

    /**
     * Referneces GeneralRepo
     */    
    private MGeneralRepo generalRepo;

    /**
     * Constructor
     * 
     * @param generalRepo GeneralRepo
     */
    public IGeneralRepo(MGeneralRepo generalRepo) {
        this.generalRepo = generalRepo;
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
        case MessageType.SRPINITSIM:
          break;
        case MessageType.SRSETREFSTAT:
          if(inMessage.getRefereeState() < 0 || inMessage.getRefereeState() > RefereeStates.END_OF_A_MATCH) {
            throw new MessageException ("Invalid Referee State!" + inMessage.getRefereeState() + "", inMessage);
          }
          break;
        case MessageType.SRSETCOASTAT:
          if(inMessage.getCoachId() < 0 || inMessage.getCoachId() >= SimulPar.nCoaches) {
            throw new MessageException ("Invalid Coach Id!", inMessage);
          }
          if(inMessage.getCoachState() < CoachStates.WAIT_FOR_REFEREE_COMMAND  || inMessage.getCoachState() > CoachStates.WATCH_TRIAL) {
            throw new MessageException ("Invalid Coach State!", inMessage);
          }
          break;
        case MessageType.SRSETCONTSTAT:
          if(inMessage.getCoachId() < 0 || inMessage.getCoachId() >= SimulPar.nCoaches) {
            throw new MessageException ("Invalid Coach Id!", inMessage);
          }
          if(inMessage.getContestantId() < 0 || inMessage.getContestantId() >= SimulPar.nContestants) {
            throw new MessageException ("Invalid Contestant Id!", inMessage);
          }
          if(inMessage.getContestantState() < ContestantStates.SEAT_AT_THE_BENCH  || inMessage.getContestantState() > ContestantStates.DO_YOUR_BEST) {
            throw new MessageException ("Invalid Contestant State!", inMessage);
          }
          break;
        case MessageType.SRSETCONTSTRG:
          if(inMessage.getCoachId() < 0 || inMessage.getCoachId() >= SimulPar.nCoaches) {
            throw new MessageException ("Invalid Coach Id!", inMessage);
          }
          if(inMessage.getContestantId() < 0 || inMessage.getContestantId() >= SimulPar.nContestants) {
            throw new MessageException ("Invalid Contestant Id!", inMessage);
          }
          if(inMessage.getContestantStrength() < 0) {
            throw new MessageException ("Invalid Contestant Strength!", inMessage);
          }
          break;
        case MessageType.SRSETCONTPOS:
          if(inMessage.getCoachId() < 0 || inMessage.getCoachId() >= SimulPar.nCoaches) {
            throw new MessageException ("Invalid Coach Id!", inMessage);
          }
          break;
        case MessageType.SRSETTRLNUM:
          if(inMessage.getTrialNumber() < 0) {
            throw new MessageException ("Invalid Trial Number!", inMessage);
          }
          break;
        case MessageType.SRSETPOSROPE:
          break;
        case MessageType.SRSETGMNUM:
          if(inMessage.getGameNumber() < 0) {
            throw new MessageException ("Invalid Game Number!", inMessage);
          }
          break;
        case MessageType.SRSETGMWIN:
          if(inMessage.getGameWinner() < 0 || inMessage.getGameWinner() >= SimulPar.nCoaches) {
            throw new MessageException ("Invalid Match Winner!", inMessage);
          }
          break;
        case MessageType.SRBASUPDT:
            break;
        case MessageType.SRSETMTWIN:
          if(inMessage.getMatchWinner() < 0 || inMessage.getMatchWinner() >= SimulPar.nCoaches) {
            throw new MessageException ("Invalid Match Winner!", inMessage);
          }
          break;
        case MessageType.SRSETGMDRAW:
          break;
        case MessageType.SRSETMTDRAW:
          break;
        case MessageType.SHUT:
          break;
        default:
          throw new MessageException ("Message type not valid!", inMessage);
      }

      // process and reply
      switch (inMessage.getMsgType ()) {
        case MessageType.SRPINITSIM:
          break;
        case MessageType.SRSETREFSTAT:
          generalRepo.setRefereeState(inMessage.getRefereeState());
          outMessage = new Message(MessageType.REPSETREFSTAT);
          break;
        case MessageType.SRSETCOASTAT:
          generalRepo.setCoachState(inMessage.getCoachId(), inMessage.getCoachState());
          outMessage = new Message(MessageType.REPSETCOASTAT);
          break;
        case MessageType.SRSETCONTSTAT:
          generalRepo.setContestantState(inMessage.getCoachId(), inMessage.getContestantId(), inMessage.getContestantState());
          outMessage = new Message(MessageType.REPSETCONTSTAT);
          break;
        case MessageType.SRSETCONTSTRG:
          generalRepo.setContestantStrength(inMessage.getCoachId(), inMessage.getContestantId(), inMessage.getContestantStrength());
          outMessage = new Message(MessageType.REPSETCONTSTRG);
          break;
        case MessageType.SRSETCONTPOS:
          generalRepo.setContestantPosition(inMessage.getCoachId(), inMessage.getContestantPosition());
          outMessage = new Message(MessageType.REPSETCONTPOS);
          break;
        case MessageType.SRSETTRLNUM:
          generalRepo.setTrialNumber(inMessage.getTrialNumber());
          outMessage = new Message(MessageType.REPSETTRLNUM);
          break;
        case MessageType.SRSETPOSROPE:
          generalRepo.setRopePosition(inMessage.getRopePosition());
          outMessage = new Message(MessageType.REPSETPOSROPE);
          break;
        case MessageType.SRSETGMNUM:
          generalRepo.setGameNumber(inMessage.getGameNumber());
          outMessage = new Message(MessageType.REPSETGMNUM);
          break;
        case MessageType.SRSETGMWIN:
          generalRepo.setGameWinner(inMessage.getGameWinner(), inMessage.getKnockout());
          outMessage = new Message(MessageType.REPSETGMWIN);
          break;
        case MessageType.SRBASUPDT:
          generalRepo.basic_update();
          outMessage = new Message(MessageType.REPBASUPDT);
          break;
        case MessageType.SRSETMTWIN:
          generalRepo.setMatchWinner(inMessage.getMatchWinner());
          outMessage = new Message(MessageType.REPSETMTWIN);
          break;
        case MessageType.SRSETGMDRAW:
          generalRepo.setGameDraw();
          outMessage = new Message(MessageType.REPSETGMDRAW);
          break;
        case MessageType.SRSETMTDRAW:
          generalRepo.setMatchDraw();
          outMessage = new Message(MessageType.REPSETMTDRAW);
          break;
        case MessageType.SHUT:
          generalRepo.terminate();
          outMessage = new Message(MessageType.SHUTDONE);
          break;
        default:
          throw new MessageException ("Message type not valid!", inMessage);
      }



        


      return(outMessage);
    }

}
