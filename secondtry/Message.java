package secondtry;

public class Message {
    private int id;
    private boolean flag;

    public Message(int id, boolean flag) {
        this.id = id;
        this.flag = flag;
    }

    public int getId() {
        return this.id;
    }

    public boolean getFlag() {
        return this.flag;
    }

}
