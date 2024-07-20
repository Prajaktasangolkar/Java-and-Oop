public class String1 {
    public static void main(String[] args) {
        //strings--> immutable
        //concatenate
        String name1= "Aman";
        String name2= "Akku";
        String name3= name1 +" and "+ name2;
        System.out.println(name3);

        //charAt
        String n1="Aman";
        System.out.println(n1.charAt(0));
        System.out.println(n1.length());
        System.out.println(n1.replace('a','l'));
        System.out.println(n1);  //can not chnage in original one
        String n3=n1.replace('n', 'o');
        System.out.println(n3);
        //substring
        String name="Aman and Akku";
        System.out.println(name.substring(3,8));



    }
}
