interface UDPServer {

    /**
     * Listens and recieves any message that arrives in the channel.
     *
     *
     * @return      The bytes recieved decoded as a String
     */
    String recieveMessage();

    /**
     * Sends through the channel a response that is meant as a reply from the recieved message.
     *
     * This method can randomly discard packets with a 20% packet loss rate,
     * and can send a duplicated packet with probability 10%
     *
     * @param  data the data to be sent through the channel
     */
    void sendMessage(String data);

    /**
     * Shuts down the server
     *
     * @return boolean denoting true if the connection was closed
     */
    boolean closeConnection()
}