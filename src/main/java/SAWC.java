import java.io.IOException;

public class SAWC implements StopAndWaitClient {
    UDP udpClient;
    int index;
    int timeOut;
    String divider;

    public SAWC(String address, int portNumber) throws IOException {
        this.udpClient = new UDP(address, portNumber);
        this.index = 0;
        this.timeOut = 3;
    }

    @Override
    public boolean sendMessage(String data) {
        boolean replied = false;
        String frame = this.index + this.divider + data;
        while (!replied) {
            this.udpClient.sendMessage(frame);

        }

        return false;
    }
}
