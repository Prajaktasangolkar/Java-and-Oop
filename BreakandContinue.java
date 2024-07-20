public class BreakandContinue {
    public static void main(String[] args) {
        //break & continue
        int i=0;
        while(true)
        {
            if(i==3)
            {
                i+=1;
                continue;
            }
            System.out.println(i);
            i+=1;
            if(i>5)
            {
                break;
            }
           
        }
    }
    
}
