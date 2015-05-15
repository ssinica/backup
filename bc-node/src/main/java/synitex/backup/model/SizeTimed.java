package synitex.backup.model;

public class SizeTimed extends AbstractTimed {

    private long size;
    private SizeMeasure measure;

    public SizeTimed(long size, SizeMeasure measure) {
        this.size = size;
        this.measure = measure;
        this.setTime(System.currentTimeMillis());
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public SizeMeasure getMeasure() {
        return measure;
    }

    public void setMeasure(SizeMeasure measure) {
        this.measure = measure;
    }
}
