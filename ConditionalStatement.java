import java.util.Scanner;

public class ConditionalStatement {
    public static void main(String[] args) {
        //IF -ELSE
        Scanner sc=new Scanner(System.in);

        int cash=sc.nextInt();
        if(cash<10)
        {
            System.out.println("Can't buy anything");
            System.out.println("get more cash");
            //cruly bracket when u want 2 printed output
        }
        else if (cash>10 && cash<50)
        {
            System.out.println("can get 1 thing");
        }
        else{
            System.out.println("can get both");
        }

        //SWITCH
        int day=1;
        switch(day)
        {
            case 1:
              System.out.println("momday");
              break;
            case 2:
              System.out.println("Tuesday");
              break;
            default:
              System.out.println("wed-sun");
        }
    }
}
