package synitex.backup.model;

public class SizeTimed extends AbstractTimed {

    private long size;

    public SizeTimed(long size) {
        this.size = size;
        this.setTime(System.currentTimeMillis());
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
