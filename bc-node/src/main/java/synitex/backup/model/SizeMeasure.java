package synitex.backup.model;

public enum SizeMeasure {

    BYTES("B"),
    MEGA_BYTES("MB"),
    GIGA_BYTES("GB");

    private final String label;

    private SizeMeasure(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
