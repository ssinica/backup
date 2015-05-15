package synitex.backup.model;

import org.springframework.core.style.ToStringCreator;

public class Destination {

    private String id;
    private String name;
    private DestinationType type;
    private String key;
    private String user;
    private String host;
    private String dir;
    private String partialDir;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DestinationType getType() {
        return type;
    }

    public void setType(DestinationType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getPartialDir() {
        return partialDir;
    }

    public void setPartialDir(String partialDir) {
        this.partialDir = partialDir;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).append("name", name).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Destination) {
            Destination casted = (Destination) obj;
            return id.equals(casted.getId());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
