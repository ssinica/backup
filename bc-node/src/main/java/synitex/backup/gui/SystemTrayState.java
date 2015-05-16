package synitex.backup.gui;

public enum SystemTrayState {

    NORMAL("res/bc-node__normal.gif"),
    BACKUP_IN_PROGRESS("res/bc-node__inprogress.gif");

    private String imageUrl;

    SystemTrayState(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
