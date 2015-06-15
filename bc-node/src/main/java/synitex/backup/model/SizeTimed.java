package synitex.backup.model;

public class SizeTimed extends AbstractTimed {

    public static SizeTimed UNDEFINED = new SizeTimed(-1l, -1l);

    private long size;

    public SizeTimed(long size) {
        this.size = size;
        setTime(System.currentTimeMillis());
    }

    public SizeTimed(long size, long time) {
        this.size = size;
        setTime(time);
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
