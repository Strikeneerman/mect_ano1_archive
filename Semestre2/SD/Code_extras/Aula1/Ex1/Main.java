import java.util.Scanner;
import src.*;

class Main{
    public static void main (String[] args){

        Scanner scan = new Scanner(System.in);
        
        System.out.println("User input: ");

        String input = scan.nextLine();
        
        scan.close();
        
        CheckPalindrome pal = new CheckPalindrome(input);
        
    }

}