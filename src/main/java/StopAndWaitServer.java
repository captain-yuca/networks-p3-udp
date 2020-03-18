interface StopAndWaitServer extends Runnable {

    void recieveMessage();
    boolean closeConnection();
}