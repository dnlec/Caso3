enum Type {
    START_CLIENT,
    END_CLIENT,
    EMAIL,
    END_PROGRAM
}

public class Message {
    
    private int id;
    private boolean flag;
    private Type type;
    private int quarantineTime;
    
    public Message(int id, boolean flag, Type type) {
        this.id = id;
        this.flag = flag;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public boolean getFlag() {
        return this.flag;
    }

    public Type getType() {
        return this.type;
    }

    public int getQuarantineTime() {
        return this.quarantineTime;
    }
    
    public void setQuarantineTime(int time) {
        this.quarantineTime = time;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.id);
    }
}
