import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class MyStopAndWaitServer implements StopAndWaitServer {

    private String serverAddress;
    private UDPClient udpServer;
    private String divider;
    private Queue<Integer> messageIdQueue;
    MyStopAndWaitServer(String serverAddress, String divider, UDPClient udpServer){
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
        StringBuilder messageQueue =  new StringBuilder();
        messageQueue.append("[X] Message Id queue: [");
        for(Integer i : this.messageIdQueue){
            messageQueue.append(i);
            messageQueue.append(",");
        }
        messageQueue.append("]");
        System.out.println(messageQueue.toString());
    }

    @Override
    public void receiveMessage() {
        while (true){
            System.out.println("Open to receive message!");
            String frame = null;

            try{
                frame = this.udpServer.receiveMessage();
            } catch(IOException e) {
                System.out.println("this.udpServer.recieveMessage failed!");
                return;
            }
            if(!frame.equals("")){
                String[] splittedFrame = frame.split(this.divider);
                int messageId = Integer.parseInt(splittedFrame[0]);
                this.udpServer.sendMessage(Integer.toString(messageId),"127.0.0.1", 3001);
                String message = splittedFrame[1];

                System.out.println("[X] Received message: " + message);

                if(message.equals("exit")) break;
                else if(!isMessageIdPresent(messageId)){
                    messageIdQueue.add(messageId);
                    messageIdQueue.poll();
                    printMessageIdQueue();
                } else{
                    // Repeated Package
                    System.out.println("[X] Received a repeated packet!");
                }

            }

        }
    }

    @Override
    public boolean closeConnection() {
        try{
            this.udpServer.closeConnection();
        } catch(IOException e){

        }
        this.udpServer = null;
        return false;
    }
}
