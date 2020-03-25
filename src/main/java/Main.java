
public class Main {

    public static void main(String[] args){
        final String serverAddress = "127.0.0.1";
        final String regexDivider = "\\?\\!\\+";
        final String divider = "?!+";


        UDPServer udpServer = new UDPServerStub(divider);
        StopAndWaitServer sAWServer = new MyStopAndWaitServer(serverAddress,
                regexDivider,
                udpServer);
        sAWServer.receiveMessage();
        System.out.println("Done!");
    }
}
