package fourthtry;



public class Message {
    enum Type {
        StartClient,
        EndClient,
        Email,
        EndProgram
    }
    private int id;
    private boolean flag;
    private Type type;
    

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
    
    @Override
    public String toString() {
        return String.valueOf(this.id);
    }

    
}
