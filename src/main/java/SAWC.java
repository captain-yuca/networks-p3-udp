import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;


class Waiter implements Callable<String> {

    UDP client;
    public Waiter(UDP client){
        this.client = client;
    }


    @Override
    public String call() throws Exception {
        return this.client.receiveMessage();
    }
}


public class SAWC implements StopAndWaitClient {
    UDP udpClient;
    int index;
    int timeOut;
    int timeOutLimit;
    String divider;

    public SAWC(String address, int portNumber) throws IOException {
        this.udpClient = new UDP(address, portNumber);
        this.index = 0;
        this.timeOut = 1;
        this.timeOutLimit = 20;
    }

    @Override
    public boolean sendMessage(String data) {
        boolean replied = false;
        String frame = this.index + this.divider + data;

        while (!replied) {
            this.udpClient.sendMessage(frame);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<String> future = executor.submit(new Waiter(this.udpClient));
            String ack = null;
            try{
                ack = future.get(1, TimeUnit.SECONDS);
            }
            catch (TimeoutException e){
                future.cancel(true);
                this.timeOutLimit--;
                System.out.println("Timeout sending again...");
                if (this.timeOutLimit == 0){
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
                System.out.println("ACK Received");
                replied = true;
                this.index++;
            }
        }

        return true;
    }

    public void closeConnection() throws IOException {
        this.udpClient.closeConnection();
    }
}
