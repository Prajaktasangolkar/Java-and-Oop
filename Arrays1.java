import java.util.Arrays;

public class Arrays1 {
    public static void main(String[] args) {
        // Arrays
        //1.When u don't know what elements u want to store 
        int[] marks = new int[3];
        marks[0]=278;
        marks[1] = 34;
        marks[2] = 12;
        System.out.println(marks[0]); // this will print "0" instead of null or error

        boolean[] marks1 = new boolean[3];
        System.out.println(marks1[0]);// false

        //length
        System.out.println(marks1.length);
        
        //sort
        System.out.println(marks[0]);
        Arrays.sort(marks);
        System.out.println(marks[0]);

        //2. when u know what u want to store
        int[] m2 = { 1, 2, 3, 4, 6 };
        System.out.println(m2);
        String[] array = new String[] { "John", "Mary", "Bob" };
        String[] array1 =  { "John", "Mary", "Bob" };
        System.out.println(Arrays.toString(array));
        System.out.println(array1);
        int[] intArray = new int[] { 1, 2, 3, 4, 5 };
        System.out.println(Arrays.toString(intArray));

        /*
        another stuff
         *System.out.println(m2.getClass().getName());
        String a = "test";
        System.out.println(a.getClass().getName());
         */
        
         //////////2D arrys
        int[][] finalMarks={{97,98,95},{95,95,98}};
        System.out.println(finalMarks[1][2]);

        
    }
}
