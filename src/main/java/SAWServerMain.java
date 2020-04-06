import java.io.IOException;

public class SAWServerMain {

    public static void main(String[] args){
        final String serverAddress = "127.0.0.1";
        final String regexDivider = "\\?\\!\\+";
        final String divider = "?!+";


        UDPServer udpServer = null;
        try{
          udpServer  = new MyUDPServer(serverAddress, 3000);
        } catch (IOException e){
            System.out.println("Error Happened!");
        }
        StopAndWaitServer sAWServer = new MyStopAndWaitServer(serverAddress,
                regexDivider,
                udpServer);
        sAWServer.receiveMessage();
        System.out.println("Done!");
    }
}
