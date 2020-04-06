import java.net.*;
import java.io.*;
import java.util.Random;

public class MyUDP implements UDPClient{

    private DatagramSocket clientSocket;
    private String divider;

    public MyUDP(String address, int portNumber) throws IOException {
        this.clientSocket = new DatagramSocket(portNumber);
    }

    @Override
    public boolean sendMessage(String message, String address, int port) {
        byte[] sendData = message.getBytes();
        InetAddress IPAddress;
        // Get the IP address of the server
        try{
            IPAddress = InetAddress.getByName( address );

        } catch (UnknownHostException e) {
            return false;
        }
        Random rand = new Random();

        try{
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            boolean sent;
            if (rand.nextInt(5) == 0){
                sent = false;
            }
            else {
                if (rand.nextInt(10) == 0) {
                    this.clientSocket.send(packet);

                }
                this.clientSocket.send(packet);
                sent = true;
            }
            return sent;
        } catch(IOException e){

            return false;
        }

    }

    @Override
    public String receiveMessage() throws IOException {
        byte[] receiveData = new byte[ 1024 ];
        DatagramPacket received = new DatagramPacket(receiveData, receiveData.length);
        this.clientSocket.receive( received );
        return new String(received.getData(), received.getOffset(), received.getLength());
    }

    @Override
    public boolean closeConnection() throws IOException {
        this.clientSocket.close();
        return true;
    }
}
