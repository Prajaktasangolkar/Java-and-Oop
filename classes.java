public class classes {
    public static void printJava()
    {
        System.out.println("Hello Java");
    }
    public static void printName(String name)
    {
        System.out.println(name);
    }
    public static void printSum(int a,int b,String name)  //voide bez not return anything
    {
        int sum=a+b;
        System.out.println(sum);
        System.out.println(name);
    }
    public static void main(String[] args) {
        //methods
        printJava();
        printJava();
        printJava();
        printName("helllooo");
        printSum(4,6,"hii");
    }
}
