interface StopAndWaitServer extends Runnable {

    void receiveMessage();
    boolean closeConnection();
}