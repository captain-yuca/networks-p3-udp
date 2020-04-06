import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class MyUDPServer implements UDPServer {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    MyUDPServer(String address, int portNumber) throws IOException{
        this.clientSocket = new Socket(address, portNumber);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public boolean sendMessage(String message) {
        Random rand = new Random();
        boolean sent=false;
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
    public String recieveMessage() throws IOException {
        String message =  in.readLine();
        System.out.println(message);
        return message;
    }

    @Override
    public boolean closeConnection() throws IOException {
        this.clientSocket.close();
        return true;
    }
}
