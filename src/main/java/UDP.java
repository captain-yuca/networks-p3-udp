import java.net.*;
import java.io.*;
import java.util.Random;

public class UDP implements UDPClient{

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public UDP(String address, int portNumber) throws IOException {
        this.clientSocket = new Socket(address, portNumber);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public boolean sendMessage(String message) {
        Random rand = new Random();
        boolean sent;
        if (rand.nextInt(5) == 0){
            sent = false;
        }
        else {
            if (rand.nextInt(10) == 0) {
                out.println(message);
            }
            out.println(message);
            sent = true;
        }
        return sent;
    }

    @Override
    public String receiveMessage() throws IOException {
        return in.readLine();
    }

    @Override
    public boolean closeConnection() throws IOException {
        this.clientSocket.close();
        return true;
    }
}
