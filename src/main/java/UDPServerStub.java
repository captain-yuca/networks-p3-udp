class UDPServerStub implements UDPServer {
    private String divider;
    private int currentNum;
    public UDPServerStub(String divider){
        this.divider = divider;
        this.currentNum = 0;
    }
    @Override
    public String recieveMessage(){
        if (currentNum >= 5){
            return this.currentNum + this.divider + "exit";
        }
        String payload = this.currentNum + this.divider + "Test Message";
        this.currentNum++;
        return payload;
    }
    @Override
    public void sendMessage(String data){
        return;
    }
    @Override
    public boolean closeConnection(){
        return true;
    }
}