package synitex.backup.model;

public abstract class AbstractTimed {

    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
