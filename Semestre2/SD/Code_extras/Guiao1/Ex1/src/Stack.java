import java.util.LinkedList;

public class Stack {
    
    //Create stack with linkedlist
    
    private LinkedList<Character>stack = new LinkedList<Character>();

    //Function to add an element to the stack

    public void pushToStack(char c){

        stack.push(c);;

    }

    //Function to remove an element from the stack

    public char popFromStack(){

        return stack.pop();

    }

    //Function to check if the stack is empty 

    public boolean isEmptyStack(){
        return stack.isEmpty();
    }

    //Function to delete all elements from the stack

    public void emptyStack(){
        stack.clear();
    }   

}
