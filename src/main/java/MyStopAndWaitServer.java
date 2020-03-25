import java.util.LinkedList;
import java.util.Queue;

public class MyStopAndWaitServer implements StopAndWaitServer {

    private String serverAddress;
    private UDPServer udpServer;
    private String divider;
    private Queue<Integer> messageIdQueue;
    MyStopAndWaitServer(String serverAddress, String divider, UDPServer udpServer){
        this.serverAddress = serverAddress;
        this.divider = divider;
        this.udpServer = udpServer;
        this.messageIdQueue = new LinkedList();
        for (int i=0; i<10;i++){
            this.messageIdQueue.add(-1);
        }
    }

    private boolean isMessageIdPresent(int id){
        for(Integer i : messageIdQueue){
            if(i==id) return true;
        }
        return false;
    }

    private void printMessageIdQueue(){

    }

    @Override
    public void receiveMessage() {
        while (true){
            String frame = this.udpServer.recieveMessage();
            if(!frame.equals("")){
                String[] splittedFrame = frame.split(this.divider);
                int messageId = Integer.parseInt(splittedFrame[0]);
                this.udpServer.sendMessage(Integer.toString(messageId));
                String message = splittedFrame[1];
                System.out.println(message);
                if(message.equals("exit")) break;
                else if(!isMessageIdPresent(messageId)){
                    messageIdQueue.add(messageId);
                    messageIdQueue.poll();
                    printMessageIdQueue();
                } else{
                    // Repeated Package
                }

            }

        }
    }

    @Override
    public boolean closeConnection() {
        this.udpServer.closeConnection();
        this.udpServer = null;
        return false;
    }
}
