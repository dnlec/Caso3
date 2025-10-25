public class DeliverySignal {
    private boolean isEmpty = true;

    public synchronized boolean getIsEmpty() {
        return this.isEmpty;
    }

    public synchronized void modIsEmpty (boolean value) {
        this.isEmpty = value;
    }
    
}
