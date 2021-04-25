package technomint.app.arbanClap.Model;

public class SubsubCategoryModel {

    String name, imageUrl, id,categoryid,service_amount,service_desc,service_type;

    public SubsubCategoryModel() {
        this.name = name;
        this.imageUrl = imageUrl;
        this.id = id;
        this.categoryid = categoryid;
        this.service_amount = service_amount;
        this.service_desc = service_desc;
        this.service_type = service_type;
    }

    public String getService_amount() {
        return service_amount;
    }

    public void setService_amount(String service_amount) {
        this.service_amount = service_amount;
    }

    public String getService_desc() {
        return service_desc;
    }

    public void setService_desc(String service_desc) {
        this.service_desc = service_desc;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
