package application.domain;
import java.util.UUID;

public class Interruption {
    private UUID threadPid;
    private InterruptionType type;

    public Interruption(UUID threadPid) {
        this.threadPid = threadPid;
        this.type = this.generateRandomInterruptionType();
    }

    public UUID getThreadPid() {
        return this.threadPid;
    }

    public InterruptionType getType() {
        return this.type;
    }

    private InterruptionType generateRandomInterruptionType() {
        return InterruptionType.values()[(int) (Math.random() * InterruptionType.values().length)];
    }
}
