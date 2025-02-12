package src;

public class CheckPalindrome{
    String palindrome;

    public CheckPalindrome(String input){
        palindrome = input;
    }

    public void addPalin(){
        FIFO fif = new FIFO();
        Stack sta = new Stack();
        for(int i=0;i<palindrome.length();i++){
            fif.add(palindrome.charAt(i));
            sta.add(palindrome.charAt(i));
        }
    }
    
}
