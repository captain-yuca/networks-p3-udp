import java.io.IOException;
import java.util.Scanner;

public class SAWClientMain {
    final static String divider = "?!+";

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String message = null;
        MyStopAndWaitClient stopAndWaitClient = null;
        try{
           stopAndWaitClient = new MyStopAndWaitClient("127.0.0.1",3001, divider);
        } catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }

        do{
            System.out.println("Write your message!");
            message = sc.nextLine();
            System.out.println("[X] Message to send: " + message);
            stopAndWaitClient.sendMessage(message);

        }while(!message.equals("Exit"));
    }
}
