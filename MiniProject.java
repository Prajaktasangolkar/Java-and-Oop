import java.util.Scanner;

public class MiniProject {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int myNumber=(int)(Math.random()*100);

       do{
        System.out.println("Guess my number");
        int userNumber=sc.nextInt();
        if(userNumber==myNumber)
        {
            System.out.println("WOOO...correct number");
            break;
        }
        else if(userNumber>myNumber)
        {
            System.out.println("Your no is too large");
        }
       else{
        System.out.println("Your no too small");
       }
       } while(myNumber>=0);
       System.out.println("My number was:");
       System.out.println(myNumber);
    }
}
