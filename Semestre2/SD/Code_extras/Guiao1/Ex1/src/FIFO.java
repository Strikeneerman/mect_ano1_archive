import java.util.LinkedList;

public class FIFO{

    //Create fifo queue with linkedlist
    
    private LinkedList<Character> fifo = new LinkedList<Character>();

    //Function to add an element to the end of the queue

    public void addToQueue(char c){

        fifo.addLast(c);

    }

    //Function to remove an element from the beginning of the queue

    public char removeFromQueue(){

        return fifo.removeFirst();

    }

    //Function to check if the queue is empty 

    public boolean isEmptyQueue(){
        return fifo.isEmpty();
    }

    //Function to delete all elements from the queue

    public void emptyQueue(){
        fifo.clear();
    }   

}