import java.io.IOException;
import java.util.concurrent.*;


class Waiter implements Callable<String> {

    UDPClient client;
    public Waiter(UDPClient client){
        this.client = client;
    }


    @Override
    public String call() throws Exception {
        return this.client.receiveMessage();
    }
}


public class MyStopAndWaitClient implements StopAndWaitClient {
    UDPClient myUdpClientClient;
    int index;
    int timeOut;
    int timeOutLimit;
    String divider;

    public MyStopAndWaitClient(String address, int portNumber, String divider) throws IOException {
        this.myUdpClientClient = new MyUDP(address, portNumber);
        this.index = 0;
        this.timeOut = 1;
        this.timeOutLimit = 20;
        this.divider = divider;
    }

    @Override
    public boolean sendMessage(String data) {
        boolean replied = false;
        String frame = this.index + this.divider + data;

        while (!replied) {
            this.myUdpClientClient.sendMessage(frame, "127.0.0.1", 3000);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<String> future = executor.submit(new Waiter(this.myUdpClientClient));
            String ack = null;
            try{
                ack = future.get(1, TimeUnit.SECONDS);
            }
            catch (TimeoutException e){
                future.cancel(true);
                this.timeOutLimit--;
                System.out.println("[X] Timeout sending again...");
                if (this.timeOutLimit == 0){
                    this.timeOutLimit = 20;
                    return false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                executor.shutdownNow();
            }

            if (ack != null && ack.equals(Integer.toString(this.index))){
                System.out.println("[X] ACK Received");
                replied = true;
                this.index++;
            }
        }

        return true;
    }

    public void closeConnection() throws IOException {
        this.myUdpClientClient.closeConnection();
    }
}
