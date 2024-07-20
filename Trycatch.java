public class Trycatch {
    public static void main(String[] args) {
        // Try-catch in EXCEPTION HANDLING

        // Errors can't be handled, but exceptions can be handled

        int[] marks = {97, 98, 95};
        try {
            // This will throw an ArrayIndexOutOfBoundsException
            System.out.println(marks[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Handle the specific exception
            System.out.println("Array index out of bounds! Please check the array index.");
            // Optionally, print the stack trace or exception message
            e.printStackTrace();
        } catch (Exception e) {
            // Handle any other exceptions that may occur
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

        // This line will still execute even if an exception occurs
        System.out.println("The name is Aman");
    }
}
