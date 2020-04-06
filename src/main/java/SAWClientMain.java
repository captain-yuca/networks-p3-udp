import java.io.IOException;
import java.util.Scanner;

public class SAWClientMain {
    final static String divider = "?!+";

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String message = null;
        MyStopAndWaitClient stopAndWaitClient = null;
        try{
           stopAndWaitClient = new MyStopAndWaitClient("127.0.0.1",3000, divider);
        } catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }

        do{
            message = sc.next();
            System.out.println(message);
            stopAndWaitClient.sendMessage(message);

        }while(message.equals("Exit"));
    }
}
