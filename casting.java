public class casting {
    public static void main(String[] args) {
        // casting
        // implicite and explicite

        // 1. Implicite casting as double have 8 byte so it will take 4 byte of int
        double price = 100.00;
        double finalPrice = price + 18;
        System.out.println(finalPrice);

        // 2. This is explicit casting where int =4 byte and and when we add double =8
        // byte overflow happen this is explicite casting
        int p = 100;
        // int fp=p+18.0; // java not allow
        int fb = p + (int) 18.18; // after 18....all values will remove
        System.out.println(fb);

        //we can't convert string to integer
        //only for number type as it possible
        int m=10;
        int fbb=m+(int)17.2F;
         System.out.println(fbb);
         int m1=10;
        int fbb1=m+(int)178990000005550L;
         System.out.println(fbb1);

    }
}
