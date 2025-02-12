public class CheckPalindrome{
    private FIFO fifo = new FIFO();
    private Stack stack = new Stack();
   

    public Boolean Checking(String input){
        for(int i = 0; i < input.length(); i++){
            char c = input.charAt(i);
            c = Character.toLowerCase(c);
            fifo.addToQueue(c);
            stack.pushToStack(c);
        }

        while (!fifo.isEmptyQueue() && !stack.isEmptyStack()){


            if(!(fifo.removeFromQueue()==stack.popFromStack())){
                fifo.emptyQueue();
                stack.emptyStack();
                return false;
            }

        }

        return true;

    }
    
}
