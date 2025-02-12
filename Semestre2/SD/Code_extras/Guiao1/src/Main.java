import java.util.Scanner;

class Main{
    public static void main (String[] args){

        Scanner scan = new Scanner(System.in);
        
        System.out.println("User input: ");

        String input = scan.nextLine();
        
        scan.close();
        
        CheckPalindrome pal = new CheckPalindrome();

        boolean palindrome = pal.Checking(input);

        if(palindrome){
            System.out.println(input+" is a palindrome");
        }
        else{
            System.out.println(input+" is not a palindrome");
        }
        
        
    }

}