package application.domain;
import java.util.UUID;

public class Thread {
    private UUID pid;
    private ThreadState state;

    public Thread() {
        this.pid = UUID.randomUUID();
        this.state = ThreadState.INITIALIZING;
    }

    public UUID getPid() {
        return this.pid;
    }

    public void run() {
        this.state = ThreadState.RUNNING;
    }
    
    public void block() {
        this.state = ThreadState.BLOCKED;
    }

    public void unBlock() {
        this.state = ThreadState.RUNNING;
    }

    public ThreadState getState() {
        return this.state;
    }
}
