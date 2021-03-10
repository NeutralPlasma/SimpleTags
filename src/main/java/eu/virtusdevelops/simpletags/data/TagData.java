package eu.virtusdevelops.simpletags.data;

public class TagData {
    private String name;
    private String description;
    private String tag;
    private String permission;

    public TagData(String name, String description, String tag, String permission){
        this.description = description;
        this.name = name;
        this.tag = tag;
        this.permission = permission;
    }
//    Getters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getTag() {
        return tag;
    }
    public String getPermission() {
        return permission;
    }

//    Setters
    public void setDescription(String description) {
        this.description = description;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
}
