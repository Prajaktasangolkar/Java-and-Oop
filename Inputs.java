import java.util.Scanner;

public class Inputs {
    public static void main(String[] args) {
        //How to take INPUT?
        Scanner sc=new Scanner(System.in);
        System.out.println("Input Your age: ");
        int age=sc.nextInt();
        System.out.println(age);
        System.out.println("Input Your age in float: ");
        float a1=sc.nextFloat();
        System.out.println(a1);

        //string
        System.out.println("Input Your Name: ");
        String name=sc.next();
        System.out.println(name); 
        
        System.out.println("Input Your Full Name: ");
        String name1=sc.nextLine();
        System.out.println(name1); 

     }
}
