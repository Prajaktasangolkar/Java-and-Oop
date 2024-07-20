public class logicaloperator {
    public static void main(String[] args) {
        //logical operator
        //&&
        int a=30;
        int b=40;
        if(a<50 && b<50)
        {
            System.out.println("both less then 50");
        }

        //||  or
        if(a<50 || b<40)
                     System.out.println("at least one ");

        boolean isAdult=false;
        if(!isAdult)
                 System.out.println("is Adult");
        else
                System.out.println("not adult");

    }
}
