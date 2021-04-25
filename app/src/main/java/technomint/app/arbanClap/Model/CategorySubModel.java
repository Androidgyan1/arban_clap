package technomint.app.arbanClap.Model;

public class CategorySubModel {

    String name, imageUrl, id, categoryid;

    public CategorySubModel() {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.categoryid  = categoryid;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
